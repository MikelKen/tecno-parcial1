package com.example.tecno_proyect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "material_project")
public class MaterialProject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "left_over")
    private Integer leftOver;
    
    @Column(name = "id_project", length = 100)
    private String idProject;
    
    @Column(name = "id_material", length = 100)
    private String idMaterial;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_project", insertable = false, updatable = false)
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "id_material", insertable = false, updatable = false)
    private Material material;
    
    // Constructor vacío
    public MaterialProject() {}
    
    // Constructor con parámetros
    public MaterialProject(Integer quantity, Integer leftOver, String idProject, String idMaterial) {
        this.quantity = quantity;
        this.leftOver = leftOver;
        this.idProject = idProject;
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
    
    public Integer getLeftOver() {
        return leftOver;
    }
    
    public void setLeftOver(Integer leftOver) {
        this.leftOver = leftOver;
    }
    
    public String getIdProject() {
        return idProject;
    }
    
    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }
    
    public String getIdMaterial() {
        return idMaterial;
    }
    
    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }
    
    @Override
    public String toString() {
        return "MaterialProject{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", leftOver=" + leftOver +
                ", idProject='" + idProject + '\'' +
                ", idMaterial='" + idMaterial + '\'' +
                '}';
    }
}