package org.example.dao.impl;

import org.example.dao.DetallePedidoDAO;
import org.example.model.DetallePedido;
import org.example.model.Pedido;
import org.example.model.Producto;
import org.example.util.DBUtil;
import org.example.util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAOImpl implements DetallePedidoDAO {

    @Override
    public void guardar(DetallePedido dp) {
        String sql = "INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dp.getPedido().getId());
            stmt.setInt(2, dp.getProducto().getId());
            stmt.setInt(3, dp.getCantidad());
            stmt.setDouble(4, dp.getSubtotal());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                dp.setId(rs.getInt(1));
            }
            Log.info("Detalle de pedido guardado correctamente ");
        } catch (SQLException e) {
            Log.error("Error al guardar detalle de pedido ", e);
        }
    }

    @Override
    public List<DetallePedido> obtenerTodos() {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = """
                SELECT dp.*, p.fecha, p.total, pr.nombre, pr.tipo_animal, pr.peso_kg, pr.precio 
                FROM detalle_pedido dp
                JOIN pedido p ON dp.pedido_id = p.id
                JOIN producto pr ON dp.producto_id = pr.id
                """;
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("pedido_id"));
                pedido.setFecha(rs.getDate("fecha").toLocalDate());
                pedido.setTotal(rs.getDouble("total"));

                Producto producto = new Producto();
                producto.setId(rs.getInt("producto_id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setTipoAnimal(rs.getString("tipo_animal"));
                producto.setPesoKg(rs.getDouble("peso_kg"));
                producto.setPrecio(rs.getDouble("precio"));

                DetallePedido dp = new DetallePedido();
                dp.setId(rs.getInt("id"));
                dp.setPedido(pedido);
                dp.setProducto(producto);
                dp.setCantidad(rs.getInt("cantidad"));
                dp.setSubtotal(rs.getDouble("subtotal"));

                detalles.add(dp);
            }
        } catch (SQLException e) {
            Log.error("Error al obtener detalles de pedido ", e);
        }
        return detalles;
    }

    @Override
   public DetallePedido obtenerPorId(int id) {
        String sql = """
                SELECT d.id, d.cantidad, d.subtotal,
                       p.id AS pedido_id, p.fecha, p.total,
                       pr.id AS producto_id, pr.nombre, pr.tipo_animal, pr.peso_kg, pr.precio
                FROM detalle_pedido d
                JOIN pedido p ON d.pedido_id = p.id
                JOIN producto pr ON d.producto_id = pr.id
                WHERE d.id = ?
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("pedido_id"));
                pedido.setFecha(rs.getDate("fecha").toLocalDate());
                pedido.setTotal(rs.getDouble("total"));

                Producto producto = new Producto();
                producto.setId(rs.getInt("producto_id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setTipoAnimal(rs.getString("tipo_animal"));
                producto.setPesoKg(rs.getDouble("peso_kg"));

                DetallePedido dp = new DetallePedido();
                dp.setId(rs.getInt("id"));
                dp.setPedido(pedido);
                dp.setProducto(producto);
                dp.setCantidad(rs.getInt("cantidad"));
                dp.setSubtotal(rs.getDouble("subtotal"));
                
                return (dp);
            }

        } catch (SQLException e) {
            Log.error("Error al obtener detalle de pedido por ID", e);
        }

        return null;
    }

    @Override
    public void actualizar(DetallePedido detalle) {
        String sql = "UPDATE detalle_pedido SET pedido_id = ?, producto_id = ?, cantidad = ?, subtotal = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detalle.getPedido().getId());
            stmt.setInt(2, detalle.getProducto().getId());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setDouble(4, detalle.getSubtotal());
            stmt.setInt(5, detalle.getId());

            stmt.executeUpdate();
            Log.info("Detalle de pedido actualizado correctamente ");
        } catch (SQLException e) {
            Log.error("Error al actualizar detalle de pedido ", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM detalle_pedido WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            Log.info("Detalle de pedido eliminado correctamente ");
        } catch (SQLException e) {
            Log.error("Error al eliminar detalle de pedido ", e);
        }
    }
}
