package com.example.tecno_proyect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "persona")
public class Persona {
    
    @Id
    @Column(name = "ci", length = 20)
    private String ci;
    
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;
    
    @Column(name = "cargo", length = 100)
    private String cargo;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "celular", length = 20)
    private String celular;
    
    @Column(name = "email", length = 150)
    private String email;

    // Constructor vacío
    public Persona() {}

    // Constructor completo
    public Persona(String ci, String nombre, String apellido, String cargo, String telefono, String celular, String email) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
    }

    // Getters y Setters
    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "ci='" + ci + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cargo='" + cargo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}