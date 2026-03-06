-- ========================================
-- 1. PRIMERO: Insertar usuarios de prueba
-- ========================================
-- Nota: Las contraseñas deben estar encriptadas con BCrypt
-- La contraseña "123456" encriptada es: $2a$10$NkM3C8YqJkZQx8qJkZQx8uJkZQx8qJkZQx8qJkZQx8
INSERT INTO users (nombre, apellido, documento, email, password, rol, estado_membresia, membresia_activa) VALUES
                                                                                                              ('Juan', 'Pérez', '12345678', 'juan@email.com', '$2a$10$NkM3C8YqJkZQx8qJkZQx8uJkZQx8qJkZQx8qJkZQx8', 'MIEMBRO', 'ACTIVA', true),
                                                                                                              ('Ana', 'García', '87654321', 'ana@email.com', '$2a$10$NkM3C8YqJkZQx8qJkZQx8uJkZQx8qJkZQx8qJkZQx8', 'MIEMBRO', 'ACTIVA', true),
                                                                                                              ('Carlos', 'López', '56781234', 'carlos@email.com', '$2a$10$NkM3C8YqJkZQx8qJkZQx8uJkZQx8qJkZQx8qJkZQx8', 'MIEMBRO', 'ACTIVA', true),
                                                                                                              ('Recepcionista', 'Uno', '99999999', 'recepcion@email.com', '$2a$10$NkM3C8YqJkZQx8qJkZQx8uJkZQx8qJkZQx8qJkZQx8', 'RECEPCIONISTA', 'ACTIVA', false);

-- ========================================
-- 2. Insertar planes de membresía activos
-- ========================================
INSERT INTO membership_plans (name, description, duration_days, price, max_group_classes, includes_locker, active) VALUES
                                                                                                                       ('Plan Básico', 'Acceso a sala de pesas y máquinas cardiovasculares', 30, 50000, 0, false, true),
                                                                                                                       ('Plan Estándar', 'Plan básico + 4 clases grupales al mes', 30, 75000, 4, false, true),
                                                                                                                       ('Plan Premium', 'Todo incluido + locker + clases ilimitadas', 30, 100000, null, true, true),
                                                                                                                       ('Plan Anual', 'Ahorra con el plan anual', 365, 900000, null, true, true),
                                                                                                                       ('Plan Quincenal', 'Plan de prueba', 15, 30000, 2, false, false);

-- ========================================
-- 3. Insertar asignaciones de prueba (para US-006)
-- ========================================
INSERT INTO membership_assignments
(user_id, plan_id, start_date, end_date, status, payment_method, payment_reference, assignment_date, assigned_by)
VALUES
    (1, 2, '2026-03-01', '2026-03-30', 'ACTIVA', 'EFECTIVO', 'REC-001', '2026-03-01', 4),  -- Asignado por recepcionista (ID 4)
    (3, 1, '2026-02-15', '2026-03-14', 'ACTIVA', 'TRANSFERENCIA', 'TR-789', '2026-02-15', 4);

-- ========================================
-- 4. Insertar asignaciones para probar RENOVACIÓN (US-007)
-- ========================================
-- Membresía que vence en 3 días (renovable) - considerando que hoy es 2026-03-06
INSERT INTO membership_assignments
(user_id, plan_id, start_date, end_date, status, payment_method, payment_reference, assignment_date, assigned_by)
VALUES
    (2, 2, '2026-02-06', '2026-03-09', 'ACTIVA', 'EFECTIVO', 'RENOV-001', '2026-02-06', 4);

-- Membresía vencida hace 5 días (renovable) - hoy es 2026-03-06, venció el 2026-03-01
INSERT INTO membership_assignments
(user_id, plan_id, start_date, end_date, status, payment_method, payment_reference, assignment_date, assigned_by)
VALUES
    (1, 1, '2026-02-01', '2026-03-01', 'ACTIVA', 'TARJETA', 'RENOV-002', '2026-02-01', 4);

-- Membresía vencida hace 40 días (NO renovable) - venció el 2026-01-25
INSERT INTO membership_assignments
(user_id, plan_id, start_date, end_date, status, payment_method, payment_reference, assignment_date, assigned_by)
VALUES
    (3, 3, '2025-12-01', '2026-01-25', 'ACTIVA', 'TRANSFERENCIA', 'RENOV-003', '2025-12-01', 4);

-- Membresía que vence en 10 días (NO renovable aún) - vence el 2026-03-16
INSERT INTO membership_assignments
(user_id, plan_id, start_date, end_date, status, payment_method, payment_reference, assignment_date, assigned_by)
VALUES
    (4, 2, '2026-02-16', '2026-03-16', 'ACTIVA', 'EFECTIVO', 'RENOV-004', '2026-02-16', 4);