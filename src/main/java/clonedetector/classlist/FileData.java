package clonedetector.classlist;

import java.util.ArrayList;

public class FileData {
    /**
     * the path of the parent directory
     */
    public String directoryName;// File Path
    /**
     * absolute path of the parent directory
     */
    public String directoryNameAbsolute;
    /**
     * The total number of lines in all files
     */
    public int lineCount = 0;
    public int tokenCount = 0;


    /**
     * The number of lines in each file is stored in order
     */
    public int[] lineCountList;
    /**
     * The number of tokens for each file is stored in order
     */
    public int[] tokenCountList;
    /**
     * The number of tokens for each file is stored in order
     */
    public int[] tokenIndexList;

    /**
     * Contains the path of each file
     */
    public ArrayList<String> filePathList;
    /**
     * Contains the absolute path of each file
     */
    public ArrayList<String> fileNameList;


    /**
     * Ngram index of each file is stored
     */
    public int[] NGramIndexList;
    /**
     * Contains the number of Ngrams for each file
     */
    public int[] NGramCountList;
}
