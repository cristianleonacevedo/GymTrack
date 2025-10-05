package com.cesde.project_spring_boot.repository;

import com.cesde.project_spring_boot.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
