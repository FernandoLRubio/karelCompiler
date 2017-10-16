package lex;


import java.util.HashMap;

public class AutomataNode {
	private boolean endNode;
	private int id;
	private HashMap<Character, AutomataNode> nextNodes;
	
	public AutomataNode(boolean endNode, int id){
		this.id = id;
		this.endNode = endNode;
		this.nextNodes = new HashMap<Character,AutomataNode>();
	}
	
	public void addPointer(char key, AutomataNode nextNode){
		this.nextNodes.put(key, nextNode);
	}
	
	public AutomataNode getNext(char key){
		/**Returns nextNode depending on the given character, if there's no nextNode returns null**/
		return this.nextNodes.get(key);
	}
	
	public boolean isFinal(){
		return this.endNode;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String toString(){
		String toString = "Node "+this.id+", "+"Final: "+this.endNode;
		return toString;
	}

}
