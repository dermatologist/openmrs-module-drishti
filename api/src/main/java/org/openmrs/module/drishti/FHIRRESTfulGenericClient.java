package org.openmrs.module.drishti;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Reference;

public class FHIRRESTfulGenericClient {

	private static final FhirContext ctx = FhirContext.forDstu3();

	public Bundle getBundleClient(org.openmrs.Patient patient) {
		IGenericClient client = ctx.newRestfulGenericClient(DrishtiConstants.FHIR_BASE);
		Bundle bundle = client.search().forResource(Bundle.class)
				.where(Bundle.IDENTIFIER.exactly().systemAndIdentifier(DrishtiConstants.URN_SYSTEM, patient.getUuid()))
				//.where(Observation.SUBJECT.hasId(patient.getId()))
				.returnBundle(org.hl7.fhir.dstu3.model.Bundle.class).execute();
		return bundle;
	}

	public Boolean saveCareplanClient(CarePlan carePlan, Patient patient) {
		IGenericClient client = ctx.newRestfulGenericClient(DrishtiConstants.FHIR_BASE);
		carePlan.setSubject(new Reference(patient));
		MethodOutcome outcome = client.create().resource(carePlan).prettyPrint().encodedJson().execute();
		return outcome.getCreated();
	}
}
