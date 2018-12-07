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

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Reference;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.drishti.DrishtiConstants;
import org.openmrs.module.drishti.Item;
import org.openmrs.module.drishti.api.DrishtiService;
import org.openmrs.module.drishti.api.dao.DrishtiDao;

public class DrishtiServiceImpl extends BaseOpenmrsService implements DrishtiService {
	
	DrishtiDao dao;

	UserService userService;


	IGenericClient client = FhirContext.forDstu3().newRestfulGenericClient(DrishtiConstants.FHIR_BASE);

	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(DrishtiDao dao) {
		this.dao = dao;
	}

	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Item getItemByUuid(String uuid) throws APIException {
		return dao.getItemByUuid(uuid);
	}

	@Override
	public Item saveItem(Item item) throws APIException {
		if (item.getOwner() == null) {
			item.setOwner(userService.getUser(1));
		}

		return dao.saveItem(item);
	}

	@Override
    public Bundle getBundle(org.openmrs.Patient patient) {
        Bundle bundle = client
				.search()
                .forResource(Bundle.class)
				.where(Bundle.IDENTIFIER.exactly().systemAndIdentifier(DrishtiConstants.URN_SYSTEM, patient.getUuid()))
                //.where(Observation.SUBJECT.hasId(patient.getId()))
				.returnBundle(org.hl7.fhir.dstu3.model.Bundle.class)
				.execute();
        return bundle;
	}

	@Override
    public Boolean saveCareplan(CarePlan carePlan, Patient patient) {
        carePlan.setSubject(new Reference(patient));
        MethodOutcome outcome = client.create()
                .resource(carePlan)
                .prettyPrint()
                .encodedJson()
                .execute();
        return outcome.getCreated();
	}
}
