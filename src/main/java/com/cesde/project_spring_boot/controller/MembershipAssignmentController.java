package com.cesde.project_spring_boot.controller;

import com.cesde.project_spring_boot.dto.AssignMembershipRequest;
import com.cesde.project_spring_boot.dto.MembershipAssignmentResponse;
import com.cesde.project_spring_boot.dto.RenewMembershipRequest;
import com.cesde.project_spring_boot.dto.RenewMembershipResponse;
import com.cesde.project_spring_boot.service.MembershipAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
@Tag(name = "Asignación de Membresías", description = "Operaciones para asignar y renovar planes a miembros")
public class MembershipAssignmentController {

    @Autowired
    private MembershipAssignmentService assignmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    @Operation(summary = "Asignar membresía a un miembro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Membresía asignada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    public ResponseEntity<MembershipAssignmentResponse> assignMembership(
            @Valid @RequestBody AssignMembershipRequest request) {
        MembershipAssignmentResponse response = assignmentService.assignMembership(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    @Operation(summary = "Obtener membresías de un miembro")
    public ResponseEntity<List<MembershipAssignmentResponse>> getMemberAssignments(
            @PathVariable Long memberId) {
        List<MembershipAssignmentResponse> assignments = assignmentService.getAssignmentsByMember(memberId);
        return ResponseEntity.ok(assignments);
    }

    // ========== NUEVOS ENDPOINTS PARA RENOVACIÓN ==========

    @PostMapping("/renew")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    @Operation(summary = "Renovar membresía de un miembro",
            description = "Permite renovar una membresía que vence en ≤7 días o que venció hace ≤30 días")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membresía renovada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Membresía no renovable o datos inválidos"),
            @ApiResponse(responseCode = "403", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Membresía o plan no encontrado")
    })
    public ResponseEntity<RenewMembershipResponse> renewMembership(
            @Valid @RequestBody RenewMembershipRequest request) {
        RenewMembershipResponse response = assignmentService.renewMembership(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-renewable/{assignmentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    @Operation(summary = "Verificar si una membresía es renovable",
            description = "Retorna true si la membresía puede renovarse (vence en ≤7 días o venció hace ≤30 días)")
    public ResponseEntity<Map<String, Boolean>> checkRenewable(
            @PathVariable Long assignmentId) {
        boolean isRenewable = assignmentService.isRenewable(assignmentId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("renewable", isRenewable);
        return ResponseEntity.ok(response);
    }
}