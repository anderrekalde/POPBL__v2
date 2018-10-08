package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import controlador.ControladorBotonesConfig;
import controlador.ControladorToolBarTiposZonaConfig;
import controlador.ControladorListaZonasConfig;
import controlador.ControladorAccionesMenuConfig;
import modelo.Casa;
import modelo.Zona;

public class MenuConfig extends JFrame {
	final static String PATHIMG = "Imagenes/";
	String actualType;
	Casa casaMenu;
	
	VentanaPrincipal vistaPrincipal;
	
	ControladorToolBarTiposZonaConfig controladorToolBar;
	ControladorAccionesMenuConfig controladorConfig;
	ControladorListaZonasConfig controladorListaZonas;
	ControladorBotonesConfig controladorBotones;

	AbstractAction accCargar, accSalir, accAnadir, accBorrar, accEditar, accBaño, accCocina, accSalon, accHabitacion,
			accJardin, accOtros;

	JButton bSalir, bCargar;
	JMenuBar barra;
	JMenu menuEdit;
	JMenuItem opcionMenu;
	JList<Zona> listaSeleccion;
	JScrollPane panelLista;
	MiPanelImg panelImg;
	
	RendererZonaConfig renderer;
	
	List<JButton> listaBotonesAcciones;
	List<Zona> listaPorTipos;

	Toolkit toolkit = Toolkit.getDefaultToolkit();

	public MenuConfig(VentanaPrincipal vistaPrincipal, Casa casa) {
		super("Menu configuracion");
		crearAcciones();
		casaMenu = casa;
		controladorToolBar = new ControladorToolBarTiposZonaConfig(this, casaMenu);
		controladorConfig = new ControladorAccionesMenuConfig(this, casaMenu);
		controladorListaZonas = new ControladorListaZonasConfig(this);
		controladorBotones = new ControladorBotonesConfig(this, casaMenu, vistaPrincipal);
		renderer = new RendererZonaConfig();
		listaBotonesAcciones = new ArrayList<>();
		this.setJMenuBar(crearBarraMenu());

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.getContentPane().add(crearToolBar(), BorderLayout.NORTH);
		this.getContentPane().add(crearPanelVentana(), BorderLayout.CENTER);
		this.getContentPane().add(crearPanelBotones(), BorderLayout.SOUTH);
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private Component crearPanelBotones() {
		JPanel panelBotones = new JPanel(new GridLayout(1,3));
		
		bSalir = new JButton(accSalir);
		bSalir.setFont(new Font("Arial", Font.BOLD, 32));
		bSalir.addActionListener(controladorBotones);
		bSalir.setActionCommand("Salir");
		bSalir.setBorderPainted(false);
		bSalir.setBorder(null);
		bSalir.setMargin(new Insets(0, 0, 0, 0));
		bSalir.setContentAreaFilled(false);
		bSalir.setFocusPainted(false);
		
		bCargar = new JButton(accCargar);
		bCargar.setFont(new Font("Arial", Font.BOLD, 32));
		bCargar.addActionListener(controladorBotones);
		bCargar.setActionCommand("Cargar");
		bCargar.setBorderPainted(false);
		bCargar.setBorder(null);
		bCargar.setMargin(new Insets(0, 0, 0, 0));
		bCargar.setContentAreaFilled(false);
		bCargar.setFocusPainted(false);

		panelBotones.setBackground(new Color(204, 204, 204));
		
		panelBotones.add(new Logo());
		panelBotones.add(bSalir);
		panelBotones.add(bCargar);
		
		return panelBotones;
	}

	private Container crearPanelVentana() {
		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, crearPanelSalas(), crearPanelImagen());
		panel.setDividerLocation((int)toolkit.getScreenSize().getWidth()/4);
		panel.setDividerSize(5);
		panel.setEnabled(false);
		return panel;
	}

	private Component crearPanelImagen() {
		panelImg = new MiPanelImg(toolkit.createImage(PATHIMG + "Baño.png"));

		return panelImg;
	}

	private Component crearPanelSalas() {
		panelLista = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		actualType = "baño";
		listaSeleccion = new JList<>();
		//listaSeleccion.setModel(casa);
		listaSeleccion.setListData(casaMenu.getStringsByType(actualType));
		listaSeleccion.setCellRenderer(renderer);
		listaSeleccion.setSelectedIndex(0);
		listaSeleccion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaSeleccion.setBackground(new Color(242,242,242));
		listaSeleccion.setOpaque(true);
		listaSeleccion.addListSelectionListener(controladorListaZonas);
		listaSeleccion.addMouseListener(controladorListaZonas);
		panelLista.setViewportView(listaSeleccion);
		return panelLista;
	}

	private JMenuBar crearBarraMenu() {
		barra = new JMenuBar();
		barra.add(crearMenuAcciones());

		return barra;
	}

