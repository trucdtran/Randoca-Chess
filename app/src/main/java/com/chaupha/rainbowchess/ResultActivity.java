package com.chaupha.rainbowchess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity{

    private TextView textBlueStatus;
    private ImageView imageBluePhoto;
    private TextView textBlueName;
    private TextView textBlueRank;
    private TextView textBlueScore;
    private TextView textBlueWinPer;
    private TextView textBlueGamesPlayed;
    private TextView textBlueGamesWon;
    private TextView textBlueGold;
    private ImageView imageBlue1;
    private ImageView imageBlue2;
    private ImageView imageBlue3;
    private ImageView imageBlue4;
    private ImageView imageBlue5;
    private ImageView imageBlue6;
    private ImageView imageBlue7;
    private ImageView imageBlue8;
    private ImageView imageBlue9;
    private ImageView imageBlue10;


    private TextView textRedStatus;
    private ImageView imageRedPhoto;
    private TextView textRedName;
    private TextView textRedRank;
    private TextView textRedScore;
    private TextView textRedWinPer;
    private TextView textRedGamesPlayed;
    private TextView textRedGamesWon;
    private TextView textRedGold;
    private ImageView imageRed1;
    private ImageView imageRed2;
    private ImageView imageRed3;
    private ImageView imageRed4;
    private ImageView imageRed5;
    private ImageView imageRed6;
    private ImageView imageRed7;
    private ImageView imageRed8;
    private ImageView imageRed9;
    private ImageView imageRed10;

    private Button button;

    private boolean blueWin;
    private boolean timeOut;
    private int countTurn;

    private String blueID;
    private String blueName;
    private String bluePhoto;
    private int blueRank;
    private int blueScore;
    private int blueGamesPlayed;
    private int blueGamesWon;
    private int blueGold;

    private String redID;
    private String redName;
    private String redPhoto;
    private int redRank;
    private int redScore;
    private int redGamesPlayed;
    private int redGamesWon;
    private int redGold;

    private float blueWinPer;
    private float blueWinPerNew;
    private float redWinPer;
    private float redWinPerNew;
    private int disparityRank;

    private int blueRankNew;
    private int blueScoreNew;
    private int blueScoreTotal;
    private int blueGamesPlayedNew;
    private int blueGamesWonNew;
    private int blueGoldNew;
    private int redRankNew;
    private int redScoreNew;
    private int redScoreTotal;
    private int redGamesPlayedNew;
    private int redGamesWonNew;
    private int redGoldNew;

    private ArrayList<ImageView> blueStars;
    private ArrayList<ImageView> redStars;
    MediaPlayer mediaPlayer;

    FirebaseUser currentUser;
    DatabaseReference usersReference;

    private User user;

    private DatabaseReference ref;
    private ChildEventListener childEventListener;
    private int[] descBlue = new int[13];
    private int[] descRed = new int[13];

    private String gameID = "";
    private String xxxblueID = "";
    private String xxxredID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        blueWin = intent.getBooleanExtra(GameActivity.BLUE_WIN, false);
        timeOut = intent.getBooleanExtra(GameActivity.TIME_OUT, false);
        countTurn = intent.getIntExtra(GameActivity.COUNT_TURN, 1);
        Log.e("XXXXXXXXX", countTurn+"");

        blueID = intent.getStringExtra(GameActivity.BLUE_ID);
        blueName = intent.getStringExtra(GameActivity.BLUE_NAME);
        bluePhoto = intent.getStringExtra(GameActivity.BLUE_PHOTO);
        blueRank = intent.getIntExtra(GameActivity.BLUE_RANK, -1);
        blueScore = intent.getIntExtra(GameActivity.BLUE_SCORE, -1);
        blueGamesPlayed = intent.getIntExtra(GameActivity.BLUE_GAMES_PLAYED, -1);
        blueGamesWon = intent.getIntExtra(GameActivity.BLUE_GAMES_WON, -1);
        blueGold = intent.getIntExtra(GameActivity.BLUE_GOLD, -1);

        redID = intent.getStringExtra(GameActivity.RED_ID);
        redName = intent.getStringExtra(GameActivity.RED_NAME);
        redPhoto = intent.getStringExtra(GameActivity.RED_PHOTO);
        redRank = intent.getIntExtra(GameActivity.RED_RANK, -1);
        redScore = intent.getIntExtra(GameActivity.RED_SCORE, -1);
        redGamesPlayed = intent.getIntExtra(GameActivity.RED_GAMES_PLAYED, -1);
        redGamesWon = intent.getIntExtra(GameActivity.RED_GAMES_WON, -1);
        redGold = intent.getIntExtra(GameActivity.RED_GOLD, -1);

        blueStars = new ArrayList<>();
        redStars = new ArrayList<>();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");

        textBlueStatus = findViewById(R.id.result_textBlueStatus);
        imageBluePhoto = findViewById(R.id.result_photoBlue);
        textBlueName = findViewById(R.id.result_textBlueName);
        textBlueRank = findViewById(R.id.result_textBlueRank);
        textBlueScore = findViewById(R.id.result_textBlueScore);
        textBlueWinPer = findViewById(R.id.result_textBlueWinPer);
        textBlueGamesPlayed = findViewById(R.id.result_textBluePlayedGame);
        textBlueGamesWon = findViewById(R.id.result_textBlueWonGame);
        textBlueGold = findViewById(R.id.result_textBlueGold);
        imageBlue1 = findViewById(R.id.result_photoBlue1);
        imageBlue2 = findViewById(R.id.result_photoBlue2);
        imageBlue3 = findViewById(R.id.result_photoBlue3);
        imageBlue4 = findViewById(R.id.result_photoBlue4);
        imageBlue5 = findViewById(R.id.result_photoBlue5);
        imageBlue6 = findViewById(R.id.result_photoBlue6);
        imageBlue7 = findViewById(R.id.result_photoBlue7);
        imageBlue8 = findViewById(R.id.result_photoBlue8);
        imageBlue9 = findViewById(R.id.result_photoBlue9);
        imageBlue10 = findViewById(R.id.result_photoBlue10);
        blueStars.add(imageBlue1);
        blueStars.add(imageBlue2);
        blueStars.add(imageBlue3);
        blueStars.add(imageBlue4);
        blueStars.add(imageBlue5);
        blueStars.add(imageBlue6);
        blueStars.add(imageBlue7);
        blueStars.add(imageBlue8);
        blueStars.add(imageBlue9);
        blueStars.add(imageBlue10);

        textRedStatus = findViewById(R.id.result_textRedStatus);
        imageRedPhoto = findViewById(R.id.result_photoRed);
        textRedName = findViewById(R.id.result_textRedName);
        textRedRank = findViewById(R.id.result_textRedRank);
        textRedScore = findViewById(R.id.result_textRedScore);
        textRedWinPer = findViewById(R.id.result_textRedWinPer);
        textRedGamesPlayed = findViewById(R.id.result_textRedPlayedGame);
        textRedGamesWon = findViewById(R.id.result_textRedWonGame);
        textRedGold = findViewById(R.id.result_textRedGold);
        imageRed1 = findViewById(R.id.result_photoRed1);
        imageRed2 = findViewById(R.id.result_photoRed2);
        imageRed3 = findViewById(R.id.result_photoRed3);
        imageRed4 = findViewById(R.id.result_photoRed4);
        imageRed5 = findViewById(R.id.result_photoRed5);
        imageRed6 = findViewById(R.id.result_photoRed6);
        imageRed7 = findViewById(R.id.result_photoRed7);
        imageRed8 = findViewById(R.id.result_photoRed8);
        imageRed9 = findViewById(R.id.result_photoRed9);
        imageRed10 = findViewById(R.id.result_photoRed10);
        redStars.add(imageRed1);
        redStars.add(imageRed2);
        redStars.add(imageRed3);
        redStars.add(imageRed4);
        redStars.add(imageRed5);
        redStars.add(imageRed6);
        redStars.add(imageRed7);
        redStars.add(imageRed8);
        redStars.add(imageRed9);
        redStars.add(imageRed10);

        button = findViewById(R.id.result_button);

        if( (blueWin&&currentUser.getUid().equals(blueID)) || (!blueWin&&currentUser.getUid().equals(redID)) ) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_win);
            mediaPlayer.start();
        }
        else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_lose);
            mediaPlayer.start();
        }

        updateStatitics();

        if( (blueWin&&currentUser.getUid().equals(blueID)) || (!blueWin&&currentUser.getUid().equals(redID)) ) {
            updateDatabase();
        }

        displayStatitics();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                Intent intent = new Intent(ResultActivity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY );
                startActivity(intent);
                finish();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("game_entry");
        listenForUser();
    }


    @Override
    protected void onStart() {
        super.onStart();
        listenForAcceptedGameRequests();
    }


    @Override
    protected void onStop() {
        ref.removeEventListener(childEventListener);
        super.onStop();
    }

    private void listenForAcceptedGameRequests() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                gameID = dataSnapshot.getKey();
                String str = dataSnapshot.getValue(String.class);
                int indexStr = str.indexOf("-");
                xxxblueID = str.substring(0, indexStr);
                xxxredID = str.substring(indexStr+1);

                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent intent = new Intent(ResultActivity.this, GameActivity.class);
                        intent.putExtra(GameActivity.GAME_ID, gameID);
                        intent.putExtra(GameActivity.BLUE_ID, xxxblueID);
                        intent.putExtra(GameActivity.BLUE_NAME, dataSnapshot.child(xxxblueID).child("name").getValue(String.class));
                        intent.putExtra(GameActivity.BLUE_PHOTO, dataSnapshot.child(xxxblueID).child("photo_uri").getValue(String.class));
                        intent.putExtra(GameActivity.RED_ID, xxxredID);
                        intent.putExtra(GameActivity.RED_NAME, dataSnapshot.child(xxxredID).child("name").getValue(String.class));
                        intent.putExtra(GameActivity.RED_PHOTO, dataSnapshot.child(xxxredID).child("photo_uri").getValue(String.class));
                        int i = 0;
                        for (DataSnapshot snapshot : dataSnapshot.child(xxxblueID).child("desc").getChildren()) {
                            descBlue[i] = Integer.valueOf(snapshot.getValue(String.class));
                            i++;
                        }
                        i = 0;
                        for (DataSnapshot snapshot : dataSnapshot.child(xxxredID).child("desc").getChildren()) {
                            descRed[i] = Integer.valueOf(snapshot.getValue(String.class));
                            i++;
                        }
                        intent.putExtra(GameActivity.BLUE_DESC, descBlue);
                        intent.putExtra(GameActivity.RED_DESC, descRed);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(childEventListener);
    }


    private void updateStatitics() {
        textBlueName.setText(blueName);
        Picasso.get().load(bluePhoto).into(imageBluePhoto);
        textRedName.setText(redName);
        Picasso.get().load(redPhoto).into(imageRedPhoto);

        blueWinPer = (blueGamesPlayed==0) ? (float) 0.5 : ((float) blueGamesWon / (float) blueGamesPlayed);
        blueWinPer = Math.round(blueWinPer*10000)/100;
        redWinPer = (redGamesPlayed==0) ? (float) 0.5 : ((float) redGamesWon / (float) redGamesPlayed);
        redWinPer = Math.round(redWinPer*10000)/100;
        disparityRank = Math.abs(blueRank/10 - redRank/10)+1;

        blueGamesPlayedNew = blueGamesPlayed+1;
        redGamesPlayedNew = redGamesPlayed+1;
        blueGamesWonNew = blueGamesWon;
        redGamesWonNew = redGamesWon;
        if(blueWin)
            blueGamesWonNew = blueGamesWon+1;
        else
            redGamesWonNew = redGamesWon+1;

        blueWinPerNew = (float) blueGamesWonNew / (float) blueGamesPlayedNew;
        blueWinPerNew = Math.round(blueWinPerNew*10000)/100;
        redWinPerNew = (float) redGamesWonNew / (float) redGamesPlayedNew;
        redWinPerNew = Math.round(redWinPerNew*10000)/100;

        blueRankNew = blueRank;
        redRankNew = redRank;

        int increaseScore = (timeOut) ? countTurn*disparityRank :  (50-countTurn)*disparityRank;
        int decreaseScore = (50-countTurn)*disparityRank;
        if(blueWin) {
            blueGoldNew = blueGold + increaseScore;
            redGoldNew = redGold;
            blueScoreNew = blueScore + increaseScore;
            blueScoreTotal = blueScoreNew;
            if(blueScore >= getLimitScore(blueRank))    upgradeRank();
            redScoreNew = redScore - decreaseScore;
            redScoreTotal = redScoreNew;
            if(redScoreNew < 0)   redScoreNew = 0;
        }
        else {
            redGoldNew = redGold + increaseScore;
            blueGoldNew = blueGold;
            redScoreNew = redScore + increaseScore;
            redScoreTotal = redScoreNew;
            if(redScoreNew >= getLimitScore(redRank))   upgradeRank();
            blueScoreNew = blueScore - decreaseScore;
            blueScoreTotal = blueScoreNew;
            if(blueScoreNew < 0)    blueScoreNew = 0;
        }
    }


    private void updateDatabase() {
        usersReference.child(blueID).child("games_played").setValue(blueGamesPlayedNew+"");
        usersReference.child(blueID).child("games_won").setValue(blueGamesWonNew+"");
        usersReference.child(blueID).child("rank").setValue(blueRankNew+"");
        usersReference.child(blueID).child("score").setValue(blueScoreNew+"");
        usersReference.child(blueID).child("gold").setValue(blueGoldNew+"");

        usersReference.child(redID).child("games_played").setValue(redGamesPlayedNew+"");
        usersReference.child(redID).child("games_won").setValue(redGamesWonNew+"");
        usersReference.child(redID).child("rank").setValue(redRankNew+"");
        usersReference.child(redID).child("score").setValue(redScoreNew+"");
        usersReference.child(redID).child("gold").setValue(redGoldNew+"");

        usersReference.child(blueID).child("online").setValue("1");
        usersReference.child(redID).child("online").setValue("1");
    }


    private void displayStatitics() {
        String result_rank = getApplicationContext().getResources().getString(R.string.result_rank);
        String result_score = getApplicationContext().getResources().getString(R.string.result_score);
        String result_win_per = getApplicationContext().getResources().getString(R.string.result_win_per);
        String result_game_played = getApplicationContext().getResources().getString(R.string.result_games_played);
        String result_game_won = getApplicationContext().getResources().getString(R.string.result_games_won);
        String result_upgrade = getApplicationContext().getResources().getString(R.string.result_upgrade);
        String buffer;
        if(blueWin) {
            textBlueStatus.setText(R.string.result_win);
            textRedStatus.setText(R.string.result_lose);
        }
        else {
            textBlueStatus.setText(R.string.result_lose);
            textRedStatus.setText(R.string.result_win);
        }

        buffer = result_rank + convertRankToString(blueRank);
        if(blueRank != blueRankNew)
            buffer = buffer + " (" + result_upgrade + " " + convertRankToString(blueRankNew) + ")";
        textBlueRank.setText(buffer);

        if(blueScoreTotal-blueScore >= 0)
            buffer = result_score + blueScore + " (+" + (blueScoreTotal-blueScore) + ")";
        else
            buffer = result_score + blueScore + " (" + (blueScoreTotal-blueScore) + ")";
        textBlueScore.setText(buffer);

        if(blueWinPerNew-blueWinPer >= 0)
            buffer = result_win_per + blueWinPer + "% (+" + Float.toString(blueWinPerNew-blueWinPer) + "%)";
        else
            buffer = result_win_per + blueWinPer + "% (" + Float.toString(blueWinPerNew-blueWinPer) + "%)";
        textBlueWinPer.setText(buffer);

        buffer = result_game_won + blueGamesWonNew;
        textBlueGamesWon.setText(buffer);

        buffer = result_game_played + blueGamesPlayedNew;
        textBlueGamesPlayed.setText(buffer);

        buffer = blueGold + " (+" + (blueGoldNew - blueGold) + ")";
        textBlueGold.setText(buffer);

        ///

        buffer = result_rank + convertRankToString(redRank);
        if(redRank != redRankNew)
            buffer = buffer + " (" + result_upgrade + " " + convertRankToString(redRankNew) + ")";
        textRedRank.setText(buffer);

        if(redScoreTotal-redScore >= 0)
            buffer = result_score + redScore + " (+" + (redScoreTotal-redScore) + ")";
        else
            buffer = result_score + redScore + " (" + (redScoreTotal-redScore) + ")";
        textRedScore.setText(buffer);

        if(redWinPerNew-redWinPer >= 0)
            buffer = result_win_per + redWinPer + "% (+" + Float.toString(redWinPerNew-redWinPer) + "%)";
        else
            buffer = result_win_per + redWinPer + "% (" + Float.toString(redWinPerNew-redWinPer) + "%)";
        textRedWinPer.setText(buffer);

        buffer = result_game_won + redGamesWonNew;
        textRedGamesWon.setText(buffer);

        buffer = result_game_played + redGamesPlayedNew;
        textRedGamesPlayed.setText(buffer);

        buffer = redGold + " (+" + (redGoldNew - redGold) + ")";
        textRedGold.setText(buffer);

        displayRankStar();
    }


    private int getLimitScore(int rank) {
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


    private void upgradeRank() {
        if(blueWin) {
            do {
                blueScoreNew = blueScoreNew - getLimitScore(blueRankNew);
                blueRankNew++;
                if(blueRankNew%10 == 0)    blueRankNew++;
            } while (blueScoreNew >= getLimitScore(blueRankNew));
        }
        else {
            do {
                redScoreNew = redScoreNew - getLimitScore(blueRankNew);
                redRankNew++;
                if(redRankNew%10 == 0)    redRankNew++;
            } while (redScoreNew >= getLimitScore(redRankNew));
        }
    }


    private String convertRankToString(int rank) {
        String str="";
        switch (rank/10) {
            case 0: {
                str = getApplicationContext().getResources().getString(R.string.user_rank_0);
                break;
            }
            case 1: {
                str = getApplicationContext().getResources().getString(R.string.user_rank_1);
                break;
            }
            case 2: {
                str = getApplicationContext().getResources().getString(R.string.user_rank_2);
                break;
            }
            case 3: {
                str = getApplicationContext().getResources().getString(R.string.user_rank_3);
                break;
            }
            case 4: {
                str = getApplicationContext().getResources().getString(R.string.user_rank_4);
                break;
            }
            case 5: {
                str = getApplicationContext().getResources().getString(R.string.user_rank_5);
                break;
            }
        }
        return str + " " + (rank%10);
    }


    private void displayRankStar() {
        int a = blueRankNew/10;
        int a2 = blueRankNew%10;
        int b = a2/5;
        int c = a2%5;
        while(a>0) {
            blueStars.get(0).setImageResource(R.drawable.icon_star_10);
            blueStars.remove(0);
            a--;
        }
        while(b>0) {
            blueStars.get(0).setImageResource(R.drawable.icon_star_5);
            blueStars.remove(0);
            b--;
        }
        while(c>0) {
            blueStars.get(0).setImageResource(R.drawable.icon_star_1);
            blueStars.remove(0);
            c--;
        }
        while(blueStars.size()>0) {
            blueStars.get(0).setVisibility(View.INVISIBLE);
            blueStars.remove(0);
        }

        a = redRankNew/10;
        a2 = redRankNew%10;
        b = a2/5;
        c = a2%5;
        while(a>0) {
            redStars.get(0).setImageResource(R.drawable.icon_star_10);
            redStars.remove(0);
            a--;
        }
        while(b>0) {
            redStars.get(0).setImageResource(R.drawable.icon_star_5);
            redStars.remove(0);
            b--;
        }
        while(c>0) {
            redStars.get(0).setImageResource(R.drawable.icon_star_1);
            redStars.remove(0);
            c--;
        }
        while(redStars.size()>0) {
            redStars.get(0).setVisibility(View.INVISIBLE);
            redStars.remove(0);
        }
    }

    @Override
    public void onBackPressed() { }


    public void listenForUser() {
        usersReference.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = dataSnapshot.getKey();
                if (dataSnapshot.child("name").getValue(String.class) == null)
                    return;
                if (dataSnapshot.child("photo_uri").getValue(String.class) == null)
                    return;
                if (dataSnapshot.child("rank").getValue(String.class) == null)
                    return;
                if (dataSnapshot.child("online").getValue(String.class) == null)
                    return;
                String name = dataSnapshot.child("name").getValue(String.class);
                Uri photo_uri = Uri.parse(dataSnapshot.child("photo_uri").getValue(String.class));
                int rank = Integer.valueOf(dataSnapshot.child("rank").getValue(String.class));
                int online = Integer.valueOf(dataSnapshot.child("online").getValue(String.class));
                ArrayList<String> games = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.child("games").getChildren()) {
                    String game = snap.getKey();
                    games.add(game);
                }
                ArrayList<String> friends = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.child("friends").getChildren()) {
                    String friend = snap.getKey();
                    friends.add(friend);
                }
                ArrayList<Request> gameRequests = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.child("game_requests").getChildren()) {
                    String sourceUid = snap.getKey();
                    String time = snap.getValue(String.class);
                    String targetUid = currentUser.getUid();
                    Request gameRequest = new Request(sourceUid, targetUid, time);
                    gameRequests.add(gameRequest);
                }
                ArrayList<Request> friendRequests = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.child("friend_requests").getChildren()) {
                    String sourceUid = snap.getKey();
                    String time = snap.getValue(String.class);
                    String targetUid = currentUser.getUid();
                    Request friendRequest = new Request(sourceUid, targetUid, time);
                    friendRequests.add(friendRequest);
                }

                user = new User(getApplicationContext(), name, photo_uri, uid, rank, online, games, friends,
                        gameRequests, friendRequests);
                invalidateOptionsMenu();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemGameRequest = menu.findItem(R.id.menuGameRequests);
        if(user == null || user.getGameRequests().size() == 0) {
            itemGameRequest.setIcon(R.drawable.icon_notification_off);
        }
        else {
            itemGameRequest.setIcon(R.drawable.icon_notification_on);
        }

        MenuItem itemFriendRequest = menu.findItem(R.id.menuFriendRequests);
        if(user == null || user.getFriendRequests().size() == 0) {
            itemFriendRequest.setIcon(R.drawable.icon_add_friend_off);
        }
        else {
            itemFriendRequest.setIcon(R.drawable.icon_add_friend_on);
        }

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuLogout) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            usersReference.child(currentUser.getUid()).child("online").setValue("0");
            user = null;
            startActivity(new Intent(ResultActivity.this, LoginActivity.class));
            finish();
        }
        else if(item.getItemId() == R.id.menuGameRequests && user.getGameRequests().size() != 0) {
            Intent intent = new Intent(ResultActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.GAME_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getGameRequests());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menuFriendRequests && user.getFriendRequests().size() != 0) {
            Intent intent = new Intent(ResultActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.FRIEND_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getFriendRequests());
            startActivity(intent);
        }
        return true;
    }
}
