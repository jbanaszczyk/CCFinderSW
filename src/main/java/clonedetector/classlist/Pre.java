package clonedetector.classlist;

/**
 * for CCFXprep
 * use in PreProcess.java
 */
public class Pre {
    /**
     * starting line
     */
    public int lineStart;
    /**
     * start column
     */
    public int clmStart;
    /**
     * end line
     */
    public int lineEnd;
    /**
     * end column
     */
    public int clmEnd;
    /**
     * starting number of characters in the file
     */
    public int sumStart;
    /**
     * number of ending characters in the file
     */
    public int sumEnd;
    /**
     * Token type defined in TokenName
     */
    public int type;
    /**
     * Actual string
     */
    public String token;

    public Pre(String str, int lineS, int clmS,
               int lineE, int clmE, int type, int sumStart, int sumEnd) {
        this.lineStart = lineS;
        this.clmStart = clmS;
        this.sumStart = sumStart;
        this.lineEnd = lineE;
        this.clmEnd = clmE;
        this.sumEnd = sumEnd;
        this.type = type;
        this.token = str;
    }


    @Override
    public String toString() {
        return "Pre [line=" + lineStart + ", clm=" + clmStart + ", sum=" + sumStart +
                ", line=" + lineEnd + ", clm=" + clmEnd + ", sum=" + sumEnd
                + ", type=" + type + ", hash=" + token + "]";
    }

}
