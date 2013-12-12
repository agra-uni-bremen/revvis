package de.dfki.revlibReader;

import java.util.List;
import java.util.Vector;

public class ToffoliGate {
//	public String inputA;
//	public String inputB;
	
	public String output;
	
	private Vector<String> inputs = new Vector<String>();
	
	public List<String> getInputs() {
		return inputs;
	}
	
	public void addInput(String input) {
		if (!inputs.contains(input))
			inputs.add(input);
	}
	
	boolean putTargetsOnTop = true;
	
	public long calculateDistance(List<String> orderOfVars) {
		
		int minIndex = 0, maxIndex = 0;
		for (int i = 0; i < orderOfVars.size(); i++) {
			if (this.inputs.contains(orderOfVars.get(i)) || orderOfVars.get(i).equals(output)) {
				minIndex = i;
				break;
			}
		}
		
		for (int i = orderOfVars.size() - 1; i >= 0; i--) {
			if (this.inputs.contains(orderOfVars.get(i)) || orderOfVars.get(i).equals(output)) {
				maxIndex = i;
				break;
			}
		}
		
		int unusedVars = 0;
		for (int i = 0; i < inputs.size(); i++) {
			if (!(orderOfVars.contains(inputs.get(i))))
				unusedVars++;
		}
		if (!(orderOfVars.contains(output)))
			unusedVars++;
		
		long targetPenalty = 0;
		if (putTargetsOnTop)
			targetPenalty = calculateTargetPenalty(orderOfVars);
		
		if (unusedVars == 0) {
			return maxIndex - minIndex + targetPenalty;
		} else {
			return orderOfVars.size() - minIndex + unusedVars + targetPenalty;
		}
	}
	
	private long calculateTargetPenalty(List<String> orderOfVars) {
		boolean countFollowers = false;
		long result = 0;
		int distance = 0;
		for (int i = 0; i < orderOfVars.size(); i++) {
			if (orderOfVars.get(i) == this.output)
				countFollowers = true;
			if (countFollowers) {
				distance++;
				if (this.inputs.contains(orderOfVars.get(i))) {
					result += distance * 2;
				}
			}
		}
		for (int i = 0; i < this.inputs.size(); i++) {
			if (!orderOfVars.contains(inputs.get(i))) {
				result += distance * 2;
				distance++;
			}
		}
		return result;
	}
}