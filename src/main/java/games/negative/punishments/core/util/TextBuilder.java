package games.negative.punishments.core.util;

import com.google.common.base.Preconditions;

public class TextBuilder {

    private String text;

    public TextBuilder(String text) {
        Preconditions.checkNotNull(text, "Text cannot be null");

        this.text = text;
    }

    public TextBuilder append(String text) {
        Preconditions.checkNotNull(text, "Text cannot be null");

        this.text += text;
        return this;
    }

    public TextBuilder replace(String placeholder, String replacement) {
        Preconditions.checkNotNull(placeholder, "Placeholder cannot be null");
        Preconditions.checkNotNull(replacement, "Replacement cannot be null");

        this.text = this.text.replace(placeholder, replacement);
        return this;
    }

    public String build() {
        Preconditions.checkNotNull(this.text, "Text cannot be null");

        return this.text;
    }
}
