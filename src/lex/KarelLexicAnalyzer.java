package lex;
import org.testng.Assert;
import lex.*;

import java.util.Arrays;

public class KarelLexicAnalyzer {
	private static final String[] reservedWords = {"move","turnleft","pickbeeper","putbeeper","turnoff","front-is-clear","front-is-blocked",
			"left-is-clear","left-is-blocked","right-is-clear","right-is-blocked","next-to-a-beeper","not-next-to-a-beeper","facing-north",
			"not-facing-north","facing-south","not-facing-south","facing-east","not-facing-east","facing-west","not-facing-west",
			"any-beepers-in-beeper-bag","no-beepers-in-beeper-bag","if","else","iterate","while","program","void","(",")","{","}"};

	public boolean checkLexic(String token){
		LexicAutomata automata = new LexicAutomata();
		if(Arrays.asList(this.reservedWords).contains(token) || automata.isAlpha(token) || automata.isNumeric(token)){
			return true;
		}else{
			return false;
		}
	}
}
