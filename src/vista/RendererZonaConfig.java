package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import modelo.Zona;

public class RendererZonaConfig implements ListCellRenderer<Zona> {

	@Override
	public Component getListCellRendererComponent(JList<? extends Zona> list, Zona value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel label = new JLabel (value.toString());
		label.setFont(new Font("arial", Font.BOLD,40));
		label.setBackground(new Color(242,242,242));
		if(isSelected){
			label.setBackground(Color.CYAN);
		}
		label.setOpaque(true);
		return label;
	}

}
