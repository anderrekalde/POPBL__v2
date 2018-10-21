package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import modelo.Casa;
import modelo.ED;
import modelo.EDProgAndReg;
import modelo.EDProgramable;
import modelo.EDRegulable;
import modelo.Zona;
import vista.DialogoED;
import vista.VentanaPrincipal;

public class ControladorListaED implements ActionListener {

	VentanaPrincipal vista;
	DialogoED dialogoED;
	Casa casa;

	public ControladorListaED(VentanaPrincipal vista, Casa casa) {
		this.vista = vista;
		this.casa = casa;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		vista.setFlag(vista.getFlag() + 1);

		if (vista.getFlag() == 1) {
			List<Zona> listaZonas = casa.getCopiaLista();
			String tipo = "";
			for (Zona z : listaZonas) {

				if (vista.getZonaActual().equals(z)) {

					for (ED electrodomestico : z.getCopiaListaED()) {
						electrodomestico.switchIsSelected();
						if (electrodomestico.getNombre().equals(e.getActionCommand())) {

							if (electrodomestico instanceof EDProgramable
									&& !(electrodomestico instanceof EDProgAndReg)) {
								tipo = "programable";
							} else if (electrodomestico instanceof EDProgAndReg) {
								tipo = "Prog + Reg";
							} else if (electrodomestico instanceof EDRegulable) {
								tipo = "regulable";
							} else if (electrodomestico instanceof ED) {
								tipo = "standar";
							}

							dialogoED = new DialogoED(vista, electrodomestico.getNombre(), tipo, electrodomestico);

						}
					}
				}

			}
		}

	}

	public DialogoED getDialogoED() {
		return dialogoED;
	}

}
