package hr.fer.zemris.java.hw17.jvdraw.tool;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Model of <i>Adapter</i> from <i>Adapter Patter Design</i>.
 * 
 * <p>
 * It represents adapter of {@link Tool} interface.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class ToolAdapter implements Tool {

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
	}

}
