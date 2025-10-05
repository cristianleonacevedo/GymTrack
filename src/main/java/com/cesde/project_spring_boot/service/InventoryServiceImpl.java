package com.cesde.project_spring_boot.service;

import com.cesde.project_spring_boot.dto.InventoryDTO;
import com.cesde.project_spring_boot.model.Inventory;
import com.cesde.project_spring_boot.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<InventoryDTO> getAll() {
        return inventoryRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryDTO getById(Long id) {
        return inventoryRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public InventoryDTO save(InventoryDTO dto) {
        Inventory inventory = toEntity(dto);
        return toDTO(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryDTO update(Long id, InventoryDTO dto) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado con id " + id));

        inventory.setItemName(dto.getItemName());
        inventory.setQuantity(dto.getQuantity());
        inventory.setPrice(dto.getPrice());

        return toDTO(inventoryRepository.save(inventory));
    }

    @Override
    public void delete(Long id) {
        inventoryRepository.deleteById(id);
    }

    // =====================
    // 🔄 Métodos de mapeo
    // =====================
    private InventoryDTO toDTO(Inventory i) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(i.getId());
        dto.setItemName(i.getItemName());
        dto.setQuantity(i.getQuantity());
        dto.setPrice(i.getPrice());
        return dto;
    }

    private Inventory toEntity(InventoryDTO dto) {
        Inventory i = new Inventory();
        i.setId(dto.getId());
        i.setItemName(dto.getItemName());
        i.setQuantity(dto.getQuantity());
        i.setPrice(dto.getPrice());
        return i;
    }
}
