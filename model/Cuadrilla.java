package model;
public class Cuadrilla {

    private final String codigo;
    private final double capacidadHoras;
    private final boolean certificadaRefrigeracion;
    private Jornada jornadaAbierta;

    public Cuadrilla(String codigo, double capacidadHoras, boolean certificadaRefrigeracion) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new ReglaNegocioException("El codigo no debe ser vacio");
        }

        if (!(capacidadHoras > 0) || Double.isInfinite(capacidadHoras)) {
            throw new ReglaNegocioException("La capacidad de horas debe ser positiva y valida");
        }

        this.codigo = codigo.trim();
        this.capacidadHoras = capacidadHoras;
        this.certificadaRefrigeracion = certificadaRefrigeracion;
        this.jornadaAbierta = null;
    }

    public boolean tieneJornadaAbierta() {
        return jornadaAbierta != null;
    }

    public void abrirJornada(Jornada jornada) {
        if (tieneJornadaAbierta()) {
            throw new ReglaNegocioException("La cuadrilla " + codigo
                    + " ya tiene la jornada " + jornadaAbierta.getId() + " sin cerrar");
        }
        this.jornadaAbierta = jornada;
    }

    public void liberar() {
        this.jornadaAbierta = null;
    }

    public String getCodigo() { return codigo; }
    public double getCapacidadHoras() { return capacidadHoras; }
    public boolean isCertificadaRefrigeracion() { return certificadaRefrigeracion; }
    public Jornada getJornadaAbierta() { return jornadaAbierta; }
}