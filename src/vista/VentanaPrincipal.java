package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import application.SpeechRecognizer;
import application.VozAsistente;
import controlador.ControladorBotones;
import controlador.ControladorListaZonas;
import modelo.Casa;
import modelo.Zona;

public class VentanaPrincipal extends JFrame implements Observer {
	final static String PATHIMG = "Imagenes/";
	int flag = 0;
	JPanel panelBotones, pListaZonas;

	SpeechRecognizer reconocedor;
	VozAsistente asistente;

	MiPanelImgAndEDs panelCentral;
	JSplitPane panelPrincipal;
	JButton bMute, bConfig, bSalir;
	Boolean mute = false;
	JLabel zoneName;
	RelojFrame reloj;

	Casa casa;
	ControladorListaZonas controladorZonas;
	ControladorBotones controladorBotones;

	Toolkit toolkit = Toolkit.getDefaultToolkit();

	List<MiBotonZona> listaBotonesZonas;

	AbstractAction accMute, accConfig, accSalir;

	public VentanaPrincipal() {
		super("Menu principal");
		crearAcciones();
		casa = new Casa();
		controladorZonas = new ControladorListaZonas(this);
		controladorBotones = new ControladorBotones(this, casa);
		listaBotonesZonas = new ArrayList<>();

		reconocedor = new SpeechRecognizer(this, casa);
		asistente = new VozAsistente(this);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setContentPane(crearPanelVentana());
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void miRepintar() {
		for (Zona z : casa.getCopiaLista()) {
			if (z.getNombre().contains("Principal")) {
				z.setSeleccionado(true);
			} else {
				z.setSeleccionado(false);
			}
		}
		this.listaBotonesZonas.clear();
		this.setContentPane(crearPanelVentana());
		this.validate();
		this.repaint();
	}

	private void crearAcciones() {
		accMute = new MiAccion("", new ImageIcon("Imagenes/Mic.png"), "Silenciar el programa", KeyEvent.VK_M);
		accConfig = new MiAccion("", new ImageIcon("Imagenes/ajuste.png"), "Acceder al menu de configuración",
				KeyEvent.VK_C);
		accSalir = new MiAccion("", new ImageIcon("Imagenes/Salir.png"), "Salir del programa", KeyEvent.VK_X);

	}

	private Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		panel.add(crearPanelPrincipal(), BorderLayout.CENTER);
		return panel;
	}

	private Component crearPanelPrincipal() {
		int posDivider = (int) toolkit.getScreenSize().getWidth() * 3 / 4;
		panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, crearPanelHoraImagen(), crearPanelListaZonas());
		panelPrincipal.setDividerLocation(posDivider);
		System.out.println("----------------------" + toolkit.getScreenSize().getWidth() * 3 / 4);
		panelPrincipal.setDividerSize(5);
		panelPrincipal.setEnabled(false); // Para que el divider no se pueda
											// seleccionar

