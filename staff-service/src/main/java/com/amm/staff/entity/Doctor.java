package com.amm.staff.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @NotNull
    private Integer totalExperience; // в годах

    @NotNull
    private Integer clinicExperience; // в годах

    @Enumerated(EnumType.STRING)
    @NotNull
    private Specialization specialization;

    @Column(columnDefinition = "TEXT")
    private String education;

    @Column(columnDefinition = "TEXT")
    private String certificates;

    // Аудит поля
    private String createdBy;
    private String lastModifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    // Конструкторы, геттеры, сеттеры
    public Doctor() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Integer getTotalExperience() { return totalExperience; }
    public void setTotalExperience(Integer totalExperience) { this.totalExperience = totalExperience; }
    public Integer getClinicExperience() { return clinicExperience; }
    public void setClinicExperience(Integer clinicExperience) { this.clinicExperience = clinicExperience; }
    public Specialization getSpecialization() { return specialization; }
    public void setSpecialization(Specialization specialization) { this.specialization = specialization; }
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    public String getCertificates() { return certificates; }
    public void setCertificates(String certificates) { this.certificates = certificates; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getLastModifiedBy() { return lastModifiedBy; }
    public void setLastModifiedBy(String lastModifiedBy) { this.lastModifiedBy = lastModifiedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getLastModifiedAt() { return lastModifiedAt; }
    public void setLastModifiedAt(LocalDateTime lastModifiedAt) { this.lastModifiedAt = lastModifiedAt; }
}
