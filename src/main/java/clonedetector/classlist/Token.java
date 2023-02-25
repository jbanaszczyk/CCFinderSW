package clonedetector.classlist;

public class Token {
    /**
     * character hash
     */
    public int hash;
    /**
     * Token type, common.TokenName.java
     */
    public int type;
    /**
     * starting line
     */
    public int lineStart;
    /**
     * end line
     */
    public int lineEnd;
    /**
     * start column
     */
    public int columnStart;
    /**
     * end column
     */
    public int columnEnd;

    public Token(String str, int lineS, int clmS, int lineE, int clmE, int type) {
        this.hash = str.hashCode();
        this.lineStart = lineS;
        this.lineEnd = lineE;
        this.columnStart = clmS;
        this.columnEnd = clmE;
        this.type = type;
    }

    public Token(Pre pre) {
        this.hash = pre.token.hashCode();
        this.lineStart = pre.lineStart;
        this.lineEnd = pre.lineEnd;
        this.columnStart = pre.clmStart;
        this.columnEnd = pre.clmEnd;
        this.type = pre.type;
    }

    @Override
    public String toString() {
        return "Token [hash=" + hash + ", type=" + type + ", lineStart="
                + lineStart + ", columnStart=" + columnStart + ", lineEnd=" + lineEnd + ", columnEnd=" + columnEnd + "]";
    }

}

