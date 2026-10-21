package model;
import constants.EstadoOrden;
import constants.Zona;

public abstract class OrdenServicio {

    private static final double CARGO_VISITA_ADICIONAL = 40.0;
    private static final double TARIFA_BASE_METROPOLITANA = 100.0;
    private static final double TARIFA_BASE_PERIFERICA = 175.0;
    private final String codigo;
    private final String cliente;
    private final String direccion;
    private final double horasEstimadas;
    private final Zona zona;
    private EstadoOrden estado;
    private int visitasRealizadas;

    public OrdenServicio(String codigo, String cliente, String direccion,
            double horasEstimadas, Zona zona) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new ReglaNegocioException("El codigo no debe ser vacio");
        }
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre no debe ser vacio");
        }
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new ReglaNegocioException("La dirección no debe ser vacía");
        }
        if (horasEstimadas <= 0) {
            throw new ReglaNegocioException("Las horas deben ser mayor a 0");
        }
        if (zona == null) {
            throw new ReglaNegocioException("La zona es obligatoria");
        }
        this.codigo = codigo.trim();
        this.cliente = cliente.trim();
        this.direccion = direccion.trim();
        this.horasEstimadas = horasEstimadas;
        this.zona = zona;
        this.estado = EstadoOrden.PENDIENTE;
        this.visitasRealizadas = 0;
    }

    public abstract String getCategoria();
    public abstract double calcularCosto();
    public abstract int calcularDiasAtencion();
    public abstract int getMaxVisitas();
    public boolean esCompatibleCon(Cuadrilla cuadrilla) {
        return true;
    }
    protected double calcularTarifaBaseZona() {
        if (zona == Zona.METROPOLITANA) {
            return TARIFA_BASE_METROPOLITANA;
        }
        return TARIFA_BASE_PERIFERICA;
    }

    public double getCargoVisitaAdicional() {
        return CARGO_VISITA_ADICIONAL;
    }

    public void asignar() {
        if (estado != EstadoOrden.PENDIENTE) {
            throw new ReglaNegocioException("La orden " + codigo + " no esta pendiente estado actual: " + estado );
        }
        estado = EstadoOrden.ASIGNADA;
    }

    public void retirar() {
        if (estado != EstadoOrden.ASIGNADA) {
            throw new ReglaNegocioException("La orden " + codigo + " no esta asignada estado actual: " + estado );
        }
        estado = EstadoOrden.PENDIENTE;
    }

    public void iniciarVisita() {
        if (estado != EstadoOrden.ASIGNADA) {
            throw new ReglaNegocioException("La orden " + codigo + " no esta asignada estado actual: " + estado );
        }
        estado = EstadoOrden.EN_ATENCION;
        visitasRealizadas++;
    }

    public void completar() {
        if (estado != EstadoOrden.EN_ATENCION) {
            throw new ReglaNegocioException("La orden " + codigo + " no esta en atencion estado actual: " + estado);
        }
        estado = EstadoOrden.COMPLETADA;
    }

    public void registrarVisitaFallida() {
        if (estado != EstadoOrden.EN_ATENCION) {
            throw new ReglaNegocioException("La orden " + codigo + " no esta en atencion estado actual: " + estado);
        }
        if (visitasRealizadas >= getMaxVisitas()) {
            estado = EstadoOrden.CANCELADA;
        } else {
            estado = EstadoOrden.PENDIENTE;
        }
    }

    public String getCodigo() { return codigo; }
    public String getCliente() { return cliente; }
    public String getDireccion() { return direccion; }
    public double getHorasEstimadas() { return horasEstimadas; }
    public Zona getZona() { return zona; }
    public EstadoOrden getEstado() { return estado; }
    public int getVisitasRealizadas() { return visitasRealizadas; }
}
