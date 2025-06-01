SET REFERENTIAL_INTEGRITY FALSE;

DROP TABLE IF EXISTS detalle_pedido;
DROP TABLE IF EXISTS pedido;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS cliente;

SET REFERENTIAL_INTEGRITY TRUE;

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo_animal VARCHAR(50),
    peso_kg DOUBLE,
    precio DOUBLE
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    fecha DATE NOT NULL,
    total DOUBLE,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
);

CREATE TABLE detalle_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DOUBLE,
    FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES producto(id) ON DELETE CASCADE
);

INSERT INTO cliente (nombre, apellido, email) VALUES
('Gian', 'Guzman', 'gian@gmail.com'),
('Alexandra', 'Filchel', 'ale@gmail.com'),
('Cristina', 'Krahulik', 'cris@gmail.com');

INSERT INTO producto (nombre, tipo_animal, peso_kg, precio) VALUES
('Dogui Adultos', 'Perro', 15.0, 4500.0),
('Cat Chow Gatitos', 'Gato', 10.0, 5800.0),
('Royal Canin Mini', 'Perro', 7.5, 8700.0),
('Whiskas Adultos', 'Gato', 8.0, 5100.0);

INSERT INTO pedido (cliente_id, fecha, total) VALUES
(1, CURRENT_DATE(), 4500.0 * 2 + 8700.0),
(2, DATEADD('DAY', -1, CURRENT_DATE()), 5100.0),
(3, DATEADD('DAY', -2, CURRENT_DATE()), 3 * 5800.0);

INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, subtotal) VALUES
(1, 1, 2, 2 * 4500.0),
(1, 3, 1, 8700.0),
(2, 4, 1, 5100.0),
(3, 2, 3, 3 * 5800.0);
