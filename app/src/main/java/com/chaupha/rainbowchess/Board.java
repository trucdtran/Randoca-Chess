package com.chaupha.rainbowchess;

import java.util.ArrayList;

public class Board {

    public static final int SIZE = 9;
    private static final int MAXIMUM_PIECES_OF_PLAYER = SIZE*4;
    public itemType[][] items = new itemType[SIZE][SIZE];
    private boolean[][] itemHovers = new boolean[SIZE][SIZE];

    public enum itemType {INVALID, EMPTY, RED, REDSTAR, BLUE, BLUESTAR};

    public int blueStarCount = 0;
    public int redStarCount = 0;

    public ArrayList<Position> pieceClickable = new ArrayList<>();
    public ArrayList<Position> itemClickable = new ArrayList<>();


    public Board() {
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                if(i<SIZE/2) {
                    items[i][j] = itemType.RED;
                }
                else if(i>SIZE/2) {
                    items[i][j] = itemType.BLUE;
                }
                else {
                    items[i][j] = itemType.EMPTY;
                }

                itemHovers[i][j] = false;
            }
        }
    }


    public itemType getItem(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
            return itemType.INVALID;
        return items[x][y];
    }


    public boolean getItemHover(int x, int y) {
        return itemHovers[x][y];
    }


    public int checkGameFinished() {
        int countPieceBlue = 0;
        int countPieceRed = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (getItem(i, j) == itemType.BLUE) {
                    if(getItem(i+1, j) == itemType.EMPTY || getItem(i-1, j) == itemType.EMPTY ||
                            getItem(i, j+1) == itemType.EMPTY || getItem(i, j-1) == itemType.EMPTY) {
                        countPieceBlue = countPieceBlue + 1;
                    }
                }
                else if(getItem(i, j) == itemType.RED) {
                    if(getItem(i+1, j) == itemType.EMPTY || getItem(i-1, j) == itemType.EMPTY ||
                            getItem(i, j+1) == itemType.EMPTY || getItem(i, j-1) == itemType.EMPTY) {
                        countPieceRed = countPieceRed + 1;
                    }
                }
            }
        }
        if(countPieceBlue == 0)
            return -1;
        if(countPieceRed == 0)
            return 1;
        return 0;
    }


    public void updatePieceClickable(boolean display) {
        pieceClickable.clear();
        if(GameActivity.turn.equals(GameActivity.blueID)) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (getItem(i, j) == itemType.BLUE) {
                        if(getItem(i+1, j) == itemType.EMPTY || getItem(i-1, j) == itemType.EMPTY ||
                        getItem(i, j+1) == itemType.EMPTY || getItem(i, j-1) == itemType.EMPTY) {
                            pieceClickable.add(new Position(i, j));
                        }
                    }
                }
            }
        }

        else {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (items[i][j] == itemType.RED) {
                        if(getItem(i+1, j) == itemType.EMPTY || getItem(i-1, j) == itemType.EMPTY ||
                        getItem(i, j+1) == itemType.EMPTY || getItem(i, j-1) == itemType.EMPTY) {
                            pieceClickable.add(new Position(i, j));
                        }
                    }
                }
            }
        }
        if(display)
            displayItemClickable();
    }


    public void updateItemClickable(int row, int col) {
        itemClickable.clear();
        int i=1;
        while(getItem(row+i, col) == itemType.EMPTY) {
            itemClickable.add(new Position(row+i, col));
            i++;
        }

        i=1;
        while(getItem(row-i, col) == itemType.EMPTY) {
            itemClickable.add(new Position(row-i, col));
            i++;
        }

        i=1;
        while(getItem(row,col+i) == itemType.EMPTY) {
            itemClickable.add(new Position(row, col+i));
            i++;
        }

        i=1;
        while(getItem(row,col-i) == itemType.EMPTY) {
            itemClickable.add(new Position(row, col-i));
            i++;
        }
        displayItemClickable();
    }


    private void displayItemClickable(){
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                itemHovers[i][j] = false;
            }
        }

        if(!GameActivity.pieceSelected) {
            for(Position position : pieceClickable) {
                itemHovers[position.row][position.col] = true;
            }
        }
        else {
            for (Position position : itemClickable) {
                itemHovers[position.row][position.col] = true;
            }
        }
        GameActivity.pieceAdapter.notifyDataSetChanged();
    }


    public void resetBoard() {
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                itemHovers[i][j] = false;
            }
        }
        GameActivity.pieceAdapter.notifyDataSetChanged();
    }


    public boolean isPieceClickable(int row, int col) {
        for(Position position : pieceClickable) {
            if(position.row == row && position.col == col)
                return true;
        }
        return false;
    }


    public boolean isItemClickable(int row, int col) {
        for(Position position : itemClickable) {
            if(position.row == row && position.col == col)
                return true;
        }
        return false;
    }


    public void removePieces(ArrayList<Position> pieceDeleteds) {
        if(GameActivity.turn.equals(GameActivity.blueID)) {
            for(Position position : pieceDeleteds) {
                items[position.row][position.col] = itemType.EMPTY;
            }
        }
        else {
            for(Position position : pieceDeleteds) {
                items[position.row][position.col] = itemType.EMPTY;
            }
        }
    }


    public static class Position {
        int row;
        int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
