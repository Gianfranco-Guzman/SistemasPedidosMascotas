package org.example.model;

public class Producto {
    private int id;
    private String nombre;
    private String tipoAnimal;
    private double pesoKg;
    private double precio;

    public Producto() {}

    public Producto( String nombre, String tipoAnimal, double pesoKg, double precio) {
        this.nombre = nombre;
        this.tipoAnimal = tipoAnimal;
        this.pesoKg = pesoKg;
        this.precio = precio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipoAnimal() { return tipoAnimal; }
    public void setTipoAnimal(String tipoAnimal) { this.tipoAnimal = tipoAnimal; }
    public double getPesoKg() { return pesoKg; }
    public void setPesoKg(double pesoKg) { this.pesoKg = pesoKg; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}