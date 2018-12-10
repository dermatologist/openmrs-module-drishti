package org.openmrs.module.drishti.page.controller;

import org.openmrs.*;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;

import java.util.Random;

public class DrishtiPageController {

    public void controller() {
        Patient patient;
        Person person;
        PatientService patientService = Context.getPatientService();

        person = Context.getAuthenticatedUser().getPerson();
        User creator = person.getCreator();
        patient = Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
        if (patient == null) {
            //patient= Context.getPatientService().getPatient(7); //For Testing
            //Create a new patient
            // https://wiki.openmrs.org/questions/79660918/creating-a-patient-from-a-module
            // OpenMRS ID set as Not required in database
            patient = new Patient(person);
            PatientIdentifierType PIT = patientService.getPatientIdentifierTypeByName("Old Identification Number");
            PatientIdentifier pI = new PatientIdentifier();
            pI.setCreator(creator);
            pI.setIdentifierType(PIT);
            pI.setLocation(Context.getLocationService().getDefaultLocation());
            //pI.setIdentifier(person.getUuid());
            Random rand = new Random();
            int randomNum = rand.nextInt((99999 - 80000) + 1) + 800000;
            pI.setIdentifier(Integer.toString(randomNum));
            patient.addIdentifier(pI);
            patientService.savePatient(patient);
        }
    }
}
