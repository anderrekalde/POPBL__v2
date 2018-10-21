package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import modelo.Casa;
import vista.DialogoA�adirEditarZona;
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
		case "A�adir":

			if(casaModelo.getCopiaLista().size()> 25) {
				
				 JOptionPane.showMessageDialog(vista, "No pueden haber mas de 25 zonas", "Error Zonas", JOptionPane.ERROR_MESSAGE);
				 
			}else {
			DialogoA�adirEditarZona d = new DialogoA�adirEditarZona(vista, "A�adir Zona", true, true,  casaModelo);
			
			}
			break;
		case "Borrar":
			casaModelo.remove((vista.getListaSeleccion().getSelectedValue()));
			
			break;
		case "Editar":
			DialogoA�adirEditarZona d2 = new DialogoA�adirEditarZona(vista, "A�adir Zona", true, false, casaModelo);			
			break;
		default:
			break;
		}
		vista.updateList();

	}

}	