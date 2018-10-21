package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelo.ED;
import modelo.EDProgAndReg;
import modelo.EDProgramable;
import modelo.EDRegulable;

public class DialogoED extends JDialog implements ActionListener {
	MiBotonEstado encendido;
	ED electrodomestico;
	JPanel panel;
	JLabel lEstado;
	JLabel lValor;
	JLabel lPrograma;
	VentanaPrincipal principal;
	MiBotonEstado bPrograma;
	JTextField tValor;
	String tipo;
	JFrame jf;
	JSlider slider;
	JButton bOk;
	JButton bCancel;
	private List<MiBotonEstado> listaBotonesProgramas;
	EDProgramable programableED;
	EDRegulable regulableED;
	EDProgAndReg progAndRegED;
	int i;
	private List<MiBotonEstado> listaBotonesED;

	public DialogoED(VentanaPrincipal principal, String nombre, String tipo, ED electrodomestico) {
		super(principal, nombre, false);
		this.principal = principal;
		this.tipo = tipo;
		this.electrodomestico = electrodomestico;
		this.electrodomestico.switchIsSelected();
		listaBotonesProgramas = new ArrayList<>();
		listaBotonesED = new ArrayList<>();
		if (electrodomestico instanceof EDProgramable && !(electrodomestico instanceof EDProgAndReg)) {
			programableED = (EDProgramable) electrodomestico;
		}
		if (electrodomestico instanceof EDProgAndReg) {
			programableED = (EDProgramable) electrodomestico;
			progAndRegED = (EDProgAndReg) electrodomestico;
		}
		if (electrodomestico instanceof EDRegulable) {
			regulableED = (EDRegulable) electrodomestico;
		}
		jf = principal;
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				principal.setFlag(0);
				DialogoED.this.electrodomestico.switchIsSelected();
				dispose();
			}
		});
		this.setSize(400, 500);
		this.setContentPane(crearPanelDialogo());
		this.pack();
		this.setLocation(principal.getToolkit().getScreenSize().width / 2 - this.getWidth() / 2,
				principal.getToolkit().getScreenSize().height / 2 - this.getHeight() / 2);
		this.setVisible(true);
	}

	private Container crearPanelDialogo() {
		JPanel pane = new JPanel(new BorderLayout(0, 20));
		pane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		pane.add(crearPanelDatos(), BorderLayout.CENTER);
		pane.add(crearPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}

	private Component crearPanelDatos() {
		panel = new JPanel(new BorderLayout(10, 10));
		panel.add(crearPanelEstado(), BorderLayout.NORTH);

		switch (tipo) {

		case "programable":
			panel.add(crearPanelProgramas(), BorderLayout.CENTER);
			break;
		case "Prog + Reg":
			panel.add(crearPanelProgAndReg(), BorderLayout.CENTER);
			break;
		case "regulable":
			int numValores = regulableED.getvMax() - regulableED.getvMin();
			int vMin = regulableED.getvMin();
			int vMax = regulableED.getvMax();
			int valor = regulableED.getValor();
			panel.add(crearPanelValor(regulableED, numValores, vMin, vMax, valor), BorderLayout.CENTER);
			break;
		default:
			break;
		}

		return panel;
	}

	private Component crearPanelProgAndReg() {
		JPanel panelProgAndReg = new JPanel(new BorderLayout(10, 10));
		int numValores = progAndRegED.getvMax() - progAndRegED.getvMin();
		int vMin = progAndRegED.getvMin();
		int vMax = progAndRegED.getvMax();
		int valor = progAndRegED.getValor();

		panelProgAndReg.add(crearPanelValor(progAndRegED, numValores, vMin, vMax, valor), BorderLayout.NORTH);
		panelProgAndReg.add(crearPanelProgramas(), BorderLayout.CENTER);
		return panelProgAndReg;
	}

	private Component crearPanelProgramas() {
		int numProgramas;
		numProgramas = programableED.getLista().size();

		JPanel panelProgramas = new JPanel(new GridLayout(numProgramas > 10 ? 10 : numProgramas, numProgramas / 10));
		panelProgramas.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 5, true), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		i = 1;
		for (String programa : programableED.getLista()) {
			if (i <= 40) {
				panelProgramas.add(crearPanelPrograma(programa));
				i++;
			}
		}
		return panelProgramas;
	}

	private Component crearPanelPrograma(String programa) {
		JPanel panelPrograma = new JPanel(new GridLayout(1, 2));
		lPrograma = new JLabel(i + ".  " + programa);
		lPrograma.setFont(new Font("", Font.PLAIN, 25));
		lPrograma.setHorizontalAlignment(SwingConstants.LEFT);
		bPrograma = new MiBotonEstado(programa.equals(programableED.getPrograma()) ? true : false);
		if (!electrodomestico.getEstado())
			bPrograma.setEnabled(false);
		bPrograma.setActionCommand("programa" + i);
		bPrograma.setName(programa);
		bPrograma.addActionListener(this);
		listaBotonesProgramas.add(bPrograma);
		listaBotonesED.add(bPrograma);

		panelPrograma.add(lPrograma);
		panelPrograma.add(bPrograma);

		return panelPrograma;
	}

	private Component crearPanelValor(ED ed, int numValores, int vMin, int vMax, int valor) {
		JPanel panelValor = new JPanel(new BorderLayout());
		panelValor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		lValor = new JLabel("Valor");
		lValor.setHorizontalAlignment(SwingConstants.CENTER);

		slider = new JSlider(JSlider.HORIZONTAL, vMin, vMax, valor);
		slider.setPreferredSize(new Dimension(numValores * 5, 50));
		slider.setSnapToTicks(true);

		if (vMax > 40) {
			slider.setMajorTickSpacing(10);
		} else {
			slider.setMajorTickSpacing(1);
		}
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setLabelTable(slider.createStandardLabels(10));
		if (!ed.getEstado()) {
			slider.setEnabled(false);
		}
		panelValor.add(lValor, BorderLayout.NORTH);
		panelValor.add(slider, BorderLayout.CENTER);
		return panelValor;
	}

	private Component crearPanelEstado() {
		JPanel panelEstado = new JPanel(new FlowLayout());

		lEstado = new JLabel("Estado ··> ");
		lEstado.setFont(new Font("", Font.PLAIN, 25));
		lEstado.setHorizontalAlignment(SwingConstants.CENTER);
		encendido = new MiBotonEstado(electrodomestico.getEstado());

		encendido.setActionCommand("encender o apagar");
		encendido.addActionListener(this);

		panelEstado.add(lEstado);
		panelEstado.add(encendido);
		listaBotonesED.add(encendido);

		return panelEstado;
	}

	public JButton crearBoton(String nombre) {

		JButton boton = new JButton(nombre);
		boton.addActionListener(this);
		boton.setActionCommand(nombre);
		return boton;
	}

	private Component crearPanelBotones() {
		JPanel pane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		bOk = new JButton("Aceptar");
		bOk.addActionListener(this);
		bOk.setActionCommand("aceptar");
		bCancel = new JButton("Cancelar");
		bCancel.addActionListener(this);
		bCancel.setActionCommand("cancelar");
		pane.add(bOk);
		pane.add(bCancel);
		return pane;
	}

	public ED getED() {

		return electrodomestico;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			switch (e.getActionCommand()) {
			case "encender o apagar":
				encendido.switchStatus();

				switch (tipo) {
				case "programable":
					if (!encendido.getEstado()) {
						for (MiBotonEstado programa : listaBotonesProgramas) {
							programa.setEnabled(false);
						}
					} else {
						for (MiBotonEstado programa : listaBotonesProgramas) {
							programa.setEnabled(true);
						}
					}
					break;
				case "regulable":
					if (!encendido.getEstado()) {
						slider.setEnabled(false);
					} else {
						slider.setEnabled(true);
					}
					break;
				case "Prog + Reg":
					if (!encendido.getEstado()) {
						for (MiBotonEstado programa : listaBotonesProgramas) {
							programa.setEnabled(false);
						}
						slider.setEnabled(false);
					} else {
						for (MiBotonEstado programa : listaBotonesProgramas) {
							programa.setEnabled(true);
						}
						slider.setEnabled(true);
					}
				default:
					break;
				}

				break;

			case "aceptar":
				// PARA GUARDAR DATOS SEGUN SU TIPO
				String nombre;
				electrodomestico.setEstado(encendido.getEstado());
				principal.setFlag(0);
				switch (tipo) {

				case "standar":
					break;
				case "programable":
					nombre = ((EDProgramable) electrodomestico).getPrograma();
					for (MiBotonEstado boton : listaBotonesProgramas) {
						if (boton.getEstado()) {
							nombre = boton.getName();
						}
					}
					((EDProgramable) electrodomestico).setPrograma(nombre);
					break;
				case "regulable":
					((EDRegulable) electrodomestico).setValor(slider.getValue());
					break;
				case "Prog + Reg":
					nombre = ((EDProgAndReg) electrodomestico).getPrograma();
					for (MiBotonEstado boton : listaBotonesProgramas) {
						if (boton.getEstado()) {
							nombre = boton.getName();
						}
					}
					((EDProgAndReg) electrodomestico).setPrograma(nombre);
					((EDProgAndReg) electrodomestico).setValor(slider.getValue());
					break;
				}
				default:
					break;

				for (MiBotonED botonED : principal.getListaBotonesEDs()) {
					botonED.paintBorder();
				}
				break;

			case "cancelar":
				principal.setFlag(0);
				this.electrodomestico.switchIsSelected();
				DialogoED.this.dispose();
				break;
			default:
				int index;
				switch (tipo) {
				case "programable":
					EDProgramable programableEd = (EDProgramable) electrodomestico;
					index = Integer.valueOf(e.getActionCommand().substring(8));
					if (e.getActionCommand().contains("programa")) {
						for (MiBotonEstado programa : listaBotonesProgramas) {
							if (programa.getEstado()) {

								programa.switchStatus();
							}
						}
						listaBotonesProgramas.get(index - 1).switchStatus();
						programableEd.setPrograma(programableEd.getListaProgramas().get(index - 1));
					}
					break;
				case "Prog + Reg":
					index = Integer.valueOf(e.getActionCommand().substring(8));
					if (e.getActionCommand().contains("programa")) {
						for (MiBotonEstado programa : listaBotonesProgramas) {
							if (programa.getEstado()) {

								programa.switchStatus();
							}
						}
						listaBotonesProgramas.get(index - 1).switchStatus();
						progAndRegED.setPrograma(progAndRegED.getListaProgramas().get(index - 1));
					}
					break;
				}
				default:
					break;
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Error en el formato de los datos", "Datos no v�lidos",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public List<MiBotonEstado> getListaBotonesED() {
		return listaBotonesED;
	}

	public JSlider getSlider() {
		return slider;
	}

	public List<JButton> getBotonesOKyCancel() {
		List<JButton> listaBotones = new ArrayList<>();
		listaBotones.add(bOk);
		listaBotones.add(bCancel);
		return listaBotones;
	}

}