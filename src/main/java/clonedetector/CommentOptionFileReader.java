package clonedetector;

import clonedetector.classlist.CommentRule;
import clonedetector.classlist.LangRuleConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static common.TokenName.*;

/**
 * Class that reads comment files
 */
public class CommentOptionFileReader {

    private final String filename;
    String language;
    OptionReader or;

    public CommentOptionFileReader(String language, OptionReader or) {
        this.language = language;
        this.filename = "comment" + File.separator + language + "_comment.txt";
        this.or = or;
    }

    public void run(String dirPath, LangRuleConstructor rule) {
        rule.commentFilePath = dirPath + File.separator + filename;
        readFile(dirPath + File.separator + filename, rule);
    }

    public void readFile(String inputFileName, LangRuleConstructor rule) {
        File file = new File(inputFileName);
        //System.out.println(inputFileName);
        if (!file.exists()) {
            System.out.println("No CommentRuleList");
            rule.comment = false;
            return;
        } else {
            System.out.println("Load CommentRuleList: " + inputFileName);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String str;
            while ((str = br.readLine()) != null) { // does not contain line breaks
                boolean notCommentOut = true;
                if (str.charAt(0) == '#' || str.charAt(0) == '%') {
                    if (str.charAt(0) == '%') {
                        notCommentOut = false;
                    }

                    if (str.startsWith("start", 1) && str.length() < 7) {
                        CommentRule x = new CommentRule(START,
                                br.readLine());
                        if (notCommentOut) rule.commentRuleList.add(x);
                    } else if (str.startsWith("prior", 1)
                            || (str.startsWith("literal", 1) && str.length() < 9)) {
                        CommentRule x = new CommentRule(PRIOR,
                                br.readLine(),
                                br.readLine());
                        if (notCommentOut) rule.literalRuleList.add(x);
                    } else if (str.startsWith("startend", 1) && str.length() < 10) {
                        CommentRule x = new CommentRule(START_END,
                                br.readLine(),
                                br.readLine());
                        if (notCommentOut) rule.commentRuleList.add(x);
                    } else if (str.startsWith("extension", 1)) {
                        if (notCommentOut) {
                            String x = br.readLine();
                            or.extensionList.add(x);
                            ArrayList<String> y = new ArrayList<>();
                            y.add(language);
                            y.add(x);
                            or.extensionMap.add(y);
                        }
                    } else if (str.startsWith("linestart", 1) && str.length() < 11) {
                        CommentRule x = new CommentRule(LINE_START,
                                br.readLine());
                        if (notCommentOut) rule.commentRuleList_Line.add(x);
                    } else if (str.startsWith("startendnest", 1)) {
                        CommentRule x = new CommentRule(START_END,
                                br.readLine(),
                                br.readLine(),
                                true);
                        rule.doNest = true;
                        if (notCommentOut) rule.commentRuleList.add(x);
                    } else if (str.startsWith("linestartend", 1)) {
                        CommentRule x = new CommentRule(LINE_START_END,
                                br.readLine(),
                                br.readLine());
                        if (notCommentOut) rule.commentRuleList_Line.add(x);
                    } else if (str.startsWith("linecontinue", 1)) {
                        String x = br.readLine();
                        if (notCommentOut) rule.lineContinue = x;
                    } else if (str.startsWith("variableregex", 1)) {
                        String x = br.readLine();
                        if (notCommentOut) or.setVariableRegex(x);
                    } else if (str.startsWith("literalverbatim", 1)) {
                        CommentRule x = new CommentRule(PRIOR,
                                br.readLine(),
                                br.readLine(),
                                true);
                        if (notCommentOut) rule.literalRuleList.add(x);
                    }
                }
            }
        } catch (StringIndexOutOfBoundsException | IOException e) {
            e.printStackTrace();
            System.err.println("Syntax error in commentfile");
            System.exit(1);
        }
    }

}
