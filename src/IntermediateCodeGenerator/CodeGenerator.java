package IntermediateCodeGenerator;

import resources.TerminalColors;

import java.net.URI;
import java.util.*;

public class CodeGenerator {
	private HashMap<String,functionData> symbolsChart;
	private LinkedList<Integer> intermediateCode;
	private int customFunctionIndex;
	private Stack<Integer> jumpStack;
	private Stack<Integer> whileStack;
	private boolean inMain;

	public CodeGenerator(){
		this.symbolsChart = new HashMap<String, functionData>();
		this.intermediateCode = new LinkedList<Integer>();
		this.jumpStack = new Stack<Integer>();
		this.whileStack = new Stack<Integer>();
		this.customFunctionIndex = 3;
	}

	public void put(int number){
		this.intermediateCode.add(number);
	}


	public void putStackedJump(){
		this.put(998);
		this.jumpStack.push(this.intermediateCode.size());
	}


	public void putcustomFunction(String name){
		if (!this.symbolsChart.containsKey(name)){
			if (this.inMain){
				System.out.println(TerminalColors.ANSI_RED+"ERROR: "+name+" function is not defined");
                try {
                    System.out.println("Opening error page...");
                    String str = "http://interplanetary.xyz/kareldisplayer/error/?error="+name+"funtion%20is%20not%20defined";
                    URI displaySite = new URI(str);
                    java.awt.Desktop.getDesktop().browse(displaySite);
                } catch (Exception e) {
                    e.printStackTrace();
                }
				System.exit(-3);
			}
			functionData data = new functionData(this.customFunctionIndex*10000,this.intermediateCode.size());
			this.put(this.customFunctionIndex*10000);
			this.symbolsChart.put(name,data);
			this.customFunctionIndex++;
		}else{
			this.intermediateCode.add(997);
			this.intermediateCode.add(this.symbolsChart.get(name).getLineOfCode());
		}
	}

	public String toString(){
		return "Intermediate code: \n" + this.intermediateCode.toString() +"\n Symbols Chart: "+ this.symbolsChart.toString();
	}

	public void finishJump() {
		int index = this.jumpStack.pop();
		this.intermediateCode.add(index,this.intermediateCode.size()+1);
	}

	public void finishJump(int x) {
		int index = this.jumpStack.pop();
		this.intermediateCode.add(index,this.intermediateCode.size()+1+x);
	}

	public void whileStack() {
		this.whileStack.push(this.intermediateCode.size());
	}

	public void finishWhile() {
		this.put(998);
		this.put(this.whileStack.pop());

	}

	public int getintermediateIndex(){
		return this.intermediateCode.size();
	}

	public void setInMain(boolean inMain) {
		this.inMain = inMain;
	}

	public LinkedList<Integer> getIntermediateCode(){
        return this.intermediateCode;
    };

	public void setCode(int x,int code){
	    this.intermediateCode.set(x,code);
    }

    public void setFirstJump() {
	    this.intermediateCode.set(1,this.intermediateCode.size());
    }
}
