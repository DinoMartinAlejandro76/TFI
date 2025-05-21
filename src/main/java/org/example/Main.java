package org.example;

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
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next(); // descarta entrada inválida
            }

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    crearProducto(scanner);
                    break;
                case 2:
                    listarProductos();
                    break;
                case 3:
                    buscarActualizarProducto(scanner);
                    break;
                case 4:
                    eliminarProducto(scanner);
                    break;
                case 5:
                    crearPedido(scanner);

                    break;
                case 6:
                    listarPedidos();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
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
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el precio del producto: ");
        double precio = scanner.nextDouble();

        System.out.print("Ingrese la cantidad en stock: ");
        int cantidad = scanner.nextInt();

        Producto nuevoProducto = new Producto(nombre, precio, cantidad, Producto.SIGUIENTE_ID);
        productos.add(nuevoProducto);

        System.out.println("Producto agregado exitosamente.");
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

        for (Producto producto : productos) {
            if (producto.getId() == Integer.parseInt(idBuscado)) {
                System.out.println("Producto encontrado: " + producto);
                System.out.print("¿Desea actualizarlo? (s/n): ");
                String respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("s")) {
                    System.out.print("Nuevo precio: ");
                    double nuevoPrecio = scanner.nextDouble();
                    System.out.print("Nueva cantidad en stock: ");
                    int nuevaCantidad = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer

                    producto.setPrecio(nuevoPrecio);
                    producto.setCantidadEnStock(nuevaCantidad);
                    System.out.println("Producto actualizado correctamente.");
                }
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }

    static void eliminarProducto(Scanner scanner) {
        System.out.print("Ingrese el nombre del producto a eliminar: ");
        String idEliminar = scanner.nextLine();

        for(Producto producto: productos) {
            if (producto.getId() == Integer.parseInt(idEliminar)){
                productos.remove(producto);
                System.out.println("Producto eliminado.");
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }

    static void crearPedido(Scanner scanner) {
        Pedido pedido = new Pedido();
        boolean agregando = true;

        while (agregando) {
            listarProductos();
            System.out.print("Ingrese el nombre del producto a agregar al pedido (o 'fin' para terminar): ");
            String nombre = scanner.nextLine();

            if (nombre.equalsIgnoreCase("fin")) {
                break;
            }

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

            System.out.print("Ingrese la cantidad deseada: ");
            int cantidadDeseada = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            if (cantidadDeseada > productoSeleccionado.getCantidadEnStock()) {
                System.out.println("No hay suficiente stock.");
            } else {
                pedido.agregarLinea(productoSeleccionado, cantidadDeseada);
                productoSeleccionado.setCantidadEnStock(productoSeleccionado.getCantidadEnStock() - cantidadDeseada);
                System.out.println("Producto agregado al pedido.");
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