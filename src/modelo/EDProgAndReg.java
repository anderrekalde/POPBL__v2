package modelo;

import java.util.ArrayList;
import java.util.List;

public class EDProgAndReg extends EDProgramable {
	String programa;
	List<String> listaProgramas;
	int vMin, vMax, valor;

	public EDProgAndReg(String n, String img, boolean estado, int vMin, int vMax, int valor, String programa, List<String> lista) {
		super(n, img, estado, programa, lista);
		this.vMax = vMax;
		this.vMin = vMin;
		this.valor = valor;

		if(this.vMax> 350) this.vMax = 350;
		if(this.valor> 350) this.valor = 350;
	}
	
	public EDProgAndReg(String linea) {
		super(linea);
		String[] datos = linea.split("[$]");
		this.vMin = Integer.parseInt(datos[5]);
		this.vMax = Integer.parseInt(datos[6]);
		this.valor = Integer.parseInt(datos[7]);
		
		if(this.vMax> 300) this.vMax = 300;		
		if(this.valor> vMax) this.valor = vMax;
		if(this.valor< vMin) this.valor = vMin;
	}

	public int getValor() {
		return valor;
	}

	public int getvMin() {
		return vMin;
	}

	public int getvMax() {
		return vMax;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public String toStringFile() {
		String s = "&"+super.toStringFile().substring(1)+"$"+this.vMin+"$"+this.vMax+"$"+this.valor;
		return s;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
