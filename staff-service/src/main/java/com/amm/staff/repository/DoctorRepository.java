package com.amm.staff.repository;

import com.amm.staff.entity.Doctor;
import com.amm.staff.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialization(Specialization specialization);
    Optional<Doctor> findByFullName(String fullName);
    List<Doctor> findByFullNameContainingIgnoreCase(String name);
}