-- ============================================
-- DDL: DATA DEFINITION LANGUAGE
-- Sistema Académico - Base de Datos PostgreSQL
-- ============================================

-- Crear la base de datos (ejecutar antes de conectar)
-- CREATE DATABASE sistema_academico;

-- ============================================
-- TABLA: docente
-- Almacena información de los profesores
-- ============================================
CREATE TABLE IF NOT EXISTS docente (
    id          BIGSERIAL       PRIMARY KEY,
    nombre      VARCHAR(100)    NOT NULL,
    apellido    VARCHAR(100)    NOT NULL,
    email       VARCHAR(150)    UNIQUE NOT NULL,
    especialidad VARCHAR(150)   NOT NULL
);

-- ============================================
-- TABLA: curso
-- Almacena información de las materias
-- Relación: ManyToOne con docente (un docente tiene muchos cursos)
-- ============================================
CREATE TABLE IF NOT EXISTS curso (
    id          BIGSERIAL       PRIMARY KEY,
    nombre      VARCHAR(150)    NOT NULL,
    descripcion TEXT,
    creditos    INTEGER         NOT NULL CHECK (creditos > 0),
    docente_id  BIGINT          NOT NULL,
    CONSTRAINT fk_curso_docente FOREIGN KEY (docente_id)
        REFERENCES docente(id) ON DELETE CASCADE
);

-- ============================================
-- TABLA: estudiante
-- Almacena información de los alumnos
-- ============================================
CREATE TABLE IF NOT EXISTS estudiante (
    id                BIGSERIAL       PRIMARY KEY,
    nombre            VARCHAR(100)    NOT NULL,
    apellido          VARCHAR(100)    NOT NULL,
    email             VARCHAR(150)    UNIQUE NOT NULL,
    fecha_nacimiento  DATE            NOT NULL
);

-- ============================================
-- TABLA: carnet
-- Identificación del estudiante
-- Relación: OneToOne con estudiante
-- ============================================
CREATE TABLE IF NOT EXISTS carnet (
    id                BIGSERIAL       PRIMARY KEY,
    codigo            VARCHAR(50)     UNIQUE NOT NULL,
    fecha_emision     DATE            NOT NULL,
    fecha_expiracion  DATE            NOT NULL,
    estudiante_id     BIGINT          UNIQUE NOT NULL,
    CONSTRAINT fk_carnet_estudiante FOREIGN KEY (estudiante_id)
        REFERENCES estudiante(id) ON DELETE CASCADE
);

-- ============================================
-- TABLA INTERMEDIA: estudiante_curso
-- Relación: ManyToMany entre estudiante y curso
-- ============================================
CREATE TABLE IF NOT EXISTS estudiante_curso (
    estudiante_id  BIGINT NOT NULL,
    curso_id       BIGINT NOT NULL,
    PRIMARY KEY (estudiante_id, curso_id),
    CONSTRAINT fk_ec_estudiante FOREIGN KEY (estudiante_id)
        REFERENCES estudiante(id) ON DELETE CASCADE,
    CONSTRAINT fk_ec_curso FOREIGN KEY (curso_id)
        REFERENCES curso(id) ON DELETE CASCADE
);

-- ============================================
-- ÍNDICES para mejorar el rendimiento
-- ============================================
CREATE INDEX IF NOT EXISTS idx_curso_docente ON curso(docente_id);
CREATE INDEX IF NOT EXISTS idx_carnet_estudiante ON carnet(estudiante_id);
