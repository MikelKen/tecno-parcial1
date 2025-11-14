package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "proveedor")
public class Proveedor {
    
    @Id
    @Column(name = "nombre", length = 100)
    private String nombre;
    
    @Column(name = "contacto", length = 100)
    private String contacto;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "email", length = 150)
    private String email;
    
    @Column(name = "direccion", length = 255)
    private String direccion;
    
    // Relaciones
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<ProductoProveedor> productoProveedores;
    
    // Constructor vacío
    public Proveedor() {}
    
    // Constructor con parámetros
    public Proveedor(String nombre, String contacto, String telefono, String email, String direccion) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getContacto() {
        return contacto;
    }
    
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public List<ProductoProveedor> getProductoProveedores() {
        return productoProveedores;
    }
    
    public void setProductoProveedores(List<ProductoProveedor> productoProveedores) {
        this.productoProveedores = productoProveedores;
    }
    
    @Override
    public String toString() {
        return "Proveedor{" +
                "nombre='" + nombre + '\'' +
                ", contacto='" + contacto + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}