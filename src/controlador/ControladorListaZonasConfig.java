package controlador;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.Zona;
import vista.DialogoEditListEDZona;
import vista.MenuConfig;

public class ControladorListaZonasConfig implements ListSelectionListener, MouseListener{
	
	MenuConfig vista;
	
	public ControladorListaZonasConfig(MenuConfig vista) {
		this.vista = vista;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Zona selectedZona = vista.getSelectedZone();
		if (e.getValueIsAdjusting())
			return;
		if(selectedZona == null){
			vista.getListaSeleccion().setSelectedIndex(0);
			selectedZona = vista.getListaSeleccion().getSelectedValue();
		}
		vista.getPanelImg().setImagen(toolkit.createImage("Imagenes/"+selectedZona.getImagen()));
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            DialogoEditListEDZona dialogEDs = new DialogoEditListEDZona(vista);
        }		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
