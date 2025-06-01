package org.example.model;

import java.time.LocalDate;

public class Pedido {
    private int id;
    private Cliente cliente;
    private LocalDate fecha;
    private double total;

    public Pedido() {}

    public Pedido(int id, Cliente cliente, LocalDate fecha, double total) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}