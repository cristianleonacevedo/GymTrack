package com.cesde.project_spring_boot.controller;

import com.cesde.project_spring_boot.dto.MembershipPlanResponse;
import com.cesde.project_spring_boot.service.MembershipPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/plans")
@CrossOrigin(origins = "*")
@Tag(name = "controlador del plan de membresía", description = "Operaciones para consultar planes disponibles")
public class MembershipPlanController {

    @Autowired
    private MembershipPlanService planService;

    @GetMapping("/active")
    @Operation(summary = "Listar planes activos",
            description = "Retorna todos los planes de membresía activos ordenados por precio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de planes obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay planes activos disponibles"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<MembershipPlanResponse>> getActivePlans() {
        List<MembershipPlanResponse> planes = planService.getActivePlans();

        if (planes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(planes);
    }
}