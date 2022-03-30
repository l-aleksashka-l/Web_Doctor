package com.lukash.jsonpostgres.controllers;

import com.lukash.jsonpostgres.entities.Doctor;
import com.lukash.jsonpostgres.entities.Patient;
import com.lukash.jsonpostgres.entities.Desiase;
import com.lukash.jsonpostgres.form.DoctorForm;
import com.lukash.jsonpostgres.form.PatientForm;
import com.lukash.jsonpostgres.form.DesiaseForm;
import com.lukash.jsonpostgres.repositories.DoctorRepository;
import com.lukash.jsonpostgres.repositories.PatientRepository;
import com.lukash.jsonpostgres.repositories.DesiaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorController {

    private final static Logger logger = LoggerFactory.getLogger(DoctorController.class);

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DesiaseRepository desiaseRepository;


    private static String login_doctor;
    private static String type_doctor;
    private static String login_user;



    private static List<Doctor> doctors = new ArrayList<Doctor>();


    @Autowired
    public DoctorController(DoctorRepository doctorRepository, PatientRepository patientRepository, DesiaseRepository desiaseRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.desiaseRepository = desiaseRepository;
    }


    @Value("${error.message}")
    private String errorMessage;

    @Value("${error.numberMessage}")
    private String errorLordExist;


    @Value("${error.nothingLordMessage}")
    private String errorNumberMessage;


    @RequestMapping(value = {"/doctor"}, method = RequestMethod.GET)
    public String showAddLordPage(Model model) {

        DoctorForm doctorForm = new DoctorForm();
        model.addAttribute("doctorForm", doctorForm);

        return "login";
    }


    @RequestMapping(value = {"/doctor"}, method = RequestMethod.POST)
    public String saveLord(Model model, //
                           @ModelAttribute("doctorForm") DoctorForm doctorForm) {

        String login = doctorForm.getLogin();
        String password = doctorForm.getPassword();
        String type = doctorForm.getType();

        doctors = null;
        doctors = (List<Doctor>) doctorRepository.findAll();

        if (login != null && login.length() > 0 //
                && password != null && password.length() > 0 && type != null && type.length() > 0) {

            for (Doctor doctor : doctors) {
                if (login.equals(doctor.getLogin()) &&
                        type.equals(doctor.getType())) {
                    if (password.equals(doctor.getPassword())) {
                        login_doctor = login;
                        type_doctor = type;
                        return "redirect:/" + login_doctor + "/" + type_doctor + "/patient";//след страница
                    }
                    model.addAttribute("errorLordExist", errorLordExist);
                    return "login";

                }

            }
            model.addAttribute("errorNumberMessage", errorNumberMessage);
            return "login";


        }

        model.addAttribute("errorMessage", errorMessage);
        return "login";
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
                    return "redirect:/" + login_doctor + "/" + type_doctor + "/" + login_user + "/desiase";

                }
            }
            model.addAttribute("errorPlanetExist", errorPlanetExist);
            return "redirect:/" + login_doctor + "/" + type_doctor + "/patient";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "redirect:/" + login_doctor + "/" + type_doctor + "/patient";
    }

    @RequestMapping(value = {"/{login_doctor}/{type}/patient"}, method = RequestMethod.GET)
    public String showAddPlanetPage(Model model) {
        PatientForm patientForm = new PatientForm();
        model.addAttribute("patientForm", patientForm);

        return "add_patient";
    }


    private static List<Desiase> desiases = new ArrayList<>();
    private static List<Desiase> tempPersons = new ArrayList<>();


    @RequestMapping(value = {"{login_doctor}/{type}/{name}/desiase"}, method = RequestMethod.GET)
    public String index(Model model) {

        DesiaseForm desiaseForm = new DesiaseForm();
        model.addAttribute("desiaseForm", desiaseForm);


        desiases = null;
        desiases = (List<Desiase>) desiaseRepository.findAll();
        tempPersons = new ArrayList<>();
        for(Desiase desiase: desiases)
            if(desiase.getName().equals(login_user) && desiase.getType().equals(type_doctor))
                tempPersons.add(desiase);
        model.addAttribute("name", login_user);

        model.addAttribute("tempPerson", tempPersons);
        model.addAttribute("link", "http://localhost:4001/" + login_doctor + "/" + type_doctor + "/patient");
        if(type_doctor.equals("Stomatolog"))
            return "add_diseases";
        else if(type_doctor.equals("Oftalmolog"))
            return "add_ofta";
        else
            return "add_surge";
    }


    @RequestMapping(value = {"/desiase"}, method = RequestMethod.POST)
    public String givePlanet(Model model, //
                             @ModelAttribute("desiaseForm") DesiaseForm desiaseForm) {

        String desiase = desiaseForm.getDiagnosis();
        String status = desiaseForm.getStatus();


        if (desiase != null && desiase.length() > 0) {
            Date date = new Date();
            String doctor = login_doctor;
            String type = type_doctor;


            desiaseRepository.save(new Desiase(doctor, login_user, type, desiase, status, date));


            return "redirect:/" + login_doctor + "/" + type_doctor + "/patient";




        }
        model.addAttribute("errorMessage", errorMessage);
        return "add_disiases";
    }
}




