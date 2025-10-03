package com.cesde.project_spring_boot.repository;

import com.cesde.project_spring_boot.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * UserRepository - Interfaz para acceder a los datos de usuarios
 *
 * ✅ Usamos JpaRepository porque nos da:
 * - Métodos CRUD básicos: save(), findById(), findAll(), deleteById()
 * - Operaciones en lote: saveAll(), deleteAllInBatch()
 * - Paginación automática: findAll(Pageable)
 * - Flush: saveAndFlush(), flush()
 * - Y mucho más...
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ========================================
    // MÉTODOS AUTOMÁTICOS DE SPRING DATA JPA
    // ========================================

    /** Buscar usuario por email */
    Optional<User> findByEmail(String email);

    /** Verificar si existe un usuario con ese email */
    boolean existsByEmail(String email);

    /** Buscar usuarios por nombre (ignorando mayúsculas/minúsculas) */
    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    /** Buscar usuarios por apellido que empiece con... */
    List<User> findByLastNameStartingWith(String lastName);

    /** Buscar usuarios ordenados por nombre */
    List<User> findAllByOrderByFirstNameAsc();

    /** Buscar usuarios creados después de una fecha */
    List<User> findByCreatedAtAfter(LocalDateTime date);

    // ========================================
    // CONSULTAS PERSONALIZADAS CON @Query
    // ========================================

    /** Buscar por nombre completo usando JPQL */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContaining(@Param("name") String name);

    /** Buscar con paginación - JPQL */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<User> findByNameContainingWithPaging(@Param("name") String name, Pageable pageable);

    /** Consulta SQL nativa para emails por dominio */
    @Query(value = "SELECT * FROM users WHERE email LIKE CONCAT('%', :domain)", nativeQuery = true)
    List<User> findByEmailDomainNative(@Param("domain") String domain);

    /** Contar usuarios por dominio de email */
    @Query("SELECT COUNT(u) FROM User u WHERE u.email LIKE CONCAT('%@', :domain)")
    Long countByEmailDomain(@Param("domain") String domain);

    // ========================================
    // MÉTODOS CON PAGINACIÓN
    // ========================================

    /** Buscar por email con paginación */
    Page<User> findByEmailContaining(String email, Pageable pageable);

    // ========================================
    // MÉTODOS PERSONALIZADOS EXTRA
    // ========================================

    /**
     * Buscar usuarios por apellido (ignorando mayúsculas y minúsculas)
     * Genera: SELECT * FROM users WHERE LOWER(last_name) LIKE LOWER('%?%')
     */
    List<User> findByLastNameIgnoreCaseContaining(String lastName);
}
