package application;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import controlador.ControladorVoz;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import modelo.Casa;
import vista.VentanaPrincipal;

public class SpeechRecognizer {
	VozAsistente vozAsistente;
	boolean clave = false;
	Timer timer;
	String palabra = "";
	ControladorVoz controladorVoz;
	
	private LiveSpeechRecognizer recognizer;
	private Logger logger = Logger.getLogger(getClass().getName());
	private String speechRecognitionResult;
	private boolean ignoreSpeechRecognitionResults = false;
	private boolean speechRecognizerThreadRunning = false;
	private boolean resourcesThreadRunning;
	private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);

	// ------------------------------------------------------------------------------------


	public SpeechRecognizer(VentanaPrincipal vista,  Casa casa) {

		controladorVoz = new ControladorVoz(vista, casa);
		//vozAsistente = new VozAsistente();
		// Loading Message
		logger.log(Level.INFO, "Loading Speech Recognizer...\n");

		// Configuration
		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resource/esp_acoustic/");
		configuration.setDictionaryPath("resource/esp_lm/es.dict");

		configuration.setLanguageModelPath("resource/esp_lm/es-20k.lm");

		// Load model from the jar
		// configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		// configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");

		// ====================================================================================
		// =====================READ
		// THIS!!!===============================================
		// Uncomment this line of code if you want the recognizer to recognize
		// every word of the language
		// you are using , here it is English for example
		// ====================================================================================
		// configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

		// ====================================================================================
		// =====================READ
		// THIS!!!===============================================
		// If you don't want to use a grammar file comment below 3 lines and
		// uncomment the above line for language model
		// ====================================================================================

		// Grammar
		configuration.setGrammarPath("resource/grammars");
		configuration.setGrammarName("grammar");
		configuration.setUseGrammar(true);

		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}

		// Start recognition process pruning previously cached data.
		// recognizer.startRecognition(true);

		// Check if needed resources are available
		startResourcesThread();
		// Start speech recognition thread
		startSpeechRecognition(vista);
	}

	// -----------------------------------------------------------------------------------------------

	/**
	 * Starts the Speech Recognition Thread
	 */
	public synchronized void startSpeechRecognition(VentanaPrincipal vista) {

		// Check lock
		if (speechRecognizerThreadRunning)
			logger.log(Level.INFO, "Speech Recognition Thread already running...\n");
		else
			// Submit to ExecutorService
			eventsExecutorService.submit(() -> {

				// locks
				speechRecognizerThreadRunning = true;
				ignoreSpeechRecognitionResults = false;

				// Start Recognition
				recognizer.startRecognition(true);

				// Information
				logger.log(Level.INFO, "You can start to speak...\n");

				try {
					while (speechRecognizerThreadRunning) {
						/*
						 * This method will return when the end of speech is
						 * reached. Note that the end pointer will determine the
						 * end of speech.
						 */
						SpeechResult speechResult = recognizer.getResult();

						// Check if we ignore the speech recognition results
						if (!ignoreSpeechRecognitionResults) {

							// Check the result

							if (speechResult == null)
								logger.log(Level.INFO, "I can't understand what you said.\n");
							else {

								// Get the hypothesis
								speechRecognitionResult = speechResult.getHypothesis();

								// You said?
								System.out.println("You said: [" + speechRecognitionResult + "]\n");

								// palabra clave
								if (clave == false && this.getSpeech().equals("casa")) {
									clave = true;
									System.out.println("Ha recogido la clave");
									//Toolkit.getDefaultToolkit().beep();

									palabra = "";
									controladorVoz.filtradorZonas("casa");

									Temporizador(5);

								} else if (clave == true) {
									//palabra = palabra + this.getSpeech();
									palabra = this.getSpeech();
									//vozAsistente.speak(palabra);
									controladorVoz.filtradorZonas(palabra);
									System.out.println("Ha dicho la palabra despues de la clave");
								}
							}
						} else {
							logger.log(Level.INFO, "Ingoring Speech Recognition Results...");
						}
					}
				} catch (Exception ex) {
					logger.log(Level.WARNING, null, ex);
					speechRecognizerThreadRunning = false;
				}

				logger.log(Level.INFO, "SpeechThread has exited...");

			});
	}

	/**
	 * Stops ignoring the results of SpeechRecognition
	 */
	public synchronized void stopIgnoreSpeechRecognitionResults() {

		// Stop ignoring speech recognition results
		ignoreSpeechRecognitionResults = false;
	}

	/**
	 * Ignores the results of SpeechRecognition
	 */
	public synchronized void ignoreSpeechRecognitionResults() {

		// Instead of stopping the speech recognition we are ignoring it's
		// results
		ignoreSpeechRecognitionResults = true;

	}

	// -----------------------------------------------------------------------------------------------

	/**
	 * Starting a Thread that checks if the resources needed to the
	 * SpeechRecognition library are available
	 */
	public void startResourcesThread() {

		// Check lock
		if (resourcesThreadRunning)
			logger.log(Level.INFO, "Resources Thread already running...\n");
		else
			// Submit to ExecutorService
			eventsExecutorService.submit(() -> {
				try {

					// Lock
					resourcesThreadRunning = true;

					// Detect if the microphone is available
					while (true) {

						// Is the Microphone Available
						if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
							logger.log(Level.INFO, "Microphone is not available.\n");

						// Sleep some period
						Thread.sleep(350);
					}

				} catch (InterruptedException ex) {
					logger.log(Level.WARNING, null, ex);
					resourcesThreadRunning = false;
				}
			});
	}

	/**
	 * Timer 
	 */
	public void Temporizador(int segundos) {
		timer = new Timer();
		timer.schedule(new Task(), segundos * 1000);
		System.out.println("Empieza timer");
		
		
	}

	class Task extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("Timer acaba");
			clave = false;
			timer.cancel();

		}
	}

	public String getPalabra() {
		return palabra;
	}

	public String getSpeech() {

		return speechRecognitionResult;
	}

	public boolean getIgnoreSpeechRecognitionResults() {
		return ignoreSpeechRecognitionResults;
	}

	public boolean getSpeechRecognizerThreadRunning() {
		return speechRecognizerThreadRunning;
	}


	
}