package synt;

import resources.TerminalColors;

public class SyntacticOperations {
	private String[] tokens;
	private int tokensIndex;
	private String errors;

	public SyntacticOperations(String[] tokens) {
		this.tokens = tokens;
		this.tokensIndex = 0;
		this.errors= "\033[0;1m"+"Syntactic Error: \n";
	}

	public String getErrors() {
		return errors;
	}

	public void advance(){
		this.tokensIndex++;
	}

	public void addSpecificError(String error){
		this.errors += TerminalColors.ANSI_RED+"Critical Error at token "+this.tokensIndex+"."+TerminalColors.ANSI_RESET+"\t Unexpected token \""+TerminalColors.ANSI_BLUE+this.tokens[tokensIndex]+"\""+TerminalColors.ANSI_RESET+error+"\n";
	}

	public void extraWarning(String warning){
		System.out.println(warning+this.tokens[tokensIndex]);
	}

	public boolean verify(String expectedToken){
		if (!this.tokens[tokensIndex].equals(expectedToken)){
			System.out.println(TerminalColors.ANSI_YELLOW+"Warning: token "+TerminalColors.ANSI_BLUE+this.tokens[tokensIndex]+TerminalColors.ANSI_YELLOW+" is not complying, "+TerminalColors.ANSI_BLUE+expectedToken+TerminalColors.ANSI_YELLOW+" expected"+". At token "+tokensIndex+TerminalColors.ANSI_RESET+"\n");
		}
		this.tokensIndex++;
		return true;
	}

	public boolean require(String expectedToken){
		if (!this.tokens[tokensIndex].equals(expectedToken)){
			this.errors += TerminalColors.ANSI_RED+"Critical Error at token "+this.tokensIndex+"."+TerminalColors.ANSI_RESET+"\t Unexpected token \""+TerminalColors.ANSI_BLUE+this.tokens[tokensIndex]+"\""+TerminalColors.ANSI_RESET+" instead of "+TerminalColors.ANSI_BLUE+expectedToken+TerminalColors.ANSI_BLUE+"\n";
			return false;
		}else{
			this.tokensIndex++;
			return true;
		}
	}

	public boolean probe(String expectedToken){
		if (this.tokens[tokensIndex].equals(expectedToken)){
			return true;
		}else{
			return false;
		}

	}

	public boolean requireType(String type) {
		boolean result = false;
		if (type.equals("Integer")){
			try {
				int x = Integer.parseInt(this.tokens[tokensIndex]);
				result = true;
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
		}
		this.tokensIndex++;
		return result;
	}

	public boolean checkLenght(int min, int max) {
		if (this.tokens[this.tokensIndex].length()>min && this.tokens[this.tokensIndex].length()<max){
			return true;
		}else{
			return false;
		}
	}
}
