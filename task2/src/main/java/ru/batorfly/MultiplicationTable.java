package ru.batorfly;

public class MultiplicationTable {
    private static final String VERTICAL_DELIMITER = "|";
    private static final String HORIZONTAL_DELIMITER = "-";
    private static final String CROSS_DELIMITER = "+";
    private static final String SPACE = " ";
    private static final String LS = System.lineSeparator();

    private final int tableSize;
    private final int maxCellLen;
    private final int maxCellLenFirstCol;
    private final String firstSpace;
    private final String dividerLine;
    private final int lineLenWithDividerLine;
    private final String firstColCellFormat;

    private final String cellFormat;

    public MultiplicationTable(Integer tableSize) {
        this.tableSize = tableSize;

        this.maxCellLen = String.valueOf(tableSize * tableSize).length();
        this.maxCellLenFirstCol = String.valueOf(tableSize).length();

        this.firstSpace = SPACE.repeat(maxCellLenFirstCol);
        this.dividerLine = getDividerLine();

        this.firstColCellFormat = "%" + (maxCellLenFirstCol > 1 ? maxCellLenFirstCol + "d" : "d");
        this.cellFormat = "%" + maxCellLen + "d";

        this.lineLenWithDividerLine =
                maxCellLenFirstCol
                + maxCellLen * tableSize // length of cells
                + tableSize // length of the VERTICAL_DELIMITER
                + dividerLine.length() // length of the divider line
                + LS.length() * 2; // LS after cells + LS after divider line
    }

    public void printTable(){
        IOUtils.print(getFirsLine());
        for (var i = 0; i < tableSize + 1; i++) {
            IOUtils.print(getNumbersLine(i));
        }
    }

    private String getDividerLine() {
        String underNumber = HORIZONTAL_DELIMITER.repeat(maxCellLen);

        return HORIZONTAL_DELIMITER.repeat(maxCellLenFirstCol)
                + CROSS_DELIMITER
                + (underNumber + CROSS_DELIMITER).repeat(tableSize - 1)
                + underNumber;
    }

    private String getFirsLine(){
        StringBuilder line = new StringBuilder(lineLenWithDividerLine);

        line.append(firstSpace);
        return buildEndOfLine(line, 1);
    }

    private String getNumbersLine(int rowNumber) {
        StringBuilder line = new StringBuilder();

        line.append(String.format(firstColCellFormat, rowNumber));
        return buildEndOfLine(line, rowNumber);
    }

    private String buildEndOfLine(StringBuilder line, int firsValue){
        for (int i = 0; i < tableSize + 1; i++) {
            line.append(VERTICAL_DELIMITER);
            line.append(String.format(cellFormat, firsValue * i));
        }
        line.append(LS).append(dividerLine).append(LS);
        return line.toString();
    }


}