		return panelPrincipal;
	}

	private Component crearPanelListaZonas() {
		pListaZonas = new JPanel(new GridLayout(casa.getSize() - 1, 1));
		pListaZonas.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		// inicializarZonas(); //TODO cargar la casa con sus casa
		for (Zona z : casa.getCopiaLista()) {
			if (!z.isSelected()) {
				pListaZonas.add(crearBotonZona(z, z.getNombre()));
			}
		}
		return pListaZonas;
	}

	private Component crearBotonZona(Zona z, String nombre) {
		MiBotonZona bZona = new MiBotonZona(z, casa.getSize() - 1);
		bZona.addActionListener(controladorZonas);
		bZona.setActionCommand(nombre);
		listaBotonesZonas.add(bZona);
		return bZona;
	}

	private Component crearPanelHoraImagen() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(crearHora(), BorderLayout.NORTH);
		panel.add(crearPanelImagen(), BorderLayout.CENTER);
		return panel;
	}

	private Component crearPanelImagen() {
		Zona zona = null;
		for (Zona z : casa.getCopiaLista()) {
			if (z.isSelected()) {
				zona = z;
			}
		}
		if (zona != null) {
			Image fondo = toolkit.createImage(PATHIMG + zona.getImagen());
			panelCentral = new MiPanelImgAndEDs(fondo, this, casa);
			panelCentral.setZona(zona);
		} else {
			panelCentral = new MiPanelImgAndEDs(null, this, casa);
		}
		return panelCentral;
	}

	private Component crearHora() {
		JPanel panelHora = new JPanel(new GridLayout(1, 2));
		reloj = new RelojFrame();
		JLabel lHora = reloj.getLabel();
		lHora.setBackground(Color.BLACK);
		lHora.setForeground(Color.GREEN);
		lHora.setOpaque(true);
		lHora.setHorizontalAlignment(SwingConstants.CENTER);
		panelHora.add(crearPanelNombreZona());
		panelHora.add(lHora);
		return panelHora;
	}

	private Component crearPanelNombreZona() {
		JPanel p = new JPanel();
		zoneName = new JLabel(getZonaActual().getNombre());
		zoneName.setFont(new Font("Arial", Font.BOLD, 85));
		zoneName.setForeground(Color.GREEN);
		zoneName.setHorizontalAlignment(SwingConstants.CENTER);
		p.setBackground(Color.BLACK);
		p.setOpaque(true);
		p.add(zoneName);
		return p;
	}

	private Component crearPanelBotones() {
		panelBotones = new JPanel(new GridLayout(1, 3));

		bSalir = new JButton(accSalir);
		bSalir.addActionListener(controladorBotones);
		bSalir.setActionCommand("Salir");
		bSalir.setBorderPainted(false);
		bSalir.setBorder(null);
		bSalir.setMargin(new Insets(0, 0, 0, 0));
		bSalir.setContentAreaFilled(false);
		bSalir.setFocusPainted(false);

		bMute = new JButton(accMute);
		bMute.addActionListener(controladorBotones);
		bMute.setActionCommand("Mute");
		bMute.setBorderPainted(false);
		bMute.setBorder(null);
		bMute.setMargin(new Insets(0, 0, 0, 0));
		bMute.setContentAreaFilled(false);
		bMute.setFocusPainted(false);

		bConfig = new JButton(accConfig);
		bConfig.addActionListener(controladorBotones);
		bConfig.setActionCommand("Config");
		bConfig.setBorderPainted(false);
		bConfig.setBorder(null);
		bConfig.setMargin(new Insets(0, 0, 0, 0));
		bConfig.setContentAreaFilled(false);
		bConfig.setFocusPainted(false);

		panelBotones.setBackground(new Color(204, 204, 204));

		panelBotones.add(new Logo());
		panelBotones.add(bSalir);
		panelBotones.add(bMute);
		panelBotones.add(bConfig);

		return panelBotones;
	}

	@Override
	public void update(Observable arg0, Object arg1) {

	}

	public List<MiBotonZona> getListaBotonesZonas() {
		return listaBotonesZonas;
	}

	public MiBotonZona getBotonLista(String name) {
		for (MiBotonZona z : listaBotonesZonas) {
			if (z.getName().equalsIgnoreCase(name)) {
				return z;
			}
		}
		return null;
	}

	public List<MiBotonED> getListaBotonesEDs() {
		return panelCentral.getListaBotonesPanel();
	}

	public Zona getZonaActual() {
		for (Zona z : casa.getCopiaLista()) {
			if (z.isSelected()) {
				return z;
			}
		}
		return null;
	}

	public JLabel getZoneName() {
		return zoneName;
	}

	public int getNumZonas() {
		return casa.getSize();
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public JPanel getpListaZonas() {
		return pListaZonas;
	}

	public MiPanelImgAndEDs getPanelCentral() {
		return panelCentral;
	}

	public JButton getbMute() {
		return bMute;
	}

	public Boolean getMute() {
		return mute;
	}

	public void setMute(Boolean mute) {
		this.mute = mute;
	}

	/*
	 * public Casa getCasa() {
	 * 
	 * return casa; }
	 */

	private class MiAccion extends AbstractAction {
		String texto;

		public MiAccion(String texto, Icon imagen, String descrip, Integer nemonic) {
			super(texto, imagen);
			this.putValue(Action.SHORT_DESCRIPTION, descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VentanaPrincipal p = new VentanaPrincipal();
	}
}
