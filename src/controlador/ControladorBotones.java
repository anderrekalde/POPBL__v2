package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import modelo.Casa;
import modelo.Zona;
import vista.MenuConfig;
import vista.VentanaPrincipal;

public class ControladorBotones implements ActionListener{
	VentanaPrincipal vistaPrincipal;
	Casa modeloCasa;
	
	public ControladorBotones (VentanaPrincipal vistaPrincipal, Casa modelo) {
		this.vistaPrincipal = vistaPrincipal;
		this.modeloCasa = modelo;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Config":
			vistaPrincipal.setFlag(-1);
			modeloCasa.guardarDatosFichero(modeloCasa.getRutaCasaConfig());
			MenuConfig menu = new MenuConfig(vistaPrincipal, modeloCasa);
			menu.setAlwaysOnTop(true);
			break;
		case "Mute":
			if(vistaPrincipal.getMute()) {
				vistaPrincipal.getbMute().setIcon(new ImageIcon("Imagenes/Mic.png"));
				vistaPrincipal.setMute(false);
			}else {
				vistaPrincipal.getbMute().setIcon(new ImageIcon("Imagenes/mute1.png"));
				vistaPrincipal.setMute(true);
			}
			break;
		case "Salir":
			modeloCasa.guardarDatosFichero(modeloCasa.getRutaCasaConfig());
			vistaPrincipal.dispose();
			System.exit(0);
			break;
		}
	}
}
