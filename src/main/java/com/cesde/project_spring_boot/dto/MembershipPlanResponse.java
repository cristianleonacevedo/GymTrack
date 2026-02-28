package com.cesde.project_spring_boot.dto;

import java.math.*;

public class MembershipPlanResponse {

    private Long id;
    private String name;
    private String description;
    private Integer durationDays;
    private BigDecimal price;
    private Integer maxGroupClasses;
    private Boolean includesLocker;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getMaxGroupClasses() {
        return maxGroupClasses;
    }

    public void setMaxGroupClasses(Integer maxGroupClasses) {
        this.maxGroupClasses = maxGroupClasses;
    }

    public Boolean getIncludesLocker() {
        return includesLocker;
    }

    public void setIncludesLocker(Boolean includesLocker) {
        this.includesLocker = includesLocker;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
