package Testing;

import org.testng.Assert;
import org.testng.annotations.Test;
import synt.SyntacticKarel;

@Test
public class TestSyntactic {

	@Test
	public void test1(){
		String[] tokens = {"class", "program", "{", "program", "(", ")","{","}","}"};
		SyntacticKarel kTest = new SyntacticKarel(tokens);
		Assert.assertTrue(kTest.analyze());
	}

	@Test
	public void test3(){
		String[] tokens = {"class", "program", "{", "void", "program", "(", ")","{","}","}"};
		SyntacticKarel kTest = new SyntacticKarel(tokens);
		Assert.assertTrue(kTest.analyze());
	}

	@Test
	public void test4(){
		String[] tokens = {"class", "program", "{",
				"void","foo","(",")","{",
				"if","(","front-is-clear",")","{",

				"}","else","{",
				"iterate","(","5",")","{",
				"move","(",")",
				"jumper","(",")",
				"}",
				"}",
				"}",
				"program", "(", ")","{",

				"}",
				"}"};
		SyntacticKarel kTest = new SyntacticKarel(tokens);
		kTest.analyze();
	}


}
