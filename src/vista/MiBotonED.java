package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import modelo.ED;
import modelo.Zona;

public class MiBotonED extends JButton {

	String name;
	Zona zona;
	ImageIcon img;
	ED e;

	public MiBotonED(ED electrodomestico, Zona zona) {
		super(electrodomestico.getNombre());
		this.e = electrodomestico;
		this.name = zona.getNombre();
		this.zona = zona;
		this.img = new ImageIcon("Imagenes/" + e.getImagen());
		this.setFont(new Font("Arial", Font.BOLD, 18));
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setHorizontalTextPosition(SwingConstants.CENTER);

		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(e.getEstado() ? Color.GREEN : Color.RED, 5),
						BorderFactory.createLineBorder(Color.BLACK, 2))));
		String[] edName = electrodomestico.getNombre().split(" ");
		this.setIcon(img);
		this.setText("<html><h3 style='text-align:center;margin:0'>"+edName[0]+"</h3><h3 style='text-align:center;margin:0'>"+edName[1]+"</h3></html>");
		this.setHorizontalAlignment(SwingConstants.LEFT);
	}

	public void paintBorder() {
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(e.getEstado() ? Color.GREEN : Color.RED, 5),
						BorderFactory.createLineBorder(Color.BLACK, 2))));
	}

}
