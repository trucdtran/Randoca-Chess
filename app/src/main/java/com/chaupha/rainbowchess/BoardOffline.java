package com.chaupha.rainbowchess;

import android.util.Log;

import java.util.ArrayList;

public class BoardOffline {

    public static final int SIZE = 9;
    private static final int MAXIMUM_PIECES_OF_PLAYER = SIZE*4;
    public itemType[][] items = new itemType[SIZE][SIZE];
    private boolean[][] itemHovers = new boolean[SIZE][SIZE];

    public enum itemType {INVALID, EMPTY, RED, REDSTAR, BLUE, BLUESTAR};

    public int redPieceCount = MAXIMUM_PIECES_OF_PLAYER;
    public int bluePieceCount = MAXIMUM_PIECES_OF_PLAYER;
    public int blueStarCount = 0;
    public int redStarCount = 0;

    public ArrayList<Position> pieceClickable = new ArrayList<>();
    public ArrayList<Position> itemClickable = new ArrayList<>();


    public BoardOffline() {
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


    public void updatePieceClickable() {
        pieceClickable.clear();
        if(GameOfflineActivity.turn.equals(GameOfflineActivity.blueID)) {
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

        if(!GameOfflineActivity.pieceSelected) {
            for(Position position : pieceClickable) {
                itemHovers[position.row][position.col] = true;
            }
        }
        else {
            for (Position position : itemClickable) {
                itemHovers[position.row][position.col] = true;
            }
        }
        GameOfflineActivity.pieceAdapter.notifyDataSetChanged();
    }


    public void resetBoard() {
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                itemHovers[i][j] = false;
            }
        }
        GameOfflineActivity.pieceAdapter.notifyDataSetChanged();
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
        if(GameOfflineActivity.turn.equals(GameOfflineActivity.blueID)) {
            for(Position position : pieceDeleteds) {
                items[position.row][position.col] = itemType.EMPTY;
                redPieceCount = redPieceCount - 1;
            }
        }
        else {
            for(Position position : pieceDeleteds) {
                items[position.row][position.col] = itemType.EMPTY;
                bluePieceCount = bluePieceCount - 1;
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

