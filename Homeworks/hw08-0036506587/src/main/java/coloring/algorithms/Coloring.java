package coloring.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {

	/**
	 * Reference to pixel.
	 */
	private Pixel reference;

	/**
	 * Reference to picture.
	 */
	private Picture picture;

	/**
	 * Color used for filling current pixel.
	 */
	private int fillColor;

	/**
	 * Reference color.
	 */
	private int refColor;

	/**
	 * Constructor.
	 * 
	 * @param reference pixel reference.
	 * @param picture   picture reference.
	 * @param fillColor fill color.
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public Pixel get() {
		return reference;
	}

	@Override
	public boolean test(Pixel pixel) {
		return fillColor != refColor && refColor == picture.getPixelColor(pixel.x, pixel.y);
	}

	@Override
	public List<Pixel> apply(Pixel pixel) {
		List<Pixel> results = new ArrayList<>();
		int xPixel = pixel.x;
		int yPixel = pixel.y;
		
		// add adjacent pixels if they are valid.
		if (checkCoordinates(xPixel, yPixel + 1)) {
			results.add(new Pixel(xPixel, yPixel + 1));
		}
		if (checkCoordinates(xPixel + 1, yPixel)) {
			results.add(new Pixel(xPixel + 1, yPixel));
		}
		if (checkCoordinates(xPixel, yPixel - 1)) {
			results.add(new Pixel(xPixel, yPixel - 1));
		}
		if (checkCoordinates(xPixel - 1, yPixel)) {
			results.add(new Pixel(xPixel - 1, yPixel));

		}
		return results;
	}

	/**
	 * Helper method used for checking whether provided coordinates are inside
	 * picture frame.
	 * 
	 * @param x x axis.
	 * @param y y axis.
	 * @return {@code true} if the coordinates are good, otherwise {@code false}.
	 */
	private boolean checkCoordinates(int x, int y) {
		return x >= 0 && x < picture.getWidth() && y >= 0 && y < picture.getHeight();
	}

	@Override
	public void accept(Pixel pixel) {
		picture.setPixelColor(pixel.x, pixel.y, fillColor);
	}

}
