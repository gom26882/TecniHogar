
package model;

import constants.Zona;

public class Instalacion extends OrdenServicio {

    private final int puntos;

    public Instalacion(String codigo, String cliente, String direccion,
                    double horasEstimadas, Zona zona, int puntos) {
        super(codigo, cliente, direccion, horasEstimadas, zona);

        if (puntos <= 0) {
            throw new ReglaNegocioException("La cantidad de puntos tiene que ser mayor a 0");
        }

        this.puntos = puntos;
    }

    public double calcularHorasPorPuntos() {
        return puntos * 0.75;
    }

    public double calcularHorasFacturables() {
        double horas = Math.max(getHorasEstimadas(), calcularHorasPorPuntos());
        return Math.ceil(horas);
    }

    @Override
    public String getCategoria() {
        return "Instalación";
    }

    @Override
    public double calcularCosto() {
        return calcularTarifaBaseZona() + 90 * calcularHorasFacturables();
    }

    @Override
    public int calcularDiasAtencion() {
        if (getZona() == Zona.METROPOLITANA) {
            return 3;
        } else {
            return 5;
        }
    }

    @Override
    public int getMaxVisitas() {
        return 3;
    }

    public int getPuntos() {
        return puntos;
    }
}
