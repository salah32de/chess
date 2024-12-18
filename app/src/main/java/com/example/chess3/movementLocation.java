package com.example.chess3;

public class movementLocation {
    private int row;
    private int col;

    public movementLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public movementLocation(){}
    public boolean equal(movementLocation id){
        if (row==id.getRow()&& col==id.getCol())
            return true;
        return false;
    }
    public movementLocation getId(){
        return new movementLocation(row,col);
    }
    public void setId(movementLocation id){
        this.row=id.getRow();
        this.col=id.getCol();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }


}
