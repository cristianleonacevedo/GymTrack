package com.cesde.project_spring_boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Entity User - Representa un usuario en la base de datos.
 *
 * @Entity: Indica que esta clase es una entidad JPA. (Le dice a Spring que esta clase es una tabla en la base de datos)
 * @Table: Especifica el nombre de la tabla.
 */
@Entity
@Table(name = "users")
public class User {

    // ID unico para cada usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental
    private Long id;

    // Nombre del usuario (obligatorio, no puede estar vacío)
    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    // Apellido del usuario (obligatorio, no puede estar vacío)
    @NotBlank(message = "Last name is mandatory")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    // Email del usuario (obligatorio, no puede estar vacío, debe ser único y válido)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    // Teléfono del usuario (opcional)
    @Column(name = "phone", length = 15)
    private String phone;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    // Fecha de creación del usuario (no puede ser nulo, no se puede actualizar)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Fecha de última actualización del usuario (no puede ser nulo)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructor vacío (Requerido por JPA)
    public User() { }

    // Constructor con parámetros
    public User(String firstName, String lastName, String email, String phone, Company company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.company = company;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Método que se ejecuta antes de guardar un nuevo usuario
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Método que se ejecuta antes de actualizar un usuario existente
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // GETTERS y SETTERS (Necesarios para que Spring funcione)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Método auxiliar para obtener el nombre completo del usuario
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}