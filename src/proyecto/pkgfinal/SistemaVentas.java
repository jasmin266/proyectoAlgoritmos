/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto.pkgfinal;

import java.util.Scanner;
import java.util.Locale;

/**
 *
 * @author JOSSELYN CARRILLO
 */
public class SistemaVentas extends ProyectoFinal {

    public static void main(String[] args) {

        // Para que el punto sea el separador decimal (ej. 25.50) en cualquier computadora
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in); {
            // ===== CONTADORES Y ACUMULADORES DE LA CAJA (sobreviven mientras el programa esté abierto) =====
            int ventasRealizadas = 0;          // contador de ventas
            int productosVendidos = 0;         // acumulador de unidades vendidas
            double totalVendido = 0;           // acumulador de dinero (total con IVA)
            
            int ventasEfectivo = 0;            // contadores por forma de pago
            int ventasTarjeta = 0;
            int ventasTransferencia = 0;
            
            double montoEfectivo = 0;          // acumuladores de dinero por forma de pago
            double montoTarjeta = 0;
            double montoTransferencia = 0;
            
            double ventaMayor = 0;             // venta de mayor valor
            double ventaMenor = 0;             // venta de menor valor
            
            int opcion;                        // opción del menú principal
            boolean salir = false;             // bandera que controla el do-while del menú
            
            // ===== MENÚ PRINCIPAL (do-while: se muestra al menos una vez) =====
            do {
                System.out.println();
                System.out.println("======= SISTEMA DE VENTAS =======");
                System.out.println("1. Registrar una venta");
                System.out.println("2. Consultar caja");
                System.out.println("3. Mostrar estadísticas");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");
                
                // Validación: que realmente se escriba un número entero
                while (!sc.hasNextInt()) {
                    sc.next();
                    System.out.print("Dato no válido. Ingrese un número del 1 al 4: ");
                }
                opcion = sc.nextInt();
                sc.nextLine(); // limpia el Enter que quedó en el buffer
                
                switch (opcion) {
                    
                    // ---------------------------------------------------------------
                    case 1: {
                        System.out.println();
                        System.out.println("--- REGISTRAR UNA VENTA ---");
                        
                        // Número de productos (debe ser mayor que cero)
                        int numProductos;
                        System.out.print("¿Cuántos productos tendrá la compra? ");
                        while (!sc.hasNextInt()) {
                            sc.next();
                            System.out.print("Dato no válido. Ingrese un número entero: ");
                        }
                        numProductos = sc.nextInt();
                        while (numProductos <= 0) {
                            System.out.print("El número de productos debe ser mayor que cero. Intente de nuevo: ");
                            while (!sc.hasNextInt()) {
                                sc.next();
                                System.out.print("Dato no válido. Ingrese un número entero: ");
                            }
                            numProductos = sc.nextInt();
                        }
                        sc.nextLine();
                        
                        double subtotal = 0;        // acumulador del subtotal de la venta
                        int cantidadProductos = 0;  // acumulador de unidades de esta venta
                        
                        // Ciclo for: procesa cada producto de la venta
                        for (int i = 1; i <= numProductos; i++) {
                            System.out.println();
                            System.out.println("Producto " + i + " de " + numProductos);
                            
                            System.out.print("Código: ");
                            String codigo = sc.nextLine();
                            
                            System.out.print("Nombre: ");
                            String nombre = sc.nextLine();
                            
                            // Precio (mayor que cero)
                            double precio;
                            System.out.print("Precio (Q): ");
                            while (!sc.hasNextDouble()) {
                                sc.next();
                                System.out.print("Dato no válido. Ingrese un precio: ");
                            }
                            precio = sc.nextDouble();
                            while (precio <= 0) {
                                System.out.print("El precio debe ser mayor que cero. Intente de nuevo: ");
                                while (!sc.hasNextDouble()) {
                                    sc.next();
                                    System.out.print("Dato no válido. Ingrese un precio: ");
                                }
                                precio = sc.nextDouble();
                            }
                            
                            // Cantidad (mayor que cero)
                            int cantidad;
                            System.out.print("Cantidad: ");
                            while (!sc.hasNextInt()) {
                                sc.next();
                                System.out.print("Dato no válido. Ingrese una cantidad entera: ");
                            }
                            cantidad = sc.nextInt();
                            while (cantidad <= 0) {
                                System.out.print("La cantidad debe ser mayor que cero. Intente de nuevo: ");
                                while (!sc.hasNextInt()) {
                                    sc.next();
                                    System.out.print("Dato no válido. Ingrese una cantidad entera: ");
                                }
                                cantidad = sc.nextInt();
                            }
                            sc.nextLine();
                            
                            // Subtotal del producto = Precio x Cantidad
                            double subtotalProducto = precio * cantidad;
                            subtotal = subtotal + subtotalProducto;
                            cantidadProductos = cantidadProductos + cantidad;
                            
                            System.out.printf("%s (%s) -> Subtotal del producto: Q%.2f%n",
                                    nombre, codigo, subtotalProducto);
                        }
                        
                        // Descuento según el subtotal de toda la venta
                        double porcentajeDescuento;
                        if (subtotal < 200) {
                            porcentajeDescuento = 0;
                        } else if (subtotal < 500) {
                            porcentajeDescuento = 5;
                        } else if (subtotal < 1000) {
                            porcentajeDescuento = 8;
                        } else {
                            porcentajeDescuento = 10;
                        }
                        
                        double descuento = subtotal * porcentajeDescuento / 100;
                        double montoConDescuento = subtotal - descuento;
                        double iva = montoConDescuento * 0.12;
                        double total = montoConDescuento + iva;
                        
                        // Forma de pago (do-while repite hasta que la opción sea válida)
                        int formaPago;
                        String nombrePago = "";
                        double efectivoRecibido = 0;
                        double cambio = 0;
                        
                        do {
                            System.out.println();
                            System.out.printf("Total a pagar: Q%.2f%n", total);
                            System.out.println("Forma de pago:");
                            System.out.println("1. Efectivo");
                            System.out.println("2. Tarjeta");
                            System.out.println("3. Transferencia");
                            System.out.print("Seleccione una opción: ");
                            while (!sc.hasNextInt()) {
                                sc.next();
                                System.out.print("Dato no válido. Ingrese 1, 2 o 3: ");
                            }
                            formaPago = sc.nextInt();
                            sc.nextLine();
                            
                            switch (formaPago) {
                                case 1: {
                                    nombrePago = "Efectivo";
                                    System.out.print("Efectivo recibido (Q): ");
                                    while (!sc.hasNextDouble()) {
                                        sc.next();
                                        System.out.print("Dato no válido. Ingrese el monto recibido: ");
                                    }
                                    efectivoRecibido = sc.nextDouble();
                                    // Si el efectivo no alcanza, se vuelve a pedir
                                    while (efectivoRecibido < total) {
                                        System.out.printf("Efectivo insuficiente. El total es Q%.2f. Ingrese de nuevo: ", total);
                                        while (!sc.hasNextDouble()) {
                                            sc.next();
                                            System.out.print("Dato no válido. Ingrese el monto recibido: ");
                                        }
                                        efectivoRecibido = sc.nextDouble();
                                    }
                                    sc.nextLine();
                                    cambio = efectivoRecibido - total;
                                    ventasEfectivo++;
                                    montoEfectivo = montoEfectivo + total;
                                    break;
                                }
                                case 2:
                                    nombrePago = "Tarjeta";
                                    ventasTarjeta++;
                                    montoTarjeta = montoTarjeta + total;
                                    break;
                                case 3:
                                    nombrePago = "Transferencia";
                                    ventasTransferencia++;
                                    montoTransferencia = montoTransferencia + total;
                                    break;
                                default:
                                    System.out.println("Opción no válida. Elija 1, 2 o 3.");
                            }
                        } while (formaPago < 1 || formaPago > 3);
                        
                        // Se actualizan los datos de la caja
                        ventasRealizadas++;
                        productosVendidos = productosVendidos + cantidadProductos;
                        totalVendido = totalVendido + total;
                        
                        if (ventasRealizadas == 1) {
                            ventaMayor = total;
                            ventaMenor = total;
                        } else {
                            if (total > ventaMayor) {
                                ventaMayor = total;
                            }
                            if (total < ventaMenor) {
                                ventaMenor = total;
                            }
                        }
                        
                        // Resumen de la venta
                        System.out.println();
                        System.out.println("========== RESUMEN DE LA VENTA ==========");
                        System.out.println("Cantidad de productos: " + cantidadProductos);
                        System.out.printf("Subtotal:             Q%.2f%n", subtotal);
                        System.out.printf("Descuento (%.0f%%):     Q%.2f%n", porcentajeDescuento, descuento);
                        System.out.printf("IVA (12%%):            Q%.2f%n", iva);
                        System.out.printf("TOTAL:                Q%.2f%n", total);
                        System.out.println("Forma de pago:        " + nombrePago);
                        if (formaPago == 1) {
                            System.out.printf("Efectivo recibido:    Q%.2f%n", efectivoRecibido);
                            System.out.printf("Cambio:               Q%.2f%n", cambio);
                        }
                        System.out.println("=========================================");
                        break;
                    }
                    
                    // ---------------------------------------------------------------
                    case 2:
                        System.out.println();
                        System.out.println("--- CONSULTAR CAJA ---");
                        if (ventasRealizadas == 0) {
                            System.out.println("No existen ventas registradas.");
                        } else {
                            System.out.println("Ventas realizadas:      " + ventasRealizadas);
                            System.out.println("Productos vendidos:     " + productosVendidos);
                            System.out.printf("Total vendido:          Q%.2f%n", totalVendido);
                            System.out.printf("Ventas en efectivo:     %d (Q%.2f)%n", ventasEfectivo, montoEfectivo);
                            System.out.printf("Ventas con tarjeta:     %d (Q%.2f)%n", ventasTarjeta, montoTarjeta);
                            System.out.printf("Ventas por transferencia: %d (Q%.2f)%n", ventasTransferencia, montoTransferencia);
                        }
                        break;
                        
                        // ---------------------------------------------------------------
                    case 3:
                        System.out.println();
                        System.out.println("--- ESTADÍSTICAS ---");
                        if (ventasRealizadas == 0) {
                            System.out.println("No existen ventas registradas.");
                        } else {
                            double promedio = totalVendido / ventasRealizadas;
                            System.out.println("Número total de ventas:            " + ventasRealizadas);
                            System.out.println("Cantidad total de productos:       " + productosVendidos);
                            System.out.printf("Total de dinero vendido:           Q%.2f%n", totalVendido);
                            System.out.printf("Promedio de dinero por venta:      Q%.2f%n", promedio);
                            System.out.println("Ventas por forma de pago:");
                            System.out.println("   Efectivo:      " + ventasEfectivo);
                            System.out.println("   Tarjeta:       " + ventasTarjeta);
                            System.out.println("   Transferencia: " + ventasTransferencia);
                            System.out.printf("Venta de mayor valor:              Q%.2f%n", ventaMayor);
                            System.out.printf("Venta de menor valor:              Q%.2f%n", ventaMenor);
                        }
                        break;
                        
                        // ---------------------------------------------------------------
                    case 4: {
                        int confirmacion;
                        System.out.print("¿Confirma que desea salir? (1 = Sí, 2 = No): ");
                        while (!sc.hasNextInt()) {
                            sc.next();
                            System.out.print("Dato no válido. Ingrese 1 para Sí o 2 para No: ");
                        }
                        confirmacion = sc.nextInt();
                        while (confirmacion != 1 && confirmacion != 2) {
                            System.out.print("Opción no válida. Ingrese 1 para Sí o 2 para No: ");
                            while (!sc.hasNextInt()) {
                                sc.next();
                                System.out.print("Dato no válido. Ingrese 1 para Sí o 2 para No: ");
                            }
                            confirmacion = sc.nextInt();
                        }
                        sc.nextLine();
                        if (confirmacion == 1) {
                            salir = true;
                            System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                        } else {
                            System.out.println("Regresando al menú principal...");
                        }
                        break;
                    }
                    
                    // ---------------------------------------------------------------
                    default:
                        System.out.println("Opción no válida. Elija una opción del 1 al 4.");
                }
                
            } while (!salir);
        } // contador de ventas
    }
}