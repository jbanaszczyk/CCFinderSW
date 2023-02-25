package clonedetector.classlist;

public class TokenData {
    // hash value
    public int hash;
    // file number
    public int file;
    // order in the file
    public int num;
    // beginning of line
    public int lineStart;
    // end of line
    public int lineEnd;
    // Column beginning
    public int columnStart;
    // end of column
    public int columnEnd;

    public TokenData(int hash, int file, int num, int lineStart, int lineEnd, int columnStart, int columnEnd) {
        this.hash = hash;
        this.file = file;
        this.num = num;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.columnStart = columnStart;
        this.columnEnd = columnEnd;
    }

    public TokenData(Token token, int hash, int file, int num) {
        this.hash = hash;
        this.lineStart = token.lineStart;
        this.lineEnd = token.lineEnd;
        this.columnStart = token.columnStart;
        this.columnEnd = token.columnEnd;
        this.file = file;
        this.num = num;
    }

    @Override
    public String toString() {
        return "TokenData [hash=" + hash + ", file=" + file + ", num=" + num + ", lineStart="
                + lineStart + ", lineEnd=" + lineEnd + ", columnStart=" + columnStart + ", columnEnd=" + columnEnd + "]";
    }
}
