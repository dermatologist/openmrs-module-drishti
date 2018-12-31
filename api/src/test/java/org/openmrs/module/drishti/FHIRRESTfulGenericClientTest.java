package org.openmrs.module.drishti;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;

import java.util.Date;
import java.util.List;

public class FHIRRESTfulGenericClientTest {

    FHIRRESTfulGenericClient fhirresTfulGenericClient;

    @Before
    public void setUp() {
		fhirresTfulGenericClient = new FHIRRESTfulGenericClient();
	}

    @After
    public void tearDown() {
    }
	
	@Test
	public void getBundleClient() {
		Patient patient = new Patient();
		patient.setUuid("44018401-fef3-4bd7-8cb9-6391491a470a");
		Bundle bundle = fhirresTfulGenericClient.getBundleClient(patient);
		System.out.println(bundle.getId());
	}

    @Test
    public void testFhirApi() {
        Patient patient = new Patient();
        patient.setUuid("d4970147-dff5-43e7-a7c8-a326f98874a6");
        FhirContext ctx = FhirContext.forDstu3();
        IGenericClient client = ctx.newRestfulGenericClient(DrishtiConstants.FHIR_BASE);


        Bundle bundle = client.search().forResource(Bundle.class)
                .where(Bundle.IDENTIFIER.exactly().systemAndIdentifier(DrishtiConstants.URN_SYSTEM, patient.getUuid()))
                //.where(Observation.SUBJECT.hasId(patient.getId()))
                .returnBundle(org.hl7.fhir.dstu3.model.Bundle.class).execute();
        int steps = 10;
        // Bundle insideBundle = (Bundle) bundle.getEntryFirstRep().getResource();

        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        for (Bundle.BundleEntryComponent bundleEntryComponentMain : bundle.getEntry()) {
            Bundle insideBundle = (Bundle) bundleEntryComponentMain.getResource();
            System.out.println(insideBundle.getMeta().getLastUpdated());
            System.out.println(yesterday);
            if (insideBundle != null && !yesterday.after(insideBundle.getMeta().getLastUpdated())) {
                for (Bundle.BundleEntryComponent bundleEntryComponent : insideBundle.getEntry()) {
                    Resource resource = bundleEntryComponent.getResource();

                    if (resource instanceof Observation) {

                        List<Coding> codes = ((Observation) resource).getCode().getCoding();
                        for (Coding code : codes) {
                            if (code.getCode().equals("55423-8")) {

                                List<Observation.ObservationComponentComponent> components = ((Observation) resource)
                                        .getComponent();
                                for (Observation.ObservationComponentComponent component : components) {
                                    Quantity quantity = component.getValueQuantity();
                                    //quantity.getValue() returns BigDecimal that is immutable
                                    steps += quantity.getValue().intValue();
                                }

                            }
                        }
                    }
                }
            }
        }
        System.out.println(steps);
    }

    @Test
	public void saveCareplanClient() {
		Bundle bundle = new Bundle();
		Bundle.BundleEntryComponent bundleEntryComponent = new Bundle.BundleEntryComponent();
		Patient patient = new Patient();
		patient.setUuid("44018401-fef3-4bd7-8cb9-6391491a470a");

    }
}
