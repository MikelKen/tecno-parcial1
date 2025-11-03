package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "task")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "init_hour")
    private LocalTime initHour;
    
    @Column(name = "final_hour")
    private LocalTime finalHour;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "state", length = 50)
    private String state;
    
    @Column(name = "id_schedule")
    private Long idSchedule;
    
    @Column(name = "user_id")
    private Long userId;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_schedule", insertable = false, updatable = false)
    private Schedule schedule;
    
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "id_project", insertable = false, updatable = false)
    private Project project;
    
    // Constructor vacío
    public Task() {}
    
    // Constructor con parámetros
    public Task(LocalTime initHour, LocalTime finalHour, String description, 
               String state, Long idSchedule, Long userId) {
        this.initHour = initHour;
        this.finalHour = finalHour;
        this.description = description;
        this.state = state;
        this.idSchedule = idSchedule;
        this.userId = userId;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalTime getInitHour() {
        return initHour;
    }
    
    public void setInitHour(LocalTime initHour) {
        this.initHour = initHour;
    }
    
    public LocalTime getFinalHour() {
        return finalHour;
    }
    
    public void setFinalHour(LocalTime finalHour) {
        this.finalHour = finalHour;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public Long getIdSchedule() {
        return idSchedule;
    }
    
    public void setIdSchedule(Long idSchedule) {
        this.idSchedule = idSchedule;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Schedule getSchedule() {
        return schedule;
    }
    
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", initHour=" + initHour +
                ", finalHour=" + finalHour +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", idSchedule=" + idSchedule +
                ", userId='" + userId + '\'' +
                '}';
    }
}