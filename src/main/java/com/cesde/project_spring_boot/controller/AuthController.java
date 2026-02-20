package com.cesde.project_spring_boot.controller;

import com.cesde.project_spring_boot.dto.RegisterRequest;
import com.cesde.project_spring_boot.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String token = authService.register(request);

        return ResponseEntity.ok(token);
    }
}
