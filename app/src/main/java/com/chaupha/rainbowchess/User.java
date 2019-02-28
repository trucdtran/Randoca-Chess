package com.chaupha.rainbowchess;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User implements Parcelable, Comparable<User> {

    private Context context;
    private String uid;
    private String name;
    private Uri photoUri;
    private int gamesPlayed;
    private int gamesWon;
    private int score;
    private int rank;
    private long gold;
    private int descPower;
    private int online;
    private ArrayList<Integer> records;
    private ArrayList<String> games;
    private ArrayList<String> friends;
    private ArrayList<Request> gameRequests;
    private ArrayList<Request> friendRequests;


    //For FindOpponentActivity
    public User(Context context, String name, Uri photoUri, String uid, int rank, int online, ArrayList<String> games,
    ArrayList<String> friends, ArrayList<Request> gameRequests, ArrayList<Request> friendRequests){
        this.context = context;
        this.uid = uid;
        this.name = name;
        this.photoUri = photoUri;
        this.rank = rank;
        this.online = online;
        this.games = games;
        this.friends = friends;
        this.gameRequests = gameRequests;
        this.friendRequests = friendRequests;
    }


    //For ProfileActivity, RankActivity
    public User(Context context, String name, Uri photoUri, String uid, int rank, int score, int gamesPlayed, int gamesWon,
    long gold, int descPower, ArrayList<Integer> records, ArrayList<String> friends, ArrayList<Request> gameRequests,
    ArrayList<Request> friendRequests){
        this.context = context;
        this.uid = uid;
        this.name = name;
        this.photoUri = photoUri;
        this.rank = rank;
        this.score = score;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gold = gold;
        this.descPower = descPower;
        this.records = records;
        this.friends = friends;
        this.gameRequests = gameRequests;
        this.friendRequests = friendRequests;
    }


    protected User(Parcel in) {
        uid = in.readString();
        name = in.readString();
        photoUri = Uri.parse(in.readString());
        gamesPlayed = in.readInt();
        gamesWon = in.readInt();
        score = in.readInt();
        rank = in.readInt();
        gold = in.readLong();
        descPower = in.readInt();
        online = in.readInt();
        records = in.readArrayList(Integer.class.getClassLoader());
        games = in.readArrayList(String.class.getClassLoader());
        friends = in.readArrayList(String.class.getClassLoader());
        gameRequests = in.readArrayList(Request.class.getClassLoader());
        friendRequests = in.readArrayList(Request.class.getClassLoader());
    }


    public String getUid(){
        return uid;
    }

    public String getName() {
        return name;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getScore() {
        return score;
    }

    public int getDescPower() {
        return descPower;
    }

    public int getOnline() {
        return online;
    }

    public int getRank() {
        return rank;
    }

    public long getGold() {
        return gold;
    }

    public ArrayList<Integer> getRecords() {
        return records;
    }

    public ArrayList<String> getGames() {
        return games;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<Request> getGameRequests() {
        return gameRequests;
    }

    public ArrayList<Request> getFriendRequests() {
        return friendRequests;
    }


    public String getRankString() {
        String str="";
        switch (rank/10) {
            case 0: {
                str = context.getResources().getString(R.string.user_rank_0);
                break;
            }
            case 1: {
                str = context.getResources().getString(R.string.user_rank_1);
                break;
            }
            case 2: {
                str = context.getResources().getString(R.string.user_rank_2);
                break;
            }
            case 3: {
                str = context.getResources().getString(R.string.user_rank_3);
                break;
            }
            case 4: {
                str = context.getResources().getString(R.string.user_rank_4);
                break;
            }
            case 5: {
                str = context.getResources().getString(R.string.user_rank_5);
                break;
            }
        }
        return str + " " + (rank%10);
    }


    public int getLimitScore() {
        switch(rank/10) {
            case 0: return 100;
            case 1: return 200;
            case 3: return 500;
            case 4: return 1000;
            case 5: return 2000;
            case 6: return 5000;
        }
        return 10000;
    }


    public boolean hasFriend(String friendID) {
        for (String friend : friends) {
            if(friend.equals(friendID)) {
                return true;
            }
        }
        return false;
    }


    public boolean hasFriendRequest(String friendID) {
        for (Request request : friendRequests) {
            if(request.getSourceUid().equals(friendID)) {
                return true;
            }
        }
        return false;
    }


    public boolean hasGameRequest(String userID) {
        for (Request request : gameRequests) {
            if(request.getSourceUid().equals(userID)) {
                return true;
            }
        }
        return false;
    }


    public float getWinPercent() {
        if(gamesPlayed == 0)
            return 50.0f;
        float x = (float) gamesWon / (float) gamesPlayed;
        return Math.round(x*10000)/100.0f;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeString(String.valueOf(photoUri));
        dest.writeString(uid);
        dest.writeInt(gamesPlayed);
        dest.writeInt(gamesWon);
        dest.writeInt(score);
        dest.writeInt(rank);
        dest.writeLong(gold);
        dest.writeInt(descPower);
        dest.writeInt(online);
        dest.writeList(records);
        dest.writeList(games);
        dest.writeList(friends);
        dest.writeList(gameRequests);
        dest.writeList(friendRequests);
    }


    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    @Override
    public int compareTo(User compareUser) {
        return compareUser.rank - rank;
    }
}






