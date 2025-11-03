package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {
    
    @Id
    @Column(name = "name", length = 100)
    private String name;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "location", length = 255)
    private String location;
    
    @Column(name = "state", length = 50)
    private String state;
    
    @Column(name = "id_client")
    private Long idClient;
    
    @Column(name = "user_id", length = 100)
    private String userId;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_client", insertable = false, updatable = false)
    private Client client;
    
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Quote> quotes;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<PayPlan> payPlans;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<MaterialProject> materialProjects;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ServiceEvaluation> serviceEvaluations;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Schedule> schedules;
    
    // Constructor vacío
    public Project() {}
    
    // Constructor con parámetros
    public Project(String name, String description, String location, String state, Long idClient, String userId) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.state = state;
        this.idClient = idClient;
        this.userId = userId;
    }
    
    // Getters y Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public Long getIdClient() {
        return idClient;
    }
    
    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public List<Quote> getQuotes() {
        return quotes;
    }
    
    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
    
    public List<PayPlan> getPayPlans() {
        return payPlans;
    }
    
    public void setPayPlans(List<PayPlan> payPlans) {
        this.payPlans = payPlans;
    }
    
    public List<MaterialProject> getMaterialProjects() {
        return materialProjects;
    }
    
    public void setMaterialProjects(List<MaterialProject> materialProjects) {
        this.materialProjects = materialProjects;
    }
    
    public List<ServiceEvaluation> getServiceEvaluations() {
        return serviceEvaluations;
    }
    
    public void setServiceEvaluations(List<ServiceEvaluation> serviceEvaluations) {
        this.serviceEvaluations = serviceEvaluations;
    }
    
    public List<Task> getTasks() {
        return tasks;
    }
    
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    public List<Schedule> getSchedules() {
        return schedules;
    }
    
    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
    
    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", state='" + state + '\'' +
                ", idClient='" + idClient + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}