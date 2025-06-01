package org.example.dao.impl;

import org.example.dao.PedidoDAO;
import org.example.model.Cliente;
import org.example.model.Pedido;
import org.example.util.DBUtil;
import org.example.util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {

    @Override
    public void guardar(Pedido pedido) {
        String sql = "INSERT INTO pedido (cliente_id, fecha, total) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pedido.getCliente().getId());
            stmt.setDate(2, Date.valueOf(pedido.getFecha()));
            stmt.setDouble(3, pedido.getTotal());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getInt(1));
                }
            }
            Log.info("Pedido guardado correctamente ");
        } catch (SQLException e) {
            Log.error("Error al guardar pedido ", e);
        }
    }

    @Override
    public List<Pedido> obtenerTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre, c.apellido, c.email FROM pedido p JOIN cliente c ON p.cliente_id = c.id";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente(rs.getInt("cliente_id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"));
                Pedido p = new Pedido(rs.getInt("id"),
                        c,
                        rs.getDate("fecha").toLocalDate(),
                        rs.getDouble("total"));
                pedidos.add(p);
            }
        } catch (SQLException e) {
            Log.error("Error al obtener pedidos ", e);
        }
        return pedidos;
    }

    @Override
    public Pedido obtenerPorId(int id) {
        Pedido pedido = null;
        String sql = "SELECT p.*, c.nombre, c.apellido, c.email FROM pedido p JOIN cliente c ON p.cliente_id = c.id WHERE p.id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente(rs.getInt("cliente_id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("email"));
                    pedido = new Pedido(rs.getInt("id"),
                            c,
                            rs.getDate("fecha").toLocalDate(),
                            rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            Log.error("Error al obtener pedido por ID ", e);
        }
        return pedido;
    }

    @Override
    public void actualizar(Pedido pedido) {
        String sql = "UPDATE pedido SET cliente_id = ?, fecha = ?, total = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getCliente().getId());
            stmt.setDate(2, Date.valueOf(pedido.getFecha()));
            stmt.setDouble(3, pedido.getTotal());
            stmt.setInt(4, pedido.getId());
            stmt.executeUpdate();
            Log.info("Pedido actualizado ");
        } catch (SQLException e) {
            Log.error("Error al actualizar pedido ", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM pedido WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            Log.info("Pedido eliminado ");
        } catch (SQLException e) {
            Log.error("Error al eliminar pedido ", e);
        }
    }
}
