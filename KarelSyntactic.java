package synt;// Made by: Andres Ruiz and Fernando Rubio

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class KarelSyntactic {
	private List<Integer> intermediateCode = new ArrayList<Integer>();
	private Stack<Integer> indices = new Stack<Integer>();
	private HashMap<String, Integer> customerFunctions = new HashMap<String, Integer>();
	private SyntacticOperations syntax;

	private int customerLength = 5;

	private int JMP = 997;
	private int CALL = 998;
	private int RET = 999;
	private int MOVE = 1000;
	private int TURNLEFT = 2000;
	private int PICKBEEPER = 3000;
	private int PUTBEEPER = 4000;
	private int END = 5000;
	private int IF = 6000;
	private int ELSE = 7000;
	private int WHILE = 8000;
	private int ITERATE = 9000;
	private int FRONTISCLEAR = 10000;
	private int LEFTISCLEAR = 11000;
	private int RIGHTISCLEAR = 12000;
	private int FRONTISBLOCKED = 13000;
	private int LEFTISBLOCKED = 14000;
	private int RIGHTISBLOCKED = 15000;
	private int NEXTTOABEEPER = 16000;
	private int NOTNEXTTOABEEPER = 17000;
	private int FACINGNORTH = 18000;
	private int FACINGSOUTH = 19000;
	private int FACINGEAST = 20000;
	private int FACINGWEST = 21000;
	private int NOTFACINGNORTH = 22000;
	private int NOTFACINGSOUTH = 23000;
	private int NOTFACINGEAST = 24000;
	private int NOTFACINGWEST = 25000;
	private int ANYBEEPERSINBEEPERBAG = 26000;
	private int NOBEEPERSINBEEPERBAG = 27000;

	public void program() {
		if(syntax.require("class")) {
			if(syntax.require("program")) {
				if(syntax.require("{")) {
					this.intermediateCode.add(JMP);
					this.indices.push(this.intermediateCode.size());
					this.intermediateCode.add(0);
					this.functions();
					this.main_function();
					if(!syntax.require("}")) syntax.ExitErrors();
				} else  syntax.ExitErrors();
			} else  syntax.ExitErrors();
		} else  syntax.ExitErrors();
	}

	public void functions() {
		if(syntax.verify("void")) {
			this.function();
			this.functions_prima();
		}
	}

	public void functions_prima() {
		this.functions();
	}

	public void main_function() {
		if(syntax.require("program")) {
			if(syntax.require("(")) {
				if(syntax.require(")")) {
					if(syntax.require("{")) {
						this.intermediateCode.set(this.indices.pop(), this.intermediateCode.size());
						this.body();
						if(!syntax.require("}")) {
							syntax.ExitErrors();
						}
					} else {
						syntax.ExitErrors();
					}
				} else {
					syntax.ExitErrors();
				}
			} else {
				syntax.ExitErrors();
			}
		} else {
			syntax.ExitErrors();
		}
	}

	public void function() {
		if(syntax.require("void")) {
			this.name_function();
			if(syntax.require("(")) {
				if(syntax.require(")")) {
					if(syntax.require("{")) {
						this.body();
						if(syntax.require("}")) {
							this.intermediateCode.add(RET);
						} else {
							syntax.ExitErrors();
						}
					} else {
						syntax.ExitErrors();
					}
				} else {
					syntax.ExitErrors();
				}
			} else {
				syntax.ExitErrors();
			}
		} else {
			syntax.ExitErrors();
		}
	}

	public void body() {
		this.expression();
		this.body_prima();
	}

	public void body_prima() {
		this.body();
	}

	public void expression() {
		if(syntax.verify("if")) {
			this.if_expression();
		}
		else if(syntax.verify("while")) {
			this.while_();
		}
		else if(syntax.verify("iterate")) {
			this.iterate_expression();
		} else {
			call_function();
		}
	}

	public void call_function() {
		if(this.official_function() | this.customer_function()) {
			if(syntax.require("(")) {
				if(syntax.require(")")) {
				
				} else {
					syntax.ExitErrors();
				}
			} else {
				syntax.ExitErrors();
			}
		} else{
			syntax.ExitErrors();
		}
	}

	public void name_function() {
		if(this.official_function()) {
			syntax.ExitErrors()
			return false;
		} else {
			this.create_customer_function();
		}
	}

	public void if_expression() {
		if(syntax.require("if")) {
			this.intermediateCode.add(IF);
			if(syntax.require("(")) {
				this.condition();
				if(syntax.require(")")) {
					if(syntax.require("{")) {
						this.intermediateCode.add(JMP);
						this.indices.push(this.intermediateCode.size());
						this.intermediateCode.add(0);
						this.body();
						if(syntax.require("}")) {
							this.else_();
						}
					} else {
						syntax.ExitErrors();
					}
				} else {
					syntax.ExitErrors();
				}
			} else {
				syntax.ExitErrors();
			}
		} else {
			syntax.ExitErrors();
		}
	}

	public void else_() {
		if(syntax.verify("else") {
			syntax.require("else");
			this.intermediateCode.add(JMP);
			this.intermediateCode.set(this.indices.pop(), this.intermediateCode.size() + 1);
			this.indices.push(this.intermediateCode.size());
			this.intermediateCode.add(0);
			if(syntax.require("{")) {
				this.body();
				if(syntax.require("}")) {
					this.intermediateCode.set(this.indices.pop(), this.intermediateCode.size());
				} else {
					syntax.ExitErrors();
				}
			} else {
				syntax.ExitErrors();
			}
		} else {
			this.intermediateCode.set(this.indices.pop(), this.intermediateCode.size());
		}
	}

	public void while_() {
		if(syntax.require("while")) {
			this.indices.push(this.intermediateCode.size());
			this.intermediateCode.add(WHILE);
			if(syntax.require("(")) {
				this.condition();
				if(syntax.require(")")) {
					if(syntax.require("{")) {
						this.intermediateCode.add(JMP);
						this.indices.push(this.intermediateCode.size());
						this.intermediateCode.add(0);
						this.body();
						if(syntax.require("}")) {
							this.intermediateCode.add(JMP);
							this.intermediateCode.add(0);
							this.intermediateCode.set(this.indices.pop(), this.intermediateCode.size());
							this.intermediateCode.set(this.intermediateCode.size() - 1, this.indices.pop());
						} else {
							syntax.ExitErrors();
						}
					} else {
						syntax.ExitErrors();
					}
				} else {
					syntax.ExitErrors();
				}
			} else {
				syntax.ExitErrors();
			}
		} else {
			syntax.ExitErrors();
		}
	}

	public void iterate_expression() {
		if(syntax.require("iterate")) {
			this.indices.push(this.intermediateCode.size());
			this.intermediateCode.add(ITERATE);
			if(syntax.require("(")) {
				this.number();
				if(syntax.require(")")) {
					if(syntax.require("{")) {
						this.body();
						if(syntax.require("}")) {
							this.intermediateCode.add(JMP);
							this.intermediateCode.add(this.indices.pop());
						} else {
							syntax.ExitErrors();
						}
					} else {
						syntax.ExitErrors();
					}
				} else {
					syntax.ExitErrors();
				}
			} else {
				syntax.ExitErrors();
			}
		} else {
			syntax.ExitErrors();
		}
	}

	public void number() {
		if(syntax.requireType("integer")) {
			int num = syntax.getInteger();
			this.intermediateCode.add(num);
			return syntax.require(num);
		} else {
			syntax.ExitErrors();
		}
	}

	public void condition() {
		if(syntax.verify("front-is-clear")) {
			this.intermediateCode.add(FRONTISCLEAR);
			return syntax.require("front-is-clear");
		}	else if(syntax.verify("left-is-clear")) {
			this.intermediateCode.add(LEFTISCLEAR);
			return syntax.require("left-is-clear");
		}	else if(syntax.verify("right-is-clear")) {
			this.intermediateCode.add(RIGHTISCLEAR);
			return syntax.require("right-is-clear");
		}	else if(syntax.verify("front-is-blocked")) {
			this.intermediateCode.add(FRONTISBLOCKED);
			return syntax.require("front-is-blocked");
		}	else if(syntax.verify("left-is-blocked")) {
			this.intermediateCode.add(LEFTISBLOCKED);
			return syntax.require("left-is-blocked");
		}	else if(syntax.verify("right-is-blocked")) {
			this.intermediateCode.add(RIGHTISBLOCKED);
			return syntax.require("right-is-blocked");
		}	else if(syntax.verify("next-to-a-beeper")) {
			this.intermediateCode.add(NEXTTOABEEPER);
			return syntax.require("next-to-a-beeper");
		}	else if(syntax.verify("not-next-to-a-beeper")) {
			this.intermediateCode.add(NOTNEXTTOABEEPER);
			return syntax.require("not-next-to-a-beeper");
		}	else if(syntax.verify("facing-north")) {
			this.intermediateCode.add(FACINGNORTH);
			return syntax.require("facing-north");
		}	else if(syntax.verify("facing-south")) {
			this.intermediateCode.add(FACINGSOUTH);
			return syntax.require("facing-south");
		}	else if(syntax.verify("facing-east")) {
			this.intermediateCode.add(FACINGEAST);
			return syntax.require("facing-east");
		}	else if(syntax.verify("facing-west")) {
			this.intermediateCode.add(FACINGWEST);
			return syntax.require("facing-west");
		}	else if(syntax.verify("not-facing-north")) {
			this.intermediateCode.add(NOTFACINGNORTH);
			return syntax.require("not-facing-north");
		}	else if(syntax.verify("not-facing-south")) {
			this.intermediateCode.add(NOTFACINGSOUTH);
			return syntax.require("not-facing-south");
		} else if(syntax.verify("not-facing-east")) {
			this.intermediateCode.add(NOTFACINGEAST);
			return syntax.require("not-facing-east");
		} else if(syntax.verify("not-facing-west")) {
			this.intermediateCode.add(NOTFACINGWEST);
			return syntax.require("not-facing-west");
		} else if(syntax.verify("any-beepers-in-beeper-bag")) {
			this.intermediateCode.add(ANYBEEPERSINBEEPERBAG);
			return syntax.require("any-beepers-in-beeper-bag");
		} else if(syntax.verify("no-beepers-in-beeper-bag")) {
			this.intermediateCode.add(NOBEEPERSINBEEPERBAG);
			return syntax.require("no-beepers-in-beeper-bag");
		} else {
			syntax.ExitErrors();
			return false;
		}
	}

	public void official_function() {
		if(syntax.verify("move")) {
			this.intermediateCode.add(MOVE);
			return syntax.require("move");
		}	else if(syntax.verify("turnLeft")) {
			this.intermediateCode.add(TURNLEFT);
			return syntax.require("turnLeft");
		}	else if(syntax.verify("pickBeeper")) {
			this.intermediateCode.add(PICKBEEPER);
			return syntax.require("pickBeeper");
		}	else if(syntax.verify("putBeeper")) {
			this.intermediateCode.add(PUTBEEPER);
			return syntax.require("putBeeper");
		}	else if(syntax.verify("end")) {
			this.intermediateCode.add(END);
			return syntax.require("end");
		}	else {
			return false
		}
	}

	public void cutomer_function() {
		String customerFn = syntax.getCustomer();
		if(this.customerFunctions.containsKey(customerFn)) {
			this.intermediateCode.add(CALL);
			this.intermediateCode.add(this.customerFunctions.get(customerFn));
			return syntax.require(syntax.getCustomer());
		} else {
			return false;
		}
	}

	public void create_customer_function() {
		if(syntax.requireType("string") && syntax.requireLength(this.customerLength)) {
			String customerFn = syntax.getCustomer();
			customerFunctions.put(customerFn, this.intermediateCode.size();
			return syntax.require(customerFn);
		} else {
			syntax.ExitErrors();
			return false;
		}
	}

	public void exitErrors(){
		System.out.println(this.sintax.getErrors());
		System.exit(-1);
	}
}