package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;

public class Zona extends AbstractListModel {
	String nombre;
	String imagen;
	String tipoZona;
	boolean seleccionado;
	ImageIcon imageIcon;
	private List<ED> listaED;
	String ruta = "Imagenes/";
	
	int i = 0;

	public Zona(String name, String imagen) {
		this.nombre = name;
		this.tipoZona = name.split(" ")[0];
		this.imagen = imagen;
		this.seleccionado = false;
		this.imageIcon = new ImageIcon(ruta + imagen);
		listaED = new ArrayList<>();

	}

	public Zona(String linea) {
		String[] datos = linea.split("[$]");
		this.nombre = datos[0];
		this.tipoZona = nombre.split(" ")[0];
		this.imagen = datos[1];
		this.seleccionado = Boolean.parseBoolean(datos[2]);
		this.imageIcon = new ImageIcon(ruta + imagen);
		listaED = new ArrayList<>();
	}

	public String toStringFile() {
		String s;
		s = this.nombre + "$" + this.imagen + "$" + this.seleccionado;
		return s;
	}

	public String toString() {
		return this.nombre;
	}

	public int getNextValue(String tipo) {
		List<ED> listaPorTipo = new ArrayList<>();
		Boolean estaCogido = false;
		for (ED ed : listaED) {
			if (ed.getTipoED().equalsIgnoreCase(tipo)) {
				listaPorTipo.add(ed);
			}
		}
		for (int j = 1; j <= 5; j++) {
			estaCogido = false;
			for (ED edTipo : listaPorTipo) {
				if (edTipo.getIDdeNombre() == j) {
					estaCogido = true;
				}
			}
			if (!estaCogido) {
				return j;
			}
		}
		return 0;
	}

	public List<ED> getCopiaListaED() {
		List<ED> copia = new ArrayList<>();
		copia.addAll(listaED);
		return copia;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public void select() {
		seleccionado = !seleccionado;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
		this.tipoZona = nombre.split(" ")[0];
	}

	public int getIDdeNombre() {
		int j;
		switch (this.nombre.split(" ")[1]) {
		case "uno":
			j = 1;
			break;
		case "dos":
			j = 2;
			break;
		case "tres":
			j = 3;
			break;
		case "cuatro":
			j = 4;
			break;
		case "cinco":
			j = 5;
			break;
		default:
			j = 0;
			break;
		}
		return j;
	}

	public boolean isSelected() {
		return seleccionado;
	}

	public String getNombre() {
		return nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public String getTipoZona() {
		return tipoZona;
	}

	public void setImage(String img) {
		this.imagen = img;
		this.imageIcon = new ImageIcon(ruta + img);
	}

	public void addED(ED ed) {
		listaED.add(ed);
		this.fireContentsChanged(listaED, 0, listaED.size());
	}

	public void removeED(ED ed) {
		listaED.remove(ed);
		this.fireContentsChanged(listaED, 0, listaED.size());
	}

	public void removeED(int index) {
		listaED.remove(index);
		this.fireContentsChanged(listaED, 0, listaED.size());
	}

	public int getNumED() {
		return listaED.size();
	}

	@Override
	public Object getElementAt(int index) {
		return listaED.get(index);
	}

	@Override
	public int getSize() {
		return listaED.size();
	}

}