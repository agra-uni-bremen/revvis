package de.dfki.revvisgdx.buttons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.dfki.revvisgdx.Button;

public class FunctionButton extends Button {
	ButtonCallBack toExecute;
	public FunctionButton(String filename, ButtonCallBack m) {
		super(filename);
		toExecute = m;
	}
	
	@Override
	public void Clicked() {
		this.toExecute.method();
	}
	
	public interface ButtonCallBack {
	    void method();
	}
}
