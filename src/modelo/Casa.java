package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

public class Casa{
	private String rutaCasaConfig = "Ficheros/Casa.txt";
	List<Zona> lista;
	List<Zona> listaSeleccionados;
	
	public Casa() {
		lista = cargarDatosFichero(rutaCasaConfig);
		listaSeleccionados = new ArrayList<>();
		if (lista == null) {
			lista = new ArrayList<>();
		}
	}
	
	public List<Zona> cargarDatosFichero(String rutaCasa) {
		String linea;
		Zona z;
		List<Zona> lista = new ArrayList<>();
		
		try (BufferedReader in = new BufferedReader(new FileReader(rutaCasa))) {
			
			while ((linea = in.readLine()) != null) {
				lista.add(z = new Zona(linea));
				while (!(linea = in.readLine()).contains("*")) {
					if (linea.startsWith("#")){
						z.addED(new EDRegulable(linea.substring(1)));				
					} else if (linea.startsWith("€")){
						z.addED(new EDProgramable(linea.substring(1)));						
					} else if (linea.startsWith("&")){
						z.addED(new EDProgAndReg(linea.substring(1)));	
					} else if (linea.startsWith("")){
						z.addED(new ED(linea));
					}		
				}
				
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	
	public void guardarDatosFichero(String rutaCasa) {
		try (PrintWriter out = new PrintWriter(new FileWriter(rutaCasa))) {

			for (Zona zona : lista) {
				out.println(zona.toStringFile());
				for (ED ed:zona.getCopiaListaED()){
					out.println(ed.toStringFile());					
				}
				out.println("*");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void add(Zona zona) {
		lista.add(zona);
	}
	
	public int getNextValue(String tipo){
		List<Zona> listaPorTipo = new ArrayList<>();
		Boolean estaCogido = false;
		for (Zona z: lista){
			if (z.getTipoZona().equalsIgnoreCase(tipo)){
				listaPorTipo.add(z);
			}
		}
		for (int i = 1;i<=5;i++){
			estaCogido = false;
			for (Zona zTipo:listaPorTipo){
				if(zTipo.getIDdeNombre() == i){
					estaCogido = true;
				}
			}
			if (!estaCogido){
				return i;
			}
		}
		return 0;
	}	
	
	public void recargarDatos(String rutaCasa){
		lista.clear();
		lista = cargarDatosFichero(rutaCasa);
		if (lista == null) {
			lista = new ArrayList<>();
		}
	}

	public void remove(Zona zona) {
		lista.remove(zona);
	}

	public void remove(int indice) {
		lista.remove(indice);
	}
	
	public Zona[] getStringsByType(String type){
		listaSeleccionados.clear();
		for (Zona zona : lista){
			if (zona.getTipoZona().equalsIgnoreCase(type)){
				listaSeleccionados.add(zona);
			}
		}
		Zona[] array = new Zona[listaSeleccionados.size()];
		for (int i = 0;i<listaSeleccionados.size();i++){
			array[i] = listaSeleccionados.get(i);
		}
		return array;
	}
	
	public List<Zona> getSeleccionados() {
		return listaSeleccionados;
		
	}
	
	public Object getElementAt(int index) {
		return lista.get(index);
	}

	public int getSize() {
		return lista.size();
	}
	
	public List<Zona> getCopiaLista() {
		List<Zona> copia = new ArrayList<>();
		copia.addAll(lista);
		return copia;
	}
	
	public int getSizeByType(String type){
		int i = 0;
		for (Zona zona : lista){
			if (zona.getTipoZona().equalsIgnoreCase(type)){
				i++;
			}
		}
		return i;
	}

	public String getRutaCasaConfig() {
		return rutaCasaConfig;
	}

	public void setRutaCasaConfig(String rutaCasaConfig) {
		this.rutaCasaConfig = rutaCasaConfig;
	}

}
