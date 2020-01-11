/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.drishti;

import org.hl7.fhir.dstu3.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.Patient;
import org.openmrs.api.UserService;
import org.openmrs.module.drishti.api.impl.DrishtiServiceImpl;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * This is a unit test, which verifies logic in DrishtiService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class DrishtiServiceTest {
	
	@InjectMocks
	DrishtiServiceImpl basicModuleService;

    @Mock
	UserService userService;
	
	@Before
	public void setupMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
    public void saveReturnBundle() {
        Patient patient = new Patient();
        patient.setUuid("2cb5a459-4eda-4d8e-a339-01804e7331a0");
        assertNotNull(basicModuleService);
        Bundle bundle = basicModuleService.getBundle(patient);
        assertNotNull(bundle);
        System.out.println(bundle.getId());

        int steps = 0;

        Bundle insideBundle = (Bundle) bundle.getEntryFirstRep().getResource();

        for (Bundle.BundleEntryComponent bundleEntryComponent : insideBundle.getEntry()) {
            Resource resource = bundleEntryComponent.getResource();
            if (resource instanceof Observation) {
                List<Coding> codes = ((Observation) resource).getCode().getCoding();
                for (Coding code : codes) {
                    if (code.getCode().equals("55423-8")) {

                        List<Observation.ObservationComponentComponent> components = ((Observation) resource).getComponent();
                        for (Observation.ObservationComponentComponent component : components) {
                            Quantity quantity = component.getValueQuantity();
                            //quantity.getValue() returns BigDecimal that is immutable
                            steps += quantity.getValue().intValue();
                        }

                    }
                }
            }
        }
        System.out.println(steps);
        assert (steps > 0);
    }
}
