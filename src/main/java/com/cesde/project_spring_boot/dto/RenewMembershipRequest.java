package com.cesde.project_spring_boot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RenewMembershipRequest {

    @NotNull(message = "El ID de la membresía actual es obligatorio")
    @Positive(message = "El ID debe ser positivo")
    private Long currentAssignmentId;

    @NotNull(message = "El ID del plan es obligatorio")
    @Positive(message = "El ID del plan debe ser positivo")
    private Long newPlanId;

    @NotNull(message = "El método de pago es obligatorio")
    private String paymentMethod;

    @NotNull(message = "La referencia de pago es obligatoria")
    private String paymentReference;

    // Getters y Setters
    public Long getCurrentAssignmentId() { return currentAssignmentId; }
    public void setCurrentAssignmentId(Long currentAssignmentId) { this.currentAssignmentId = currentAssignmentId; }

    public Long getNewPlanId() { return newPlanId; }
    public void setNewPlanId(Long newPlanId) { this.newPlanId = newPlanId; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }
}