package com.chaupha.rainbowchess;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Random;

public class Action {

    private Context context;
    public int sourceX;
    public int sourceY;
    public int targetX;
    public int targetY;
    public int cardID;
    private ArrayList<Board.Position> pieceDeleteds = new ArrayList<>();


    public Action(Context context) {
        this.context = context;
    }


    public String codeAction(){
        return sourceX +"#"+ sourceY +"#"+ targetX +"#"+ targetY+"#"+cardID;
    }


    public void decodeAction(String codeAction) {
        int indexCopy = 0;
        int index = codeAction.indexOf('#', indexCopy);
        sourceX = Integer.parseInt(codeAction.substring(indexCopy,index));

        indexCopy = index+1;
        index = codeAction.indexOf('#',indexCopy);
        sourceY = Integer.parseInt(codeAction.substring(indexCopy, index));

        indexCopy = index+1;
        index = codeAction.indexOf('#',indexCopy);
        targetX = Integer.parseInt(codeAction.substring(indexCopy, index));

        indexCopy = index+1;
        index = codeAction.indexOf('#', indexCopy);
        targetY = Integer.parseInt(codeAction.substring(indexCopy, index));

        indexCopy = index+1;
        cardID = Integer.parseInt(codeAction.substring(indexCopy));
    }


    public int createCard() {
        Random random = new Random();
        if(GameActivity.turn.equals(GameActivity.blueID)) {
            int num = random.nextInt(GameActivity.blueDesc.length);
            cardID = GameActivity.blueDesc[num];
        }
        else {
            int num = random.nextInt(GameActivity.redDesc.length);
            cardID = GameActivity.redDesc[num];
        }

        return getCardResource();
    }


    public int getCardResource() {
        int source = R.drawable.image_player_default;
        switch (cardID) {
            case 1: {source = R.drawable.card001; break;}
            case 2: {source = R.drawable.card002; break;}
            case 3: {source = R.drawable.card003; break;}
            case 4: {source = R.drawable.card004; break;}
            case 5: {source = R.drawable.card005; break;}
            case 6: {source = R.drawable.card006; break;}
            case 7: {source = R.drawable.card007; break;}
            case 8: {source = R.drawable.card008; break;}
            case 9: {source = R.drawable.card009; break;}
            case 10: {source = R.drawable.card010; break;}
            case 11: {source = R.drawable.card011; break;}
            case 12: {source = R.drawable.card012; break;}
            case 13: {source = R.drawable.card013; break;}
            case 14: {source = R.drawable.card014; break;}
            case 15: {source = R.drawable.card015; break;}
            case 16: {source = R.drawable.card016; break;}
            case 17: {source = R.drawable.card017; break;}
            case 18: {source = R.drawable.card018; break;}
            case 19: {source = R.drawable.card019; break;}
            case 20: {source = R.drawable.card020; break;}
            case 21: {source = R.drawable.card021; break;}
            case 22: {source = R.drawable.card022; break;}
            case 23: {source = R.drawable.card023; break;}
            case 24: {source = R.drawable.card024; break;}
            case 25: {source = R.drawable.card025; break;}
            case 26: {source = R.drawable.card026; break;}
            case 27: {source = R.drawable.card027; break;}
        }
        return source;
    }


