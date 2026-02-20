package com.cesde.project_spring_boot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class RegisterRequest {

    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre no puede ser nulo")
    public String nombre;
    public String apellido;
    public String documento;

    public String email;
    public String telefono;
    public LocalDate fecha_nacimiento;
    public String contacto_emergencia;
    public String nombre_contacto;

    @NotBlank(message = "La contraseña no puede estar vacio")
    @NotNull(message = "El contraseña no puede ser nulo")
    public String password;
}
