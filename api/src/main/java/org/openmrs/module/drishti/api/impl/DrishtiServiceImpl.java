/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.drishti.api.impl;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.drishti.FHIRRESTfulGenericClient;
import org.openmrs.module.drishti.api.DrishtiService;

public class DrishtiServiceImpl extends BaseOpenmrsService implements DrishtiService {


	FHIRRESTfulGenericClient fhirresTfulGenericClient = new FHIRRESTfulGenericClient();

	/**
	 * Injected in moduleApplicationContext.xml
	 */

	@Override
    public Bundle getBundle(org.openmrs.Patient patient) {
		return fhirresTfulGenericClient.getBundleClient(patient);
	}

	@Override
    public Boolean saveCareplan(CarePlan carePlan, Patient patient) {
		return fhirresTfulGenericClient.saveCareplanClient(carePlan, patient);
	}
}
