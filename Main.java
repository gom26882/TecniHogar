import controller.TecniHogarController;
import controller.DatosIniciales;
import view.ConsolaView;

public class Main {

    public static void main(String[] args) {
        TecniHogarController controller = new TecniHogarController();

        DatosIniciales.cargar(controller);

        ConsolaView vista = new ConsolaView(controller);
        vista.iniciar();
    }
}