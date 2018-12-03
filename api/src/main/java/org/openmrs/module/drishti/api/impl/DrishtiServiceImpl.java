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

import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.ResourceType;
import org.openmrs.api.APIException;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.drishti.DrishtiActivator;
import org.openmrs.module.drishti.Item;
import org.openmrs.module.drishti.api.DrishtiService;
import org.openmrs.module.drishti.api.dao.DrishtiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrishtiServiceImpl extends BaseOpenmrsService implements DrishtiService {
	
	DrishtiDao dao;
	
	UserService userService;

	@Autowired
	AdministrationService administrationService;


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
	public Bundle getBundle(Patient patient, ResourceType resourceType){
		String serverBase = administrationService.getGlobalProperty("omhOnFhirAPIBase", "/") + "/ProcessBundle";
		IGenericClient client = DrishtiActivator.getCtx().newRestfulGenericClient(serverBase);

		// TODO: To change this (placeholder)
		Bundle results = client
				.search()
				.forResource(Patient.class)
				.where(Patient.FAMILY.matches().value("duck"))
				.returnBundle(org.hl7.fhir.dstu3.model.Bundle.class)
				.execute();
		return results;
	}

	@Override
	public Boolean saveBundle(Bundle bundle, Resource resource){
		return true;
	}
}
