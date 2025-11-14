package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "proyecto")
public class Proyecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombre", length = 100, unique = true)
    private String nombre;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "ubicacion", length = 255)
    private String ubicacion;
    
    @Column(name = "estado", length = 50)
    private String estado;
    
    @Column(name = "id_cliente")
    private Long idCliente;
    
    @Column(name = "usuario_id")
    private Long usuarioId;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Cuota> coutas;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<PlanPago> planPagos;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<ProductoProject> productoProyectos;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<ServicioEvaluacion> servicioEvaluaciones;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Tarea> tareas;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Cronograma> cronogramas;
    
    // Constructor vacío
    public Proyecto() {}
    
    // Constructor con parámetros (sin ID - se auto-genera)
    public Proyecto(String nombre, String descripcion, String ubicacion, String estado, Long idCliente, Long usuarioId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.idCliente = idCliente;
        this.usuarioId = usuarioId;
    }
    
    // Constructor con ID (para casos especiales)
    public Proyecto(Long id, String nombre, String descripcion, String ubicacion, String estado, Long idCliente, Long usuarioId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.idCliente = idCliente;
        this.usuarioId = usuarioId;
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
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Long getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Cuota> getCuotas() {
        return coutas;
    }
    
    public void setCuotas(List<Cuota> cuotas) {
        this.coutas = cuotas;
    }
    
    public List<PlanPago> getPlanPagos() {
        return planPagos;
    }
    
    public void setPlanPagos(List<PlanPago> planPagos) {
        this.planPagos = planPagos;
    }
    
    public List<ProductoProject> getProductoProjects() {
        return productoProyectos;
    }
    
    public void setProductoProjects(List<ProductoProject> productoProyectos) {
        this.productoProyectos = productoProyectos;
    }
    
    public List<ServicioEvaluacion> getServicioEvaluaciones() {
        return servicioEvaluaciones;
    }
    
    public void setServicioEvaluaciones(List<ServicioEvaluacion> servicioEvaluaciones) {
        this.servicioEvaluaciones = servicioEvaluaciones;
    }
    
    public List<Tarea> getTareas() {
        return tareas;
    }
    
    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
    
    public List<Cronograma> getCronogramas() {
        return cronogramas;
    }
    
    public void setCronogramas(List<Cronograma> cronogramas) {
        this.cronogramas = cronogramas;
    }
    
    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", estado='" + estado + '\'' +
                ", idCliente=" + idCliente +
                ", usuarioId=" + usuarioId +
                '}';
    }
}