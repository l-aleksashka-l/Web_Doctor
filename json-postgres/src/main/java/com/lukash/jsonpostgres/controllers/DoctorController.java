package com.lukash.jsonpostgres.controllers;

import com.lukash.jsonpostgres.entities.Doctor;
import com.lukash.jsonpostgres.entities.Patient;
import com.lukash.jsonpostgres.entities.PersonWithPlanets;
import com.lukash.jsonpostgres.form.DoctorForm;
import com.lukash.jsonpostgres.form.PatientForm;
import com.lukash.jsonpostgres.form.PersonWithPlanetForm;
import com.lukash.jsonpostgres.repositories.DoctorRepository;
import com.lukash.jsonpostgres.repositories.PatientRepository;
import com.lukash.jsonpostgres.repositories.PersonWithPlanetsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorController {

    private final static Logger logger = LoggerFactory.getLogger(DoctorController.class);

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PersonWithPlanetsRepository personWithPlanetsRepository;


    private static String login_doctor;
    private static String type_doctor;
    private static String login_user;


    private static List<Doctor> doctors = new ArrayList<Doctor>();


    @Autowired
    public DoctorController(DoctorRepository doctorRepository, PatientRepository patientRepository,PersonWithPlanetsRepository personWithPlanetsRepository ) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.personWithPlanetsRepository = personWithPlanetsRepository;
    }



    @Value("${error.message}")
    private String errorMessage;

    @Value("${error.numberMessage}")
    private String errorLordExist;


    @Value("${error.nothingLordMessage}")
    private String errorNumberMessage;



    @RequestMapping(value = { "/doctor" }, method = RequestMethod.GET)
    public String showAddLordPage(Model model) {

        DoctorForm doctorForm = new DoctorForm();
        model.addAttribute("doctorForm", doctorForm);

        return "doctor";
    }

    @RequestMapping(value = { "/doctor" }, method = RequestMethod.POST)
    public String saveLord(Model model, //
                             @ModelAttribute("doctorForm") DoctorForm doctorForm) {

        String login = doctorForm.getLogin();
        String password = doctorForm.getPassword();
        String type = doctorForm.getType();

        doctors = null;
        doctors = (List<Doctor>) doctorRepository.findAll();

        if (login != null && login.length() > 0 //
                && password != null && password.length() > 0 && type != null && type.length() > 0) {

                for(Doctor doctor : doctors){
                    if(login.equals(doctor.getLogin()) &&
                    type.equals(doctor.getType())){
                        if(password.equals(doctor.getPassword())){
                        login_doctor = login;
                        type_doctor = type;
                        return "redirect:/"+login_doctor + "/" + type_doctor +"/patient";//след страница
                        }
                        model.addAttribute("errorLordExist", errorLordExist);
                        return "doctor";

                    }

                }
                model.addAttribute("errorNumberMessage", errorNumberMessage);
                return "doctor";


        }

        model.addAttribute("errorMessage", errorMessage);
        return "doctor";
    }






    private static List<Patient> planets = new ArrayList<Patient>();




    @Value("${error.nothingPlanetMessage}")
    private String errorPlanetExist;

    @RequestMapping(value = {"/patient"}, method = RequestMethod.POST)
    public String savePlanet(Model model, //
                             @ModelAttribute("planetForm") PatientForm patientForm) {
        String name = patientForm.getName();
        planets = null;
        planets = (List<Patient>) patientRepository.findAll();
        if (name != null && name.length() > 0 //
        ) {
            for (Patient planet : planets) {
                if (name.equals(planet.getName())) {
                    login_user = name;
                    return "redirect:/"+login_doctor+ "/" + type_doctor+"/"+ login_user + "/desiase";

                }
            }
            model.addAttribute("errorPlanetExist", errorPlanetExist);
            return "redirect:/"+login_doctor+ "/" + type_doctor+"/patient";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "redirect:/"+login_doctor+ "/" + type_doctor+"/patient";
    }

    @RequestMapping(value = { "/{login_doctor}/{type}/patient" }, method = RequestMethod.GET)
    public String showAddPlanetPage(Model model) {
        PatientForm patientForm = new PatientForm();
        model.addAttribute("patientForm", patientForm);

        return "patient";
    }


    private static List<PersonWithPlanets> persons = new ArrayList<>();
    private static List<PersonWithPlanets> tempPersons = new ArrayList<>();


    @RequestMapping(value = { "{login_doctor}/{type}/{name}/desiase" }, method = RequestMethod.GET)
    public String index(Model model) {

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

}




