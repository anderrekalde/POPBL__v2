package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
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

	String opcionesNormal[] = { "Luz", "Agua", "Persiana" };
	String opcionesRegulable[] = { "Luz", "Temperatura" };
	String opcionesProgramable[] = { "Lavadora" };
	String opcionesProgReg[] = { "Horno", "Televisión", "Radio" };

	String ficheroListaProgramas, ficheroImagen;

	boolean estaAñadiendo;

	JTextField lVmaxtf, lVmintf, tImg, tProgramas;
	JComboBox<String> nombreED;
	JLabel lTipos, lVmax, lVmin, lImagen, lLista;
	JButton bOk, bCancel, bImg, bLista;
	JPanel panelRadioButtons;

	ControladorAñadirEditarED controladorDialogoED;

	DialogoEditListEDZona vista;
	ED selectedED;

	private JRadioButton botonNormal, botonRegulable, botonProgramable, botonProgReg;
	private ButtonGroup group;

	JPanel panelVentana;

	public DialogoAñadirEditarED(DialogoEditListEDZona ventana, String titulo, boolean modo, boolean estaAñadiendo) {
		super(ventana, titulo, modo);
		this.estaAñadiendo = estaAñadiendo;
		controladorDialogoED = new ControladorAñadirEditarED(this);
		vista = ventana;
		this.selectedED = vista.getSelectedED();

		this.setSize(600, 800);
		this.setLocation(1000, 200);
		this.setContentPane(crearPanelVentana());
		// this.pack();
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

		bOk = new JButton("OK");
		bOk.setActionCommand("ok");
		bOk.addActionListener(controladorDialogoED);

		bCancel = new JButton("Cancelar");
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(controladorDialogoED);

		panel.add(bOk);
		panel.add(bCancel);
		return panel;
	}

	private Component panelProgramas() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		lLista = new JLabel("Lista programas");
		JPanel p;
		panel.add(lLista);
		panel.add(p = crearPanelFileChooser());
		p.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.setAlignmentY(Component.CENTER_ALIGNMENT);

		return panel;
	}

	private Component panelImagen() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		lImagen = new JLabel("Imagen");
		JPanel p;

		panel.add(lImagen);
		panel.add(p = crearPanelFileChooserImg());
		p.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.setAlignmentY(Component.CENTER_ALIGNMENT);

		return panel;
	}

	private JPanel crearPanelFileChooser() {
		JPanel p = new JPanel(new BorderLayout());
		String programas = "";

		bLista = new JButton(" ... ");
		bLista.setActionCommand("lista");
		bLista.addActionListener(controladorDialogoED);

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

		bImg = new JButton(" ... ");
		bImg.setActionCommand("img");
		bImg.addActionListener(controladorDialogoED);
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
		group.add(botonNormal = crearRadioButton("Normal"));
		group.add(botonProgramable = crearRadioButton("Programable"));
		group.add(botonRegulable = crearRadioButton("Regulable"));
		group.add(botonProgReg = crearRadioButton("ProgReg"));

		if (!estaAñadiendo) {
			if (selectedED instanceof EDRegulable) {
				botonRegulable.setSelected(true);
			} else if (selectedED instanceof EDProgramable) {
				botonProgramable.setSelected(true);
			} else if (selectedED instanceof EDProgramable) {
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
		}
		return s;
	}

	public void crearEditarED(JRadioButton selectedRadioButton) {
		Zona z = vista.getSelectedZona();
		String numString, tipo = this.nombreED.getSelectedItem().toString();
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

	public String getProgramasFromFile(String absolutePath) {
		String s = "";
		String linea;

		try (BufferedReader in = new BufferedReader(new FileReader(ficheroListaProgramas))) {

			while ((linea = in.readLine()) != null) {
				s += linea;
				System.out.println(s);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return s;
	}

}