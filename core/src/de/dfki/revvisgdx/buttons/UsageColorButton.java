package de.dfki.revvisgdx.buttons;

import de.dfki.revvisgdx.Button;
import de.dfki.revvisgdx.RevVisGDX;

/**
 * A button class that takes the original functionality from the
 * de.dfki.revvisgdx.buttons.Button class and turns it into a three-way toggle
 * button to cycle through the available coloring methods.
 */
public class UsageColorButton extends Button {

	/**
	 * Creates a new UsageColorButton.
	 * There shouldn't really be more than one.
	 */
	public UsageColorButton() {
		super("data/VariableButtonUsageNone.png");
	}

	/**
	 * Cycles the RevVisGDX.singleton's line usage coloring value and
	 * replaces this button's texture accordingly.
	 */
	@Override
	public void Clicked() {
		super.Clicked();

		RevVisGDX.singleton.currentCircuit.toggleLineUsageColouring();
		switch
			(RevVisGDX.singleton.currentCircuit.drawLinesColourizedWhenUsed) {
		case absolute:
			this.setTexture("data/VariableButtonUsageAbsolute.png");
			break;
		case none:
			this.setTexture("data/VariableButtonUsageNone.png");
			break;
		case relative:
			this.setTexture("data/VariableButtonUsageRelative.png");
			break;
		default:
			break;
		}
	}
}
