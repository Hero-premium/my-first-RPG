package io.github.hero_premium.storyutil;

import java.util.Objects;
import java.util.Optional;

class DialogNode {

    final String text;
    private final int[] nextNodes;
    private Runnable action;

    DialogNode(String text, int[] nextNodes, Runnable action) {
        this.text = Objects.requireNonNull(text);
        this.nextNodes = nextNodes;
        this.action = action;
    }

    int[] getNextNodes() {
        return nextNodes != null ? nextNodes.clone() : null;
    }

    Optional<Runnable> getAction() {
        return Optional.ofNullable(action);
    }

    void setAction(Runnable action) {
        if (this.action != null)
            throw new IllegalStateException("The action linked with the text " + text + " cannot be reassigned");
        this.action = Objects.requireNonNull(action, "action cannot be null");
    }
}
