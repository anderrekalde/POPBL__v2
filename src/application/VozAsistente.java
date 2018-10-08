package application;

import java.io.IOException;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import vista.VentanaPrincipal;

/**
 * This is where all begins .
 * 
 * @author GOXR3PLUS
 *
 */
public class VozAsistente {
	VentanaPrincipal vista;
	Thread thread;
	SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
	AdvancedPlayer player;

	/**
	 * Constructor
	 */
	public VozAsistente(VentanaPrincipal vista) {
		this.vista = vista;
	}

	public void speak(String text) {

		if (!vista.getMute()) {
			if (player != null) {

				player.close();
			}
			thread = new Thread(() -> {
				try {

					// Create a JLayer instance
					player = new AdvancedPlayer(synthesizer.getMP3Data(text));
					player.play();

					System.out.println("Successfully got back synthesizer data");
					player = null;

				} catch (IOException | JavaLayerException e) {

					e.printStackTrace();

				}
			});

			thread.setDaemon(false);
			thread.start();

		}
	}

}