    public void playAction() {
        if((GameActivity.turn.equals(GameActivity.blueID)) && (targetX==0)) {
            MediaPlayer.create(context, R.raw.sound_up_level).start();
            GameActivity.board.items[targetX][targetY] = Board.itemType.BLUESTAR;
            GameActivity.board.items[Board.SIZE-1][targetY] = Board.itemType.BLUESTAR;
            if(sourceX != Board.SIZE-1)
                GameActivity.board.items[sourceX][sourceY] = Board.itemType.EMPTY;
            GameActivity.board.blueStarCount += 2;
        }
        else if((GameActivity.turn.equals(GameActivity.redID)) && (targetX==Board.SIZE-1)) {
            MediaPlayer.create(context, R.raw.sound_up_level).start();
            GameActivity.board.items[targetX][targetY] = Board.itemType.REDSTAR;
            GameActivity.board.items[0][targetY] = Board.itemType.REDSTAR;
            if(sourceX != 0)
                GameActivity.board.items[sourceX][sourceY] = Board.itemType.EMPTY;
            GameActivity.board.redStarCount += 2;
        }
        else {
            GameActivity.board.items[targetX][targetY] = GameActivity.board.items[sourceX][sourceY];
            GameActivity.board.items[sourceX][sourceY] = Board.itemType.EMPTY;
        }

        pieceDeleteds.clear();
        switch(cardID) {
            case 9: {
                performCardEffect(1);
                performCardEffect(2);
                break;
            }

            case 10: {
                performCardEffect(3);
                performCardEffect(4);
                break;
            }

            case 11: {
                performCardEffect(5);
                performCardEffect(7);
                performCardEffect(2);
                break;
            }

            case 20: {
                performCardEffect(12);
                performCardEffect(13);
                break;
            }

            case 21: {
                performCardEffect(14);
                performCardEffect(15);
                break;
            }

            case 27: {
                performCardEffect(13);
                performCardEffect(22);
                performCardEffect(24);
                break;
            }

            default: performCardEffect(cardID);
        }
        if(pieceDeleteds.size() > 0) {
            GameActivity.board.removePieces(pieceDeleteds);
        }

        GameActivity.pieceAdapter.notifyDataSetChanged();
    }


    public void performCardEffect(int card) {
        Board.itemType type;
        if (GameActivity.turn.equals(GameActivity.blueID)) {
            type = Board.itemType.RED;
        } else {
            type = Board.itemType.BLUE;
        }

        switch(card) {
            case 1: {
                if (GameActivity.board.getItem(targetX - 1, targetY) == type) {
                    pieceDeleteds.add(new Board.Position(targetX - 1, targetY));
                }
                if (GameActivity.board.getItem(targetX + 1, targetY) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 1, targetY));
                }
                break;
            }

