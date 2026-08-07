package storyUtil;

class DialogNode {

	public final String text;
	public final Integer[] nextNodes;

	public DialogNode(String text) {
		this.text = text;
		this.nextNodes = null;
	}

	public DialogNode(String text, Integer[] nextNodes) {
		this.text = text;
		this.nextNodes = nextNodes;
	}

}
