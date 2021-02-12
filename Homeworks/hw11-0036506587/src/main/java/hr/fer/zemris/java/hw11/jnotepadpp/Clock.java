package hr.fer.zemris.java.hw11.jnotepadpp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * An implementation of clock which shows current DateTime.
 * 
 * @author dbrcina
 *
 */
public class Clock extends JLabel {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Clock's time.
	 */
	private volatile String time;

	/**
	 * DateTime formatter.
	 */
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	/**
	 * Boolean flag used for stopping this clock.
	 */
	private volatile boolean stopRequested;

	/**
	 * Constructor.
	 */
	public Clock() {
		setHorizontalAlignment(JLabel.RIGHT);
		updateTime();

		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (Exception ex) {
				}
				if (stopRequested)
					break;
				SwingUtilities.invokeLater(() -> {
					updateTime();
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Setter for stop requeste.
	 * 
	 * @param stopRequested stop requested.
	 */
	public void setStopRequested(boolean stopRequested) {
		this.stopRequested = stopRequested;
	}

	/**
	 * Updates current time.
	 */
	private void updateTime() {
		time = formatter.format(LocalDateTime.now());
		setText(time);
	}
}
