package Testing;


import lex.LexicAutomata;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestLexic {

	@Test
	public void test1(){
		LexicAutomata lA = new LexicAutomata();
		lA.createStates(4);
		lA.setStateFlow('a', 0, 1);
		lA.setStateFlow('b', 1, 2);
		lA.setStateFlow('1', 2, 3);
		lA.setLoop('-', 3);
		Assert.assertFalse(lA.verifyToken("nada"));
		Assert.assertTrue(lA.verifyToken("ab1-------"));
	}

}
