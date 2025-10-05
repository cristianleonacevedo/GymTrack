package com.cesde.project_spring_boot.controller;

import com.cesde.project_spring_boot.dto.InventoryDTO;
import com.cesde.project_spring_boot.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // 📦 Obtener todos los productos
    @GetMapping
    public List<InventoryDTO> getAll() {
        return inventoryService.getAll();
    }

    // 🔍 Obtener un producto por ID
    @GetMapping("/{id}")
    public InventoryDTO getById(@PathVariable Long id) {
        return inventoryService.getById(id);
    }

    // ➕ Crear producto
    @PostMapping
    public InventoryDTO save(@RequestBody InventoryDTO dto) {
        return inventoryService.save(dto);
    }

    // ✏️ Actualizar producto
    @PutMapping("/{id}")
    public InventoryDTO update(@PathVariable Long id, @RequestBody InventoryDTO dto) {
        return inventoryService.update(id, dto);
    }

    // ❌ Eliminar producto
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        inventoryService.delete(id);
    }
}
