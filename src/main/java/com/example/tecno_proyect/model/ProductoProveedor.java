package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "producto_proveedor")
public class ProductoProveedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    
    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;
    
    @Column(name = "id_supplier", length = 100)
    private String idProveedor;
    
    @Column(name = "id_material")
    private Long idProducto;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_supplier", insertable = false, updatable = false)
    private Proveedor proveedor;
    
    @ManyToOne
    @JoinColumn(name = "id_material", insertable = false, updatable = false)
    private Producto producto;
    
    // Constructor vacío
    public ProductoProveedor() {}
    
    // Constructor con parámetros
    public ProductoProveedor(Integer cantidad, BigDecimal precioUnitario, BigDecimal total, 
                           String idProveedor, Long idProducto) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
        this.idProveedor = idProveedor;
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
    
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
    
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public String getIdProveedor() {
        return idProveedor;
    }
    
    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    public Long getIdProducto() {
        return idProducto;
    }
    
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }
    
    public Proveedor getProveedor() {
        return proveedor;
    }
    
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    @Override
    public String toString() {
        return "MaterialSupplier{" +
                "id=" + id +
                ", quantity=" + cantidad +
                ", unitPrice=" + precioUnitario +
                ", total=" + total +
                ", idProveedor='" + idProveedor + '\'' +
                ", idProducto=" + idProducto +
                '}';
    }
}