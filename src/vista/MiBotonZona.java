package vista;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import modelo.Zona;

public class MiBotonZona extends JButton {
	final static String PATHIMG= "Imagenes/";
	Image imagen;
	String name;
	ImageIcon imageIcon;
	Zona zona;
	int numZonas;
	
	//boton zonas
	public MiBotonZona(Zona zona, int numZonas){
		super(zona.getNombre());
		this.name = zona.getNombre();
		this.zona = zona;
		this.numZonas = numZonas;
		imageIcon = new ImageIcon(PATHIMG + zona.getImagen()); // load the image to a imageIcon
		this.imagen = imageIcon.getImage().getScaledInstance(1000/numZonas, 1000/numZonas,  Image.SCALE_SMOOTH); // transform it 
		imageIcon = new ImageIcon(imagen);  // transform it back
		this.setFont(new Font("Arial",Font.PLAIN,35));
		
		this.setIcon(imageIcon);
		this.setHorizontalAlignment(SwingConstants.LEFT);
	}
	
	public void setZona(Zona zona){
		this.name = zona.getNombre();
		//this.zona.select();
		this.zona = zona;
		//this.zona.select();
		ImageIcon imageIcon = new ImageIcon(PATHIMG + zona.getImagen()); // load the image to a imageIcon
		this.imagen = imageIcon.getImage().getScaledInstance(1000/numZonas, 1000/numZonas,  Image.SCALE_SMOOTH); // transform it 
		imageIcon = new ImageIcon(imagen);  // transform it back
		
		this.setText(name);
		this.setActionCommand(name);
		this.setIcon(imageIcon);
		this.setHorizontalAlignment(SwingConstants.LEFT);
		
		repaint();
	}
	
	public Image getImagen(){
		return this.imagen;
	}

	public void setImagen(Image nuevaImagen) {
		this.imagen = nuevaImagen;

		repaint();
	}
	
	public Zona getZona() {
		return zona;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public String getName() {
		return name;
	}
	
}
