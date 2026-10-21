package model;

import constants.Zona;

public class Diagnostico extends OrdenServicio {

    private static final double MAX_HORAS = 1.5;
    private static final double COSTO_METROPOLITANA = 150.0;
    private static final double COSTO_PERIFERICA = 225.0;

    public Diagnostico(String codigo, String cliente, String direccion,double horasEstimadas, Zona zona) {
        super(codigo, cliente, direccion, horasEstimadas, zona);
        if (horasEstimadas > MAX_HORAS) {
            throw new ReglaNegocioException("Un diagnóstico no puede superar 1.5 horas estimadas.");
        }
    }

    @Override
    public String getCategoria() {
        return "Diagnóstico";
    }

    @Override
    public double calcularCosto() {
        if (getZona() == Zona.METROPOLITANA) {
            return COSTO_METROPOLITANA;
        }
        return COSTO_PERIFERICA;
    }

    @Override
    public int calcularDiasAtencion() {
        if (getZona() == Zona.METROPOLITANA) {
            return 1;
        }
        return 2;
    }

    @Override
    public int getMaxVisitas() {
        return 3;
    }
}