            case 2: {
                if (GameActivity.board.getItem(targetX, targetY - 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX, targetY - 1));
                }
                if (GameActivity.board.getItem(targetX, targetY + 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX, targetY + 1));
                }
                break;
            }

            case 3: {
                if (GameActivity.board.getItem(targetX - 1, targetY - 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX - 1, targetY - 1));
                }
                if (GameActivity.board.getItem(targetX + 1, targetY + 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 1, targetY + 1));
                }
                break;
            }

            case 4: {
                if (GameActivity.board.getItem(targetX + 1, targetY - 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 1, targetY - 1));
                }
                if (GameActivity.board.getItem(targetX - 1, targetY + 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX - 1, targetY + 1));
                }
                break;
            }

            case 5: {
                for (int x = targetX - 1, y = targetY - 1; y <= targetY + 1; y++) {
                    if (GameActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new Board.Position(x, y));
                    }
                }
                break;
            }

            case 6: {
                for (int x = targetX - 1, y = targetY + 1; x <= targetX + 1; x++) {
                    if (GameActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new Board.Position(x, y));
                    }
                }
                break;
            }

            case 7: {
                for (int x = targetX + 1, y = targetY - 1; y <= targetY + 1; y++) {
                    if (GameActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new Board.Position(x, y));
                    }
                }
                break;
            }

            case 8: {
                for (int x = targetX - 1, y = targetY - 1; x <= targetX + 1; x++) {
                    if (GameActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new Board.Position(x, y));
                    }
                }
                break;
            }

            case 12: {
                if (GameActivity.board.getItem(targetX - 2, targetY) == type) {
                    pieceDeleteds.add(new Board.Position(targetX - 2, targetY));
                }
                if (GameActivity.board.getItem(targetX - 1, targetY) == type) {
                    pieceDeleteds.add(new Board.Position(targetX - 1, targetY));
                }
                if (GameActivity.board.getItem(targetX + 1, targetY) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 1, targetY));
                }
                if (GameActivity.board.getItem(targetX + 2, targetY) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 2, targetY));
                }
                break;
            }

            case 13: {
                if (GameActivity.board.getItem(targetX, targetY - 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX, targetY - 2));
                }
                if (GameActivity.board.getItem(targetX, targetY - 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX, targetY - 1));
                }
                if (GameActivity.board.getItem(targetX, targetY + 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX, targetY + 1));
                }
                if (GameActivity.board.getItem(targetX, targetY + 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX, targetY + 2));
                }
                break;
            }

            case 14: {
                if (GameActivity.board.getItem(targetX - 2, targetY - 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX - 2, targetY - 2));
                }
                if (GameActivity.board.getItem(targetX - 1, targetY - 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX - 1, targetY - 1));
                }
                if (GameActivity.board.getItem(targetX + 1, targetY + 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 1, targetY + 1));
                }
                if (GameActivity.board.getItem(targetX + 2, targetY + 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 2, targetY + 2));
                }
                break;
            }

            case 15: {
                if (GameActivity.board.getItem(targetX + 2, targetY - 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 2, targetY - 2));
                }
                if (GameActivity.board.getItem(targetX + 1, targetY - 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 1, targetY - 1));
                }
                if (GameActivity.board.getItem(targetX - 1, targetY + 1) == type) {
                    pieceDeleteds.add(new Board.Position(targetX - 1, targetY + 1));
                }
                if (GameActivity.board.getItem(targetX - 2, targetY + 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX -2, targetY + 2));
                }
                break;
            }

            case 16: {
                for (int x = targetX - 2, y = targetY - 2; y <= targetY + 2; y++) {
                    if (GameActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new Board.Position(x, y));
                    }
                }
                break;
            }

            case 17: {
                for (int x = targetX - 2, y = targetY + 2; x <= targetX + 2; x++) {
                    if (GameActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new Board.Position(x, y));
                    }
                }
                break;
            }

            case 18: {
                for (int x = targetX + 2, y = targetY - 2; y <= targetY + 2; y++) {
                    if (GameActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new Board.Position(x, y));
                    }
                }
                break;
            }

            case 19: {
                for (int x = targetX - 2, y = targetY - 2; x <= targetX + 2; x++) {
                    if (GameActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new Board.Position(x, y));
                    }
                }
                break;
            }

            case 22: {
                for (int x = targetX - 2; x <= targetX - 1; x++) {
                    for (int y = targetY - 2; y <= targetY + 2; y++) {
                        if (GameActivity.board.getItem(x, y) == type) {
                            pieceDeleteds.add(new Board.Position(x, y));
                        }
                    }
                }
                break;
            }

            case 23: {
                for (int y = targetY + 1; y <= targetY + 2; y++) {
                    for (int x = targetX - 2; x <= targetX + 2; x++) {
                        if (GameActivity.board.getItem(x, y) == type) {
                            pieceDeleteds.add(new Board.Position(x, y));
                        }
                    }
                }
                break;
            }

            case 24: {
                for (int x = targetX + 1; x <= targetX + 2; x++) {
                    for (int y = targetY - 2; y <= targetY + 2; y++) {
                        if (GameActivity.board.getItem(x, y) == type) {
                            pieceDeleteds.add(new Board.Position(x, y));
                        }
                    }
                }
                break;
            }

            case 25: {
                for (int y = targetY - 2; y <= targetY - 1; y++) {
                    for (int x = targetX - 2; x <= targetX + 2; x++) {
                        if (GameActivity.board.getItem(x, y) == type) {
                            pieceDeleteds.add(new Board.Position(x, y));
                        }
                    }
                }
                break;
            }

            case 26: {
                if (GameActivity.board.getItem(targetX - 1, targetY - 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX - 1, targetY - 2));
                }
                if (GameActivity.board.getItem(targetX - 1, targetY + 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX - 1, targetY + 2));
                }
                if (GameActivity.board.getItem(targetX, targetY - 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX, targetY - 2));
                }
                if (GameActivity.board.getItem(targetX, targetY + 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX, targetY + 2));
                }
                if (GameActivity.board.getItem(targetX + 1, targetY - 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 1, targetY - 2));
                }
                if (GameActivity.board.getItem(targetX + 1, targetY + 2) == type) {
                    pieceDeleteds.add(new Board.Position(targetX + 1, targetY + 2));
                }
                for (int x = targetX - 2, y = targetY - 2; y <= targetY + 2; y++) {
                    if (GameActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new Board.Position(x, y));
                    }
                }
                for (int x = targetX + 2, y = targetY - 2; y <= targetY + 2; y++) {
                    if (GameActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new Board.Position(x, y));
                    }
                }
                break;
            }
        }
    }
}
