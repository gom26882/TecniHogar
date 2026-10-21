
package view;

import constants.*;
import controller.TecniHogarController;
import model.*;
import java.util.Scanner;
import java.util.Locale;

public class ConsolaView {
    private final TecniHogarController controller;
    private final Scanner scanner = new Scanner(System.in);

    public ConsolaView(TecniHogarController controller) {
        this.controller = controller;
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n----- Tecni-Hogar -----");
            System.out.println("1. Cotizar");
            System.out.println("2. Registrar orden");
            System.out.println("3. Registrar cuadrilla");
            System.out.println("4. Crear jornada");
            System.out.println("5. Asignar orden");
            System.out.println("6. Retirar orden");
            System.out.println("7. Iniciar jornada");
            System.out.println("8. Registrar resultado");
            System.out.println("9. Consultar órdenes");
            System.out.println("10. Consultar cuadrillas");
            System.out.println("11. Reportes");
            System.out.println("0. Salir");
            
            opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1: 
                        cotizarOrden(); 
                        break;
                    case 2: 
                        registrarOrden(); 
                        break;
                    case 3:     
                        registrarCuadrilla(); 
                        break;
                    case 4: 
                        crearJornada(); 
                        break;
                    case 5: 
                        asignarOrden(); 
                        break;
                    case 6: 
                        retirarOrden(); 
                        break;
                    case 7: 
                        iniciarJornada(); 
                        break;
                    case 8: 
                        registrarResultado(); 
                        break;
                    case 9: 
                        consultarOrdenes(); 
                        break;
                    case 10: 
                        consultarCuadrillas(); 
                        break;
                    case 11: 
                        mostrarReportes(); 
                        break;
                    case 0: 
                        System.out.println("adioooos :)"); 
                        break;
                    default: 
                        System.out.println("Opción inválida");
                }
            } catch (ReglaNegocioException e) {
                System.out.println("err: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void cotizarOrden() {
        OrdenServicio orden = leerOrden();
        System.out.println("Costo: " + dinero(orden.calcularCosto()));
        System.out.println("Tiempo: " + orden.calcularDiasAtencion() + " dsas hábiles");
        if (leerSiNo("acepta? (s/n): ")) {
            controller.registrarOrden(orden);
            System.out.println("Orden registrada y cobrada");
        } else {
            System.out.println("Cotización descartada");
        }
    }

    private void registrarOrden() {
        OrdenServicio orden = leerOrden();
        controller.registrarOrden(orden);
        System.out.println("Orden registrada: " + dinero(orden.calcularCosto()));
    }

    private void registrarCuadrilla() {
        String codigo = leerTexto("Código: ");
        double horas = leerDecimal("Capacidad en horas: ");
        boolean certificada = leerSiNo("certificada? (s/n): ");
        controller.registrarCuadrilla(codigo, horas, certificada);
        System.out.println("Cuadrilla registrada.");
    }

    private void crearJornada() {
        Jornada j = controller.crearJornada(leerTexto("Codigo de cuadrilla: "));
        System.out.println("Jornada creada: " + j.getId());
    }

    private void asignarOrden() {
        String jornada = leerTexto("Jornada: ");
        String orden = leerTexto("Orden: ");
        controller.asignarOrden(jornada, orden);
        System.out.println("orden asignada");
    }

    private void retirarOrden() {
        String jornada = leerTexto("Jornada: ");
        String orden = leerTexto("Orden: ");
        controller.retirarOrden(jornada, orden);
        System.out.println("Orden retirada.");
    }

    private void iniciarJornada() {
        controller.iniciarJornada(leerTexto("Jornada: "));
        System.out.println("Jornada iniciada");
    }

    private void registrarResultado() {
        String jornada = leerTexto("Jornada: ");
        String orden = leerTexto("Orden: ");
        boolean completada = leerSiNo("completada? (s/n): ");
        controller.registrarResultado(jornada, orden, completada);
        System.out.println("Estado: " + controller.buscarOrden(orden).getEstado());
        if (controller.buscarJornada(jornada).estaCerrada()) {
            System.out.println("Jornada cerrada");
        }
    }

    private void consultarOrdenes() {
        System.out.println("\n--- Ordenes ---");
        for (OrdenServicio o : controller.getOrdenes()) {
            System.out.println(o.getCodigo() + " | " + o.getCategoria() + " | " + o.getCliente()
                    + " | " + o.getZona() + " | " + o.getHorasEstimadas() + " h | "
                    + dinero(o.calcularCosto()) + " | " + o.getEstado() + " | Visitas: "
                    + o.getVisitasRealizadas() + "/" + o.getMaxVisitas());
        }
    }

    private void consultarCuadrillas() {
        System.out.println("\n--- Cuadrillas ---");
        for (Cuadrilla c : controller.getCuadrillas()) {
            String jornada = "Ninguna";
            if (c.tieneJornadaAbierta()) jornada = c.getJornadaAbierta().getId();
            System.out.println(c.getCodigo() + " | Capacidad: " + c.getCapacidadHoras()
                    + " h | Certificada: " + c.isCertificadaRefrigeracion()
                    + " | Jornada: " + jornada);
        }
    }

    private void mostrarReportes() {
        System.out.println("\n--- Ordenes por cat y estado ---");
        for (String categoria : controller.obtenerCategorias()) {
            System.out.println(categoria);
            for (EstadoOrden estado : EstadoOrden.values()) {
                System.out.println("  " + estado + ": " + controller.contarOrdenes(categoria, estado));
            }
        }
        System.out.println("\n--- Ingresos ---");
        System.out.println("Total: " + dinero(controller.calcularIngresoTotal()));
        System.out.println("Registros: " + dinero(controller.calcularIngresoPorOrigen(OrigenIngreso.REGISTRO)));
        System.out.println("Visitas adicionales: " + dinero(controller.calcularIngresoPorOrigen(OrigenIngreso.VISITA_ADICIONAL)));
        for (String categoria : controller.obtenerCategorias()) {
            System.out.println(categoria + ": " + dinero(controller.calcularIngresoPorCategoria(categoria)));
        }
        System.out.println("\n--- Jornadas sin cerrar ---");
        if (controller.obtenerJornadasNoCerradas().isEmpty()) System.out.println("No hay jornadas abiertas");
        for (Jornada j : controller.obtenerJornadasNoCerradas()) {
            System.out.println(j.getId() + " | Cuadrilla: " + j.getCuadrilla().getCodigo()
                    + " | " + j.getEstado() + " | Horas: " + j.calcularHorasAsignadas()
                    + " | Disponibles: " + j.calcularCapacidadDisponible());
        }
    }

    private OrdenServicio leerOrden() {
        System.out.println("1. Diagnóstico\n2. Instalación\n3. Instalación eléctrica certificada\n4. Aire acondicionado");
        int tipo = leerEntero("Categoría: ");
        if (tipo < 1 || tipo > 4) throw new ReglaNegocioException("Categoría inválida.");
        String codigo = leerTexto("Código: ");
        String cliente = leerTexto("Cliente: ");
        String direccion = leerTexto("Dirección: ");
        double horas = leerDecimal("Horas estimadas: ");
        Zona zona = leerZona();
        switch (tipo) {
            case 1: 
                return new Diagnostico(codigo, cliente, direccion, horas, zona);
            case 2: 
                return new Instalacion(codigo, cliente, direccion, horas, zona, leerEntero("Puntos: "));
            case 3:
                int puntos = leerEntero("Puntos: ");
                double materiales = leerDecimal("Valor de materiales: ");
                return new InstalacionElectricaCertificada(codigo, cliente, direccion, horas, zona, puntos, materiales);
            default: return new AireAcondicionado(codigo, cliente, direccion, horas, zona, leerEntero("BTU: "));
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try { return Integer.parseInt(leerTexto(mensaje)); }
            catch (NumberFormatException e) { System.out.println("Ingresa un entero válido"); }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            try { return Double.parseDouble(leerTexto(mensaje).replace(',', '.')); }
            catch (NumberFormatException e) { System.out.println("Ingresá un número válido"); }
        }
    }

    private boolean leerSiNo(String mensaje) {
        while (true) {
            String respuesta = leerTexto(mensaje);
            if (respuesta.equalsIgnoreCase("s")) return true;
            if (respuesta.equalsIgnoreCase("n")) return false;
            System.out.println("Escribí s o n.");
        }
    }

    private Zona leerZona() {
        while (true) {
            int opcion = leerEntero("Zona (1. Metropolitana, 2. Periférica): ");
            if (opcion == 1) return Zona.METROPOLITANA;
            if (opcion == 2) return Zona.PERIFERICA;
            System.out.println("Elige 1 o 2.");
        }
    }

    private String dinero(double monto) {
        return String.format(Locale.US, "Q%.2f", monto);
    }
}
