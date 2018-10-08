package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import modelo.Casa;
import modelo.Zona;
import vista.MenuConfig;
import vista.VentanaPrincipal;

public class ControladorBotonesConfig implements ActionListener {

	MenuConfig vistaMenu;
	VentanaPrincipal vistaPrincipal;
	Casa casaConfig;

	public ControladorBotonesConfig(MenuConfig vistaMenu, Casa casaConfig, VentanaPrincipal vistaPrincipal) {
		this.vistaMenu = vistaMenu;
		this.vistaPrincipal = vistaPrincipal;
		this.casaConfig = casaConfig;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Cargar":
			JFileChooser chooserImg = new JFileChooser();
			chooserImg.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooserImg.setVisible(true);
			int returnVal = chooserImg.showOpenDialog(vistaMenu);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					casaConfig.setRutaCasaConfig(chooserImg.getSelectedFile().getAbsolutePath());
					casaConfig.recargarDatos(casaConfig.getRutaCasaConfig());
					// casaConfig.setRutaCasa(chooserImg.getSelectedFile().getAbsolutePath());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(vistaMenu, "Este archivo no es compatible", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case "Salir":
			vistaPrincipal.setFlag(0);
			casaConfig.guardarDatosFichero(casaConfig.getRutaCasaConfig());
			vistaMenu.dispose();
			vistaPrincipal.miRepintar();
			break;
		}
	}

}
