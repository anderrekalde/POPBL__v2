package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controlador.ControladorAñadirEditarED;
import modelo.ED;
import modelo.EDProgAndReg;
import modelo.EDProgramable;
import modelo.EDRegulable;
import modelo.Zona;

public class DialogoAñadirEditarED extends JDialog {

	String []opcionesNormal = { "Luz", "Agua", "Ventana" };
	String []opcionesRegulable = { "Luz", "Temperatura" };
	String []opcionesProgramable = { "Lavadora" };
	String []opcionesProgReg = { "Horno", "Televisión", "Radio" };

	String ficheroListaProgramas;
	String ficheroImagen;

	boolean estaAñadiendo;

	JTextField lVmaxtf;
	JTextField lVmintf;
	JTextField tImg;
	JTextField tProgramas;
	JComboBox<String> nombreED;
	JLabel lTipos;
	JLabel lVmax;
	JLabel lVmin;
	JLabel lImagen;
	JLabel lLista;
	JButton bOk;
	JButton bCancel;
	JButton bImg;
	JButton bLista;
	JPanel panelRadioButtons;

	ControladorAñadirEditarED controladorDialogoED;

	DialogoEditListEDZona vista;
	ED selectedED;

	private JRadioButton botonNormal;
	private JRadioButton botonRegulable;
	private JRadioButton botonProgramable;
	private JRadioButton botonProgReg;
	private ButtonGroup group;

	JPanel panelVentana;

