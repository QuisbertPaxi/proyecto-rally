CREATE TABLE `usuarios`(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `usuario` VARCHAR(255) NOT NULL,
    `nombre` VARCHAR(255) NOT NULL,
    `apellidos` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(255) NOT NULL,
    `fec_cre` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `fec_mod` TIMESTAMP NULL,
    `estado` VARCHAR(255) NOT NULL
);
ALTER TABLE
    `usuarios` ADD UNIQUE `usuarios_usuario_unique`(`usuario`);
ALTER TABLE
    `usuarios` ADD UNIQUE `usuarios_email_unique`(`email`);
CREATE TABLE `fotografias`(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_participante` BIGINT NOT NULL,
    `descripcion` TEXT NOT NULL,
    `titulo` TEXT NOT NULL,
    `link` TEXT NOT NULL,
    `fec_aprobacion` timestamp NULL,
    `usu_aprobacion` VARCHAR(255) NULL,
    `usu_cre` VARCHAR(255) NOT NULL,
    `fec_cre` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `usu_mod` VARCHAR(255) NULL,
    `fec_mod` TIMESTAMP NULL,
    `estado` VARCHAR(255) NOT NULL
);
CREATE TABLE `concurso`(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `descripcion` TEXT NOT NULL,
    `fecha_inicio_envio` DATE NOT NULL,
    `fecha_fin_envio` DATE NOT NULL,
    `fecha_inicio_votacion` DATE NOT NULL,
    `fecha_fin_votacion` DATE NOT NULL,
    `fecha_anuncio` DATE NOT NULL,
    `nro_fotografias` INT NOT NULL,
    `usu_cre` VARCHAR(255) NOT NULL,
    `fec_cre` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `usu_mod` VARCHAR(255) NULL,
    `fec_mod` TIMESTAMP NULL,
    `estado` VARCHAR(255) NOT NULL
);
CREATE TABLE `votos`(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_fotografia` BIGINT NOT NULL,
    `ip` VARCHAR(255) NOT NULL,
    `fec_cre` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE `votos` ADD UNIQUE `votos_ip_unique`(`ip`);

ALTER TABLE
    `votos` ADD CONSTRAINT `votos_id_fotografia_foreign` FOREIGN KEY(`id_fotografia`) REFERENCES `fotografias`(`id`);
ALTER TABLE
    `fotografias` ADD CONSTRAINT `fotografias_id_participante_foreign` FOREIGN KEY(`id_participante`) REFERENCES `usuarios`(`id`);

INSERT INTO concurso
(id, descripcion, fecha_inicio_envio, fecha_fin_envio, fecha_inicio_votacion, fecha_fin_votacion, fecha_anuncio, nro_fotografias, usu_cre, fec_cre, usu_mod, fec_mod, estado)
VALUES(1, 'Concurso de fotografía paisajística', '2025-05-31', '2025-06-09', '2025-06-10', '2025-06-19', '2025-06-21', 3, 'admin', '2025-06-05 14:28:52.000', 'dcastillo5', '2025-06-05 14:28:52.000', 'CREADO');

INSERT INTO usuarios
(id, apellidos, email, nombre, password, `role`, usuario, fec_cre, fec_mod, estado)
VALUES(0, '', 'admin@mail.com', 'admin', '$2a$10$hGaXeA3qNvekCDqtOtWHN.GRx3YLo0m1mP7/xJ15hiNhwBqr5gxtC', 'admin', 'admin', NOW(), NULL, 'CREADO');
