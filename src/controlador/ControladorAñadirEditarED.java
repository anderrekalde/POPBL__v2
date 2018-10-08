package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import modelo.ED;
import vista.DialogoAñadirEditarED;
import vista.SingleRootFileSystemView;

public class ControladorAñadirEditarED implements ActionListener {

	DialogoAñadirEditarED vista;

	public ControladorAñadirEditarED(DialogoAñadirEditarED vista) {
		this.vista = vista;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ED ed;
		switch (e.getActionCommand()) {
		case "ok":
			try{
			vista.crearEditarED(vista.getSelectedRadioButton());
			vista.dispose();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(vista, "Introduce los datos correctamente", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "cancel":
			vista.dispose();
			break;
		case "lista":
			JFileChooser chooserLista = new JFileChooser();
			chooserLista.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooserLista.setVisible(true);
			int returnValLista = chooserLista.showOpenDialog(vista);
			if (returnValLista == JFileChooser.APPROVE_OPTION) {
				vista.setFicheroListaProgramas(chooserLista.getSelectedFile().getAbsolutePath());
				vista.gettProgramas().setText(vista.getProgramasFromFile(chooserLista.getSelectedFile().getAbsolutePath()));
			}
			break;
		case "img":
			File dir = new File("Imagenes/");
			FileSystemView fsv = new SingleRootFileSystemView(dir);
			JFileChooser chooserImg = new JFileChooser(fsv);
			chooserImg.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooserImg.setVisible(true);
			int returnVal = chooserImg.showOpenDialog(vista);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				vista.setFicheroImagen(chooserImg.getSelectedFile().getName());
				vista.gettImg().setText(chooserImg.getSelectedFile().getName());
			}
			break;
		case "Normal":
			vista.getNombreED().removeAllItems();
			vista.setEverything("Normal");
			for (String s : vista.getOpcionesNormal()) {
				vista.getNombreED().addItem(s);
			}
			break;
		case "Regulable":
			vista.getNombreED().removeAllItems();
			vista.setEverything("Regulable");
			for (String s : vista.getOpcionesRegulable()) {
				vista.getNombreED().addItem(s);
			}
			break;
		case "Programable":
			vista.getNombreED().removeAllItems();
			vista.setEverything("Programable");
			for (String s : vista.getOpcionesProgramable()) {
				vista.getNombreED().addItem(s);
			}
			break;
		case "ProgReg":
			vista.getNombreED().removeAllItems();
			vista.setEverything("ProgReg");
			for (String s : vista.getOpcionesProgReg()) {
				vista.getNombreED().addItem(s);
			}
			break;
		}
	}
}
