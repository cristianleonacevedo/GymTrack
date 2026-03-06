package com.cesde.project_spring_boot.dto;

import java.time.LocalDate;

public class RenewMembershipResponse {
    private Long newAssignmentId;
    private Long memberId;
    private String memberName;
    private Long planId;
    private String planName;
    private LocalDate previousEndDate;
    private LocalDate newStartDate;
    private LocalDate newEndDate;
    private String status;
    private String paymentMethod;
    private String paymentReference;
    private String priceFormatted;
    private String durationText;
    private Boolean wasExpired;

    public RenewMembershipResponse(Long newAssignmentId, Long memberId, String memberName,
                                   Long planId, String planName, LocalDate previousEndDate,
                                   LocalDate newStartDate, LocalDate newEndDate, String status,
                                   String paymentMethod, String paymentReference,
                                   Double price, Integer durationDays, Boolean wasExpired) {
        this.newAssignmentId = newAssignmentId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.planId = planId;
        this.planName = planName;
        this.previousEndDate = previousEndDate;
        this.newStartDate = newStartDate;
        this.newEndDate = newEndDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentReference = paymentReference;
        this.priceFormatted = "$" + price;
        this.durationText = formatDuration(durationDays);
        this.wasExpired = wasExpired;
    }

    private String formatDuration(Integer days) {
        if (days == null) return "";
        if (days == 30) return "1 mes";
        if (days == 60) return "2 meses";
        if (days == 90) return "3 meses";
        if (days == 180) return "6 meses";
        if (days == 365) return "1 año";
        return days + " días";
    }

    // Getters y Setters
    public Long getNewAssignmentId() { return newAssignmentId; }
    public void setNewAssignmentId(Long newAssignmentId) { this.newAssignmentId = newAssignmentId; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }

    public LocalDate getPreviousEndDate() { return previousEndDate; }
    public void setPreviousEndDate(LocalDate previousEndDate) { this.previousEndDate = previousEndDate; }

    public LocalDate getNewStartDate() { return newStartDate; }
    public void setNewStartDate(LocalDate newStartDate) { this.newStartDate = newStartDate; }

    public LocalDate getNewEndDate() { return newEndDate; }
    public void setNewEndDate(LocalDate newEndDate) { this.newEndDate = newEndDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }

    public String getPriceFormatted() { return priceFormatted; }
    public void setPriceFormatted(String priceFormatted) { this.priceFormatted = priceFormatted; }

    public String getDurationText() { return durationText; }
    public void setDurationText(String durationText) { this.durationText = durationText; }

    public Boolean getWasExpired() { return wasExpired; }
    public void setWasExpired(Boolean wasExpired) { this.wasExpired = wasExpired; }
}