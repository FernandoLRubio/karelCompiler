package IntermediateCodeGenerator;

import java.util.HashMap;
import java.util.LinkedList;

public class CodeGenerator {
	private HashMap<String,functionData> symbolsChart;
	private LinkedList<Integer> intermediateCode;
	private int customFunctionIndex;

	public CodeGenerator(){
		this.symbolsChart = new HashMap<String, functionData>();
		this.intermediateCode = new LinkedList<Integer>();
		this.customFunctionIndex = 3;
	}

	public void put(int number){
		this.intermediateCode.add(number);
	}

	public void putIf(){
		this.intermediateCode.add(11111);
	}

	public void putElse(){
		this.intermediateCode.add(22222);
	}

	public void putWhile(){
		this.intermediateCode.add(33333);
	}

	public void putIterate(){
		this.intermediateCode.add(44444);
	}

	public void putcustomFunction(String name){
		if (!this.symbolsChart.containsKey(name)){
			functionData data = new functionData(this.customFunctionIndex*10000,this.intermediateCode.size());
			this.put(this.customFunctionIndex*10000);
			this.put(999);
			this.symbolsChart.put(name,data);
			this.customFunctionIndex++;
		}
	}

	public String toString(){
		return "Intermediate code: \n" + this.intermediateCode.toString() +"\n Symbols Chart: "+ this.symbolsChart.toString();
	}

}
