package com.directa24.test.mostactiveauthors.way.too.simple.json.parser.constants;

public enum Constants {
    COLON(':'),
    COMMA(','),
    SPECIAL('|'),
    CURLY_OPEN('{'),
    CURLY_CLOSE('}'),
    SQUARE_OPEN('['),
    SQUARE_CLOSE(']');

    private final char value;

    Constants(char value) {
        this.value = value;
    }

    public char charValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
