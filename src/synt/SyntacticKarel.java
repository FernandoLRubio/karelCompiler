package synt;

import IntermediateCodeGenerator.CodeGenerator;
import resources.TerminalColors;

import java.util.LinkedList;

public class SyntacticKarel implements KarelLang {
	private SyntacticOperations operations;
	private CodeGenerator intermediate;

	public SyntacticKarel(String[] tokens){
		this.operations = new SyntacticOperations(tokens);
		this.intermediate = new CodeGenerator();
	}

	public boolean analyze(){
		System.out.println(TerminalColors.ANSI_GREEN+"Starting Syntactic Analyzer..."+TerminalColors.ANSI_RESET);
		this.program();
		System.out.println("Succesful!");
		System.out.println(this.intermediate.toString());
		return true;
	}

	public void exitErrors(){
		System.out.println(this.operations.getErrors());
		System.exit(-1);
	}

	@Override
	public void program() {
		if ( operations.require("class") ) {
			if ( operations.require("program") ) {
				if ( operations.require("{") ) {
					this.functions();
					this.main_function();
					if ( !operations.require("}") ){
						this.exitErrors();
					}
				} else  this.exitErrors();
			} else  this.exitErrors();
		} else  this.exitErrors();
	}

	@Override
	public void functions() {
		if(!operations.verify("program")){
			this.function();
			this.functions_prima();
		}

	}

	@Override
	public void functions_prima() {
		if(!operations.verify("program")){
			this.function();
			this.functions_prima();
		}
	}

	@Override
	public void function() {
		if(operations.require("void")){
			this.name_function();
			if (operations.require("(")){
				if(operations.require(")")){
					if (operations.require("{")){
						this.body();
						if (!operations.require("}")){
							this.exitErrors();
						}
					}else  this.exitErrors();
				}else  this.exitErrors();
			}else  this.exitErrors();
		}else  this.exitErrors();
	}

	@Override
	public void main_function() {
		if (operations.require("program")){
			if (operations.require("(")){
				if (operations.require(")")){
					if (operations.require("{")){
						this.body();
						if (!operations.require("}")){
							this.exitErrors();
						}
					}else  this.exitErrors();
				}else  this.exitErrors();
			}else  this.exitErrors();
		}else  this.exitErrors();
	}



	@Override
	public void body() {
		if (!operations.verify("}")){
			this.expression();
			this.body_prima();
		}
	}

	@Override
	public void body_prima() {
		if (!operations.verify("}")){
			this.expression();
			this.body_prima();
		}
	}

	@Override
	public void expression() {
		if (operations.verify("if")){
			this.if_expression();
		}
		else if(operations.verify("while")){
			this.while_();
		}
		else if(operations.verify("iterate")){
			this.iterate_expression();
		}
		else {
			call_function();
		}
	}

	@Override
	public void call_function() {
		this.name_function();
		if (operations.require("(")){
			if (!operations.require(")")){
				this.exitErrors();
			}
		}else{
			this.exitErrors();
		}
	}

	@Override
	public void name_function() {
		if (this.official_function()){
			operations.advance();
		}else{
			this.customer_function();
		}

	}

	@Override
	public void if_expression() {
		if (operations.require("if")){
			this.intermediate.putIf();
			if (operations.require("(")){
				this.condition();
				if (operations.require(")")){
					if (operations.require("{")){
						this.body();
						if (operations.require("}")){
							this.else_();
						}else  this.exitErrors();
					}else  this.exitErrors();
				}else  this.exitErrors();
			}else  this.exitErrors();
		}else  this.exitErrors();
	}

	@Override
	public void else_() {
		if (operations.verify("else")){
			if (operations.require("else")){
				this.intermediate.putElse();
				if (operations.require("{")){
					this.body();
					if (!operations.require("}")){
						this.exitErrors();
					}
				}else  this.exitErrors();
			}else  this.exitErrors();
		}else  this.exitErrors();
	}

