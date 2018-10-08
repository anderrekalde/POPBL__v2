package vista;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Logo extends JButton {

	public Logo(){
		this.setIcon(new ImageIcon("Imagenes/Logo.png"));
		this.setBorderPainted(false);
		this.setBorder(null);
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
	}
	
}
