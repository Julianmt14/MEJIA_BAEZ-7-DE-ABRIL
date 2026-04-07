-- ============================================
-- DML: DATA MANIPULATION LANGUAGE
-- Datos de prueba para el Sistema Académico
-- ============================================

-- ============================================
-- INSERT: Docentes
-- ============================================
INSERT INTO docente (nombre, apellido, email, especialidad) VALUES
('Carlos',   'Mendoza',    'carlos.mendoza@universidad.edu',   'Ingeniería de Software'),
('María',    'García',     'maria.garcia@universidad.edu',     'Bases de Datos'),
('Roberto',  'Fernández',  'roberto.fernandez@universidad.edu','Inteligencia Artificial'),
('Ana',      'López',      'ana.lopez@universidad.edu',        'Redes y Telecomunicaciones'),
('Luis',     'Martínez',   'luis.martinez@universidad.edu',    'Matemáticas Aplicadas');

-- ============================================
-- INSERT: Cursos (cada curso pertenece a un docente)
-- ============================================
INSERT INTO curso (nombre, descripcion, creditos, docente_id) VALUES
('Programación Orientada a Objetos', 'Fundamentos de POO con Java',             4, 1),
('Estructuras de Datos',             'Listas, pilas, colas, árboles y grafos',   4, 1),
('Bases de Datos I',                 'Modelo relacional y SQL',                  3, 2),
('Bases de Datos II',                'Diseño avanzado y optimización',           3, 2),
('Machine Learning',                 'Algoritmos de aprendizaje automático',     4, 3),
('Deep Learning',                    'Redes neuronales profundas',               4, 3),
('Redes de Computadoras',            'Protocolos TCP/IP y arquitectura de red',  3, 4),
('Cálculo Diferencial',              'Límites, derivadas y aplicaciones',        5, 5),
('Álgebra Lineal',                   'Espacios vectoriales y transformaciones',  4, 5);

-- ============================================
-- INSERT: Estudiantes
-- ============================================
INSERT INTO estudiante (nombre, apellido, email, fecha_nacimiento) VALUES
('Juan',      'Pérez',      'juan.perez@estudiante.edu',      '2002-03-15'),
('Laura',     'Ramírez',    'laura.ramirez@estudiante.edu',    '2001-07-22'),
('Diego',     'Hernández',  'diego.hernandez@estudiante.edu',  '2003-01-10'),
('Sofía',     'Torres',     'sofia.torres@estudiante.edu',     '2002-11-05'),
('Andrés',    'Vargas',     'andres.vargas@estudiante.edu',    '2001-09-18'),
('Valentina', 'Rojas',      'valentina.rojas@estudiante.edu',  '2003-06-30');

-- ============================================
-- INSERT: Carnets (uno por estudiante — relación 1:1)
-- ============================================
INSERT INTO carnet (codigo, fecha_emision, fecha_expiracion, estudiante_id) VALUES
('CRN-2024-001', '2024-01-15', '2028-01-15', 1),
('CRN-2024-002', '2024-01-15', '2028-01-15', 2),
('CRN-2024-003', '2024-01-15', '2028-01-15', 3),
('CRN-2024-004', '2024-02-01', '2028-02-01', 4),
('CRN-2024-005', '2024-02-01', '2028-02-01', 5),
('CRN-2024-006', '2024-02-15', '2028-02-15', 6);

-- ============================================
-- INSERT: Matrículas (relación N:M entre estudiante y curso)
-- ============================================
INSERT INTO estudiante_curso (estudiante_id, curso_id) VALUES
(1, 1), (1, 3), (1, 8),   -- Juan: POO, BD I, Cálculo
(2, 1), (2, 2), (2, 5),   -- Laura: POO, Estructuras, ML
(3, 3), (3, 4), (3, 9),   -- Diego: BD I, BD II, Álgebra
(4, 5), (4, 6), (4, 7),   -- Sofía: ML, Deep Learning, Redes
(5, 1), (5, 8), (5, 9),   -- Andrés: POO, Cálculo, Álgebra
(6, 2), (6, 3), (6, 7);   -- Valentina: Estructuras, BD I, Redes
