package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import modelo.ED;

public class RendererEDConfig implements ListCellRenderer<ED>{

	@Override
	public Component getListCellRendererComponent(JList<? extends ED> list, ED value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel label = new JLabel(value.toString());
		label.setFont(new Font("arial", Font.BOLD,30));
		if(isSelected){
			label.setBackground(Color.CYAN);
		}
		label.setOpaque(true);
		return label;
	}

}
