package com.cesde.project_spring_boot.model;

import jakarta.persistence.*;
import java.math.*;

    @Entity
    @Table(name = "membership_plans")
    public class MembershipPlan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String name;

        @Column(length = 500)
        private String description;

        @Column(nullable = false)
        private Integer durationDays;

        @Column(nullable = false)
        private BigDecimal price;

        // null = ilimitadas
        private Integer maxGroupClasses;

        @Column(nullable = false)
        private Boolean includesLocker;

        @Column(nullable = false)
        private Boolean active = true;

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
