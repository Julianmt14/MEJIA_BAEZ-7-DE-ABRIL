package com.academico.sistema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * @SpringBootApplication combina:
 *   - @Configuration: define esta clase como fuente de beans
 *   - @EnableAutoConfiguration: auto-configura Spring según el classpath
 *   - @ComponentScan: escanea componentes en este paquete y sub-paquetes
 */
@SpringBootApplication
public class SistemaAcademicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaAcademicoApplication.class, args);
    }
}
