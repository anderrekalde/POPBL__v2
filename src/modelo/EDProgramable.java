package modelo;

import java.util.ArrayList;
import java.util.List;

public class EDProgramable extends ED {
	String programa;
	List<String> listaProgramas;
	
	public EDProgramable(String n, String img, boolean estado, String programa, List<String> lista) {
		super(n, img, estado);
		this.programa = programa;
		this.listaProgramas = lista;
	}
	
	public String getPrograma() {
		return programa;
		
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	
	}

	public List<String> getListaProgramas() {
		return listaProgramas;
	}

	public EDProgramable(String linea){
		super(linea);
		String[] datos = linea.split("[$]");
		this.programa = datos[3];
		String programas = datos[4];
		String[] datos2 = programas.split("[/]");
		this.listaProgramas = new ArrayList<String>();
		for(int i = 0; i < datos2.length; i++) {
			
			listaProgramas.add(datos2[i]);
		}
		
	}
	

	public List<String> getLista(){
		return listaProgramas;
	}
	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public String toStringFile() {
		String s = "€"+super.toStringFile()+"$"+this.programa+"$";
		for (String prog: listaProgramas){
			s+=prog+"/";
		}
		return s;
	}

}