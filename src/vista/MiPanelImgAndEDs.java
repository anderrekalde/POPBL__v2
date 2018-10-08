package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import controlador.ControladorListaED;
import modelo.Casa;
import modelo.ED;
import modelo.Zona;

public class MiPanelImgAndEDs extends JPanel {
	Image imagen;
	Zona zona;
	VentanaPrincipal vista;
	List<MiBotonED> listaBotonesPanel;

	ControladorListaED controladorED;

	public MiPanelImgAndEDs(Image imagen, VentanaPrincipal vista, Casa casa) {
		this.vista = vista;
		controladorED = new ControladorListaED(this.vista, casa);
		listaBotonesPanel = new ArrayList<>();
		if (imagen != null) {
			this.imagen = imagen;
		}
		this.setLayout(new GridLayout());

	}

	public Image getImagen() {
		return this.imagen;
	}

	public void setImagen(Image nuevaImagen) {
		this.imagen = nuevaImagen;

		repaint();
	}

	public void setZona(Zona zona) {
		this.removeAll();
		this.zona = zona;
		this.imagen = zona.getImageIcon().getImage();
		listaBotonesPanel.clear();
		if (zona.getNombre().equals("Principal")) {

		} else {
			this.add(crearPanelEDs());

		}

		repaint();
	}

	private Component crearPanelEDs() {
		JPanel panel = new JPanel(new FlowLayout(0,30,10));
		MiBotonED boton;
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panel.setOpaque(false);
		for (ED e : zona.getCopiaListaED()) {
			boton = new MiBotonED(e, zona);
			boton.setActionCommand(e.getNombre());
			boton.addActionListener(controladorED);
			listaBotonesPanel.add(boton);
			panel.add(boton);
		}
		return panel;
	}

	public List<MiBotonED> getListaBotonesPanel() {
		return listaBotonesPanel;
	}

	public ControladorListaED getControladorED() {
		return controladorED;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		if (imagen != null) {
			gr.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
			setOpaque(false);
		} else {
			setOpaque(true);
		}
	}
}
