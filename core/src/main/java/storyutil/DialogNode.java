package storyutil;

class DialogNode {

	final String text;
	private final Integer[] nextNodes;
	private Runnable action;

	DialogNode(String text, Integer[] nextNodes, Runnable action) {
		this.text = text;
		this.nextNodes = nextNodes;
		this.action = action;
	}

	Integer[] getNextNodes() {
		return nextNodes != null ? nextNodes.clone() : null;
	}

	void setAction(Runnable action) {
		if (this.action != null)
			throw new IllegalStateException("the action cannot be reassigned " + this.action.toString());
		this.action = action;
	}

	Runnable getAction() {
		return this.action;
	}
}
