package controller;
import constants.EstadoOrden;
import constants.OrigenIngreso;
import model.*;
import java.util.ArrayList;
import java.util.List;

public class TecniHogarController {

    private final List<OrdenServicio> ordenes = new ArrayList<>();
    private final List<Cuadrilla> cuadrillas = new ArrayList<>();
    private final List<Jornada> jornadas = new ArrayList<>();
    private final List<Ingreso> ingresos = new ArrayList<>();
    private int contadorJornadas = 0;

    public void agregarOrdenInicial(OrdenServicio orden) {
        if (existeOrden(orden.getCodigo())) {
            throw new ReglaNegocioException("Ya existe una orden con ese codigo");
        }
        ordenes.add(orden);
    }

    public void registrarOrden(OrdenServicio orden) {
        if (existeOrden(orden.getCodigo())) {
            throw new ReglaNegocioException("Ya existe una orden con ese código");
        }
        ordenes.add(orden);
        ingresos.add(new Ingreso(orden, orden.calcularCosto(), OrigenIngreso.REGISTRO));
    }

    public void registrarCuadrilla(String codigo, double capacidad, boolean certificada) {
        for (Cuadrilla c : cuadrillas) {
            if (c.getCodigo().equalsIgnoreCase(codigo.trim())) {
                throw new ReglaNegocioException("Ya existe una cuadrilla con ese código");
            }
        }
        cuadrillas.add(new Cuadrilla(codigo, capacidad, certificada));
    }

    public boolean existeOrden(String codigo) {
        for (OrdenServicio o : ordenes) {
            if (o.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return true;
            }
        }
        return false;
    }

    public Jornada crearJornada(String codigoCuadrilla) {
        Cuadrilla cuadrilla = buscarCuadrilla(codigoCuadrilla);
        Jornada jornada = new Jornada("J-" + (contadorJornadas + 1), cuadrilla);

        contadorJornadas++;
        jornadas.add(jornada);
        return jornada;
    }

    public void asignarOrden(String idJornada, String codigoOrden) {
        buscarJornada(idJornada).asignarOrden(buscarOrden(codigoOrden));
    }

    public void retirarOrden(String idJornada, String codigoOrden) {
        buscarJornada(idJornada).retirarOrden(buscarOrden(codigoOrden));
    }

    public void iniciarJornada(String idJornada) {
        Jornada jornada = buscarJornada(idJornada);
        jornada.iniciar();

        for (OrdenServicio orden : jornada.getOrdenes()) {
            if (orden.getVisitasRealizadas() > 1) {
                ingresos.add(new Ingreso(orden, orden.getCargoVisitaAdicional(),
                    OrigenIngreso.VISITA_ADICIONAL));
            }
        }
    }

    public void registrarResultado(String idJornada, String codigoOrden, boolean completada) {
        buscarJornada(idJornada).registrarResultado(buscarOrden(codigoOrden), completada);
    }

    public OrdenServicio buscarOrden(String codigo) {
        for (OrdenServicio o : ordenes) {
            if (o.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return o;
            }
        }
        throw new ReglaNegocioException("No existe la orden " + codigo);
    }

    public Cuadrilla buscarCuadrilla(String codigo) {
        for (Cuadrilla c : cuadrillas) {
            if (c.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return c;
            }
        }
        throw new ReglaNegocioException("No existe la cuadrilla " + codigo);
    }

    public Jornada buscarJornada(String id) {
        for (Jornada j : jornadas) {
            if (j.getId().equalsIgnoreCase(id.trim())) {
                return j;
            }
        }
        throw new ReglaNegocioException("No existe la jornada " + id);
    }

    public List<OrdenServicio> getOrdenes() {
        return new ArrayList<>(ordenes);
    }

    public List<Cuadrilla> getCuadrillas() {
        return new ArrayList<>(cuadrillas);
    }

    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();

        for (OrdenServicio o : ordenes) {
            if (!categorias.contains(o.getCategoria())) {
                categorias.add(o.getCategoria());
            }
        }
        return categorias;
    }

    public int contarOrdenes(String categoria, EstadoOrden estado) {
        int total = 0;

        for (OrdenServicio o : ordenes) {
            if (o.getCategoria().equals(categoria) && o.getEstado() == estado) {
                total++;
            }
        }
        return total;
    }

    public double calcularIngresoTotal() {
        double total = 0;

        for (Ingreso i : ingresos) {
            total += i.getImporte();
        }
        return total;
    }

    public double calcularIngresoPorOrigen(OrigenIngreso origen) {
        double total = 0;

        for (Ingreso i : ingresos) {
            if (i.getOrigen() == origen) {
                total += i.getImporte();
            }
        }
        return total;
    }

    public double calcularIngresoPorCategoria(String categoria) {
        double total = 0;

        for (Ingreso i : ingresos) {
            if (i.getCategoria().equals(categoria)) {
                total += i.getImporte();
            }
        }
        return total;
    }

    public List<Jornada> obtenerJornadasNoCerradas() {
        List<Jornada> abiertas = new ArrayList<>();

        for (Jornada j : jornadas) {
            if (!j.estaCerrada()) {
                abiertas.add(j);
            }
        }
        return abiertas;
    }
}
