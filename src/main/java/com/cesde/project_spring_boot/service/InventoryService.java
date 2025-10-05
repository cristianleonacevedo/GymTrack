package com.cesde.project_spring_boot.service;

import com.cesde.project_spring_boot.dto.InventoryDTO;
import java.util.List;

public interface InventoryService {

    List<InventoryDTO> getAll();
    InventoryDTO getById(Long id);
    InventoryDTO save(InventoryDTO dto);
    InventoryDTO update(Long id, InventoryDTO dto);
    void delete(Long id);
}
