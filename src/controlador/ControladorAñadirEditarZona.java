package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import modelo.Casa;
import vista.DialogoAñadirEditarZona;
import vista.SingleRootFileSystemView;

public class ControladorAñadirEditarZona implements ActionListener {

	DialogoAñadirEditarZona vista;
	String img;
	Casa casa;
	String tipo;
	boolean fileSelected = false, estaAñadiendo;

	public ControladorAñadirEditarZona(DialogoAñadirEditarZona vista, Casa casa, boolean estaAñadiendo) {
		this.vista = vista;
		this.casa = casa;
		this.estaAñadiendo = estaAñadiendo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String numString;
		tipo = (String) vista.getTipoZonaCombo().getSelectedItem();
		
		int nextValue;

		if (e.getActionCommand().equals("ok")) {
			int tamaño = casa.getSizeByType(tipo);					  
			if (!fileSelected) {
				img = tipo + ".png";
			}
			nextValue = casa.getNextValue(tipo);
			numString = vista.getNumToString(nextValue);
			if (estaAñadiendo) {
				if (tamaño < 5) {
					vista.addZonaToCasa(tipo + " " + numString, img);
				} else {

					JOptionPane.showMessageDialog(vista, "No puedes añadir mas de 5 zonas del mismo tipo", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {

				if (tamaño < 5) {
					if (!vista.getSelectedZone().getTipoZona().equalsIgnoreCase(tipo)) {
						vista.getSelectedZone().setNombre(tipo + " " + numString);
					}
				} else {

					JOptionPane.showMessageDialog(vista, "No puedes añadir mas de 5 zonas del mismo tipo", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				vista.getSelectedZone().setImage(img);
			}
			vista.dispose();

			vista.updateList();
			vista.getListaSeleccion().setSelectedIndex(0);
		}

		if (e.getActionCommand().equals("cancel")) {
			vista.dispose();
		}

		if (e.getActionCommand().equals("imagen")) {
			File dir = new File("Imagenes/");
			FileSystemView fsv = new SingleRootFileSystemView(dir);
			JFileChooser chooserImg = new JFileChooser(fsv);
			chooserImg.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooserImg.setVisible(true);
			int returnVal = chooserImg.showOpenDialog(vista);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				img = chooserImg.getSelectedFile().getName();
				vista.gettRuta().setText(chooserImg.getSelectedFile().getName());
				fileSelected = true;
			}
		}
	}

}
