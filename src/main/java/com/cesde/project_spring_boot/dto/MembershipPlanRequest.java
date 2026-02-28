package com.cesde.project_spring_boot.dto;

import jakarta.validation.constraints.*;
import java.math.*;

public class MembershipPlanRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Integer durationDays;

    @NotNull
    private BigDecimal price;

    private Integer maxGroupClasses;

    @NotNull
    private Boolean includesLocker;

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
}
