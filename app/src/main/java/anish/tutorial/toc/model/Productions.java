package anish.tutorial.toc.model;

public class Productions {
    String symbol, variable;

    String rhsv,lhsv;

    public String getRhsv() {
        return rhsv;
    }

    public void setRhsv(String rhsv) {
        this.rhsv = rhsv;
    }

    public String getLhsv() {
        return lhsv;
    }

    public void setLhsv(String lhsv) {
        this.lhsv = lhsv;
    }

    public Productions() {
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
