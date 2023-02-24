package gemx.utility;

import gemx.ccfinderx.CCFinderX;

public class Picosel {
    String[] expressions;
    private String tableFile;
    private String outputFile;
    private String column;

    public String setCommandLine(
            String tableFile,
            String outputFile,
            String column,
            String[] expressions) {
        this.tableFile = tableFile;
        this.outputFile = outputFile;
        this.column = column;
        this.expressions = expressions;

        StringBuffer buf = new StringBuffer();
        buf.append(String.format("picosel -o %s from %s select %s where ", outputFile, tableFile, column)); //$NON-NLS-1$
        for (int i = 0; i < expressions.length; ++i) {
            buf.append(expressions[i]);
            buf.append(" "); //$NON-NLS-1$
        }

        return buf.toString();
    }

    public int invokePicosel() {
        return CCFinderX.invokePicosel(tableFile, outputFile, column, expressions);
    }
}
