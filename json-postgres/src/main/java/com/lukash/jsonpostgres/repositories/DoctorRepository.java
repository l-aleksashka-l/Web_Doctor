package com.lukash.jsonpostgres.repositories;

import com.lukash.jsonpostgres.entities.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
}
