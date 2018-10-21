package modelo;

public class EDRegulable extends ED {
	int vMin;
	int vMax;
	int valor;
	
	public EDRegulable(String n, String img, boolean estado, int vMin, int vMax, int valor) {
		super(n, img, estado);
		this.vMin = vMin;
		this.vMax = vMax;
		this.valor = valor;
		if(this.vMax> 300) this.vMax = 300;
		if(this.valor> 300) this.valor = 300;
	}
	
	public int getvMax() {
		return vMax;
	}

	public void setvMax(int vMax) {
		this.vMax = vMax;
	}

	public int getvMin() {
		return vMin;
	}

	public void setvMin(int vMin) {
		this.vMin = vMin;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public EDRegulable(String linea){
		super(linea);
		String[] datos = linea.split("[$]");
		this.vMin = Integer.parseInt(datos[3]);
		this.vMax = Integer.parseInt(datos[4]);
		this.valor = Integer.parseInt(datos[5]);
		if(this.vMax> 300) this.vMax = 300;
		if(this.valor> 300) this.valor = 300;
		
	}
	
	@Override
	public String toStringFile() {
		return "#"+super.toStringFile()+"$"+this.vMin+"$"+this.vMax+"$"+this.valor;
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
}