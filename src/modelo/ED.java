package modelo;

import java.util.ArrayList;
import java.util.Observable;

import javax.swing.ImageIcon;

public class ED{
	String nombre;
	String imagen;
	String tipoED;
	boolean estado, isSelected;
	
	public ED(String n, String img, boolean estado){
		this.nombre = n;
		this.tipoED = nombre.split(" ")[0];
		this.imagen = img;
		this.estado = estado;
		this.isSelected = false;
	}

	public ED(String linea){
		String[] datos = linea.split("[$]");
		this.nombre = datos[0];
		this.tipoED = nombre.split(" ")[0];
		this.imagen = datos[1];
		this.estado = Boolean.parseBoolean(datos[2]);
		this.isSelected = false;
	}	
	
	public int getIDdeNombre(){
		int i;
		switch (this.nombre.split(" ")[1]) {
		case "uno":
			i = 1;
			break;
		case "dos":
			i = 2;
			break;
		case "tres":
			i = 3;
			break;
		case "cuatro":
			i = 4;
			break;
		case "cinco":
			i = 5;
			break;
		case "seis":
			i = 6;
			break;
		case "siete":
			i = 7;
			break;
		case "ocho":
			i = 8;
			break;
		case "nueve":
			i = 9;
			break;
		case "diez":
			i = 10;
			break;
		default:
			i = 0;
			break;
		}
		return i;
	}
	
	public String getTipoED() {
		return tipoED;
	}

	public void switchStatus(){
		this.estado = !estado;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	public void switchIsSelected(){
		isSelected = !isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean getEstado() {
		return estado;
	}

	public void cambiarEstado() {
		
		setEstado(!getEstado());
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
		
	public String toStringFile(){
		String s;
		s = this.nombre+"$"+this.imagen+"$"+this.estado;
		return s;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
