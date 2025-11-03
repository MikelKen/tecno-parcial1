package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "schedule")
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "init_date")
    private LocalDate initDate;
    
    @Column(name = "final_date")
    private LocalDate finalDate;
    
    @Column(name = "estimate_days")
    private Integer estimateDays;
    
    @Column(name = "state", length = 50)
    private String state;
    
    @Column(name = "id_project")
    private Long idProject;
    
    @Column(name = "user_id", length = 100)
    private String userId;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_project", insertable = false, updatable = false)
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Task> tasks;
    
    // Constructor vacío
    public Schedule() {}
    
    // Constructor con parámetros
    public Schedule(LocalDate initDate, LocalDate finalDate, Integer estimateDays, 
                   String state, Long idProject, String userId) {
        this.initDate = initDate;
        this.finalDate = finalDate;
        this.estimateDays = estimateDays;
        this.state = state;
        this.idProject = idProject;
        this.userId = userId;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getInitDate() {
        return initDate;
    }
    
    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }
    
    public LocalDate getFinalDate() {
        return finalDate;
    }
    
    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }
    
    public Integer getEstimateDays() {
        return estimateDays;
    }
    
    public void setEstimateDays(Integer estimateDays) {
        this.estimateDays = estimateDays;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public Long getIdProject() {
        return idProject;
    }
    
    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public List<Task> getTasks() {
        return tasks;
    }
    
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", initDate=" + initDate +
                ", finalDate=" + finalDate +
                ", estimateDays=" + estimateDays +
                ", state='" + state + '\'' +
                ", idProject=" + idProject +
                ", userId='" + userId + '\'' +
                '}';
    }
}