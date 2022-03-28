package com.lukash.jsonpostgres.repositories;

import com.lukash.jsonpostgres.entities.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long> {
}
