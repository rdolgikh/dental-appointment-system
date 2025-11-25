package com.amm.staff.service;

import com.amm.staff.entity.Doctor;
import com.amm.staff.entity.Specialization;
import com.amm.staff.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }

    public List<Doctor> findBySpecialization(Specialization specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    public Doctor saveDoctor(Doctor doctor) {
        // Аудит полей при создании
        doctor.setCreatedAt(LocalDateTime.now());
        doctor.setCreatedBy("system"); // В реальном приложении брать из контекста безопасности
        doctor.setLastModifiedAt(LocalDateTime.now());
        doctor.setLastModifiedBy("system");

        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        return doctorRepository.findById(id)
                .map(doctor -> {
                    doctor.setFullName(doctorDetails.getFullName());
                    doctor.setTotalExperience(doctorDetails.getTotalExperience());
                    doctor.setClinicExperience(doctorDetails.getClinicExperience());
                    doctor.setSpecialization(doctorDetails.getSpecialization());
                    doctor.setEducation(doctorDetails.getEducation());
                    doctor.setCertificates(doctorDetails.getCertificates());

                    // Обновление аудит полей
                    doctor.setLastModifiedAt(LocalDateTime.now());
                    doctor.setLastModifiedBy("system");

                    return doctorRepository.save(doctor);
                })
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public Optional<Doctor> findByFullName(String fullName) {
        return doctorRepository.findByFullName(fullName);
    }
}