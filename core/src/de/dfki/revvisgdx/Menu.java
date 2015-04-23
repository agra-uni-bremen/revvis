package de.dfki.revvisgdx;

import java.util.Vector;

import com.badlogic.gdx.Gdx;

import de.dfki.revvisgdx.buttons.FunctionButton;

/**
 * This is the menu you can see at the bottom edge of the screen. It's just a collection
 * of a few buttons to call certain methods that alter how the ReversibleCircuit is drawn.
 * 
 * @author jannis
 *
 */
public class Menu implements Drawable {
	private Vector<Button> buttons = new Vector<Button>();
	private int lines = 1;
	
	public Menu() {
//		try {
			buttons.add(new FunctionButton("data/Preset0.png", new setDefault()));
			buttons.add(new FunctionButton("data/Preset1.png", new setConstGarbage()));
			buttons.add(new FunctionButton("data/Preset2.png", new setBoxesAndUsage()));
			buttons.add(new FunctionButton("data/Preset3.png", new setColourizedUsage()));
			buttons.add(new FunctionButton("data/Preset4.png", new setGreyNeighboursWithBlackTargets()));
			buttons.add(new FunctionButton("data/Preset5.png", new setColourizeLineType()));
			buttons.add(new FunctionButton("data/Preset6.png", new setMovingRuleBoxOverlay()));
			buttons.add(new FunctionButton("data/Preset7.png", new setMovingRuleColoured()));
			buttons.add(new FunctionButton("data/Preset8.png", new setColourizeUsageAbsolute()));
			buttons.add(new FunctionButton("data/Preset9.png", new setMovingRuleColouredAbsolute()));
//			buttons.add(new FunctionButton("data/LoadFile.png", RevVisGDX.class.getMethod("loadNewFile")));
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * Call this if the mouse has been moved.
	 * @param x the new x coordinate
	 * @param y the new y coordinate
	 */
	public void MouseCoords(int x, int y) {
		float buttonX = x - Gdx.graphics.getWidth() / 2f;
		float buttonY = -y + Gdx.graphics.getHeight() / 2f;
		
		for (Button b : buttons) {
			b.IsHovered((int)buttonX, (int)buttonY);
		}
	}
	
	/**
	 * Call this if the uses clicked somewhere
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return true if a button was clicked, false otherwise
	 */
	public boolean click(int x, int y) {
		float xButton = x - Gdx.graphics.getWidth() / 2f;
		float yButton = -y + Gdx.graphics.getHeight() / 2f;
		for (Button b : this.buttons) {
			if (b.IsClicked((int)xButton, (int)yButton))
				return true;
		}
		return false;
	}
	
	@Override
	public void draw() {
		float desiredPlacePerButton = 128 + 38;
		int buttonsPerLine = (int)(Gdx.graphics.getWidth() / desiredPlacePerButton);
		int buttonsInLastLine = buttons.size() % buttonsPerLine;
		if (buttonsInLastLine == 0)
			buttonsInLastLine = buttonsPerLine;
		
		lines = (int)(buttons.size() / buttonsPerLine);
		if (buttons.size() % buttonsPerLine != 0)
			lines++;
		
		int currentButton = 1;
		int currentLine = lines;
		float placePerButton = (Gdx.graphics.getWidth() / (buttonsPerLine + 1));
		
		for (Button b : this.buttons) {
			
			float originalX = placePerButton * currentButton - Gdx.graphics.getWidth() / 2f;
			
			b.x = originalX;
			b.y = Gdx.graphics.getHeight() / -2f - 32 + (72 * currentLine);
			if (currentLine == 1) {
				b.x += placePerButton * ((buttonsPerLine - buttonsInLastLine) / 2f);
			}
			
			currentButton++;
			if (currentButton > buttonsPerLine) {
				currentButton = 1;
				currentLine--;
			}
			
			b.draw();
		}
	}
	
	private class setDefault implements FunctionButton.ButtonCallBack{
		@Override
		public void method() {
			Presets.setDefault();
		}
	}
	
	private class setConstGarbage implements FunctionButton.ButtonCallBack{
		@Override
		public void method() {
			Presets.setConstGarbage();
		}
	}
	
	private class setBoxesAndUsage implements FunctionButton.ButtonCallBack{
		@Override
		public void method() {
			Presets.setBoxesAndUsage();
		}
	}
	
	private class setColourizedUsage implements FunctionButton.ButtonCallBack{
		@Override
		public void method() {
			Presets.setColourizedUsage();
		}
	}
	
	private class setGreyNeighboursWithBlackTargets implements FunctionButton.ButtonCallBack{
		@Override
		public void method() {
			Presets.setGreyNeighboursWithBlackTargets();
		}
	}
	
	private class setColourizeLineType implements FunctionButton.ButtonCallBack{
		@Override
		public void method() {
			Presets.setColourizeLineType();
		}
	}
	
	private class setMovingRuleBoxOverlay implements FunctionButton.ButtonCallBack{
		@Override
		public void method() {
			Presets.setMovingRuleBoxOverlay();
		}
	}
	
	private class setMovingRuleColoured implements FunctionButton.ButtonCallBack{
		@Override
		public void method() {
			Presets.setMovingRuleColoured();
		}
	}
	
	private class setColourizeUsageAbsolute implements FunctionButton.ButtonCallBack{
		@Override
		public void method() {
			Presets.setColourizeUsageAbsolute();
		}
	}
	
	private class setMovingRuleColouredAbsolute implements FunctionButton.ButtonCallBack{
		@Override
		public void method() {
			Presets.setMovingRuleColouredAbsolute();
		}
	}
}
