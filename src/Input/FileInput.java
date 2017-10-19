package Input;

import lex.KarelLexicAnalyzer;
import resources.TerminalColors;

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class FileInput implements InputBuffer {
	private LinkedList<String> tokens = new LinkedList<String>();
	private KarelLexicAnalyzer Karelex = new KarelLexicAnalyzer();

	@Override
	public LinkedList<String> processInput(String url) {
		System.out.println(TerminalColors.ANSI_GREEN+"Starting Lexicographic Analyzer..."+TerminalColors.ANSI_RESET);
		final String delimiter = " ";
		File sourcecode = new File(url);
		FileReader fetcher = this.fetchFile(sourcecode);
		BufferedReader parser = new BufferedReader(fetcher);
		StringTokenizer tokenizer;
		String currentLine = "";
		String token;

		try {
			parser = new BufferedReader(fetcher);
			currentLine = parser.readLine();
			while(currentLine != null){
				tokenizer = new StringTokenizer(currentLine, delimiter);

				while(tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					if (Karelex.checkLexic(token)){
						tokens.add(token);
					}else{
						System.out.println(TerminalColors.ANSI_RED+"Lexical ERROR:"+TerminalColors.ANSI_RESET+"Token "+TerminalColors.ANSI_BLUE+token+TerminalColors.ANSI_RESET+" is not valid");
					}

				}

				currentLine = parser.readLine();
			}

		}catch (FileNotFoundException e) {
			System.out.println("Unable to open file " + sourcecode);
		} catch (IOException e) {
			System.out.println("Unable to read from file " + sourcecode);
		}finally {

			// Close the file
			try {
				if (parser != null)
					parser.close();
			} catch (IOException e) {
				System.out.println("Unable to close file " + sourcecode);
			}

		}

		return this.tokens;
	}

	public FileReader fetchFile(File sourcecode) {
		try {
			FileReader parser = new FileReader(sourcecode);
			return parser;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error, file not found");
			System.exit(-2);
			return null;
		}
	}

}