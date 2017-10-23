package main;

import Input.FileInput;
import Input.InputBuffer;
import synt.SyntacticKarel;

import javax.swing.*;
import java.io.File;
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

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("E:\\"));
		fileChooser.setSelectedFile(new File("README.html"));
		String filename = "none";

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION)
		{
			filename = fileChooser.getSelectedFile().getPath();
			JOptionPane.showMessageDialog(null, "You selected " + filename);
		}
		else if (result == JFileChooser.CANCEL_OPTION)
		{
			JOptionPane.showMessageDialog(null, "You selected nothing.");
		}
		else if (result == JFileChooser.ERROR_OPTION)
		{
			JOptionPane.showMessageDialog(null, "An error occurred.");
		}


		InputBuffer input = new FileInput();
		LinkedList<String> tokenList = input.processInput(filename);
		System.out.println(tokenList.toString()+"\n");
		String[] tokenarray = Main.transform(tokenList);
		SyntacticKarel syntKarel = new SyntacticKarel(tokenarray);
		syntKarel.analyze();


	}


}
