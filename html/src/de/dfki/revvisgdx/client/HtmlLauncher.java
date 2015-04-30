package de.dfki.revvisgdx.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;

import de.dfki.revvisgdx.RevVisGDX;

public class HtmlLauncher extends GwtApplication {

	int paddingX = 64;
	int paddingY = 192;

	@Override
	public ApplicationListener getApplicationListener () {
		prepareDoc();
		return new RevVisGDX();
	}

	@Override
	public void onModuleLoad () {
		super.onModuleLoad();
		com.google.gwt.user.client.Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent ev) {
				Gdx.graphics.setDisplayMode(ev.getWidth() - paddingX, ev.getHeight() - paddingY, false);
			}
		});
	}

	@Override
	public GwtApplicationConfiguration getConfig () {
		int height = com.google.gwt.user.client.Window.getClientHeight();
		int width = com.google.gwt.user.client.Window.getClientWidth();
		
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(width - paddingX, height - paddingY);
		return cfg;
	}
	
	public static native void prepareDoc() /*-{
		$doc.getElementById('files').addEventListener('change', handleFileSelect, false);
		
		function handleFileSelect(evt) {
			//alert('boh');
			var files = evt.target.files; // FileList object

			var f = files[0];
			if (f != undefined && f != null) {
				var r = new FileReader();
				r.onload = function(e) {
					var contents = e.target.result;
					@de.dfki.revvisgdx.client.HtmlLauncher::loadContents(Ljava/lang/String;)(e.target.result);
				}
				r.readAsText(f);
			}
		}
	}-*/;
	
	public static void loadContents(String contents) {
		RevVisGDX.loadNewContents(contents);
	}

	public static native String loadFile() /*-{
		if ($wnd.File && $wnd.FileReader && $wnd.FileList && $wnd.Blob) {
			alert('Success');
			// Great success! All the File APIs are supported.
		} else {
			alert('The File APIs are not fully supported in this browser.');
		}
	}-*/;
}