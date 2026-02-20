package com.cesde.project_spring_boot.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "documento")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // datos basicos del miembro
    private String nombre;
    private String apellido;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false)
    private String email;

    private String telefono;
    private LocalDate fechaNacimiento;
    private String contactoEmergencia;
    private String nombre_contacto;

    private String password;

    // cosas de negocio
    private String rol; // ADMIN, MIEMBRO, etc
    private String estadoMembresia; // PENDIENTE, ACTIVA, BLOQUEADA...

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombre_contacto() {return nombre_contacto;}
    public void setNombre_contacto(String nombre_contacto) {this.nombre_contacto = nombre_contacto;}

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getContactoEmergencia() { return contactoEmergencia; }
    public void setContactoEmergencia(String contactoEmergencia) { this.contactoEmergencia = contactoEmergencia; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEstadoMembresia() { return estadoMembresia; }
    public void setEstadoMembresia(String estadoMembresia) { this.estadoMembresia = estadoMembresia; }
}

