
package model;

import constants.Zona;

public class InstalacionElectricaCertificada extends Instalacion {

    private final double valorMateriales;

    public InstalacionElectricaCertificada(String codigo, String cliente,
        String direccion, double horasEstimadas,
        Zona zona, int puntos, double valorMateriales) {
        super(codigo, cliente, direccion, horasEstimadas, zona, puntos);

        if (valorMateriales <= 0) {
            throw new ReglaNegocioException("El valor de los materiales debe ser mayor a 0");
        }

        this.valorMateriales = valorMateriales;
    }

    public double calcularGarantia() {
        return valorMateriales * 0.05;
    }

    @Override
    public String getCategoria() {
        return "Instalación electrica certificada";
    }

    @Override
    public double calcularCosto() {
        return super.calcularCosto() + 120 + calcularGarantia();
    }

    @Override
    public int calcularDiasAtencion() {
        return super.calcularDiasAtencion() + 2;
    }

    @Override
    public int getMaxVisitas() {
        return 2;
    }

    public double getValorMateriales() {
        return valorMateriales;
    }
}