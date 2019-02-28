package com.chaupha.rainbowchess;

public class Card {
    private int cardID;
    private int count;


    public Card(int cardID, int count) {
        this.cardID = cardID;
        this.count = count;
    }

    public int getCardID() {
        return cardID;
    }

    public int getCount() {
        return count;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setCount(int count) {
        this.count = count;
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


    public static int getCardResource(int cardID) {
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


    public int getCardPower() {
        int power = 0;
        switch (cardID) {
            case 1:
            case 2:
            case 3:
            case 4: {power = 2; break;}
            case 5:
            case 6:
            case 7:
            case 8: {power = 3; break;}
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15: {power = 4; break;}
            case 16:
            case 17:
            case 18:
            case 19: {power = 5; break;}
            case 11:
            case 20:
            case 21: {power = 8; break;}
            case 22:
            case 23:
            case 24:
            case 25: {power = 10; break;}
            case 26: {power = 16; break;}
            case 27: {power = 24; break;}
        }
        return power;
    }


    public static int getCardPower(int cardID) {
        int power = 0;
        switch (cardID) {
            case 1:
            case 2:
            case 3:
            case 4: {power = 2; break;}
            case 5:
            case 6:
            case 7:
            case 8: {power = 3; break;}
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15: {power = 4; break;}
            case 16:
            case 17:
            case 18:
            case 19: {power = 5; break;}
            case 11:
            case 20:
            case 21: {power = 8; break;}
            case 22:
            case 23:
            case 24:
            case 25: {power = 10; break;}
            case 26: {power = 16; break;}
            case 27: {power = 24; break;}
        }
        return power;
    }
}
