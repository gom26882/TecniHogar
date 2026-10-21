package model;

import constants.OrigenIngreso;

public class Ingreso {

    private final OrdenServicio orden;
    private final double importe;
    private final OrigenIngreso origen;

    public Ingreso(OrdenServicio orden, double importe, OrigenIngreso origen) {
        this.orden = orden;
        this.importe = importe;
        this.origen = origen;
    }

    public OrdenServicio getOrden() { return orden; }
    public String getCategoria() { return orden.getCategoria(); }
    public double getImporte() { return importe; }
    public OrigenIngreso getOrigen() { return origen; }
}
