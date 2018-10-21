package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import modelo.Casa;
import vista.DialogoA�adirEditarZona;
import vista.SingleRootFileSystemView;

public class ControladorA�adirEditarZona implements ActionListener {

	DialogoA�adirEditarZona vista;
	String img;
	Casa casa;
	String tipo;
	boolean fileSelected = false, estaA�adiendo;

	public ControladorA�adirEditarZona(DialogoA�adirEditarZona vista, Casa casa, boolean estaA�adiendo) {
		this.vista = vista;
		this.casa = casa;
		this.estaA�adiendo = estaA�adiendo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String numString;
		tipo = (String) vista.getTipoZonaCombo().getSelectedItem();
		
		int nextValue;

		if (e.getActionCommand().equals("ok")) {
			int tama�o = casa.getSizeByType(tipo);					  
			if (!fileSelected) {
				img = tipo + ".png";
			}
			nextValue = casa.getNextValue(tipo);
			numString = vista.getNumToString(nextValue);
			if (estaA�adiendo) {
				if (tama�o < 5) {
					vista.addZonaToCasa(tipo + " " + numString, img);
				} else {

					JOptionPane.showMessageDialog(vista, "No puedes A�adir mas de 5 zonas del mismo tipo", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {

				if (tama�o < 5) {
					if (!vista.getSelectedZone().getTipoZona().equalsIgnoreCase(tipo)) {
						vista.getSelectedZone().setNombre(tipo + " " + numString);
					}
				} else {

					JOptionPane.showMessageDialog(vista, "No puedes A�adir mas de 5 zonas del mismo tipo", "Error",
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
