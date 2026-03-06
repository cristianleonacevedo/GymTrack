package com.cesde.project_spring_boot.service;

import com.cesde.project_spring_boot.dto.AssignMembershipRequest;
import com.cesde.project_spring_boot.dto.MembershipAssignmentResponse;
import com.cesde.project_spring_boot.dto.RenewMembershipRequest;
import com.cesde.project_spring_boot.dto.RenewMembershipResponse;
import com.cesde.project_spring_boot.model.MembershipAssignment;
import com.cesde.project_spring_boot.model.MembershipPlan;
import com.cesde.project_spring_boot.model.User;
import com.cesde.project_spring_boot.repository.MembershipAssignmentRepository;
import com.cesde.project_spring_boot.repository.MembershipPlanRepository;
import com.cesde.project_spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembershipAssignmentService {

    @Autowired
    private MembershipAssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MembershipPlanRepository planRepository;

    @Transactional
    public MembershipAssignmentResponse assignMembership(AssignMembershipRequest request) {

        User member = userRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado con ID: " + request.getMemberId()));

        MembershipPlan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado con ID: " + request.getPlanId()));

        if (!plan.getActive()) {
            throw new RuntimeException("El plan seleccionado no está activo");
        }

        if (request.getStartDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("La fecha de inicio no puede ser anterior a hoy");
        }

        LocalDate calculatedEndDate = request.getStartDate().plusDays(plan.getDurationDays());

        List<MembershipAssignment> activeMemberships = assignmentRepository
                .findActiveMembershipsAfterDate(member.getId(), LocalDate.now());

        LocalDate finalStartDate = request.getStartDate();
        LocalDate finalEndDate = calculatedEndDate;

        if (!activeMemberships.isEmpty()) {
            MembershipAssignment lastActive = activeMemberships.get(0);
            if (lastActive.getEndDate().isAfter(request.getStartDate())) {
                finalStartDate = lastActive.getEndDate().plusDays(1);
                finalEndDate = finalStartDate.plusDays(plan.getDurationDays());
            }
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

        MembershipAssignment assignment = new MembershipAssignment();
        assignment.setUser(member);
        assignment.setPlan(plan);
        assignment.setStartDate(finalStartDate);
        assignment.setEndDate(finalEndDate);
        assignment.setStatus("ACTIVA");
        assignment.setPaymentMethod(request.getPaymentMethod());
        assignment.setPaymentReference(request.getPaymentReference());
        assignment.setAssignmentDate(LocalDate.now());
        assignment.setAssignedBy(currentUser.getId());

        MembershipAssignment saved = assignmentRepository.save(assignment);

        member.setEstadoMembresia("ACTIVA");
        member.setMembresiaActiva(true);
        userRepository.save(member);

        return convertToResponse(saved);
    }

    public List<MembershipAssignmentResponse> getAssignmentsByMember(Long memberId) {
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        List<MembershipAssignment> assignments = assignmentRepository.findByUserAndStatus(member, "ACTIVA");

        return assignments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // ========== NUEVO MÉTODO PARA RENOVACIÓN ==========
    @Transactional
    public RenewMembershipResponse renewMembership(RenewMembershipRequest request) {

        // 1. Buscar la membresía actual
        MembershipAssignment currentAssignment = assignmentRepository.findById(request.getCurrentAssignmentId())
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada con ID: " + request.getCurrentAssignmentId()));

        // 2. Validar que la membresía esté activa
        if (!"ACTIVA".equals(currentAssignment.getStatus())) {
            throw new RuntimeException("La membresía no está activa o ya fue renovada");
        }

        // 3. Validar que esté dentro del rango permitido (próximos 7 días o hasta 30 días vencida)
        LocalDate today = LocalDate.now();
        LocalDate maxExpiredDate = today.minusDays(30); // Máximo 30 días vencida
        LocalDate maxFutureDate = today.plusDays(7);    // Próximos 7 días

        LocalDate endDate = currentAssignment.getEndDate();

        if (endDate.isBefore(maxExpiredDate)) {
            throw new RuntimeException("La membresía venció hace más de 30 días. No es renovable.");
        }

        if (endDate.isAfter(maxFutureDate) && endDate.isAfter(today)) {
            throw new RuntimeException("La membresía vence en más de 7 días. Aún no es renovable.");
        }

        // 4. Validar que el nuevo plan existe y está activo
        MembershipPlan newPlan = planRepository.findById(request.getNewPlanId())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado con ID: " + request.getNewPlanId()));

        if (!newPlan.getActive()) {
            throw new RuntimeException("El plan seleccionado no está activo");
        }

        // 5. Calcular nueva fecha de inicio
        boolean wasExpired = endDate.isBefore(today);
        LocalDate newStartDate;

        if (wasExpired) {
            // Si ya venció, la nueva membresía empieza hoy
            newStartDate = today;
        } else {
            // Si aún no vence, empieza al día siguiente del vencimiento
            newStartDate = endDate.plusDays(1);
        }

        LocalDate newEndDate = newStartDate.plusDays(newPlan.getDurationDays());

        // 6. Obtener recepcionista autenticado
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

        // 7. Crear NUEVA asignación (la renovación)
        MembershipAssignment newAssignment = new MembershipAssignment();
        newAssignment.setUser(currentAssignment.getUser());
        newAssignment.setPlan(newPlan);
        newAssignment.setStartDate(newStartDate);
        newAssignment.setEndDate(newEndDate);
        newAssignment.setStatus("ACTIVA");
        newAssignment.setPaymentMethod(request.getPaymentMethod());
        newAssignment.setPaymentReference(request.getPaymentReference());
        newAssignment.setAssignmentDate(today);
        newAssignment.setAssignedBy(currentUser.getId());

        MembershipAssignment saved = assignmentRepository.save(newAssignment);

        // 8. Marcar la membresía anterior como RENOVADA
        currentAssignment.setStatus("RENOVADA");
        assignmentRepository.save(currentAssignment);

        // 9. Asegurar que el usuario sigue con membresía activa
        User member = currentAssignment.getUser();
        member.setEstadoMembresia("ACTIVA");
        member.setMembresiaActiva(true);
        userRepository.save(member);

        // 10. Retornar respuesta
        return new RenewMembershipResponse(
                saved.getId(),
                member.getId(),
                member.getNombre() + " " + member.getApellido(),
                newPlan.getId(),
                newPlan.getName(),
                endDate,
                newStartDate,
                newEndDate,
                saved.getStatus(),
                request.getPaymentMethod(),
                request.getPaymentReference(),
                newPlan.getPrice().doubleValue(),
                newPlan.getDurationDays(),
                wasExpired
        );
    }

    // ========== MÉTODO AUXILIAR PARA VERIFICAR RENOVACIÓN ==========
    public boolean isRenewable(Long assignmentId) {
        MembershipAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));

        LocalDate today = LocalDate.now();
        LocalDate endDate = assignment.getEndDate();

        // Es renovable si: está activa Y (vence en ≤7 días O venció hace ≤30 días)
        return "ACTIVA".equals(assignment.getStatus()) &&
                !endDate.isBefore(today.minusDays(30)) &&
                !endDate.isAfter(today.plusDays(7));
    }

    // ========== MÉTODO PRIVADO PARA CONVERSIÓN ==========
    private MembershipAssignmentResponse convertToResponse(MembershipAssignment assignment) {
        User member = assignment.getUser();
        MembershipPlan plan = assignment.getPlan();

        return new MembershipAssignmentResponse(
                assignment.getId(),
                member.getId(),
                member.getNombre() + " " + member.getApellido(),
                member.getDocumento(),
                plan.getId(),
                plan.getName(),
                assignment.getStartDate(),
                assignment.getEndDate(),
                assignment.getStatus(),
                assignment.getPaymentMethod(),
                assignment.getPaymentReference(),
                assignment.getAssignmentDate(),
                assignment.getAssignedBy(),
                plan.getDurationDays(),
                plan.getPrice().doubleValue()
        );
    }
}