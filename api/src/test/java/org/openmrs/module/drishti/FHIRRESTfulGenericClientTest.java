package org.openmrs.module.drishti;

import org.hl7.fhir.dstu3.model.Bundle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;

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
	public void saveCareplanClient() {
		Bundle bundle = new Bundle();
		Bundle.BundleEntryComponent bundleEntryComponent = new Bundle.BundleEntryComponent();
		Patient patient = new Patient();
		patient.setUuid("44018401-fef3-4bd7-8cb9-6391491a470a");

    }
}
