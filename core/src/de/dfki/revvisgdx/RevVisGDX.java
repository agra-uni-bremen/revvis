package de.dfki.revvisgdx;

import java.io.File;
//import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Vector;

//import javax.swing.JFileChooser;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Pixmap;
//import com.badlogic.gdx.graphics.Pixmap.Format;
//import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.dfki.revlibReader.ReversibleCircuit;
import de.dfki.revlibReader.RevlibFileReader;

/**
 * Core class that represents the visualization itself.
 * @author jannis
 *
 */
public class RevVisGDX implements ApplicationListener {
	public OrthographicCamera camera;
	public SpriteBatch batch;

	private static File path;

	public DrawableCircuit currentCircuit;
	public static RevVisGDX singleton;
	private Vector<Drawable> drawables = new Vector<Drawable>();

	MessageCenter mc = new MessageCenter();

	private String filename;

	boolean runFullPresetScreenshots = false;
	float fullPresetScreenshotsScaling = 6f;

	public Menu menu;
	
	public IFileLoaderCallback loader;

	public RevVisGDX() {
		super();
		System.out
				.println("Starting without filename being specified; loading example.");
		System.out.println("Usage: java -jar RevVisGDX.jar filename.real");
		singleton = this;
	}

	public RevVisGDX(String filename) {
		this();
		System.out.println("Starting RevVisGDX, loading " + filename);
		this.filename = filename;
	}

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(1, h / w);
		batch = new SpriteBatch();

		ReversibleCircuit c;
		if (this.filename != null && this.filename != "") {
			c = RevlibFileReader.readRealFile(filename);
		} else {
			FileHandle handle = Gdx.files
					.internal("examples/____intro_beispiel.real");
			String fileContents = handle.readString();
			c = RevlibFileReader.readRealFileContents(fileContents);
		}
		currentCircuit = new DrawableCircuitReordered(c);
		drawables.add(currentCircuit);

		RevVisInputProcessor inputProcessor = new RevVisInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		this.menu = new Menu();
		this.drawables.add(menu);

		mc.addMessage("RevVisGDX started");
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		// camera.apply(Gdx.gl20);

		// clear previous frame
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		for (int i = 0; i < drawables.size(); i++) {
			drawables.get(i).draw();
		}

		mc.render();

