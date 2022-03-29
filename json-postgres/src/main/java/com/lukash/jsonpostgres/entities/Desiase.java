package com.lukash.jsonpostgres.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema="space",name="desiase")
public class Desiase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login_doctor;
    private String name;
    private String type;
    private String diagnosis;
    private String status;
    private Date date;


    public Desiase() {
    }

    public Desiase(String login_doctor, String name, String type, String diagnosis, String status, Date date) {
        this.login_doctor = login_doctor;
        this.name = name;
        this.type = type;
        this.diagnosis = diagnosis;
        this.status = status;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin_doctor() {
        return login_doctor;
    }

    public void setLogin_doctor(String doctor) {
        this.login_doctor = doctor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
