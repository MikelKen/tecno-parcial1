package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "material_supplier")
public class MaterialSupplier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;
    
    @Column(name = "id_supplier", length = 100)
    private String idSupplier;
    
    @Column(name = "id_material")
    private Long idMaterial;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_supplier", insertable = false, updatable = false)
    private Supplier supplier;
    
    @ManyToOne
    @JoinColumn(name = "id_material", insertable = false, updatable = false)
    private Material material;
    
    // Constructor vacío
    public MaterialSupplier() {}
    
    // Constructor con parámetros
    public MaterialSupplier(Integer quantity, BigDecimal unitPrice, BigDecimal total, 
                           String idSupplier, Long idMaterial) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.idSupplier = idSupplier;
        this.idMaterial = idMaterial;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public String getIdSupplier() {
        return idSupplier;
    }
    
    public void setIdSupplier(String idSupplier) {
        this.idSupplier = idSupplier;
    }
    
    public Long getIdMaterial() {
        return idMaterial;
    }
    
    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }
    
    public Supplier getSupplier() {
        return supplier;
    }
    
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }
    
    @Override
    public String toString() {
        return "MaterialSupplier{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                ", idSupplier='" + idSupplier + '\'' +
                ", idMaterial=" + idMaterial +
                '}';
    }
}