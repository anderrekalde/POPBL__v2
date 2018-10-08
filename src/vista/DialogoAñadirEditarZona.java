package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controlador.ControladorAñadirEditarZona;
import modelo.Casa;
import modelo.Zona;

public class DialogoAñadirEditarZona extends JDialog {

	JComboBox<String> tipoZonaCombo;
	JButton bImg, bOk, bCancel;
	JLabel lTipo, lImg;
	JTextField tRuta;

	ActionListener controlador;
	Casa casa;
	MenuConfig vista;
	int i = 0;
	String tiposZona[] = { "Baño", "Salón", "Cocina", "Habitación", "Jardin" };

	public DialogoAñadirEditarZona(JFrame ventana, String titulo, boolean modo, boolean estaAñadiendo, Casa casa) {
		super(ventana, titulo, modo);
		this.vista = (MenuConfig) ventana;
		this.casa = casa;
		controlador = new ControladorAñadirEditarZona(this, vista.getCasa(), estaAñadiendo);
		
		this.setSize(600, 800);
		this.setLocation(200, 200);
		this.setContentPane(crearPanelVentana());
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		panel.add(crearPanelPrincipal(), BorderLayout.CENTER);
		return panel;
	}

	private Component crearPanelPrincipal() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

		lTipo = new JLabel("Tipo");
		lTipo.setHorizontalAlignment(SwingConstants.CENTER);
		lTipo.setFont(new Font("Arial", Font.PLAIN, 20));
		lImg = new JLabel("Imagen");
		lImg.setHorizontalAlignment(SwingConstants.CENTER);
		lImg.setFont(new Font("Arial", Font.PLAIN, 20));

		tipoZonaCombo = new JComboBox(tiposZona);
		
		for (i = 0; i < tiposZona.length; i++) {
			if (vista.getActualType().equals(tiposZona[i]))
				tipoZonaCombo.setSelectedIndex(i);
		}

		panel.add(lTipo);
		panel.add(tipoZonaCombo);
		panel.add(lImg);
		panel.add(crearPanelSelImg());

		return panel;
	}

	private Component crearPanelSelImg() {
		JPanel p = new JPanel(new BorderLayout());
		tRuta = new JTextField("");
		bImg = new JButton("...");
		bImg.setActionCommand("imagen");
		bImg.addActionListener(controlador);
		p.add(tRuta,BorderLayout.CENTER);
		p.add(bImg,BorderLayout.EAST);
		return p;
	}
	
	private Component crearPanelBotones() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 60, 0));

		panel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

		bOk = new JButton("OK");
		bOk.setActionCommand("ok");
		bOk.addActionListener(controlador);

		bCancel = new JButton("Cancelar");
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(controlador);

		panel.add(bOk);
		panel.add(bCancel);
		return panel;
	}

	public JComboBox<String> getTipoZonaCombo() {
		return tipoZonaCombo;
	}

	public JList<Zona> getListaSeleccion() {
		return vista.getListaSeleccion();
	}

	public void addZonaToCasa(String nombre, String imagen){
		vista.getCasa().add(new Zona(nombre, imagen));
	}
	
	public void updateList() {
		vista.updateList();
	}

	public Zona getSelectedZone() {
		return vista.getSelectedZone();
	}

	public JTextField gettRuta() {
		return tRuta;
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
		}
		return s;
	}
}
