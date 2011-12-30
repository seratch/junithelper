package org.junithelper.core.meta;

public enum CurrentLineBreak {

    CRLF("CRLF"), LF("LF");

    private String name;

    private CurrentLineBreak(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
