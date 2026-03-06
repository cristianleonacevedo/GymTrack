package com.cesde.project_spring_boot.repository;

import com.cesde.project_spring_boot.model.MembershipAssignment;
import com.cesde.project_spring_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface MembershipAssignmentRepository extends JpaRepository<MembershipAssignment, Long> {

    List<MembershipAssignment> findByUserAndStatus(User user, String status);

    @Query("SELECT ma FROM MembershipAssignment ma WHERE ma.user.id = :userId AND ma.status = 'ACTIVA' ORDER BY ma.endDate DESC")
    List<MembershipAssignment> findActiveByUserId(@Param("userId") Long userId);

    @Query("SELECT ma FROM MembershipAssignment ma WHERE ma.user.id = :userId AND ma.status = 'ACTIVA' AND ma.endDate >= :date")
    List<MembershipAssignment> findActiveMembershipsAfterDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    // NUEVOS MÉTODOS PARA RENOVACIÓN
    @Query("SELECT ma FROM MembershipAssignment ma WHERE ma.user.id = :userId AND ma.status = 'ACTIVA' AND ma.endDate <= :maxDate")
    List<MembershipAssignment> findExpiringOrExpired(@Param("userId") Long userId, @Param("maxDate") LocalDate maxDate);

    @Query("SELECT ma FROM MembershipAssignment ma WHERE ma.user.id = :userId ORDER BY ma.endDate DESC")
    List<MembershipAssignment> findLastByUserId(@Param("userId") Long userId);
}