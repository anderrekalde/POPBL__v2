package vista;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MiBotonEstado extends JButton{
	String nombre;
	final static String PATHIMG= "Imagenes/";
	Boolean estado;
	ImageIcon imageIcon;
	
	public MiBotonEstado(Boolean estado){
		this.estado = estado;
		//this.nombre = nombre;
		this.imageIcon = this.estado? new ImageIcon(PATHIMG+"On.png"):new ImageIcon(PATHIMG+"Off.png");
		this.setIcon(imageIcon);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBorderPainted(false);
		this.setBorder(null);
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre( String nombre) {
		 this.nombre = nombre;
	}
	public void switchStatus(){
		this.estado = !estado;
		this.imageIcon = estado? new ImageIcon(PATHIMG+"On.png"):new ImageIcon(PATHIMG+"Off.png");
		this.setIcon(imageIcon);
	}
	
	public Boolean getEstado() {
		return estado;
	}
	
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}
}
