package synt;

import resources.TerminalColors;

import java.util.LinkedList;

public class SyntacticKarel implements KarelLang {
	private SyntacticOperations operations;
	private LinkedList<Integer> intermediateCode= new LinkedList<Integer>();

	public SyntacticKarel(String[] tokens){
		this.operations = new SyntacticOperations(tokens);

	}

	public boolean analyze(){
		System.out.println(TerminalColors.ANSI_GREEN+"Starting Syntactic Analyzer..."+TerminalColors.ANSI_RESET);
		this.program();
		System.out.println("Succesful!");
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
		if (!operations.verify("e}")){
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
			operations.advance();
		}
		else if (operations.verify("left-is-clear")) {
			operations.advance();
		}
		else if (operations.verify("right-is-clear")) {
			operations.advance();
		}
		else if (operations.verify("front-is-blocked")) {
			operations.advance();
		}
		else if (operations.verify("left-is-blocked")) {
			operations.advance();
		}
		else if (operations.verify("right-is-blocked")) {
			operations.advance();
		}
		else if (operations.verify("next-to-a-beeper")) {
			operations.advance();
		}
		else if (operations.verify("facing-north")) {
			operations.advance();
		}
		else if (operations.verify("facing-south")) {
			operations.advance();
		}
		else if (operations.verify("facing-east")) {
			operations.advance();
		}
		else if (operations.verify("facing-west")) {
			operations.advance();
		}
		else if (operations.verify("not-facing-north")) {
			operations.advance();
		}
		else if (operations.verify("not-facing-south")) {
			operations.advance();
		}
		else if (operations.verify("not-facing-east")) {
			operations.advance();
		}
		else if (operations.verify("not-facing-west")) {
			operations.advance();
		}
		else if (operations.verify("any-beepers-in-beeper-bag")) {
			operations.advance();
		}
		else if (operations.verify("no-beepers-in-beeper-bag")) {
			operations.advance();
		}else{
			operations.addSpecificError(" instead of any Karel condition");
			this.exitErrors();
		}

	}

	@Override
	public boolean official_function() {
		if (operations.verify("move") || operations.verify("turnLeft") || operations.verify("pickBeeper") || operations.verify("end") ) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void customer_function() {
		if(this.operations.checkLenght(2, 11)){
			this.operations.extraWarning("Custom function found: ");
			this.operations.advance();
		}else{
			this.operations.addSpecificError("Custom function too long");
			this.exitErrors();
		}

	}

}
