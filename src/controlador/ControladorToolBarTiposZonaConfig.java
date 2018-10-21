package controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import modelo.Casa;
import vista.MenuConfig;

public class ControladorToolBarTiposZonaConfig implements ActionListener {
	MenuConfig vista;
	Casa casaModelo;
	Toolkit toolkit = Toolkit.getDefaultToolkit();

	public ControladorToolBarTiposZonaConfig(MenuConfig vista, Casa modelo) {
		this.vista = vista;
		this.casaModelo = modelo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String tipo;
		switch (e.getActionCommand()) {
		case "Ba�os":
			tipo = "Ba�o";
			vista.setActualType("Ba�o");
			vista.getListaSeleccion().setListData(casaModelo.getStringsByType(tipo));
			break;
		case "Cocinas":
			tipo = "Cocina";
			vista.setActualType("Cocina");
			vista.getListaSeleccion().setListData(casaModelo.getStringsByType(tipo));
			break;
		case "Habitaciones":
			tipo = "Habitaci�n";
			vista.setActualType("Habitaci�n");
			vista.getListaSeleccion().setListData(casaModelo.getStringsByType(tipo));
			break;
		case "Jardines":
			tipo = "Jard�n";
			vista.setActualType("Jard�n");
			vista.getListaSeleccion().setListData(casaModelo.getStringsByType(tipo));
			break;
		case "Salones":
			tipo = "Sal�n";
			vista.setActualType("Sal�n");
			vista.getListaSeleccion().setListData(casaModelo.getStringsByType(tipo));
			break;
		default:
			break;
		}
		for (JButton b : vista.getListaBotonesAcciones()){
			if (b.getText().equals(e.getActionCommand())){
				b.setEnabled(false);
			}else{
				b.setEnabled(true);
			}
		}
		
	}

}
