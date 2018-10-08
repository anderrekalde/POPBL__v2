package vista;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class MiPanelImg extends JPanel {
	
	Image imagen;
	public MiPanelImg(Image imagen) {
		if (imagen != null) {
		 this.imagen = imagen;
		}
	}
	
	public void setImagen(Image nuevaImagen) {
		this.imagen = nuevaImagen;

		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D)g;
		if (imagen != null) {
			gr.drawImage(imagen, 0, 0, getWidth(), getHeight(),this);
			setOpaque(false);
		} else {
			setOpaque(true);
		}	
	}
}
