package com.cesde.project_spring_boot.dto;

import java.time.LocalDate;

public class MembershipAssignmentResponse {
    private Long id;
    private Long memberId;
    private String memberName;
    private String memberDocument;
    private Long planId;
    private String planName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String paymentMethod;
    private String paymentReference;
    private LocalDate assignmentDate;
    private Long assignedBy;
    private String durationText;
    private String priceFormatted;

    public MembershipAssignmentResponse(Long id, Long memberId, String memberName, String memberDocument,
                                        Long planId, String planName, LocalDate startDate, LocalDate endDate,
                                        String status, String paymentMethod, String paymentReference,
                                        LocalDate assignmentDate, Long assignedBy,
                                        Integer durationDays, Double price) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberDocument = memberDocument;
        this.planId = planId;
        this.planName = planName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentReference = paymentReference;
        this.assignmentDate = assignmentDate;
        this.assignedBy = assignedBy;
        this.durationText = formatDuration(durationDays);
        this.priceFormatted = "$" + price;
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
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public String getMemberDocument() { return memberDocument; }
    public void setMemberDocument(String memberDocument) { this.memberDocument = memberDocument; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }

    public LocalDate getAssignmentDate() { return assignmentDate; }
    public void setAssignmentDate(LocalDate assignmentDate) { this.assignmentDate = assignmentDate; }

    public Long getAssignedBy() { return assignedBy; }
    public void setAssignedBy(Long assignedBy) { this.assignedBy = assignedBy; }

    public String getDurationText() { return durationText; }
    public void setDurationText(String durationText) { this.durationText = durationText; }

    public String getPriceFormatted() { return priceFormatted; }
    public void setPriceFormatted(String priceFormatted) { this.priceFormatted = priceFormatted; }
}