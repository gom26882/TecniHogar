package controller;
import constants.Zona;
import model.*;

public class DatosIniciales {

    public static void cargar(TecniHogarController controller) {
        controller.agregarOrdenInicial(new Diagnostico(
                "DIAG-001", "Ana López", "Zona 10",
                1.0, Zona.METROPOLITANA));
        controller.agregarOrdenInicial(new Diagnostico(
                "DIAG-002", "Luis Pérez", "Villa Nueva",
                1.5, Zona.PERIFERICA));
        controller.agregarOrdenInicial(new Instalacion(
                "INST-001", "María Gómez", "Zona 15",
                2.5, Zona.METROPOLITANA, 5));
        controller.agregarOrdenInicial(new Instalacion(
                "INST-002", "Carlos Ruiz", "Carretera a El Salvador",
                4.0, Zona.PERIFERICA, 3));
        controller.agregarOrdenInicial(new InstalacionElectricaCertificada(
                "ELEC-001", "Sofía Díaz", "Zona 4",
                2.5, Zona.METROPOLITANA, 5, 2000));
        controller.agregarOrdenInicial(new InstalacionElectricaCertificada(
                "ELEC-002", "Jorge Marroquín", "San Miguel Petapa",
                3.0, Zona.PERIFERICA, 4, 1500));
        controller.agregarOrdenInicial(new AireAcondicionado(
                "AIRE-001", "Pedro Castillo", "Zona 11",
                2.0, Zona.METROPOLITANA, 12000));
        controller.agregarOrdenInicial(new AireAcondicionado(
                "AIRE-002", "Lucía Morales", "Zona 14",
                1.5, Zona.METROPOLITANA, 24000));
        controller.registrarCuadrilla("C-01", 8.0, true);
        controller.registrarCuadrilla("C-02", 6.0, false);
        controller.registrarCuadrilla("C-03", 4.0, false);
    }
}
