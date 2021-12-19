
/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic Pantalla LCD', 299, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 745, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Apple iPhone 12', 1137, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Notebook Z110', 1399, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280', 249, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Orbea Bicicleta 26', 754, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Mica Comoda 5 Cajones', 1600, NOW());

INSERT INTO usuarios (id,username,password,enabled) VALUES (1, 'david', '$2a$10$25wfKDCGVBog9aXMS7OZTe7yAyvyliXfW1UvcOXHp1aMuY7nOhf1a', 1);
INSERT INTO usuarios (id,username,password,enabled) VALUES (2, 'rodrigo', '$2a$10$1GUaAfTjNo2VQBWTnAmAhufdZgg5kYSAloG/bAYwoUnc/z1jr1gPq', 1);
INSERT INTO usuarios (id,username,password,enabled) VALUES (3, 'admin', '$2a$10$vFhEXOqhiu7pBmt2zZU0RODHFFc43AmWe8p3uppLE4ZuQA7cm9A.2', 1);


INSERT INTO roles_usuario (user_id, role_code) VALUES (1, 'ROLE_USER');
INSERT INTO roles_usuario (user_id, role_code) VALUES (2, 'ROLE_USER');
INSERT INTO roles_usuario (user_id, role_code) VALUES (3, 'ROLE_USER');
INSERT INTO roles_usuario (user_id, role_code) VALUES (3, 'ROLE_ADMIN');






