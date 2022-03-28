package com.lukash.jsonpostgres.controllers;


import com.lukash.jsonpostgres.entities.Doctor;
import com.lukash.jsonpostgres.entities.PersonWithPlanets;
import com.lukash.jsonpostgres.entities.Patient;
import com.lukash.jsonpostgres.form.PersonWithPlanetForm;
import com.lukash.jsonpostgres.repositories.DoctorRepository;
import com.lukash.jsonpostgres.repositories.PersonWithPlanetsRepository;
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
public class PersonWithPlanetsController {

    private final static Logger logger = LoggerFactory.getLogger(PersonWithPlanetsController.class);

    private final PersonWithPlanetsRepository personWithPlanetsRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository lordRepository;

    @Autowired
    public PersonWithPlanetsController(PersonWithPlanetsRepository personWithPlanetsRepository, PatientRepository patientRepository, DoctorRepository lordRepository) {
        this.personWithPlanetsRepository = personWithPlanetsRepository;
        this.patientRepository = patientRepository;
        this.lordRepository = lordRepository;
    }

    private static List<PersonWithPlanets> persons = new ArrayList<PersonWithPlanets>();
    private static List<Patient> planets = new ArrayList<Patient>();
    private static List<Doctor> lords = new ArrayList<Doctor>();
    private static List<PersonWithPlanets> tempPersons = new ArrayList<>();


    @Value("${error.message}")
    private String errorMessage;

    @Value("${error.nothingPlanetMessage}")
    private String errorNothingPlanetMessage;

    @Value("${error.planetExist}")
    private String errorPlanetExist;

    @Value("${error.nothingLordMessage}")
    private String errorNothingLordMessage;

    @Value("${error.badMessage}")
    private String errorBadMessage;



    @RequestMapping(value = {"/transList"}, method = RequestMethod.GET)
    public String transList(Model model) {

        persons = null;
        persons = (List<PersonWithPlanets>) personWithPlanetsRepository.findAll();

        model.addAttribute("persons", persons);

        return "transList";
    }

    @RequestMapping(value = {"/givePlanet"}, method = RequestMethod.GET)
    public String showGivePlanetPage(Model model) {

        PersonWithPlanetForm personWithPlanetForm = new PersonWithPlanetForm();
        model.addAttribute("personWithPlanetForm", personWithPlanetForm);


        persons = null;
        persons = (List<PersonWithPlanets>) personWithPlanetsRepository.findAll();

        boolean x = false;

        for (PersonWithPlanets person : persons) {
            if (person.getLordName() == null && !x) {
                x = true;
                tempPersons = null;
                tempPersons = new ArrayList<>();
            }
            if (person.getLordName() == null && x) {
                tempPersons.add(person);
            }

        }
        model.addAttribute("tempPerson", tempPersons);
        return "desiase";
    }

    @RequestMapping(value = {"/givePlanet"}, method = RequestMethod.POST)
    public String givePlanet(Model model, //
                             @ModelAttribute("personWithPlanetForm") PersonWithPlanetForm personWithPlanetForm) {

        String planetName = personWithPlanetForm.getPlanetName();
        String lordName = personWithPlanetForm.getLordName();

        lords = (List<Doctor>) lordRepository.findAll();
        if (planetName != null && planetName.length() > 0 && lordName != null && lordName.length() > 0) {
            boolean a = true;

            for (PersonWithPlanets person : tempPersons) {
                if (person.getPlanetName().equals(planetName)) {
                    a = false;
                    break;
                }
            }
            if (a) {
                model.addAttribute("errorBadMessage", errorBadMessage);
                return "desiase";
            }
            for (Doctor lord : lords) {
                if (lord.getLogin().equals(lordName)) {
                    persons = (List<PersonWithPlanets>) personWithPlanetsRepository.findAll();
                    persons.removeIf(person -> person.getPlanetName().equals(planetName));
                    personWithPlanetsRepository.deleteAll();
                    personWithPlanetsRepository.saveAll(persons);
                    personWithPlanetsRepository.save(new PersonWithPlanets(lordName, planetName));
                    return "redirect:/index";
                }
            }


            model.addAttribute("errorNothingLordMessage", errorNothingLordMessage);
            return "desiase";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "desiase";
    }

    @RequestMapping(value = {"/lordWithoutPlanet"}, method = RequestMethod.GET)
    public String LordTopTenList(Model model) {

        persons = null;
        persons = (List<PersonWithPlanets>) personWithPlanetsRepository.findAll();
        lords = null;
        lords = (List<Doctor>) lordRepository.findAll();
        boolean x = false;
        List<Doctor> temp = new ArrayList<Doctor>();

        for (Doctor lord : lords) {
            boolean fact = true;
            for (PersonWithPlanets person : persons) {
                if (lord.getLogin().equals(person.getLordName())) {
                    fact = false;
                    break;
                }
            }
            if (fact && !x) {
                x = true;
                temp = null;
                temp = new ArrayList<>();
            }
            if (fact)
                temp.add(lord);
        }
        model.addAttribute("lordsTemp", temp);

        return "lordWithoutPlanet";
    }


    /*@RequestMapping(value = { "/write" }, method = RequestMethod.GET)
    public String planetList(Model model) {
        lords = (List<Lord>) lordRepository.findAll();
        planets = (List<Planet>) planetRepository.findAll();
        int[] array = new int[planets.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) Math.round((Math.random() * 600));
        }
        int i = 0;
        for(Planet planet: planets){
            persons.add(new PersonWithPlanets(lords.get(array[i]).getName(),planet.getName()));
            i++;
        }
        personWithPlanetsRepository.saveAll(persons);

        return "planetList";
    }*/
}
