package com.cesde.project_spring_boot.dto;

import java.math.BigDecimal;

public class MembershipPlanResponse {
    private Long id;
    private String name;
    private String description;
    private Integer durationDays;
    private BigDecimal price;
    private Integer maxGroupClasses;
    private Boolean includesLocker;
    private Boolean active;
    private String durationText;
    private String priceFormatted;
    private String classesText;

    public MembershipPlanResponse(Long id, String name, String description,
                                  Integer durationDays, BigDecimal price,
                                  Integer maxGroupClasses, Boolean includesLocker,
                                  Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationDays = durationDays;
        this.price = price;
        this.maxGroupClasses = maxGroupClasses;
        this.includesLocker = includesLocker;
        this.active = active;

        // Campos calculados para mostrar en el frontend
        this.durationText = formatDuration(durationDays);
        this.priceFormatted = "$" + price;
        this.classesText = formatClasses(maxGroupClasses);
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

    private String formatClasses(Integer classes) {
        if (classes == null) return "Ilimitadas";
        if (classes == 0) return "No incluye";
        if (classes == 1) return "1 clase grupal";
        return classes + " clases grupales";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getMaxGroupClasses() { return maxGroupClasses; }
    public void setMaxGroupClasses(Integer maxGroupClasses) { this.maxGroupClasses = maxGroupClasses; }

    public Boolean getIncludesLocker() { return includesLocker; }
    public void setIncludesLocker(Boolean includesLocker) { this.includesLocker = includesLocker; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public String getDurationText() { return durationText; }
    public void setDurationText(String durationText) { this.durationText = durationText; }

    public String getPriceFormatted() { return priceFormatted; }
    public void setPriceFormatted(String priceFormatted) { this.priceFormatted = priceFormatted; }

    public String getClassesText() { return classesText; }
    public void setClassesText(String classesText) { this.classesText = classesText; }
}