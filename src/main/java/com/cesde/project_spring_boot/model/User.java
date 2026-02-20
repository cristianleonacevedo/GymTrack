package com.cesde.project_spring_boot.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "documento")
        })
public class User implements UserDetails {  // Implementa UserDetails

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
    private String rol; // ADMIN, MIEMBRO
    private String estadoMembresia; // PENDIENTE, ACTIVA, BLOQUEADA
    private Boolean membresiaActiva; // NUEVO: true si estadoMembresia = "ACTIVA"

    // ===== MÉTODOS DE USERDETAILS =====
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol));
    }

    @Override
    public String getUsername() {
        return this.email; // Usamos email como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // ===== GETTERS Y SETTERS =====
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

    @Override
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEstadoMembresia() { return estadoMembresia; }
    public void setEstadoMembresia(String estadoMembresia) {
        this.estadoMembresia = estadoMembresia;
        // Actualiza automáticamente membresiaActiva
        this.membresiaActiva = "ACTIVA".equals(estadoMembresia);
    }

    public Boolean getMembresiaActiva() { return membresiaActiva; }
    public void setMembresiaActiva(Boolean membresiaActiva) {
        this.membresiaActiva = membresiaActiva;
    }
}