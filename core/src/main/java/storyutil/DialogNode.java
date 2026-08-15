package storyutil;

class DialogNode {

	final String text;
	private final Integer[] nextNodes;
	private Runnable action;

	DialogNode(String text) {
		this.text = text;
		this.nextNodes = null;
		this.action = null;
	}

	DialogNode(String text, Integer[] nextNodes) {
		this.text = text;
		this.nextNodes = nextNodes;
		this.action = null;
	}

	DialogNode(String text, Integer[] nextNodes, Runnable action) {
		this.text = text;
		this.nextNodes = nextNodes;
		this.action = action;
	}

	Integer[] getNextNodes() {
		return nextNodes != null ? nextNodes.clone() : null;
	}

	void setAction(Runnable action) {
		this.action = action;
	}

	Runnable getAction() {
		return action;
	}
}
