package data;

import java.io.Serializable;

/**
 * @author nilstes
 */
public class Move implements Serializable {
    private int row, column;

    public Move() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Move{" + "row=" + row + ", column=" + column + '}';
    }
}
