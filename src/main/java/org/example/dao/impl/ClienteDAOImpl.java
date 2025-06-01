package org.example.dao.impl;

import org.example.dao.ClienteDAO;
import org.example.model.Cliente;
import org.example.util.DBUtil;
import org.example.util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void guardar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, apellido, email) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.executeUpdate();
            Log.info("Cliente guardado exitosamente");
        } catch (SQLException e) {
            Log.error("Error al guardar cliente", e);
        }
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setEmail(rs.getString("email"));
                clientes.add(c);
            }
        } catch (SQLException e) {
            Log.error("Error al obtener clientes", e);
        }
        return clientes;
    }

    @Override
    public Cliente obtenerPorId(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            Log.error("Error al buscar cliente por ID", e);
        }
        return cliente;
    }

    @Override
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre = ?, apellido = ?, email = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setInt(4, cliente.getId());
            stmt.executeUpdate();
            Log.info("Cliente actualizado ");
        } catch (SQLException e) {
            Log.error("Error al actualizar cliente ", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            Log.info("Cliente eliminado ");
        } catch (SQLException e) {
            Log.error("Error al eliminar cliente ", e);
        }
    }
}