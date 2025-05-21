package com.techlab.pedidos;

import com.techlab.productos.Producto;

import java.util.List;
import java.util.ArrayList;

public class Pedido{

    private List<LineaPedido> lineas;
    private double total;

    public Pedido() {
        this.lineas = new ArrayList<>();
        this.total = 0.0;
    }

     public void agregarLinea(Producto producto, int cantidad) {
         LineaPedido linea = new LineaPedido(producto, cantidad);
         lineas.add(linea);
         total += linea.getSubtotal();
     }

     public double getTotal(){
            return total;
     }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pedido:\n");
        for (LineaPedido linea : lineas) {
            sb.append("  ").append(linea).append("\n");
        }
        sb.append("Total: $").append(total);
        return sb.toString();
    }


}
