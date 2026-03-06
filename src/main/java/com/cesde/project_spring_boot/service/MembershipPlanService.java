package com.cesde.project_spring_boot.service;

import com.cesde.project_spring_boot.dto.MembershipPlanResponse;
import com.cesde.project_spring_boot.model.MembershipPlan;
import com.cesde.project_spring_boot.repository.MembershipPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembershipPlanService {

    @Autowired
    private MembershipPlanRepository planRepository;

    public List<MembershipPlanResponse> getActivePlans() {
        // 1. Obtener planes activos
        List<MembershipPlan> planes = planRepository.findByActiveTrue();

        // 2. Ordenar por precio ascendente (usando Comparator)
        planes.sort(Comparator.comparing(MembershipPlan::getPrice));

        // 3. Convertir a DTO y devolver
        return planes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private MembershipPlanResponse convertToResponse(MembershipPlan plan) {
        return new MembershipPlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getDescription(),
                plan.getDurationDays(),
                plan.getPrice(),
                plan.getMaxGroupClasses(),
                plan.getIncludesLocker(),
                plan.getActive()
        );
    }
}