		batch.end();
	}

	private boolean firstRun = true;

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		if (firstRun) {
			if (!runFullPresetScreenshots)
				currentCircuit.zoomExtents();
			else {
				File f = new File(".\\screenshots");
				if (f.list().length == 0) {
					currentCircuit.setScaleImmediately(
							fullPresetScreenshotsScaling,
							fullPresetScreenshotsScaling);
					Presets.setConstGarbage();
					saveScreenshotCircuit("preset1_");
					Presets.setBoxesAndUsage();
					saveScreenshotCircuit("preset2_");
					Presets.setColourizedUsage();
					saveScreenshotCircuit("preset3_");
					Presets.setGreyNeighboursWithBlackTargets();
					saveScreenshotCircuit("preset4_");
					Presets.setColourizeLineType();
					saveScreenshotCircuit("preset5_");
					Presets.setMovingRuleColoured();
					saveScreenshotCircuit("preset7_");
					Presets.setColourizeUsageAbsolute();
					saveScreenshotCircuit("preset8_");
					Presets.setMovingRuleColouredAbsolute();
					saveScreenshotCircuit("preset9_");
					try {
						String execString = "montage screenshots/*.png -geometry +0+0 -tile "
								+ ((int) (this.currentCircuit.data.getGates()
										.size() / (Gdx.graphics.getWidth() / currentCircuit
										.getScaleX())) + 1)
								+ "x"
								+ ((int) (this.currentCircuit.data
										.getAmountOfVars() / (Gdx.graphics
										.getHeight() / currentCircuit
										.getScaleY())) + 1)
								+ " -crop "
								+ (int) (fullPresetScreenshotsScaling * currentCircuit.data
										.getGates().size())
								+ "x"
								+ (int) (fullPresetScreenshotsScaling * currentCircuit.data
										.getAmountOfVars())
								+ "+0+0 screenshots/fullPreset.png";
						System.out.println(execString);
						System.out.println("Screenshot export feature currently disabled");
						//Runtime.getRuntime().exec(execString);
					} catch (Exception e) {
						//System.out.println(System.getenv("PATH"));
						System.out.println(e.getMessage());
					}
				} else {
					System.out
							.println("screenshots folder must be empty, no images were generated.");
				}
				currentCircuit.zoomExtents();
			}
			firstRun = false;
		}
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public void saveScreenshotCircuit() {
		saveScreenshotCircuit("");
	}

	public void saveScreenshotCircuit(String prefix) {
		this.mc.hidden = true;

		int requiredYDim = (int) (this.currentCircuit.data.getAmountOfVars() / (Gdx.graphics
				.getHeight() / currentCircuit.getScaleY())) + 1;
		int requiredXDim = (int) (this.currentCircuit.data.getGates().size() / (Gdx.graphics
				.getWidth() / currentCircuit.getScaleX())) + 1;
		float elementsPerScreenY = (Gdx.graphics.getHeight() / currentCircuit
				.getScaleY());
		float minCoordY = -this.currentCircuit.data.getAmountOfVars() / 2f
				+ elementsPerScreenY / 2f + 0.5f;
		float minCoordX = (Gdx.graphics.getWidth() / currentCircuit.getScaleX()) / 2f - 0.5f;

		for (int y = 0; y < requiredYDim; y++) {
			this.currentCircuit.offsetY = y
					* -(Gdx.graphics.getHeight() * (1f / currentCircuit
							.getScaleY())) - minCoordY;
			for (int x = 0; x < requiredXDim; x++) {
				this.currentCircuit.offsetX = x
						* -(Gdx.graphics.getWidth() * (1f / currentCircuit
								.getScaleX())) - minCoordX;
				saveScreenshotFull(prefix);
			}
		}
	}

	private static int screenshotCount = 0;

	public void saveScreenshotFull(String prefix) {
		render();
		FileHandle fh = Gdx.files.getFileHandle("screenshots/" + prefix + ""
				+ new Date().getTime() + "_" + screenshotCount + ".png",
				FileType.Local);
		screenshotCount++;
		saveScreenshot(fh, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		mc.addMessage("saved screenshot: " + fh.path());
	}

	public void saveScreenshotFull() {
		saveScreenshotFull("");
	}

	/**
	 * Taken from http://code.google.com/p/libgdx-users/wiki/Screenshots
	 * 
	 * @param file
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	private void saveScreenshot(FileHandle file, int x, int y, int w, int h) {
//		Pixmap pixmap = getScreenshot(x, y, w, h, true);
//		PixmapIO.writePNG(file, pixmap);
//		pixmap.dispose();
		System.out.println("Screenshot saving currently disabled.");
	}

	/**
	 * Taken from http://code.google.com/p/libgdx-users/wiki/Screenshots
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param flipY
	 * @return
	 */
//	private Pixmap getScreenshot(int x, int y, int w, int h, boolean flipY) {
//		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
//
//		final Pixmap pixmap = new Pixmap(w, h, Format.RGBA8888);
//		ByteBuffer pixels = pixmap.getPixels();
//		Gdx.gl.glReadPixels(x, y, w, h, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE,
//				pixels);
//
//		final int numBytes = w * h * 4;
//		byte[] lines = new byte[numBytes];
//		if (flipY) {
//			final int numBytesPerLine = w * 4;
//			for (int i = 0; i < h; i++) {
//				pixels.position((h - i - 1) * numBytesPerLine);
//				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
//			}
//			pixels.clear();
//			pixels.put(lines);
//		} else {
//			pixels.clear();
//			pixels.get(lines);
//		}
//
//		return pixmap;
//	}
	
	public interface IFileLoaderCallback {
		String loadFile();
	}
	
	public static void loadNewContents(String contents) {
		ReversibleCircuit c = RevlibFileReader.readRealFileContents(contents);
		RevVisGDX.singleton.drawables
				.remove(RevVisGDX.singleton.currentCircuit);
		RevVisGDX.singleton.mc.clear();
		RevVisGDX.singleton.currentCircuit = new DrawableCircuitReordered(
				c);
		RevVisGDX.singleton.drawables
				.add(RevVisGDX.singleton.currentCircuit);
		RevVisGDX.singleton.currentCircuit.zoomExtents();
		RevVisGDX.singleton.mc.addMessage("Loaded " + contents.substring(0, Math.min(24, contents.length())));
	}

	public static void loadNewFile() {
		if (RevVisGDX.singleton.loader != null){
			String contents = RevVisGDX.singleton.loader.loadFile();
			if (!(contents.equals(""))) {
				RevVisGDX.loadNewContents(contents);
			}
		}
	}
}
