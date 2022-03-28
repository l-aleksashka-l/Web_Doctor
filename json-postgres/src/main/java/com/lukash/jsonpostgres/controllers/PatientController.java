/*
package com.lukash.jsonpostgres.controllers;


import com.lukash.jsonpostgres.entities.Patient;
import com.lukash.jsonpostgres.entities.PersonWithPlanets;
import com.lukash.jsonpostgres.form.PatientForm;
import com.lukash.jsonpostgres.repositories.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


@Controller
public class PatientController {

    private final static Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientRepository patientRepository;

    private static List<Patient> planets = new ArrayList<Patient>();

    @Autowired
    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Value("${error.message}")
    private String errorMessage;

    @Value("${error.nothingPlanetMessage}")
    private String errorPlanetExist;

    @RequestMapping(value = {"/{login}/patient"}, method = RequestMethod.POST)
    public String savePlanet(Model model, //
                             @ModelAttribute("planetForm") PatientForm patientForm) {

        String name = patientForm.getName();
        planets = null;
        planets = (List<Patient>) patientRepository.findAll();
        if (name != null && name.length() > 0 //
        ) {

            for (Patient planet : planets) {
                if (name.equals(planet.getName())) {
                    return "redirect:/index";

                }
            }
            model.addAttribute("errorPlanetExist", errorPlanetExist);
            return "redirect:/patient";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "redirect:/patient";
    }

    @RequestMapping(value = { "/{login}/patient" }, method = RequestMethod.GET)
    public String showAddPlanetPage(Model model) {
        PatientForm patientForm = new PatientForm();
        model.addAttribute("patientForm", patientForm);

        return "patient";
    }







}
*/
