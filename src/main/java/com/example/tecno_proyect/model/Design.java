package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "design")
public class Design {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_design")
    private Long idDesign;
    
    @Column(name = "id_quote")
    private Long idQuote;
    
    @Column(name = "url_render", length = 500)
    private String urlRender;
    
    @Column(name = "laminated_plane", length = 500)
    private String laminatedPlane;
    
    @Column(name = "approved")
    private Boolean approved;
    
    @Column(name = "approved_date")
    private LocalDate approvedDate;
    
    @Column(name = "comments", length = 500)
    private String comments;
    
    @Column(name = "user_id")
    private Long userId;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_quote", insertable = false, updatable = false)
    private Quote quote;
    
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    // Constructor vacío
    public Design() {}
    
    // Constructor con parámetros
    public Design(Long idQuote, String urlRender, String laminatedPlane, Boolean approved, 
                 LocalDate approvedDate, String comments, Long userId) {
        this.idQuote = idQuote;
        this.urlRender = urlRender;
        this.laminatedPlane = laminatedPlane;
        this.approved = approved;
        this.approvedDate = approvedDate;
        this.comments = comments;
        this.userId = userId;
    }
    
    // Getters y Setters
    public Long getIdDesign() {
        return idDesign;
    }
    
    public void setIdDesign(Long idDesign) {
        this.idDesign = idDesign;
    }
    
    public Long getIdQuote() {
        return idQuote;
    }
    
    public void setIdQuote(Long idQuote) {
        this.idQuote = idQuote;
    }
    
    public String getUrlRender() {
        return urlRender;
    }
    
    public void setUrlRender(String urlRender) {
        this.urlRender = urlRender;
    }
    
    public String getLaminatedPlane() {
        return laminatedPlane;
    }
    
    public void setLaminatedPlane(String laminatedPlane) {
        this.laminatedPlane = laminatedPlane;
    }
    
    public Boolean getApproved() {
        return approved;
    }
    
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
    
    public LocalDate getApprovedDate() {
        return approvedDate;
    }
    
    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Quote getQuote() {
        return quote;
    }
    
    public void setQuote(Quote quote) {
        this.quote = quote;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "Design{" +
                "idDesign=" + idDesign +
                ", idQuote=" + idQuote +
                ", urlRender='" + urlRender + '\'' +
                ", laminatedPlane='" + laminatedPlane + '\'' +
                ", approved=" + approved +
                ", approvedDate=" + approvedDate +
                ", comments='" + comments + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}