package storyUtil;

public class DialogNode {

	public String text;
	public Integer[] nextNodes;

	public DialogNode(String text) {
		this.text = text;
	}
	public DialogNode(String text, Integer[] nextNodes) {
		this.text = text;
		this.nextNodes = nextNodes;
	}
	
}
