package vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class RelojFrame extends JLabel{
	Calendar calendario;
	int hora, minutos;
	private JLabel label;

	public RelojFrame() {
		reloj();
	}

	private void reloj() {
		label = new JLabel();
		calendario = new GregorianCalendar();
		Font font = null;
		label.setVerticalAlignment(SwingConstants.NORTH);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/digital-7 (italic).ttf"));
			font = font.deriveFont(Font.BOLD,170);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timer timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Date actual = new Date();
				calendario.setTime(actual);
				hora = calendario.get(Calendar.HOUR_OF_DAY);
				minutos = calendario.get(Calendar.MINUTE);
				String hour = String.format("%02d : %02d", hora, minutos);
				label.setText(hour);
			}
		});
		label.setFont(font);
		label.setPreferredSize(new Dimension(100,140));
		timer.start();
	}

	public JLabel getLabel() {
		return label;
	}
}