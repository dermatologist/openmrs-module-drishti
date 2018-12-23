package org.openmrs.module.drishti;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Reference;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;

import java.util.List;

public class FHIRRESTfulGenericClient {

	private Log log = LogFactory.getLog(this.getClass());

	private static final FhirContext ctx = FhirContext.forDstu3();


	public Bundle getBundleClient(org.openmrs.Patient patient) {
		IGenericClient client = ctx.newRestfulGenericClient(DrishtiConstants.FHIR_BASE);

		UserService userService = Context.getUserService();
		List<User> users = userService.getUsersByName(patient.getGivenName(), patient.getFamilyName(), false);
		String uuid = users.get(0).getUuid();

		Bundle bundle = client.search().forResource(Bundle.class)
				.where(Bundle.IDENTIFIER.exactly().systemAndIdentifier(DrishtiConstants.URN_SYSTEM, uuid))
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
