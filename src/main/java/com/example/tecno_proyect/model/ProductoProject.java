package com.example.tecno_proyect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producto_project")
public class ProductoProject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "sobrante")
    private Integer sobrante;
    
    @Column(name = "id_proyecto")
    private Long idProyecto;
    
    @Column(name = "id_producto")
    private Long idProducto;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
    private Proyecto proyecto;
    
    @ManyToOne
    @JoinColumn(name = "id_producto", insertable = false, updatable = false)
    private Producto producto;
    
    // Constructor vacío
    public ProductoProject() {}
    
    // Constructor con parámetros
    public ProductoProject(Integer cantidad, Integer sobrante, Long idProyecto, Long idProducto) {
        this.cantidad = cantidad;
        this.sobrante = sobrante;
        this.idProyecto = idProyecto;
        this.idProducto = idProducto;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public Integer getSobrante() {
        return sobrante;
    }
    
    public void setSobrante(Integer sobrante) {
        this.sobrante = sobrante;
    }
    
    public Long getIdProyecto() {
        return idProyecto;
    }
    
    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }
    
    public Long getIdProducto() {
        return idProducto;
    }
    
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }
    
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    @Override
    public String toString() {
        return "MaterialProject{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", sobrante=" + sobrante +
                ", idProyecto=" + idProyecto +
                ", idProducto=" + idProducto +
                '}';
    }
}