	private JMenu crearMenuAcciones() {
		menuEdit = new JMenu("Menu");
		menuEdit.setFont(new Font("arial", Font.PLAIN, 30));
		menuEdit.setMnemonic(KeyEvent.VK_A);

		opcionMenu = menuEdit.add(accAnadir);
		opcionMenu.setFont(new Font("arial", Font.PLAIN, 30));
		opcionMenu.addActionListener(controladorConfig);
		opcionMenu.setActionCommand("Añadir");
		opcionMenu = menuEdit.add(accBorrar);
		opcionMenu.setFont(new Font("arial", Font.PLAIN, 30));
		opcionMenu.addActionListener(controladorConfig);
		opcionMenu.setActionCommand("Borrar");
		opcionMenu = menuEdit.add(accEditar);
		opcionMenu.setFont(new Font("arial", Font.PLAIN, 30));
		opcionMenu.addActionListener(controladorConfig);
		opcionMenu.setActionCommand("Editar");

		return menuEdit;
	}

	private JToolBar crearToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setLayout(new GridLayout(1, 6));
		toolBar.setBorder(BorderFactory.createRaisedBevelBorder());

		toolBar.add(crearBotonToolBar(accBaño));
		accBaño.setEnabled(false);
		toolBar.add(crearBotonToolBar(accSalon));
		toolBar.add(crearBotonToolBar(accCocina));
		toolBar.add(crearBotonToolBar(accHabitacion));
		toolBar.add(crearBotonToolBar(accJardin));

		return toolBar;
	}

	public JButton crearBotonToolBar(AbstractAction name){
		JButton b = new JButton(name);
		b.setFont(new Font("arial", Font.PLAIN,30));
		b.setActionCommand(name.toString());
		b.addActionListener(controladorToolBar);
		listaBotonesAcciones.add(b);
		
		return b;
	}
	
	public JList<Zona> getListaSeleccion() {
		return listaSeleccion;
	}

	public MiPanelImg getPanelImg() {
		return panelImg;
	}

	public List<JButton> getListaBotonesAcciones() {
		return listaBotonesAcciones;
	}

	public JScrollPane getPanelLista() {
		return panelLista;
	}

	public void setActualType(String actualType) {
		this.actualType = actualType;
	}

	public String getActualType() {
		return actualType;
	}
	
	public Casa getCasa() {
		return casaMenu;
	}
	
	public Zona getSelectedZone(){
		return listaSeleccion.getSelectedValue();
	}

	public void updateList() {
		panelLista.setViewportView(listaSeleccion = new JList<>(casaMenu.getStringsByType(actualType)));
		listaSeleccion.setCellRenderer(renderer);
		listaSeleccion.setSelectedIndex(0);
		listaSeleccion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaSeleccion.setBackground(new Color(242,242,242));
		listaSeleccion.addListSelectionListener(controladorListaZonas);
		listaSeleccion.addMouseListener(controladorListaZonas);
		listaSeleccion.setOpaque(true);		
	}

	private void crearAcciones() {
		accCargar = new MiAccion("Cargar casa", new ImageIcon("Imagenes/Cargar.png"), "Cargar el fichero de una casa",
				KeyEvent.VK_C);
		accSalir = new MiAccion("Volver", new ImageIcon("Imagenes/Back.png"), "Salir del menu", KeyEvent.VK_V);
		accAnadir = new MiAccion("Añadir", new ImageIcon("Imagenes/Añadir.png"),
				"Añade una zona nueva", KeyEvent.VK_A);
		accBorrar = new MiAccion("Borrar", new ImageIcon("Imagenes/Eliminar.png"), "Borra la zona seleccionada",
				KeyEvent.VK_B);
		accEditar = new MiAccion("Editar", new ImageIcon("Imagenes/Editar.png"), "Edita la zona seleccionada",
				KeyEvent.VK_E);
		accBaño = new MiAccion("Baños", new ImageIcon("Imagenes/Agua.png"), "Muestra todos los baños", KeyEvent.VK_P);
		accSalon = new MiAccion("Salones", new ImageIcon("Imagenes/salonICON.png"), "Muestra todos los salones",
				KeyEvent.VK_S);
		accCocina = new MiAccion("Cocinas", new ImageIcon("Imagenes/cocinaICON.png"), "Muestra todas las cocinas",
				KeyEvent.VK_K);
		accHabitacion = new MiAccion("Habitaciones", new ImageIcon("Imagenes/habitacionICON.png"),
				"Muestra todas las habitaciones", KeyEvent.VK_H);
		accJardin = new MiAccion("Jardines", new ImageIcon("Imagenes/jardinICON.png"), "Muestra todos los jardines",
				KeyEvent.VK_J);

	}

	private class MiAccion extends AbstractAction {
		String texto;

		public MiAccion(String texto, Icon imagen, String descrip, Integer nemonic) {
			super(texto, imagen);
			this.texto = texto;
			this.putValue(Action.SHORT_DESCRIPTION, descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return texto;
		}
	}

	/*public static void main(String[] args) {
		MenuConfig p = new MenuConfig();

	}*/
}
