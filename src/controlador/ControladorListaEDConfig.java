package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Zona;
import vista.DialogoAñadirEditarED;
import vista.DialogoEditListEDZona;

public class ControladorListaEDConfig implements ActionListener {

	DialogoEditListEDZona vista;
	Zona selectedZona;

	public ControladorListaEDConfig(DialogoEditListEDZona vista, Zona selectedZona) {
		this.vista = vista;
		this.selectedZona = selectedZona;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Añadir":
			vista.getjLista().clearSelection();
			new DialogoAñadirEditarED(vista, "Añadir electrodoméstico", true, true);
			vista.getjLista().setSelectedIndex(0);
			vista.getAccBorrar().setEnabled(true);
			vista.getAccEdit().setEnabled(true);
			break;
		case "Eliminar":
			if (vista.getSelectedED() != null) {
				selectedZona.removeED(vista.getSelectedED());
			}
			if (vista.getjLista().getModel().getSize() == 0){
				vista.getAccBorrar().setEnabled(false);
				vista.getAccEdit().setEnabled(false);
			}
			break;
		case "Editar":
			if (vista.getSelectedED() != null) {
				new DialogoAñadirEditarED(vista, "Editar electrodoméstico", true, false);
			}
			break;
		}
	}

}
