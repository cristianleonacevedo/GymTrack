package com.cesde.project_spring_boot.service;

import com.cesde.project_spring_boot.dto.AuthResponse;
import com.cesde.project_spring_boot.dto.LoginRequest;
import com.cesde.project_spring_boot.dto.RegisterRequest;
import com.cesde.project_spring_boot.model.User;
import com.cesde.project_spring_boot.repository.UserRepository;
import com.cesde.project_spring_boot.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        // Validar si ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        User user = new User();
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setDocumento(request.getDocumento());
        user.setTelefono(request.getTelefono());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRol("MIEMBRO");
        user.setEstadoMembresia("PENDIENTE");
        user.setMembresiaActiva(false);

        userRepository.save(user);
        return "Usuario registrado exitosamente";
    }

    public AuthResponse login(LoginRequest request) {
        // Autenticar al usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Buscar el usuario
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear claims extra para el token
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        extraClaims.put("email", user.getEmail());
        extraClaims.put("rol", user.getRol());
        extraClaims.put("membresiaActiva", user.getMembresiaActiva());

        // Generar tokens
        String accessToken = jwtService.generateToken(extraClaims, user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Retornar respuesta
        return new AuthResponse(
                accessToken,
                refreshToken,
                user.getId(),
                user.getEmail(),
                user.getRol(),
                user.getMembresiaActiva()
        );
    }

    public AuthResponse refreshToken(String refreshToken) {
        String email = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (jwtService.isTokenValid(refreshToken, user)) {
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("userId", user.getId());
            extraClaims.put("email", user.getEmail());
            extraClaims.put("rol", user.getRol());
            extraClaims.put("membresiaActiva", user.getMembresiaActiva());

            String accessToken = jwtService.generateToken(extraClaims, user);
            String newRefreshToken = jwtService.generateRefreshToken(user);

            return new AuthResponse(
                    accessToken,
                    newRefreshToken,
                    user.getId(),
                    user.getEmail(),
                    user.getRol(),
                    user.getMembresiaActiva()
            );
        } else {
            throw new RuntimeException("Refresh token inválido");
        }
    }
}