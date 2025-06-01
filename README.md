# 🐾 Sistema de Gestión de Pedidos de Comida para Mascotas

Este proyecto es una aplicación de consola desarrollada en Java que permite gestionar clientes, productos y pedidos de comida para mascotas. Utiliza JDBC con base de datos H2 en modo archivo, y sigue una arquitectura en capas utilizando el patrón DAO.

## 🚀 Funcionalidades

- **Gestión de clientes:**
  - Agregar, listar, buscar, actualizar y eliminar clientes.
- **Gestión de productos:**
  - Agregar, listar, buscar, actualizar y eliminar productos.
- **Gestión de pedidos:**
  - Crear pedidos con selección de cliente y productos.
  - Listar todos los pedidos.
  - Consultar detalles de un pedido.
  - Eliminar pedidos.
- **Datos de prueba:**
  - Se cargan automáticamente desde el archivo `DatosPruebas.sql`.

## 🧱 Estructura del Proyecto

- `org.example.main`: Clase principal `Main` con menú interactivo.
- `org.example.model`: Modelos de datos (`Cliente`, `Producto`, `Pedido`, `DetallePedido`).
- `org.example.dao`: Interfaces DAO y clases DAO.
- `org.example.util`: Utilidades (`DBUtil`, `Log`, scripts SQL y configuración de logging).

## 🛠️ Tecnologías utilizadas

- Java 17+
- Gradle
- JDBC
- H2 Database (modo archivo)
- Log4j 2
- Arquitectura en capas + DAO

## 🗂️ Base de Datos

- La base H2 se genera automáticamente como archivo `base_mascotas.mv.db`.
- No se necesita instalar un servidor externo de base de datos.

## ▶️ Ejecución

1. Clonar el repositorio.
2. Abrir el proyecto con un IDE compatible con Gradle (recomendado: **NetBeans**).
3. Ejecutar la clase `Main.java` desde `org.example.main`.

## 📋 Requisitos

- Java JDK 17 o superior.
- Gradle (el proyecto ya incluye `gradlew`).
- IDE recomendado: NetBeans (fue el más estable durante el desarrollo).

## 📝 Notas

- Aplicación por consola.
- Incluye validaciones básicas (campos obligatorios, tipo de datos, email válido).
- El sistema puede evolucionar a interfaz gráfica (Swing/JavaFX) o web.

---

📫 Desarrollado por [Gianfranco Guzmán](https://github.com/Gianfranco-Guzman)

