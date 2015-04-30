package de.dfki.revvisgdx.desktop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFileChooser;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.dfki.revvisgdx.RevVisGDX;
import de.dfki.revvisgdx.RevVisGDX.IFileLoaderCallback;

public class DesktopLauncher implements IFileLoaderCallback{
	private File path;
	
	public static void main (String[] arg) {
		DesktopLauncher launcher = new DesktopLauncher();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new RevVisGDX(), config);
		RevVisGDX.singleton.loader = launcher;
	}

	@Override
	public String loadFile() {
		if (path == null) {
			path = new File(System.getProperty("user.dir"));
		}

		final JFileChooser fc = new JFileChooser(path);
		fc.showOpenDialog(null);
		File sFile = fc.getSelectedFile();

		if (sFile != null) {

			path = sFile;

			String text;
			try {
				text = new Scanner( sFile ).useDelimiter("\\A").next();
				return text;
				//RevVisGDX.loadNewContents(text);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
}
