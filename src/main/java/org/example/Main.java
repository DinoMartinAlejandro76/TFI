package org.example;

import com.techlab.exceptions.NumeroInvalidoException;
import com.techlab.exceptions.StockInsuficienteException;
import com.techlab.pedidos.Pedido;
import com.techlab.productos.Producto;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Producto> productos = new ArrayList<>();
    static ArrayList<Pedido> pedidos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> crearProducto(scanner);
                    case 2 -> listarProductos();
                    case 3 -> buscarActualizarProducto(scanner);
                    case 4 -> eliminarProducto(scanner);
                    case 5 -> crearPedido(scanner);
                    case 6 -> listarPedidos();
                    case 7 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
                opcion = 0;
            }

        } while (opcion != 7);

        scanner.close();
    }

    static void mostrarMenu() {
        System.out.println("\n---- Sistema de Gestión de Productos ----");
        System.out.println("1. Crear producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar/Actualizar producto");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Crear Pedido");
        System.out.println("6. Listar Pedidos");
        System.out.println("7. Salir");
    }

    static void crearProducto(Scanner scanner) {
        try {
            System.out.print("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el precio del producto: ");
            double precio = Double.parseDouble(scanner.nextLine());

            System.out.print("Ingrese la cantidad en stock: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            Producto nuevoProducto = new Producto(nombre, precio, cantidad, Producto.SIGUIENTE_ID);
            productos.add(nuevoProducto);
            System.out.println("Producto agregado exitosamente.");

        } catch (NumberFormatException e) {
            throw new NumeroInvalidoException("Precio o cantidad inválida.");
        }
    }

    static void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            System.out.println("Lista de productos:");
            for (int i = 0; i < productos.size(); i++) {
                System.out.println((i + 1) + ". " + productos.get(i));
            }
        }
    }

    static void buscarActualizarProducto(Scanner scanner) {
        System.out.print("Ingrese el id del producto a buscar: ");
        String idBuscado = scanner.nextLine();

        try {
            int id = Integer.parseInt(idBuscado);
            for (Producto producto : productos) {
                if (producto.getId() == id) {
                    System.out.println("Producto encontrado: " + producto);
                    System.out.print("¿Desea actualizarlo? (s/n): ");
                    String respuesta = scanner.nextLine();
                    if (respuesta.equalsIgnoreCase("s")) {
                        System.out.print("Nuevo precio: ");
                        double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                        System.out.print("Nueva cantidad en stock: ");
                        int nuevaCantidad = Integer.parseInt(scanner.nextLine());

                        producto.setPrecio(nuevoPrecio);
                        producto.setCantidadEnStock(nuevaCantidad);
                        System.out.println("Producto actualizado correctamente.");
                    }
                    return;
                }
            }
            System.out.println("Producto no encontrado.");

        } catch (NumberFormatException e) {
            throw new NumeroInvalidoException("ID, precio o cantidad inválida.");
        }
    }

    static void eliminarProducto(Scanner scanner) {
        System.out.print("Ingrese el ID del producto a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            for (Producto producto : productos) {
                if (producto.getId() == id) {
                    productos.remove(producto);
                    System.out.println("Producto eliminado.");
                    return;
                }
            }
            System.out.println("Producto no encontrado.");
        } catch (NumberFormatException e) {
            throw new NumeroInvalidoException("ID inválido.");
        }
    }

    static void crearPedido(Scanner scanner) {
        Pedido pedido = new Pedido();

        while (true) {
            listarProductos();
            System.out.print("Ingrese el nombre del producto a agregar al pedido (o 'fin' para terminar): ");
            String nombre = scanner.nextLine();

            if (nombre.equalsIgnoreCase("fin")) break;

            Producto productoSeleccionado = null;
            for (Producto p : productos) {
                if (p.getNombre().equalsIgnoreCase(nombre)) {
                    productoSeleccionado = p;
                    break;
                }
            }

            if (productoSeleccionado == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }

            try {
                System.out.print("Ingrese la cantidad deseada: ");
                int cantidadDeseada = Integer.parseInt(scanner.nextLine());

                if (cantidadDeseada > productoSeleccionado.getCantidadEnStock()) {
                    throw new StockInsuficienteException("No hay suficiente stock disponible.");
                }

                pedido.agregarLinea(productoSeleccionado, cantidadDeseada);
                productoSeleccionado.setCantidadEnStock(
                        productoSeleccionado.getCantidadEnStock() - cantidadDeseada
                );
                System.out.println("Producto agregado al pedido.");

            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            } catch (StockInsuficienteException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (pedido.getLineas().isEmpty()) {
            System.out.println("No se creó el pedido (vacío).");
        } else {
            pedidos.add(pedido);
            System.out.println("Pedido creado exitosamente:");
            System.out.println(pedido);
        }
    }

    static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        for (int i = 0; i < pedidos.size(); i++) {
            System.out.println("Pedido #" + (i + 1));
            System.out.println(pedidos.get(i));
            System.out.println("----------------------------");
        }
    }
}
