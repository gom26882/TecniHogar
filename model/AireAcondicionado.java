package model;
import constants.Zona;

public class AireAcondicionado extends OrdenServicio {

    private final int btu;

    public AireAcondicionado(String codigo, String cliente, String direccion,
            double horasEstimadas, Zona zona, int btu) {
        super(codigo, cliente, direccion, horasEstimadas, zona);

        if (zona != Zona.METROPOLITANA) {
            throw new ReglaNegocioException(
                "El mantenimiento de aire acondicionado solo es para zona metropolitana.");
        }

        if (btu < 9000 || btu > 60000) {
            throw new ReglaNegocioException(
                "La capacidad debe estar entre 9000 y 60000 BTU");
        }
        this.btu = btu;
    }

    @Override
    public String getCategoria() {
        return "Mantenimiento de aire acondicionado";
    }

    @Override
    public double calcularCosto() {
        return calcularTarifaBaseZona() + 110 * getHorasEstimadas() + 80;
    }

    @Override
    public int calcularDiasAtencion() {
        return 2;
    }

    @Override
    public int getMaxVisitas() {
        return 1;
    }

    @Override
    public boolean esCompatibleCon(Cuadrilla cuadrilla) {
        return cuadrilla.isCertificadaRefrigeracion();
    }

    public int getBtu() {
        return btu;
    }
}