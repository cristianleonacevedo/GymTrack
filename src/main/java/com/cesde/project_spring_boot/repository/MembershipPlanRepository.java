package com.cesde.project_spring_boot.repository;

import com.cesde.project_spring_boot.model.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {

    List<MembershipPlan> findByActiveTrue();

    boolean existsByName(String name);
}

