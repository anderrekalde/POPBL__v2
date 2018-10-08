package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import modelo.Casa;
import vista.DialogoAñadirEditarZona;
import vista.MenuConfig;

public class ControladorAccionesMenuConfig implements ActionListener {
	
	MenuConfig vista;
	Casa casaModelo;

	public ControladorAccionesMenuConfig(MenuConfig vista, Casa modelo) {
		this.vista = vista;
		this.casaModelo = modelo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		case "Añadir":

			if(casaModelo.getCopiaLista().size()> 25) {
				
				 JOptionPane.showMessageDialog(vista, "No pueden haber mas de 25 zonas", "Error Zonas", JOptionPane.ERROR_MESSAGE);
				 
			}else {
			DialogoAñadirEditarZona d = new DialogoAñadirEditarZona(vista, "Añadir Zona", true, true,  casaModelo);
			
			}
			break;
		case "Borrar":
			casaModelo.remove((vista.getListaSeleccion().getSelectedValue()));
			
			break;
		case "Editar":
			DialogoAñadirEditarZona d2 = new DialogoAñadirEditarZona(vista, "Añadir Zona", true, false, casaModelo);			
			break;
		}
		vista.updateList();

	}

}
