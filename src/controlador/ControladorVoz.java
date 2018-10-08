package controlador;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;

import application.SpeechRecognizer;
import application.VozAsistente;
import modelo.Casa;
import modelo.ED;
import modelo.EDProgAndReg;
import modelo.EDRegulable;
import modelo.Zona;
import vista.DialogoED;
import vista.MiBotonED;
import vista.MiBotonEstado;
import vista.MiBotonZona;
import vista.VentanaPrincipal;

public class ControladorVoz extends Thread {

	ED electrodomestico;
	Thread hilo;
	Boolean running;
	VentanaPrincipal vista;
	SpeechRecognizer reconocedor;
	VozAsistente voz;
	DialogoED dialogo;
	ControladorListaED controladorED;
	int flag;
	Casa casa;

	public ControladorVoz(VentanaPrincipal vista, Casa casa) {

		this.vista = vista;
		voz = new VozAsistente(this.vista);
		this.casa = casa;

	}

	public void filtradorZonas(String palabra) {

		if (vista.getFlag() >= 0) {
			if (palabra.equals("casa"))
				Toolkit.getDefaultToolkit().beep();

			Zona zonaActual = vista.getZonaActual();
			List<MiBotonZona> botonesZona = vista.getListaBotonesZonas();
			List<MiBotonED> botonesED = vista.getListaBotonesEDs();

			this.dialogo = vista.getPanelCentral().getControladorED().getDialogoED();

			String[] palabrasZona = { "cocina", "baño", "habitación", "salón", "principal", "jardin" };
			String[] palabrasED = { "luz", "persiana", "temperatura", "lavadora", "horno", "agua", "televisión" };
			String[] palabrasEstados = { "valor", "encender", "apagar", "programa", "aceptar", "cancelar" };

			if (palabra.equals("en que habitación estamos")) {

				voz.speak(vista.getZonaActual().getNombre());

			}

			if (vista.getFlag() == 1) {
				for (int i = 0; i < palabrasEstados.length; i++) {

					if (palabra.toLowerCase().contains(palabrasEstados[i])) {

						for (Zona z : casa.getCopiaLista()) {

							for (ED electr : z.getCopiaListaED()) {

								if (electr.isSelected()) {

									electrodomestico = electr;

									for (MiBotonEstado b : dialogo.getListaBotonesED()) {

										if (palabra.contains("programa")) {

											String actionCommand = b.getActionCommand();
											String nuevaPalabra = digitToWord(actionCommand.substring(8));

											if (palabra.equals(("programa ").concat(nuevaPalabra)) && b.isEnabled()) {

												b.doClick();
												return;
											}

										} else if (b.getActionCommand().contains(palabra)) {

											if (b.getEstado() && palabra.equals("apagar")) {

												System.out.println("Boton Apagar");
												b.doClick();
												return;
											} else if (!b.getEstado() && palabra.equals("encender")) {
												System.out.println("Boton Encender");
												b.doClick();
												return;
											}
										}
									}

									if (palabra.contains("valor ") && dialogo.getSlider() != null) {

										this.dialogo.getSlider()
												.setValue(Integer.parseInt(textoEnUnidad(palabra.substring(6))));

									}

									if (palabra.contains("cancelar") || palabra.contains("aceptar")) {
										for (JButton boton : dialogo.getBotonesOKyCancel()) {
											if (palabra.equalsIgnoreCase(boton.getActionCommand())) {
												System.out.println("Boton " + boton.getActionCommand());
												boton.doClick();
												return;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			// } else {
			if (vista.getFlag() == 0) {

				for (int i = 0; i < palabrasZona.length; i++) {

					if (palabra.toLowerCase().contains(palabrasZona[i])) {

						for (Zona z : casa.getCopiaLista()) {

							if (palabra.equalsIgnoreCase(z.getNombre())) {

								for (JButton boton : botonesZona) {

									if (boton.getActionCommand().equalsIgnoreCase(palabra)) {
										boton.doClick();
										return;
									}
								}
							}
						}
					}
				}

				for (int i = 0; i < palabrasED.length; i++) {

					if (palabra.toLowerCase().contains(palabrasED[i])) {

						for (ED e : zonaActual.getCopiaListaED()) {

							if (palabra.equalsIgnoreCase(e.getNombre())) {

								for (JButton boton : botonesED) {

									if (boton.getActionCommand().equalsIgnoreCase(palabra)) {
										boton.doClick();

										return;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public String digitToWord(String palabra) {

		switch (palabra) {
		case "0":
			return "cero";
		case "1":
			return "uno";
		case "2":
			return "dos";
		case "3":
			return "tres";
		case "4":
			return "cuatro";
		case "5":
			return "cinco";
		case "6":
			return "seis";
		case "7":
			return "siete";
		case "8":
			return "ocho";
		case "9":
			return "nueve";
		case "10":
			return "diez";
		case "11":
			return "once";
		case "12":
			return "doce";
		case "13":
			return "trece";
		case "14":
			return "catorce";
		case "15":
			return "quince";
		case "16":
			return "dieciseis";
		case "17":
			return "diecisiete";
		case "18":
			return "dieciocho";
		case "19":
			return "diecinueve";
		case "20":
			return "veinte";
		case "21":
			return "veintiuno";
		case "22":
			return "veintidos";
		case "23":
			return "veintitres";
		case "24":
			return "veinticuatro";
		case "25":
			return "veinticinco";
		case "26":
			return "veintiseis";
		case "27":
			return "veintisiete";
		case "28":
			return "veintiocho";
		case "29":
			return "veintinueve";
		case "30":
			return "treinta";
		case "31":
			return "treinta y uno";
		case "32":
			return "treinta y dos";
		case "33":
			return "treinta y tres";
		case "34":
			return "treinta y cuatro";
		case "35":
			return "treinta y cinco";
		case "36":
			return "treinta y seis";
		case "37":
			return "treinta y siete";
		case "38":
			return "treinta y ocho";
		case "39":
			return "treinta y nueve";
		case "40":
			return "cuarenta";
		case "100":
			return "cien";
		case "190":
			return "ciento noventa";
		case "200":
			return "doscientos";
		default:
			return "";
		}

	}

	public String textoEnUnidad(String palabra) {

		switch (palabra) {
		case "cero":
			return "0";
		case "uno":
			return "1";
		case "dos":
			return "2";
		case "tres":
			return "3";
		case "cuatro":
			return "4";
		case "cinco":
			return "5";
		case "seis":
			return "6";
		case "siete":
			return "7";
		case "ocho":
			return "8";
		case "nueve":
			return "9";
		case "diez":
			return "10";
		case "once":
			return "11";
		case "doce":
			return "12";
		case "trece":
			return "13";
		case "catorce":
			return "14";
		case "quince":
			return "15";
		case "dieciseis":
			return "16";
		case "diecisiete":
			return "17";
		case "dieciocho":
			return "18";
		case "diecinueve":
			return "19";
		case "veinte":
			return "20";
		case "veintiuno":
			return "21";
		case "veintidos":
			return "22";
		case "veintitres":
			return "23";
		case "veinticuatro":
			return "24";
		case "veinticinco":
			return "25";
		case "veintiseis":
			return "26";
		case "veintisiete":
			return "27";
		case "veintiocho":
			return "28";
		case "veintinueve":
			return "29";
		case "treinta":
			return "30";
		case "reinta y uno":
			return "31";
		case "treinta y dos":
			return "32";
		case "treinta y tres":
			return "33";
		case "treinta y cuatro":
			return "34";
		case "treinta y cinco":
			return "35";
		case "treinta y seis":
			return "36";
		case "treinta y siete":
			return "37";
		case "treinta y ocho":
			return "38";
		case "treinta y nueve":
			return "39";
		case "cuarenta":
			return "40";
		case "cien":
			return "100";
		case "ciento noventa":
			return "190";
		case "doscientos":
			return "200";
		default:
			return "";
		}
	}

}
