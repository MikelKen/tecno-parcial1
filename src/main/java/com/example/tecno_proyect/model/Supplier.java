package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "supplier")
public class Supplier {
    
    @Id
    @Column(name = "name", length = 100)
    private String name;
    
    @Column(name = "contact", length = 100)
    private String contact;
    
    @Column(name = "phone", length = 20)
    private String phone;
    
    @Column(name = "email", length = 150)
    private String email;
    
    @Column(name = "address", length = 255)
    private String address;
    
    // Relaciones
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<MaterialSupplier> materialSuppliers;
    
    // Constructor vacío
    public Supplier() {}
    
    // Constructor con parámetros
    public Supplier(String name, String contact, String phone, String email, String address) {
        this.name = name;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
    
    // Getters y Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<MaterialSupplier> getMaterialSuppliers() {
        return materialSuppliers;
    }
    
    public void setMaterialSuppliers(List<MaterialSupplier> materialSuppliers) {
        this.materialSuppliers = materialSuppliers;
    }
    
    @Override
    public String toString() {
        return "Supplier{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}