package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "material")
public class Material {
    
    @Id
    @Column(name = "name", length = 100)
    private String name;
    
    @Column(name = "type", length = 50)
    private String type;
    
    @Column(name = "unit_measure", length = 20)
    private String unitMeasure;
    
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(name = "stock")
    private Integer stock;
    
    // Relaciones
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<MaterialProject> materialProjects;
    
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<MaterialSupplier> materialSuppliers;
    
    // Constructor vacío
    public Material() {}
    
    // Constructor con parámetros
    public Material(String name, String type, String unitMeasure, BigDecimal unitPrice, Integer stock) {
        this.name = name;
        this.type = type;
        this.unitMeasure = unitMeasure;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }
    
    // Getters y Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getUnitMeasure() {
        return unitMeasure;
    }
    
    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public List<MaterialProject> getMaterialProjects() {
        return materialProjects;
    }
    
    public void setMaterialProjects(List<MaterialProject> materialProjects) {
        this.materialProjects = materialProjects;
    }
    
    public List<MaterialSupplier> getMaterialSuppliers() {
        return materialSuppliers;
    }
    
    public void setMaterialSuppliers(List<MaterialSupplier> materialSuppliers) {
        this.materialSuppliers = materialSuppliers;
    }
    
    @Override
    public String toString() {
        return "Material{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", unitMeasure='" + unitMeasure + '\'' +
                ", unitPrice=" + unitPrice +
                ", stock=" + stock +
                '}';
    }
}