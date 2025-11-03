package com.example.tecno_proyect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "service_evaluation")
public class ServiceEvaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "comments", length = 500)
    private String comments;
    
    @Column(name = "id_project")
    private Long idProject;
    
    @Column(name = "design_qualification")
    private Integer designQualification;
    
    @Column(name = "fabric_qualification")
    private Integer fabricQualification;
    
    @Column(name = "installation_qualification")
    private Integer installationQualification;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_project", insertable = false, updatable = false)
    private Project project;
    
    // Constructor vacío
    public ServiceEvaluation() {}
    
    // Constructor con parámetros
    public ServiceEvaluation(String comments, Long idProject, Integer designQualification, 
                           Integer fabricQualification, Integer installationQualification) {
        this.comments = comments;
        this.idProject = idProject;
        this.designQualification = designQualification;
        this.fabricQualification = fabricQualification;
        this.installationQualification = installationQualification;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public Long getIdProject() {
        return idProject;
    }
    
    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }
    
    public Integer getDesignQualification() {
        return designQualification;
    }
    
    public void setDesignQualification(Integer designQualification) {
        this.designQualification = designQualification;
    }
    
    public Integer getFabricQualification() {
        return fabricQualification;
    }
    
    public void setFabricQualification(Integer fabricQualification) {
        this.fabricQualification = fabricQualification;
    }
    
    public Integer getInstallationQualification() {
        return installationQualification;
    }
    
    public void setInstallationQualification(Integer installationQualification) {
        this.installationQualification = installationQualification;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    @Override
    public String toString() {
        return "ServiceEvaluation{" +
                "id=" + id +
                ", comments='" + comments + '\'' +
                ", idProject='" + idProject + '\'' +
                ", designQualification=" + designQualification +
                ", fabricQualification=" + fabricQualification +
                ", installationQualification=" + installationQualification +
                '}';
    }
}