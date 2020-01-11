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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Role;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class DrishtiActivator extends BaseModuleActivator {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	/*
	Note that FhirContext is an expensive object to create,
	so you should try to keep an instance around for the lifetime of your application.
	It is thread-safe so it can be passed as needed.
	Client instances, on the other hand, are very inexpensive to create
	so you can create a new one for each request if needed
	(although there is no requirement to do so, clients are reusable and thread-safe as well).
	 */
	//private static FhirContext ctx = null;
	
	/**
	 * @see #started()
	 */
	public void started() {
		log.info("Started Drishti");
	}
	
	/**
	 * @see #shutdown()
	 */
	public void shutdown() {
		log.info("Shutdown Drishti");
	}

	//    public static FhirContext getCtx() {
	//        return ctx;
	//    }

	/**
	 * @see BaseModuleActivator#contextRefreshed()
	 */
	@Override
	public void contextRefreshed() {
		super.contextRefreshed(); //To change body of overridden methods use File | Settings | File Templates.
		ensureRolesAreCreated();
		// Start the fhir context when the context is refreshed.
		//startFhirContext();
	}

	/**
	 * From patient portal toolkit
	 */

	private void ensureRolesAreCreated() {
		UserService userService = Context.getUserService();
		Role patientportalbasicrole = userService.getRole(DrishtiConfig.APP_VIEW_PRIVILEGE_ROLE);
		if (patientportalbasicrole == null) {
			patientportalbasicrole = new Role();
			patientportalbasicrole.setRole(DrishtiConfig.APP_VIEW_PRIVILEGE_ROLE);
			patientportalbasicrole.setDescription(DrishtiConfig.APP_VIEW_PRIVILEGE_ROLE_DESCRIPTION);
			patientportalbasicrole.addPrivilege(userService.getPrivilege(DrishtiConfig.APP_VIEW_PRIVILEGE));
			patientportalbasicrole.addPrivilege(userService.getPrivilege(DrishtiConfig.VIEW_PROVIDER_PRIVILEGE));
			patientportalbasicrole.addPrivilege(userService.getPrivilege(DrishtiConfig.VIEW_PATIENT_PRIVILEGE));
			patientportalbasicrole.addPrivilege(userService.getPrivilege(DrishtiConfig.GET_PATIENT_PRIVILEGE));
			patientportalbasicrole.addPrivilege(userService.getPrivilege(DrishtiConfig.ADD_PATIENT_PRIVILEGE));
			patientportalbasicrole.addPrivilege(userService.getPrivilege(DrishtiConfig.EDIT_PATIENT_PRIVILEGE));
			
			userService.saveRole(patientportalbasicrole);
		}
		userService.saveRole(patientportalbasicrole);
	}

	//    private void startFhirContext() {
	//        log.info("Starting FhirContext");
	//        ctx = FhirContext.forDstu3();
	//    }
}
