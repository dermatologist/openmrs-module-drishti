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

import org.springframework.stereotype.Component;

/**
 * Contains module's config.
 */
@Component("drishti.DrishtiConfig")
public class DrishtiConfig {
	
	public final static String MODULE_PRIVILEGE = "Drishti Privilege";

    public static final String APP_VIEW_PRIVILEGE_ROLE = "patientportaltoolkit.view";

    public static final String APP_VIEW_PRIVILEGE_ROLE_DESCRIPTION = "Role to have access to the Patient Portal Toolkit Module";

    public static final String APP_VIEW_PRIVILEGE = "App: patientportaltoolkit";

    public static final String VIEW_PROVIDER_PRIVILEGE = "View Providers";

    public static final String VIEW_PATIENT_PRIVILEGE = "View Patients";

}
