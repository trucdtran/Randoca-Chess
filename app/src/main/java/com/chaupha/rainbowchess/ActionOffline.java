package com.chaupha.rainbowchess;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class ActionOffline {

    private Context context;
    public int sourceX;
    public int sourceY;
    public int targetX;
    public int targetY;
    public int cardID;
    private ArrayList<BoardOffline.Position> pieceDeleteds = new ArrayList<>();


    public ActionOffline(Context context) {
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
        if(GameOfflineActivity.turn.equals(GameOfflineActivity.blueID)) {
            int num = random.nextInt(GameOfflineActivity.blueDesc.length);
            cardID = GameOfflineActivity.blueDesc[num];
        }
        else {
            int num = random.nextInt(GameOfflineActivity.redDesc.length);
            cardID = GameOfflineActivity.redDesc[num];
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


    public int getPieceDelete() {
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
        return pieceDeleteds.size();
    }


    public void playAction() {
        if((GameOfflineActivity.turn.equals(GameOfflineActivity.blueID)) && (targetX==0)) {
            MediaPlayer.create(context, R.raw.sound_up_level).start();
            GameOfflineActivity.board.items[targetX][targetY] = BoardOffline.itemType.BLUESTAR;
            GameOfflineActivity.board.items[BoardOffline.SIZE-1][targetY] = BoardOffline.itemType.BLUESTAR;
            GameOfflineActivity.board.blueStarCount = GameOfflineActivity.board.blueStarCount + 2;
            if(sourceX != BoardOffline.SIZE-1)
                GameOfflineActivity.board.items[sourceX][sourceY] = BoardOffline.itemType.EMPTY;
            Log.e("BLUE STAR: ", GameOfflineActivity.board.blueStarCount+"");
        }
        else if((GameOfflineActivity.turn.equals(GameOfflineActivity.redID)) && (targetX==BoardOffline.SIZE-1)) {
            MediaPlayer.create(context, R.raw.sound_up_level).start();
            GameOfflineActivity.board.items[targetX][targetY] = BoardOffline.itemType.REDSTAR;
            GameOfflineActivity.board.items[0][targetY] = BoardOffline.itemType.REDSTAR;
            GameOfflineActivity.board.redStarCount = GameOfflineActivity.board.redStarCount + 2;
            if(sourceX != 0)
                GameOfflineActivity.board.items[sourceX][sourceY] = BoardOffline.itemType.EMPTY;
            Log.e("RED STAR: ", GameOfflineActivity.board.redStarCount+"");
        }
        else {
            GameOfflineActivity.board.items[targetX][targetY] = GameOfflineActivity.board.items[sourceX][sourceY];
            GameOfflineActivity.board.items[sourceX][sourceY] = BoardOffline.itemType.EMPTY;
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
            GameOfflineActivity.board.removePieces(pieceDeleteds);
        }

        GameOfflineActivity.pieceAdapter.notifyDataSetChanged();
    }


    public void performCardEffect(int card) {
        BoardOffline.itemType type;
        if (GameOfflineActivity.turn.equals(GameOfflineActivity.blueID)) {
            type = BoardOffline.itemType.RED;
        } else {
            type = BoardOffline.itemType.BLUE;
        }

        switch(card) {
            case 1: {
                if (GameOfflineActivity.board.getItem(targetX - 1, targetY) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX - 1, targetY));
                }
                if (GameOfflineActivity.board.getItem(targetX + 1, targetY) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 1, targetY));
                }
                break;
            }

            case 2: {
                if (GameOfflineActivity.board.getItem(targetX, targetY - 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX, targetY - 1));
                }
                if (GameOfflineActivity.board.getItem(targetX, targetY + 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX, targetY + 1));
                }
                break;
            }

            case 3: {
                if (GameOfflineActivity.board.getItem(targetX - 1, targetY - 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX - 1, targetY - 1));
                }
                if (GameOfflineActivity.board.getItem(targetX + 1, targetY + 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 1, targetY + 1));
                }
                break;
            }

            case 4: {
                if (GameOfflineActivity.board.getItem(targetX + 1, targetY - 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 1, targetY - 1));
                }
                if (GameOfflineActivity.board.getItem(targetX - 1, targetY + 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX - 1, targetY + 1));
                }
                break;
            }

            case 5: {
                for (int x = targetX - 1, y = targetY - 1; y <= targetY + 1; y++) {
                    if (GameOfflineActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new BoardOffline.Position(x, y));
                    }
                }
                break;
            }

            case 6: {
                for (int x = targetX - 1, y = targetY + 1; x <= targetX + 1; x++) {
                    if (GameOfflineActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new BoardOffline.Position(x, y));
                    }
                }
                break;
            }

            case 7: {
                for (int x = targetX + 1, y = targetY - 1; y <= targetY + 1; y++) {
                    if (GameOfflineActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new BoardOffline.Position(x, y));
                    }
                }
                break;
            }

            case 8: {
                for (int x = targetX - 1, y = targetY - 1; x <= targetX + 1; x++) {
                    if (GameOfflineActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new BoardOffline.Position(x, y));
                    }
                }
                break;
            }

            case 12: {
                if (GameOfflineActivity.board.getItem(targetX - 2, targetY) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX - 2, targetY));
                }
                if (GameOfflineActivity.board.getItem(targetX - 1, targetY) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX - 1, targetY));
                }
                if (GameOfflineActivity.board.getItem(targetX + 1, targetY) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 1, targetY));
                }
                if (GameOfflineActivity.board.getItem(targetX + 2, targetY) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 2, targetY));
                }
                break;
            }

            case 13: {
                if (GameOfflineActivity.board.getItem(targetX, targetY - 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX, targetY - 2));
                }
                if (GameOfflineActivity.board.getItem(targetX, targetY - 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX, targetY - 1));
                }
                if (GameOfflineActivity.board.getItem(targetX, targetY + 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX, targetY + 1));
                }
                if (GameOfflineActivity.board.getItem(targetX, targetY + 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX, targetY + 2));
                }
                break;
            }

            case 14: {
                if (GameOfflineActivity.board.getItem(targetX - 2, targetY - 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX - 2, targetY - 2));
                }
                if (GameOfflineActivity.board.getItem(targetX - 1, targetY - 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX - 1, targetY - 1));
                }
                if (GameOfflineActivity.board.getItem(targetX + 1, targetY + 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 1, targetY + 1));
                }
                if (GameOfflineActivity.board.getItem(targetX + 2, targetY + 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 2, targetY + 2));
                }
                break;
            }

            case 15: {
                if (GameOfflineActivity.board.getItem(targetX + 2, targetY - 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 2, targetY - 2));
                }
                if (GameOfflineActivity.board.getItem(targetX + 1, targetY - 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 1, targetY - 1));
                }
                if (GameOfflineActivity.board.getItem(targetX - 1, targetY + 1) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX - 1, targetY + 1));
                }
                if (GameOfflineActivity.board.getItem(targetX - 2, targetY + 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX -2, targetY + 2));
                }
                break;
            }

            case 16: {
                for (int x = targetX - 2, y = targetY - 2; y <= targetY + 2; y++) {
                    if (GameOfflineActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new BoardOffline.Position(x, y));
                    }
                }
                break;
            }

            case 17: {
                for (int x = targetX - 2, y = targetY + 2; x <= targetX + 2; x++) {
                    if (GameOfflineActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new BoardOffline.Position(x, y));
                    }
                }
                break;
            }

            case 18: {
                for (int x = targetX + 2, y = targetY - 2; y <= targetY + 2; y++) {
                    if (GameOfflineActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new BoardOffline.Position(x, y));
                    }
                }
                break;
            }

            case 19: {
                for (int x = targetX - 2, y = targetY - 2; x <= targetX + 2; x++) {
                    if (GameOfflineActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new BoardOffline.Position(x, y));
                    }
                }
                break;
            }

            case 22: {
                for (int x = targetX - 2; x <= targetX - 1; x++) {
                    for (int y = targetY - 2; y <= targetY + 2; y++) {
                        if (GameOfflineActivity.board.getItem(x, y) == type) {
                            pieceDeleteds.add(new BoardOffline.Position(x, y));
                        }
                    }
                }
                break;
            }

            case 23: {
                for (int y = targetY + 1; y <= targetY + 2; y++) {
                    for (int x = targetX - 2; x <= targetX + 2; x++) {
                        if (GameOfflineActivity.board.getItem(x, y) == type) {
                            pieceDeleteds.add(new BoardOffline.Position(x, y));
                        }
                    }
                }
                break;
            }

            case 24: {
                for (int x = targetX + 1; x <= targetX + 2; x++) {
                    for (int y = targetY - 2; y <= targetY + 2; y++) {
                        if (GameOfflineActivity.board.getItem(x, y) == type) {
                            pieceDeleteds.add(new BoardOffline.Position(x, y));
                        }
                    }
                }
                break;
            }

            case 25: {
                for (int y = targetY - 2; y <= targetY - 1; y++) {
                    for (int x = targetX - 2; x <= targetX + 2; x++) {
                        if (GameOfflineActivity.board.getItem(x, y) == type) {
                            pieceDeleteds.add(new BoardOffline.Position(x, y));
                        }
                    }
                }
                break;
            }

            case 26: {
                if (GameOfflineActivity.board.getItem(targetX - 1, targetY - 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX - 1, targetY - 2));
                }
                if (GameOfflineActivity.board.getItem(targetX - 1, targetY + 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX - 1, targetY + 2));
                }
                if (GameOfflineActivity.board.getItem(targetX, targetY - 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX, targetY - 2));
                }
                if (GameOfflineActivity.board.getItem(targetX, targetY + 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX, targetY + 2));
                }
                if (GameOfflineActivity.board.getItem(targetX + 1, targetY - 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 1, targetY - 2));
                }
                if (GameOfflineActivity.board.getItem(targetX + 1, targetY + 2) == type) {
                    pieceDeleteds.add(new BoardOffline.Position(targetX + 1, targetY + 2));
                }
                for (int x = targetX - 2, y = targetY - 2; y <= targetY + 2; y++) {
                    if (GameOfflineActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new BoardOffline.Position(x, y));
                    }
                }
                for (int x = targetX + 2, y = targetY - 2; y <= targetY + 2; y++) {
                    if (GameOfflineActivity.board.getItem(x, y) == type) {
                        pieceDeleteds.add(new BoardOffline.Position(x, y));
                    }
                }
                break;
            }
        }
    }
}

