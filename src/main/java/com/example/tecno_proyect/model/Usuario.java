package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombre", length = 100, unique = true)
    private String nombre;
    
    @Column(name = "email", length = 150)
    private String email;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "direccion", length = 255)
    private String direccion;
    
    @Column(name = "password", length = 255)
    private String password;
    
    @Column(name = "rol", length = 50)
    private String rol;
    
    // Relaciones
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Proyecto> proyectos;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Tarea> tareas;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Cronograma> cronogramas;
    
    // Constructor vacío
    public Usuario() {}
    
    // Constructor con parámetros (sin ID - se auto-genera)
    public Usuario(String nombre, String email, String telefono, String direccion, String password, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.password = password;
        this.rol = rol;
    }
    
    // Constructor con ID (para casos especiales)
    public Usuario(Long id, String nombre, String email, String telefono, String direccion, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.password = password;
        this.rol = rol;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public List<Proyecto> getProyectos() {
        return proyectos;
    }
    
    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
    
    public List<Tarea> getTareas() {
        return tareas;
    }
    
    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
    
    public List<Cronograma> getCronogramas() {
        return cronogramas;
    }
    
    public void setCronogramas(List<Cronograma> cronogramas) {
        this.cronogramas = cronogramas;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}