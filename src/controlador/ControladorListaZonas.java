package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import application.VozAsistente;
import modelo.Zona;
import vista.MiBotonZona;
import vista.MiPanelImgAndEDs;
import vista.VentanaPrincipal;

public class ControladorListaZonas implements ActionListener {
	static final String PATHIMG = "Imagenes/";
	VentanaPrincipal vista;
	VozAsistente voz;

	public ControladorListaZonas(VentanaPrincipal vista) {
		this.vista = vista;
		voz = new VozAsistente(this.vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (vista.getFlag() == 0) {
			MiPanelImgAndEDs panelCentral = vista.getPanelCentral();
			MiBotonZona botonPulsado = vista.getBotonLista(e.getActionCommand());
			voz.speak("entrando a " + e.getActionCommand());
			Zona zBoton;
			Zona zImagen;
			Zona zAux;

			zBoton = botonPulsado.getZona();
			zImagen = vista.getZonaActual();

			zImagen.select();
			zBoton.select();

			zAux = zImagen;
			panelCentral.setZona(zBoton);
			vista.getZoneName().setText(zBoton.getNombre());
			botonPulsado.setZona(zAux);
		}
	}
}
