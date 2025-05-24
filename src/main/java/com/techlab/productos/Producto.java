package com.techlab.productos;

public class Producto {

    public static int SIGUIENTE_ID = 1;
    private int id;
    private String nombre;
    private double precio;
    private int cantidadStock;


    //Constructor
    public Producto(String nombre, double precio, int cantidadStock, int id) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.id = SIGUIENTE_ID++;

    }

    public void mostrarDetalles() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Precio: " + precio);
        System.out.println("Cantidad en stock: " + cantidadStock);
    }

    public void calcularPrecioFinal() {
        double precioFinal = precio;
        System.out.println("Precio final del producto: " + precioFinal);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public void setCantidadEnStock(int cantidad) {
        this.cantidadStock = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidadStock=" + cantidadStock +
                '}';
    }



    public int getCantidadEnStock() {
        return cantidadStock;
    }
}