	public DialogoAñadirEditarED(DialogoEditListEDZona ventana, String titulo, boolean modo, boolean estaAñadiendo) {
		super(ventana, titulo, modo);
		this.estaAñadiendo = estaAñadiendo;
		//controladorDialogoED = new ControladorAñadirEditarED(this);
		vista = ventana;
		this.selectedED = vista.getSelectedED();

		this.setSize(600, 800);
		this.setLocation(1000, 200);
		this.setContentPane(crearPanelVentana());
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private Container crearPanelVentana() {

		panelVentana = new JPanel(new GridLayout(6, 2));

		panelVentana.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
						BorderFactory.createEmptyBorder(10, 10, 10, 10))));
		panelVentana.add(panelBotonTipo());
		panelVentana.add(panelNombre());
		panelVentana.add(panelImagen());

		panelVentana.add(panelValor());
		panelVentana.add(panelProgramas());
		panelVentana.add(panelAceptar());

		return panelVentana;
	}

	private Component panelAceptar() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 60, 0));

		panel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

		bOk = (JButton) crearBotonAction("OK","ok",controladorDialogoED);
		bCancel = (JButton) crearBotonAction("Cancelar","cancel",controladorDialogoED);

		panel.add(bOk);
		panel.add(bCancel);
		return panel;
	}

	private Component crearBotonAction(String s, String action, ActionListener listener){
		JButton btn = new JButton(s);
		btn.setActionCommand(action);
		btn.addActionListener(listener);
		return btn;
	}
	
	private Component panelProgramas() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		lLista = new JLabel("Lista programas");
		JPanel p;
		p = crearPanelFileChooser();
		panel.add(lLista);
		panel.add(p);
		p.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.setAlignmentY(Component.CENTER_ALIGNMENT);

		return panel;
	}

	private Component panelImagen() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		lImagen = new JLabel("Imagen");
		JPanel p;
		p = crearPanelFileChooserImg();
		panel.add(lImagen);
		panel.add(p);
		p.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.setAlignmentY(Component.CENTER_ALIGNMENT);

		return panel;
	}

	private JPanel crearPanelFileChooser() {
		JPanel p = new JPanel(new BorderLayout());
		String programas = "";

		bLista = (JButton) crearBotonAction(" ... ","lista",controladorDialogoED);

		tProgramas = new JTextField();

		if (!estaAñadiendo) {
			if (selectedED instanceof EDProgramable || selectedED instanceof EDProgAndReg) {
				bLista.setEnabled(true);
				tProgramas.setEnabled(true);
				if (selectedED instanceof EDProgramable) {
					for (String s : ((EDProgramable) selectedED).getListaProgramas()) {
						programas += s + ",";
					}
				}else{
					for (String s : ((EDProgAndReg) selectedED).getListaProgramas()) {
						programas += s + ",";
					}
				}
				tProgramas.setText(programas);
			} else {
				bLista.setEnabled(false);
				tProgramas.setEnabled(false);
			}
		} else {
			bLista.setEnabled(false);
			tProgramas.setEnabled(false);
		}

		p.setBorder(BorderFactory.createEmptyBorder(35, 0, 35, 0));
		p.add(tProgramas, BorderLayout.CENTER);
		p.add(bLista, BorderLayout.EAST);

		return p;
	}

	private JPanel crearPanelFileChooserImg() {
		JPanel p = new JPanel(new BorderLayout());

		bImg = (JButton) crearBotonAction(" ... ","img",controladorDialogoED);
		if (selectedED != null) {
			ficheroImagen = selectedED.getTipoED() + ".png";
		}
		tImg = new JTextField(ficheroImagen);

		p.setBorder(BorderFactory.createEmptyBorder(35, 0, 35, 0));
		p.add(bImg, BorderLayout.EAST);
		p.add(tImg, BorderLayout.CENTER);

		return p;
	}

	private Component panelValor() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 40, 40));

		lVmin = new JLabel("Valor min");
		lVmintf = new JTextField();
		lVmintf.setEnabled(false);

		lVmax = new JLabel("Valor max");
		lVmaxtf = new JTextField();
		lVmaxtf.setEnabled(false);

		if (!estaAñadiendo) {
			if (selectedED instanceof EDRegulable || selectedED instanceof EDProgAndReg) {
				lVmintf.setEnabled(true);
				lVmaxtf.setEnabled(true);
			} else {
				lVmintf.setEnabled(false);
				lVmaxtf.setEnabled(false);
			}
			if (selectedED instanceof EDRegulable) {
				lVmaxtf.setText(String.valueOf(((EDRegulable) selectedED).getvMax()));
				lVmintf.setText(String.valueOf(((EDRegulable) selectedED).getvMin()));
			} 
			if (selectedED instanceof EDProgAndReg) {
				lVmaxtf.setText(String.valueOf(((EDProgAndReg) selectedED).getvMax()));
				lVmintf.setText(String.valueOf(((EDProgAndReg) selectedED).getvMin()));
			}
		} else {
			lVmintf.setEnabled(false);
			lVmaxtf.setEnabled(false);
		}

		panel.add(lVmin);
		panel.add(lVmintf);
		panel.add(lVmax);
		panel.add(lVmaxtf);

		return panel;
	}

	private JRadioButton crearRadioButton(String nombre) {
		JRadioButton rb = new JRadioButton(nombre);
		rb.addActionListener(controladorDialogoED);
		rb.setActionCommand(nombre);
		return rb;
	}

	private Component panelBotonTipo() {
		panelRadioButtons = new JPanel(new GridLayout(2, 2));

		group = new ButtonGroup();
		botonNormal = crearRadioButton("Normal");
		botonProgramable = crearRadioButton("Programable");
		botonRegulable = crearRadioButton("Regulable");
		botonProgReg = crearRadioButton("ProgReg");
		group.add(botonNormal);
		group.add(botonProgramable);
		group.add(botonRegulable);
		group.add(botonProgReg);

		if (!estaAñadiendo) {
			if (selectedED instanceof EDRegulable) {
				botonRegulable.setSelected(true);
			} else if (selectedED instanceof EDProgramable) {
				botonProgramable.setSelected(true);
			} else if (selectedED instanceof EDProgAndReg) {
				botonProgReg.setSelected(true);
			} else if (selectedED instanceof ED) {
				botonNormal.setSelected(true);
			} else {
				botonNormal.setSelected(true);
			}
		} else {
			botonNormal.setSelected(true);
		}

		panelRadioButtons.add(botonNormal);
		panelRadioButtons.add(botonProgramable);
		panelRadioButtons.add(botonRegulable);
		panelRadioButtons.add(botonProgReg);

		return panelRadioButtons;
	}

	public boolean isEstaAñadiendo() {
		return estaAñadiendo;
	}

	private Component panelNombre() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 30, 0));

		panel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 20));

		lTipos = new JLabel("Tipo de electrodoméstico");

		if (botonNormal.isSelected()) {
			nombreED = new JComboBox<>(opcionesNormal);
		}
		if (botonProgramable.isSelected()) {
			nombreED = new JComboBox<>(opcionesProgramable);
		}
		if (botonRegulable.isSelected()) {
			nombreED = new JComboBox<>(opcionesRegulable);
		}
		if (botonProgReg.isSelected()) {
			nombreED = new JComboBox<>(opcionesProgReg);
		}

		panel.add(lTipos);
		panel.add(nombreED);

		return panel;
	}

	public JComboBox getNombreED() {
		return nombreED;
	}

	public void setEverything(String tipo) {
		switch (tipo) {
		case "Normal":
			lVmaxtf.setEnabled(false);
			lVmintf.setEnabled(false);
			bLista.setEnabled(false);
			tProgramas.setEnabled(false);
			break;
		case "Regulable":
			lVmaxtf.setEnabled(true);
			lVmintf.setEnabled(true);
			bLista.setEnabled(false);
			tProgramas.setEnabled(false);
			break;
		case "Programable":
			lVmaxtf.setEnabled(false);
			lVmintf.setEnabled(false);
			bLista.setEnabled(true);
			tProgramas.setEnabled(true);
			break;
		case "ProgReg":
			lVmaxtf.setEnabled(true);
			lVmintf.setEnabled(true);
			bLista.setEnabled(true);
			tProgramas.setEnabled(true);
			break;
		default:			
			lVmaxtf.setEnabled(false);
			lVmintf.setEnabled(false);
			bLista.setEnabled(false);
			tProgramas.setEnabled(false);
			break;
		}
	}

	public ButtonGroup getGroup() {
		return group;
	}

	public JRadioButton getSelectedRadioButton() {
		JRadioButton rb = new JRadioButton();
		if (botonNormal.isSelected()) {
			rb = botonNormal;
		}
		if (botonProgramable.isSelected()) {
			rb = botonProgramable;
		}
		if (botonRegulable.isSelected()) {
			rb = botonRegulable;
		}
		if (botonProgReg.isSelected()) {
			rb = botonProgReg;
		}
		return rb;
	}

	public String[] getOpcionesNormal() {
		return opcionesNormal;
	}

	public String[] getOpcionesRegulable() {
		return opcionesRegulable;
	}

	public String[] getOpcionesProgramable() {
		return opcionesProgramable;
	}

	public String[] getOpcionesProgReg() {
		return opcionesProgReg;
	}

	public JTextField getVmaxtf() {
		return lVmaxtf;
	}

	public void setFicheroListaProgramas(String ficheroListaProgramas) {
		this.ficheroListaProgramas = ficheroListaProgramas;
	}

	public JTextField getVmintf() {
		return lVmintf;
	}

	public JButton getbImg() {
		return bImg;
	}

	public JButton getbLista() {
		return bLista;
	}

	public void setFicheroImagen(String ficheroImagen) {
		this.ficheroImagen = ficheroImagen;
	}

	public JTextField gettImg() {
		return tImg;
	}

	public JTextField gettProgramas() {
		return tProgramas;
	}

	public String getNumToString(int i) {
		String s = "";
		switch (i) {
		case 1:
			s += "uno";
			break;
		case 2:
			s += "dos";
			break;
		case 3:
			s += "tres";
			break;
		case 4:
			s += "cuatro";
			break;
		case 5:
			s += "cinco";
			break;
		case 6:
			s += "seis";
			break;
		case 7:
			s += "siete";
			break;
		case 8:
			s += "ocho";
			break;
		case 9:
			s += "nueve";
			break;
		case 10:
			s += "diez";
			break;
		default:
			break;
		}
		return s;
	}

	public void crearEditarED(JRadioButton selectedRadioButton) {
		Zona z = vista.getSelectedZona();
		String numString;
		String tipo = this.nombreED.getSelectedItem().toString();
		ED ed;
		int nextValue;
		List<String> lListaProgramas = new ArrayList<>();
		String[] listaProgramas;
		nextValue = z.getNextValue(tipo);
		numString = getNumToString(nextValue);
		if (!estaAñadiendo) {
			z.removeED(selectedED);
		}
		switch (selectedRadioButton.getText()) {
		case "Regulable":
			ed = new EDRegulable(tipo + " " + numString, ficheroImagen, false, Integer.valueOf(lVmintf.getText()),
					Integer.valueOf(lVmaxtf.getText()), Integer.valueOf(lVmintf.getText()));
			break;
		case "Programable":
			listaProgramas = tProgramas.getText().split(",");
			for (String s : listaProgramas) {
				lListaProgramas.add(s);
			}
			ed = new EDProgramable(tipo + " " + numString, ficheroImagen, false, lListaProgramas.get(0),
					lListaProgramas);
			break;
		case "ProgReg":
			listaProgramas = tProgramas.getText().split(",");
			for (String s : listaProgramas) {
				lListaProgramas.add(s);
			}
			ed = new EDProgAndReg(tipo + " " + numString, ficheroImagen, false, Integer.valueOf(lVmintf.getText()),
					Integer.valueOf(lVmaxtf.getText()), Integer.valueOf(lVmintf.getText()), lListaProgramas.get(0),
					lListaProgramas);
			break;
		default:
			ed = new ED(tipo + " " + numString, ficheroImagen, false);
			break;
		}
		z.addED(ed);
	}

	public String getProgramasFromFile() {
		String s = "";
		String linea;

		try (BufferedReader in = new BufferedReader(new FileReader(ficheroListaProgramas))) {

			while ((linea = in.readLine()) != null) {
				s += linea;
				logger.log("Exception", s);
			}

		} catch (FileNotFoundException e || IOException e) {
			logger.log("Exception", e);
		}

		return s;
	}

}