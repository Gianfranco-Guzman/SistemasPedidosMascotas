# 🐾 Sistema de Gestión de Pedidos de Comida para Mascotas

Este proyecto es una aplicación de consola desarrollada en Java que permite gestionar clientes, productos y pedidos de comida para mascotas. Utiliza JDBC con base de datos H2 en modo archivo, y sigue una arquitectura en capas utilizando el patrón DAO.

## 🚀 Funcionalidades

- Gestión de clientes:
  - Agregar, listar, buscar, actualizar y eliminar clientes.
- Gestión de productos:
  - Agregar, listar, buscar, actualizar y eliminar productos.
- Gestión de pedidos:
  - Crear pedidos con selección de cliente y productos.
  - Listar todos los pedidos.
  - Consultar detalles de un pedido.
  - Eliminar pedidos.
- Datos de Prueba:
  - Cuenta con datos que se crean automaticamente para probar las funcionales desde un comienzo.

## 🧱 Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes:

- `org.example.main`: Contiene la clase principal `Main` que lanza el menú interactivo.
- `org.example.model`: Contiene las clases modelo `Cliente`, `Producto`, `Pedido`, `DetallePedido`.
- `org.example.dao`: Contiene las interfaces y clases DAO (`ClienteDAO`, `ProductoDAO`, `PedidoDAO`, etc.).
- `org.example.util`: Contiene la clase `DBUtil` para la conexión a la base de datos.

## 🛠️ Tecnologías utilizadas

- Java 17+
- Gradle
- JDBC
- H2 Database (modo archivo)
- Log4j (para logging)
- Arquitectura en capas + patrón DAO

## 🗂️ Base de Datos

- El archivo de base H2 se crea automáticamente en el directorio raíz con el nombre: `base_mascotas.mv.db`.
- No requiere instalación adicional de servidor de base de datos.

## ▶️ Ejecución

1. Clonar o descargar el proyecto.
2. Abrir con IntelliJ o cualquier IDE compatible con Gradle.
3. Ejecutar la clase `Main.java` desde `org.example.main`.

## 📋 Requisitos

- Java JDK 17 o superior.
- Gradle (el proyecto incluye `build.gradle`).
- IDE recomendado: NetBeans.

## 📝 Notas

- La aplicación corre por consola.
- Incluye validaciones básicas (campos obligatorios, número válido, email válido, etc.).
- El sistema se puede ampliar fácilmente a una versión con interfaz gráfica (Swing/JavaFX) o web.

---

📫 Desarrollado por [Gianfranco Guzman]



