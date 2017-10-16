package lex;


import java.util.LinkedList;
import java.util.List;

public class LexicAutomata implements LAutomata{
	private int currentId;
	private List<AutomataNode> listNodes;
	private AutomataNode startNode;
	
	
	public LexicAutomata() {
		this.currentId = 1;
		this.startNode = new AutomataNode(false, 0);	
		this.listNodes = new LinkedList<AutomataNode>();
		this.listNodes.add(this.startNode);
	}

	
	public void createStates(int x){
		for (int i = 0; i < x-1; i++) {
			this.createSingleState();
		}	
	}
	
	public void createSingleState(){
		this.listNodes.add(new AutomataNode(false, this.currentId));
		this.currentId++;
	}
	
	public void createFinalNode(){
		this.listNodes.add(new AutomataNode(true,this.currentId));
		this.currentId++;
	}
	
	public void setStateFlow(char key, int idFrom, int idTo){
		this.listNodes.get(idFrom).addPointer(key, this.listNodes.get(idTo));
	}
	
	public void setLoop(char key, int id){
		this.setStateFlow(key, id, id);
	}

	@Override
	public boolean verifyToken(String Token) {
		// TODO Auto-generated method stub
		AutomataNode pointer = this.startNode;
		for (int i = 0; i < Token.length(); i++) {
			if(pointer.getNext(Token.charAt(i))!=null) {
				pointer = pointer.getNext(Token.charAt(i));
			}else{
				return false;
			}
		}
		return true;
	}


}
