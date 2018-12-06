package org.openmrs.module.drishti.fragment.controller;

import org.apache.commons.beanutils.PropertyUtils;
import org.hl7.fhir.dstu3.model.*;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.drishti.api.DrishtiService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.math.BigDecimal;
import java.util.List;

public class HgraphFragmentController {
    public void controller(FragmentConfiguration config,
                           @SpringBean("patientService") PatientService patientService,
                           FragmentModel model) throws Exception {
        // unfortunately in OpenMRS 2.1 the coreapps patient page only gives us a patientId for this extension point
        // (not a patient) but I assume we'll fix this to pass patient, so I'll code defensively
        Patient patient;
        config.require("patient|patientId");
        Object pt = config.getAttribute("patient");
        if (pt == null)
            patient = patientService.getPatient((Integer) config.getAttribute("patientId"));
        else
            patient = (Patient) (pt instanceof Patient ? pt : PropertyUtils.getProperty(pt, "patient"));
        DrishtiService drishtiService = Context.getService(DrishtiService.class);

        Bundle bundle = drishtiService.getBundle(patient);

        BigDecimal steps = new BigDecimal(0);
        for (Bundle.BundleEntryComponent bundleEntryComponent : bundle.getEntry()) {
            Resource resource = bundleEntryComponent.getResource();
            if (resource instanceof Observation) {
                //
                List<Coding> codes = ((Observation) resource).getCode().getCoding();
                for (Coding code : codes) {
                    if (code.getCode().equals("55423-8")) {

                        List<Observation.ObservationComponentComponent> components = ((Observation) resource).getComponent();
                        for (Observation.ObservationComponentComponent component : components) {
                            Quantity quantity = component.getValueQuantity();
                            //steps = steps + quantity.getValue();
                            steps = steps.add(quantity.getValue());
                        }

                    }
                }
            }
        }

        model.addAttribute("patient", patient);
        model.addAttribute("steps", steps.intValue());
    }
}
