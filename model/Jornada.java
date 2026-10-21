package model;
import constants.EstadoJornada;
import constants.EstadoOrden;
import java.util.ArrayList;
import java.util.List;

public class Jornada {

    private final String id;
    private final Cuadrilla cuadrilla;
    private final List<OrdenServicio> ordenes;
    private final List<OrdenServicio> ordenesConResultado;
    private EstadoJornada estado;

    public Jornada(String id, Cuadrilla cuadrilla) {
        this.id = id;
        this.cuadrilla = cuadrilla;
        this.ordenes = new ArrayList<>();
        this.ordenesConResultado = new ArrayList<>();
        this.estado = EstadoJornada.EN_PREPARACION;
        cuadrilla.abrirJornada(this);
    }

    public void asignarOrden(OrdenServicio orden) {
        if (estado != EstadoJornada.EN_PREPARACION) {
            throw new ReglaNegocioException("La jornada no esta en preparación");
        }

        if (orden.getEstado() != EstadoOrden.PENDIENTE) {
            throw new ReglaNegocioException("La orden no esta pendiente");
        }

        if (!orden.esCompatibleCon(cuadrilla)) {
            throw new ReglaNegocioException("La orden no es compatible con la cuadrilla");
        }

        if (orden.getHorasEstimadas() > calcularCapacidadDisponible()) {
            throw new ReglaNegocioException("La orden excede la capacidad de la cuadrilla");
        }

        orden.asignar();
        ordenes.add(orden);
    }

    public void retirarOrden(OrdenServicio orden) {
        if (estado != EstadoJornada.EN_PREPARACION) {
            throw new ReglaNegocioException("La jornada no esta en preparación");
        }

        if (!ordenes.contains(orden)) {
            throw new ReglaNegocioException("La orden no pertenece a esta jornada");
        }

        orden.retirar();
        ordenes.remove(orden);
    }

    public void iniciar() {
        if (estado != EstadoJornada.EN_PREPARACION) {
            throw new ReglaNegocioException("La jornada ya fue iniciada");
        }

        if (ordenes.isEmpty()) {
            throw new ReglaNegocioException("No se puede iniciar una jornada vacía");
        }

        for (OrdenServicio orden : ordenes) {
            orden.iniciarVisita();
        }

        estado = EstadoJornada.EN_ATENCION;
    }

    public void registrarResultado(OrdenServicio orden, boolean completada) {
        if (estado != EstadoJornada.EN_ATENCION) {
            throw new ReglaNegocioException("La jornada no esta en atención");
        }

        if (!ordenes.contains(orden)) {
            throw new ReglaNegocioException("La orden no pertenece a esta jornada");
        }

        if (ordenesConResultado.contains(orden)) {
            throw new ReglaNegocioException("Esta orden ya tiene un resultado");
        }

        if (completada) {
            orden.completar();
        } else {
            orden.registrarVisitaFallida();
        }

        ordenesConResultado.add(orden);

        if (ordenesConResultado.size() == ordenes.size()) {
            estado = EstadoJornada.CERRADA;
            cuadrilla.liberar();
        }
    }

    public double calcularHorasAsignadas() {
        double total = 0;

        for (OrdenServicio orden : ordenes) {
            total += orden.getHorasEstimadas();
        }

        return total;
    }

    public double calcularCapacidadDisponible() {
        return cuadrilla.getCapacidadHoras() - calcularHorasAsignadas();
    }

    public boolean estaCerrada() {
        return estado == EstadoJornada.CERRADA;
    }

    public String getId() { return id; }
    public Cuadrilla getCuadrilla() { return cuadrilla; }
    public EstadoJornada getEstado() { return estado; }
    public List<OrdenServicio> getOrdenes() { return new ArrayList<>(ordenes); }
}
