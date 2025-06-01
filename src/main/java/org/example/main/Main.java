package org.example.main;

import java.time.LocalDate;
import org.example.dao.impl.ClienteDAOImpl;
import org.example.dao.impl.ProductoDAOImpl;
import org.example.model.Cliente;
import org.example.model.Producto;
import org.example.dao.impl.PedidoDAOImpl;
import org.example.dao.impl.DetallePedidoDAOImpl;
import org.example.model.Pedido;
import org.example.model.DetallePedido;
import org.example.util.DBUtil;
import org.example.util.Log;

import java.util.List;
import java.util.Scanner;
import org.example.util.Log;

public class Main {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteDAOImpl clienteDAO = new ClienteDAOImpl();
    private static final ProductoDAOImpl productoDAO = new ProductoDAOImpl();
    private static final PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();
    private static final DetallePedidoDAOImpl detalleDAO = new DetallePedidoDAOImpl();

    public static void main(String[] args) {
        
        Log.info("Iniciando base de datos ...");
        DBUtil.inicializarBase();

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> gestionarClientes();
                case "2" -> gestionarProductos();
                case "3" -> gestionarPedidos();
                case "0" -> {
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        Log.info("Aplicación finalizada.");
    }

    private static void mostrarMenu() {
        System.out.println("\n========= MENÚ PRINCIPAL =========");
        System.out.println("1. Gestionar Clientes");
        System.out.println("2. Gestionar Productos");
        System.out.println("3. Gestionar Pedidos");
        System.out.println("0. Salir");
        System.out.println("==================================");
        
    }

    private static void gestionarClientes() {
    boolean volver = false;

    while (!volver) {
        System.out.println("\n--- Gestión de Clientes ---");
        System.out.println("1. Listar Clientes");
        System.out.println("2. Agregar Cliente");
        System.out.println("3. Eliminar Cliente");
        System.out.println("4. Actualizar Cliente");
        System.out.println("5. Buscar Cliente por ID");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");
        String opcion = scanner.nextLine().trim();

        switch (opcion) {
            case "1" -> {
                List<Cliente> clientes = clienteDAO.obtenerTodos();
                if (clientes.isEmpty()) {
                    System.out.println("No hay clientes cargados.");
                } else {
                    System.out.println("Clientes registrados:");
                    for (Cliente c : clientes) {
                        System.out.printf("ID: %d - %s %s (%s)%n",
                                c.getId(), c.getNombre(), c.getApellido(), c.getEmail());
                    }
                }
            }

            case "2" -> {
                String nombre = leerCampoObligatorio("Nombre");
                if (nombre == null ) break;
               
                String apellido = leerCampoObligatorio("Apellido");
                if (apellido == null) break;
                                
                System.out.print("Email: ");
                String email = scanner.nextLine().trim();
                if (!esEmailValido(email)) {
                    System.out.println("Email inválido.");
                    break;
                }

                Cliente nuevo = new Cliente(nombre, apellido, email);
                clienteDAO.guardar(nuevo);
                System.out.println("Cliente agregado correctamente.");
            }

            case "3" -> {
                System.out.print("ID del cliente a eliminar: ");
                int idEliminar = Integer.parseInt(scanner.nextLine().trim());
                Cliente existente = clienteDAO.obtenerPorId(idEliminar);
                if (existente != null) {
                    clienteDAO.eliminar(idEliminar);
                    System.out.println("Cliente eliminado.");
                } else {
                    System.out.println("No se encontró un cliente con ese ID.");
                }
            }

            case "4" -> {
                System.out.print("ID del cliente a actualizar: ");
                int idActualizar = Integer.parseInt(scanner.nextLine().trim());
                Cliente cliente = clienteDAO.obtenerPorId(idActualizar);
                if (cliente == null) {
                    System.out.println("No se encontró un cliente con ese ID.");
                    break;
                }

                System.out.println("Dejar en blanco para mantener el valor actual.");

                System.out.print("Nuevo nombre (" + cliente.getNombre() + "): ");
                String nuevoNombre = scanner.nextLine().trim();
                if (!esTextoVacio(nuevoNombre)) {
                    cliente.setNombre(nuevoNombre);
                }

                System.out.print("Nuevo apellido (" + cliente.getApellido() + "): ");
                String nuevoApellido = scanner.nextLine().trim();
                if (!esTextoVacio(nuevoApellido)) {
                    cliente.setApellido(nuevoApellido);
                }

                System.out.print("Nuevo email (" + cliente.getEmail() + "): ");
                String nuevoEmail = scanner.nextLine().trim();
                if (!esTextoVacio(nuevoEmail)) {
                    if (esEmailValido(nuevoEmail)) {
                        cliente.setEmail(nuevoEmail);
                    } else {
                        System.out.println("Email inválido. Se mantendrá el actual.");
                    }
                }

                clienteDAO.actualizar(cliente);
                System.out.println("Cliente actualizado correctamente.");
            }

            case "5" -> {
                System.out.print("ID del cliente a buscar: ");
                int idBuscar = Integer.parseInt(scanner.nextLine().trim());
                Cliente cliente = clienteDAO.obtenerPorId(idBuscar);
                if (cliente != null) {
                    System.out.printf("ID: %d - %s %s (%s)%n", cliente.getId(),
                            cliente.getNombre(), cliente.getApellido(), cliente.getEmail());
                } else {
                    System.out.println("No se encontró un cliente con ese ID.");
                }
            }

            case "0" -> volver = true;
            default -> System.out.println("Opción inválida.");
        }
    }
}


   private static void gestionarProductos() {
    boolean volver = false;

    while (!volver) {
        System.out.println("\n--- Gestión de Productos ---");
        System.out.println("1. Listar Productos");
        System.out.println("2. Agregar Producto");
        System.out.println("3. Eliminar Producto");
        System.out.println("4. Actualizar Producto");
        System.out.println("5. Buscar Producto por ID");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");
        String opcion = scanner.nextLine().trim();

        switch (opcion) {
            case "1" -> {
                List<Producto> productos = productoDAO.obtenerTodos();
                if (productos.isEmpty()) {
                    System.out.println("No hay productos cargados.");
                } else {
                    System.out.println("Productos disponibles:");
                    for (Producto p : productos) {
                        System.out.printf("ID: %d - %s (%.2fkg) - $%.2f - [%s]%n",
                                p.getId(), p.getNombre(), p.getPesoKg(), p.getPrecio(), p.getTipoAnimal());
                    }
                }
            }

            case "2" -> {
                String nombre = leerCampoObligatorio("Nombre");
                if (nombre == null) break;
                
                String tipoAnimal = leerCampoObligatorio("Tipo de animal");
                
                System.out.print("Peso (kg): ");
                double pesoKg = leerDoubleSeguro();

                System.out.print("Precio: ");
                double precio = leerDoubleSeguro();

                if (esTextoVacio(nombre) || esTextoVacio(tipoAnimal)) {
                    System.out.println("Todos los campos son obligatorios.");
                    break;
                }

                if (pesoKg <= 0 || precio <= 0) {
                    System.out.println("El peso y el precio deben ser mayores a 0.");
                    break;
                }

                Producto nuevo = new Producto(nombre, tipoAnimal, pesoKg, precio);
                productoDAO.guardar(nuevo);
                Log.info("Producto agregado: "+ nuevo.getNombre());
            }

            case "3" -> {
                System.out.print("ID del producto a eliminar: ");
                int idEliminar = Integer.parseInt(scanner.nextLine());

                Producto producto = productoDAO.obtenerPorId(idEliminar);
                if (producto != null) {
                    productoDAO.eliminar(idEliminar);
                    Log.info("Producto eliminado con ID: "+ idEliminar);
                } else {
                    System.out.println("No se encontró ningún producto con ese ID.");
                }
            }

            case "4" -> {
                System.out.print("ID del producto a actualizar: ");
                int idActualizar = Integer.parseInt(scanner.nextLine());
                Producto existente = productoDAO.obtenerPorId(idActualizar);

                if (existente == null) {
                    System.out.println("No se encontró ningún producto con ese ID.");
                    break;
                }

                String nuevoNombre = leerCampoObligatorio("Nuevo nombre (actual: " + existente.getNombre() + ")");
                if (nuevoNombre == null) break;

                String nuevoTipoAnimal = leerCampoObligatorio("Nuevo tipo de animal (actual: " + existente.getTipoAnimal() + ")");
                if (nuevoTipoAnimal == null) break;

                System.out.print("Nuevo peso (kg) (actual: " + existente.getPesoKg() + "): ");
                double nuevoPeso = leerDoubleSeguro();

                System.out.print("Nuevo precio (actual: $" + existente.getPrecio() + "): ");
                double nuevoPrecio = leerDoubleSeguro();

                if (esTextoVacio(nuevoNombre) || esTextoVacio(nuevoTipoAnimal)) {
                    System.out.println("Todos los campos son obligatorios.");
                    break;
                }

                if (nuevoPeso <= 0 || nuevoPrecio <= 0) {
                    System.out.println("El peso y el precio deben ser mayores a 0.");
                    break;
                }

                existente.setNombre(nuevoNombre);
                existente.setTipoAnimal(nuevoTipoAnimal);
                existente.setPesoKg(nuevoPeso);
                existente.setPrecio(nuevoPrecio);
                productoDAO.actualizar(existente);
                System.out.println("Producto actualizado correctamente.");
            }

            case "5" -> {
                System.out.print("Ingrese el ID del producto a buscar: ");
                int idBuscar = Integer.parseInt(scanner.nextLine());
                Producto producto = productoDAO.obtenerPorId(idBuscar);

                if (producto != null) {
                    System.out.printf("ID: %d - %s (%.2fkg) - $%.2f - [%s]%n",
                            producto.getId(), producto.getNombre(), producto.getPesoKg(), producto.getPrecio(), producto.getTipoAnimal());
                } else {
                    System.out.println("No se encontró ningún producto con ese ID.");
                }
            }

            case "0" -> volver = true;
            default -> System.out.println("Opción inválida.");
        }
    }
}
   
   private static void gestionarPedidos() {

    while (true) {
        System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
        System.out.println("1. Crear nuevo pedido");
        System.out.println("2. Listar todos los pedidos");
        System.out.println("3. Buscar pedido por ID");
        System.out.println("4. Eliminar pedido");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");

        String opcion = scanner.nextLine();
        switch (opcion) {
            case "1":
                System.out.println("\n--- CREAR NUEVO PEDIDO ---");
                List<Cliente> clientes = clienteDAO.obtenerTodos();
                if (clientes.isEmpty()) {
                    System.out.println("No hay clientes registrados.");
                    break;
                }
                System.out.println("Seleccione el ID del cliente:");
                for (Cliente c : clientes) {
                    System.out.println(c.getId() + ". " + c.getNombre() + " " + c.getApellido());
                }
                int clienteId = Integer.parseInt(scanner.nextLine());
                Cliente cliente = clienteDAO.obtenerPorId(clienteId);
                if (cliente == null) {
                    System.out.println("Cliente no encontrado.");
                    break;
                }

                Pedido pedido = new Pedido();
                pedido.setCliente(cliente);
                pedido.setFecha(LocalDate.now());
                pedido.setTotal(0); // se actualiza luego
                pedidoDAO.guardar(pedido);

                double total = 0;
                while (true) {
                    System.out.println("\nAgregar producto al pedido (0 para terminar):");
                    List<Producto> productos = productoDAO.obtenerTodos();
                    for (Producto p : productos) {
                        System.out.println(p.getId() + ". " + p.getNombre() + " ($" + p.getPrecio() + ")");
                    }
                    System.out.print("ID del producto: ");
                    String input = scanner.nextLine();
                    if (input.equals("0")) break;

                    int productoId = Integer.parseInt(input);
                    Producto producto = productoDAO.obtenerPorId(productoId);
                    if (producto == null) {
                        System.out.println("Producto no encontrado.");
                        continue;
                    }
                    
                    int cantidad;
                    while (true) {
                        System.out.print("Cantidad: ");
                        try {
                            cantidad = Integer.parseInt(scanner.nextLine());
                            if (cantidad <= 0) {
                                System.out.println("Cantidad inválida.");
                            } else {
                                break; 
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Ingrese un número válido.");
                        }
                    }
                    double subtotal = cantidad * producto.getPrecio();
                    total += subtotal;

                    DetallePedido detalle = new DetallePedido();
                    detalle.setPedido(pedido);
                    detalle.setProducto(producto);
                    detalle.setCantidad(cantidad);
                    detalle.setSubtotal(subtotal);
                    detalleDAO.guardar(detalle);
                }

                pedido.setTotal(total);
                pedidoDAO.actualizar(pedido);
                System.out.println("Pedido creado correctamente con total: $" + total);
                break;

            case "2":
                System.out.println("\n--- LISTADO DE PEDIDOS ---");
                List<Pedido> pedidos = pedidoDAO.obtenerTodos();
                
                if (pedidos.isEmpty()) {
                System.out.println("No hay pedidos registrados.");
                break;
                }
                for (Pedido p : pedidos) {
                    System.out.println("Pedido ID: " + p.getId() + ", Cliente: " + p.getCliente().getNombre() + " " + p.getCliente().getApellido() + ", Fecha: " + p.getFecha() + ", Total: $" + p.getTotal());
                }
                break;

            case "3":
                System.out.print("Ingrese el ID del pedido: ");
                int idBuscar = Integer.parseInt(scanner.nextLine());
                Pedido p = pedidoDAO.obtenerPorId(idBuscar);
                if (p != null) {
                    System.out.println("Pedido ID: " + p.getId() + ", Cliente: " + p.getCliente().getNombre() + " " + p.getCliente().getApellido() + ", Fecha: " + p.getFecha() + ", Total: $" + p.getTotal());
                    List<DetallePedido> detalles = detalleDAO.obtenerTodos();
                    for (DetallePedido dp : detalles) {
                        if (dp.getPedido().getId() == p.getId()) {
                            System.out.println("  Producto: " + dp.getProducto().getNombre() + ", Cantidad: " + dp.getCantidad() + ", Subtotal: $" + dp.getSubtotal());
                        }
                    }
                } else {
                    System.out.println("Pedido no encontrado.");
                }
                break;

            case "4":
                System.out.print("Ingrese el ID del pedido a eliminar: ");
                int idEliminar = Integer.parseInt(scanner.nextLine());
                p = pedidoDAO.obtenerPorId(idEliminar);
                if (p != null) {
                   pedidoDAO.eliminar(idEliminar);
                    System.out.println("Pedido eliminado correctamente.");
                } else {
                    System.out.println("No se encontró el pedido con ese ID.");
                }
                
            case "0":
                return;
            default:
                System.out.println("Opción inválida.");
        }
    }
}

   private static String leerCampoObligatorio (String nombreCampo){
       System.out.println(nombreCampo + ": ");
       String input = scanner.nextLine().trim();
       if (esTextoVacio(input)){
           System.out.println("El campo \"" + nombreCampo + "\" no puede estar vacio.");
           return null;
       }
       return input;
    }

    private static double leerDoubleSeguro() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
    
    private static boolean esEmailValido(String email) {
    return email.contains("@") && email.contains(".") && !email.contains(" ");
    }

    private static boolean esTextoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
