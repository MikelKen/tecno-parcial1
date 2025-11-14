package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "producto")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombre", length = 100, unique = true)
    private String nombre;
    
    @Column(name = "tipo", length = 50)
    private String tipo;
    
    @Column(name = "unidad_medida", length = 20)
    private String unidadMedida;
    
    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    
    @Column(name = "stock")
    private Integer stock;
    
    // Relaciones
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<ProductoProject> productoProjects;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<ProductoProveedor> productoProveedor;
    
    // Constructor vacío
    public Producto() {}
    
    // Constructor con parámetros (sin ID - se auto-genera)
    public Producto(String nombre, String tipo, String unidadMedida, BigDecimal precioUnitario, Integer stock) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.unidadMedida = unidadMedida;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
    }
    
    // Constructor con ID (para casos especiales)
    public Producto(Long id, String nombre, String tipo, String unidadMedida, BigDecimal precioUnitario, Integer stock) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.unidadMedida = unidadMedida;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
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
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getUnidadMedida() {
        return unidadMedida;
    }
    
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
    
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public List<ProductoProject> getProductoProjects() {
        return productoProjects;
    }
    
    public void setProductoProjects(List<ProductoProject> productoProjects) {
        this.productoProjects = productoProjects;
    }
    
    public List<ProductoProveedor> getProductoProveedor() {
        return productoProveedor;
    }
    
    public void setProductoProveedor(List<ProductoProveedor> productoProveedor) {
        this.productoProveedor = productoProveedor;
    }
    
    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", type='" + tipo + '\'' +
                ", unitMeasure='" + unidadMedida + '\'' +
                ", unitPrice=" + precioUnitario +
                ", stock=" + stock +
                '}';
    }
}