	@Override
	public void while_() {
		if (operations.require("while")){
			this.intermediate.putWhile();
			if (operations.require("(")){
				this.condition();
				if (operations.require(")")){
					if (operations.require("{")){
						this.body();
						if (!operations.require("}")){
							this.exitErrors();
						}
					}else  this.exitErrors();
				}else  this.exitErrors();
			}else  this.exitErrors();
		}else  this.exitErrors();
	}

	@Override
	public void iterate_expression() {
		if (operations.require("iterate")) {
			this.intermediate.putIterate();
			if (operations.require("(")) {
				this.number();
				if (operations.require(")")) {
					if (operations.require("{")) {
						this.body();
						if (!operations.require("}")) {
							this.exitErrors();
						}
					} else this.exitErrors();
				} else this.exitErrors();
			} else this.exitErrors();
		} else this.exitErrors();
	}

	@Override
	public void number() {
		if (!operations.requireType("Integer")){
			operations.getErrors();
		}
	}

	@Override
	public void condition() {
		if (operations.verify("front-is-clear")) {
			this.intermediate.put(9000);
			operations.advance();
		}
		else if (operations.verify("left-is-clear")) {
			this.intermediate.put(10000);
			operations.advance();
		}
		else if (operations.verify("right-is-clear")) {
			this.intermediate.put(11000);
			operations.advance();

		}
		else if (operations.verify("front-is-blocked")) {
			this.intermediate.put(12000);
			operations.advance();
		}
		else if (operations.verify("left-is-blocked")) {
			this.intermediate.put(13000);
			operations.advance();
		}
		else if (operations.verify("right-is-blocked")) {
			this.intermediate.put(14000);
			operations.advance();
		}
		else if (operations.verify("next-to-a-beeper")) {
			this.intermediate.put(15000);
			operations.advance();
		}
		else if (operations.verify("facing-north")) {
			this.intermediate.put(16000);
			operations.advance();
		}
		else if (operations.verify("facing-south")) {
			this.intermediate.put(17000);
			operations.advance();
		}
		else if (operations.verify("facing-east")) {
			this.intermediate.put(18000);
			operations.advance();
		}
		else if (operations.verify("facing-west")) {
			this.intermediate.put(19000);
			operations.advance();
		}
		else if (operations.verify("not-facing-north")) {
			this.intermediate.put(20000);
			operations.advance();
		}
		else if (operations.verify("not-facing-south")) {
			this.intermediate.put(21000);
			operations.advance();
		}
		else if (operations.verify("not-facing-east")) {
			this.intermediate.put(22000);
			operations.advance();
		}
		else if (operations.verify("not-facing-west")) {
			operations.advance();
			this.intermediate.put(23000);
		}
		else if (operations.verify("any-beepers-in-beeper-bag")) {
			operations.advance();
			this.intermediate.put(24000);
		}
		else if (operations.verify("no-beepers-in-beeper-bag")) {
			this.intermediate.put(25000);
			operations.advance();
		}else{
			operations.addSpecificError(" instead of any Karel condition");
			this.exitErrors();
		}

	}

	@Override
	public boolean official_function() {
		if (operations.verify("move") || operations.verify("turnLeft") || operations.verify("pickBeeper") || operations.verify("end") ) {
			switch (this.operations.checkToken()){
				case "move":
					this.intermediate.put(1000);
				case "turnLeft":
					this.intermediate.put(2000);
				case "pickbeeper":
					this.intermediate.put(3000);
				case "end":
					this.intermediate.put(4000);
			}
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void customer_function() {
		if(this.operations.checkLenght(2, 11)){
			this.operations.extraWarning("Custom function found: ");
			this.intermediate.putcustomFunction(this.operations.checkToken());
			this.operations.advance();
		}else{
			this.operations.addSpecificError("Custom function too long");
			this.exitErrors();
		}

	}

}
