package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Cliente")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name", length = 100, unique = true)
    private String nombre;
    
    @Column(name = "email", length = 150)
    private String email;
    
    @Column(name = "phone", length = 20)
    private String telefono;
    
    @Column(name = "address", length = 255)
    private String direccion;
    
    // Relaciones
    @OneToMany(mappedBy = "Cliente", cascade = CascadeType.ALL)
    private List<Proyecto> projects;
    
    // Constructor vacío
    public Cliente() {}
    
    // Constructor con parámetros (sin id, se genera automáticamente)
    public Cliente(String nombre, String email, String telefono, String direccion) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
    // Constructor completo (con id para casos especiales)
    public Cliente(Long id, String nombre, String email, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
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
    
    public List<Proyecto> getProyectos() {
        return projects;
    }
    
    public void setProyectos(List<Proyecto> proyectos) {
        this.projects = proyectos;
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
