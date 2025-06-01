package org.example.dao;

import java.util.List;

public interface GenericDAO<T> {
    void guardar(T t);
    List<T> obtenerTodos();
    T obtenerPorId(int id);
    void actualizar(T t);
    void eliminar(int id);
}