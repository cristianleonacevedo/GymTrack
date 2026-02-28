package com.cesde.project_spring_boot.controller;


import com.cesde.project_spring_boot.dto.MembershipPlanRequest;
import com.cesde.project_spring_boot.dto.MembershipPlanResponse;
import com.cesde.project_spring_boot.service.MembershipPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "📦Gestión de planes", description = "CRUD de las membresías de los usuarioss")
@RestController
@RequestMapping("/api/admin/membership-plans")
@PreAuthorize("hasRole('ADMIN')")
public class MembershipPlanController {

    private final MembershipPlanService service;

    public MembershipPlanController(MembershipPlanService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MembershipPlanResponse> create(
            @Valid @RequestBody MembershipPlanRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<MembershipPlanResponse>> listActive() {
        return ResponseEntity.ok(service.findActivePlans());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembershipPlanResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody MembershipPlanRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}