package storyutil;

import java.util.Objects;

class DialogNode {

    final String text;
    private final Integer[] nextNodes;
    private Runnable action;

    DialogNode(String text, Integer[] nextNodes, Runnable action) {
        this.text = Objects.requireNonNull(text);
        this.nextNodes = nextNodes;
        this.action = action;
    }

    Integer[] getNextNodes() {
        return nextNodes != null ? nextNodes.clone() : null;
    }

    Runnable getAction() {
        return this.action;
    }

    void setAction(Runnable action) {
        if (this.action != null)
            throw new IllegalStateException("The action linked with the text " + text + " cannot be reassigned");
        this.action = action;
    }
}
