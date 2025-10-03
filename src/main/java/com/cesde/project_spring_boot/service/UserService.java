package com.cesde.project_spring_boot.service;

import com.cesde.project_spring_boot.dto.UserDTO;
import com.cesde.project_spring_boot.model.User;
import com.cesde.project_spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UserService - Contiene la lógica de negocio para usuarios
 *
 * @Service: Le dice a Spring que esta es una clase de servicio
 * Aquí van las reglas de negocio, validaciones, etc.
 */
@Service
public class UserService {

    // Inyección de dependencia - Spring nos da una instancia del repository
    @Autowired
    private UserRepository userRepository;

    // ========================================
    // MÉTODOS BÁSICOS CRUD
    // ========================================

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<UserDTO> getAllUsersWithPaging(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserDTO> userDTOs = usersPage.getContent().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(userDTOs, pageable, usersPage.getTotalElements());
    }

    public UserDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return UserDTO.fromEntity(userOptional.get());
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + userDTO.getEmail());
        }
        User user = userDTO.toEntity();
        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (!user.getEmail().equals(userDTO.getEmail()) &&
                    userRepository.existsByEmail(userDTO.getEmail())) {
                throw new RuntimeException("Ya existe un usuario con el email: " + userDTO.getEmail());
            }

            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());

            User updatedUser = userRepository.save(user);
            return UserDTO.fromEntity(updatedUser);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    // ========================================
    // MÉTODOS DE BÚSQUEDA
    // ========================================

    public UserDTO getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return UserDTO.fromEntity(userOptional.get());
        } else {
            throw new RuntimeException("Usuario no encontrado con email: " + email);
        }
    }

    public List<UserDTO> searchUsersByName(String name) {
        List<User> users = userRepository.findByNameContaining(name);
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<UserDTO> searchUsersByNameWithPaging(String name, Pageable pageable) {
        Page<User> usersPage = userRepository.findByNameContainingWithPaging(name, pageable);
        List<UserDTO> userDTOs = usersPage.getContent().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(userDTOs, pageable, usersPage.getTotalElements());
    }

    public List<UserDTO> getUsersByFirstNameStarting(String firstName) {
        List<User> users = userRepository.findByFirstNameContainingIgnoreCase(firstName);
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUsersByEmailDomain(String domain) {
        List<User> users = userRepository.findByEmailDomainNative(domain);
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getRecentUsers(int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        List<User> users = userRepository.findByCreatedAtAfter(since);
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * ✅ NUEVO MÉTODO: Buscar usuarios por apellido
     */
    public List<UserDTO> searchUsersByLastName(String lastName) {
        List<User> users = userRepository.findByLastNameIgnoreCaseContaining(lastName);
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // ========================================
    // MÉTODOS DE ESTADÍSTICAS
    // ========================================

    public long getTotalUsersCount() {
        return userRepository.count();
    }

    public long countUsersByEmailDomain(String domain) {
        return userRepository.countByEmailDomain(domain);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
