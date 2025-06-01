package org.example.dao.impl;

import org.example.dao.ProductoDAO;
import org.example.model.Producto;
import org.example.util.DBUtil;
import org.example.util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public void guardar(Producto producto) {
        String sql = "INSERT INTO producto (nombre, tipo_animal, peso_kg, precio) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getTipoAnimal());
            stmt.setDouble(3, producto.getPesoKg());
            stmt.setDouble(4, producto.getPrecio());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                producto.setId(rs.getInt(1));
            }
            Log.info("Producto guardado correctamente ");
        } catch (SQLException e) {
            Log.error("Error al guardar producto ", e);
        }
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setTipoAnimal(rs.getString("tipo_animal"));
                p.setPesoKg(rs.getDouble("peso_kg"));
                p.setPrecio(rs.getDouble("precio"));
                productos.add(p);
            }
        } catch (SQLException e) {
            Log.error("Error al obtener productos ", e);
        }
        return productos;
    }

    @Override
    public Producto obtenerPorId(int id) {
        Producto producto = null;
        String sql = "SELECT * FROM producto WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto();
                    producto.setId(rs.getInt("id"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setTipoAnimal(rs.getString("tipo_animal"));
                    producto.setPesoKg(rs.getDouble("peso_kg"));
                    producto.setPrecio(rs.getDouble("precio"));
                    
                    return (producto);
                }
            }
        } catch (SQLException e) {
            Log.error("Error al buscar producto por ID ", e);
        }
        return null;
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, tipo_animal = ?, peso_kg = ?, precio = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getTipoAnimal());
            stmt.setDouble(3, producto.getPesoKg());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getId());
            stmt.executeUpdate();
            Log.info("Producto actualizado ");
        } catch (SQLException e) {
            Log.error("Error al actualizar producto ", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            Log.info("Producto eliminado ");
        } catch (SQLException e) {
            Log.error("Error al eliminar producto ", e);
        }
    }
}
