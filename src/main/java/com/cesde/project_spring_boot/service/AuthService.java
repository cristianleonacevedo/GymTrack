package com.cesde.project_spring_boot.service;

import com.cesde.project_spring_boot.dto.RegisterRequest;
import com.cesde.project_spring_boot.model.User;
import com.cesde.project_spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String register(RegisterRequest request) {

        // validaciones simples (no tan limpias, pero reales)
        if (userRepository.existsByEmail(request.email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        if (userRepository.existsByDocumento(request.documento)) {
            throw new RuntimeException("El documento ya existe");
        }

        User user = new User();
        user.setNombre(request.nombre);
        user.setApellido(request.apellido);
        user.setDocumento(request.documento);
        user.setEmail(request.email);
        user.setTelefono(request.telefono);
        user.setFechaNacimiento(request.fecha_nacimiento);
        user.setContactoEmergencia(request.contacto_emergencia);

        // seguridad mínima decente
        user.setPassword(encoder.encode(request.password));

        // reglas de negocio de la historia
        user.setRol("MIEMBRO");
        user.setEstadoMembresia("PENDIENTE");

        userRepository.save(user);

        // por ahora uno simple para cumplir la historia
        return  user.getId().toString(); // corregir (JWT)
    }
}
