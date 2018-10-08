package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import controlador.ControladorListaEDConfig;
import modelo.ED;
import modelo.Zona;

public class DialogoEditListEDZona extends JDialog {

	AbstractAction accAñadir, accBorrar, accEdit;

	RendererEDConfig rendererEDConfig;

	JPanel panelPrincipal;
	JList<ED> jLista;
	MenuConfig vista;
	Zona selectedZona;

	ControladorListaEDConfig controladorListaED;

	public DialogoEditListEDZona(JFrame ventana) {
		super(ventana, "Lista Electrodomésticos", true);
		this.vista = (MenuConfig) ventana;
		this.selectedZona = vista.getSelectedZone();
		rendererEDConfig = new RendererEDConfig();
		controladorListaED = new ControladorListaEDConfig(this, selectedZona);

		this.crearAcciones();

		this.setSize(600, 800);
		this.setLocation(200, 200);
		this.setContentPane(crearPanelVentana());
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void crearAcciones() {
		accAñadir = new MiAccion("Añadir", new ImageIcon("Imagenes/edit_add.png"), "Añade un nuevo electrodoméstico",
				KeyEvent.VK_A);
		accBorrar = new MiAccion("Eliminar", new ImageIcon("Imagenes/edit_remove.png"),
				"Borra el electrodoméstico seleccionado", KeyEvent.VK_X);
		accEdit = new MiAccion("Editar", new ImageIcon("Imagenes/lapiz.png"), "Borra el electrodoméstico seleccionado",
				KeyEvent.VK_E);

	}

	private Container crearPanelVentana() {

		panelPrincipal = new JPanel(new BorderLayout());

		panelPrincipal.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
						BorderFactory.createEmptyBorder(10, 10, 10, 10))));

		panelPrincipal.add(crearToolBar(), BorderLayout.NORTH);
		panelPrincipal.add(crearLista(), BorderLayout.CENTER);

		return panelPrincipal;
	}

	private Component crearLista() {

		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jLista = new JList<>();
		jLista.setCellRenderer(rendererEDConfig);
		jLista.setModel(selectedZona);
		if (jLista.getModel().getSize() == 0){
			accBorrar.setEnabled(false);
			accEdit.setEnabled(false);
		}
		jLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jLista.setSelectedIndex(0);
		panel.setViewportView(jLista);
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0),
				BorderFactory.createLineBorder(Color.black)));
		return panel;
	}

	private Component crearToolBar() {
		JButton btn;
		JToolBar toolBar = new JToolBar();

		btn = (JButton) toolBar.add(new JButton(accAñadir));
		btn.addActionListener(controladorListaED);
		btn.setActionCommand("Añadir");

		btn = (JButton) toolBar.add(new JButton(accBorrar));
		btn.addActionListener(controladorListaED);
		btn.setActionCommand("Eliminar");

		btn = (JButton) toolBar.add(new JButton(accEdit));
		btn.addActionListener(controladorListaED);
		btn.setActionCommand("Editar");

		return toolBar;
	}

	public Zona getSelectedZona() {
		return selectedZona;
	}

	public JList<ED> getjLista() {
		return jLista;
	}

	public ED getSelectedED() {
		return this.jLista.getSelectedValue();
	}

	public AbstractAction getAccBorrar() {
		return accBorrar;
	}

	public AbstractAction getAccEdit() {
		return accEdit;
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
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}