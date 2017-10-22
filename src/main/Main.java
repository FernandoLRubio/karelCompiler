package main;

import Input.FileInput;
import Input.InputBuffer;
import synt.SyntacticKarel;

import java.util.LinkedList;

public class Main {

	public static String[] transform(LinkedList<String> tokenList){
		String[] result = new String[tokenList.size()];
		for (int i= 0; i<result.length;i++){
			result[i]=tokenList.get(i);
		}
		return  result;
	}

	public static void main(String[] args) {
		InputBuffer input = new FileInput();
		LinkedList<String> tokenList = input.processInput("C:\\Users\\mafel\\Documents\\compilers\\src\\Testing\\test_perfect1.karel");
		System.out.println(tokenList.toString()+"\n");
		String[] tokenarray = Main.transform(tokenList);
		SyntacticKarel syntKarel = new SyntacticKarel(tokenarray);
		syntKarel.analyze();


	}


}
