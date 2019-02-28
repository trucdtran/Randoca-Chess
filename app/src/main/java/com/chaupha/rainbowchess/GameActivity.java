package com.chaupha.rainbowchess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class GameActivity extends Activity {

    private GridView gridView;
    private ImageView imageViewTurn;

    private ImageView imagePlayerRed;
    private TextView namePlayerRed;
    private Button timePlayerRed;
    private ImageView cardPlayerRed;

    private ImageView imagePlayerBlue;
    private TextView namePlayerBlue;
    private Button timePlayerBlue;
    private ImageView cardPlayerBlue;

    public static final String GAME_ID = "GAME_ID";
    public static final String BLUE_ID = "BLUE_ID";
    public static final String BLUE_NAME = "BLUE_NAME";
    public static final String BLUE_PHOTO = "BLUE_PHOTO";
    public static final String BLUE_DESC = "BLUE_DESC";
    public static final String RED_ID = "RED_ID";
    public static final String RED_NAME = "RED_NAME";
    public static final String RED_PHOTO = "RED_PHOTO";
    public static final String RED_DESC = "RED_DESC";

    public static final String BLUE_WIN = "BLUE_WIN";
    public static final String TIME_OUT = "TIME_OUT";
    public static final String BLUE_RANK = "BLUE_RANK";
    public static final String BLUE_SCORE = "BLUE_SCORE";
    public static final String BLUE_GAMES_PLAYED = "BLUE_GAMES_PLAYED";
    public static final String BLUE_GAMES_WON = "BLUE_GAMES_WON";
    public static final String BLUE_GOLD = "BLUE_GOLD";
    public static final String RED_RANK = "RED_RANK";
    public static final String RED_SCORE = "RED_SCORE";
    public static final String RED_GAMES_PLAYED = "RED_GAMES_PLAYED";
    public static final String RED_GAMES_WON = "RED_GAMES_WON";
    public static final String RED_GOLD = "RED_GOLD";
    public static final String COUNT_TURN = "COUNT_TURN";


    private String gameID;
    public static String blueID;
    public static String blueName;
    public static String bluePhoto;
    public static String redID;
    public static String redName;
    public static String redPhoto;
    public static String turn;
    public static boolean pieceSelected = false;
    private int countTurn;
    public static int[] blueDesc;
    public static int[] redDesc;

    DatabaseReference gameReference;
    DatabaseReference usersReference;
    FirebaseUser currentUser;

    public static Board board;
    Action action;
    public static PieceAdapter pieceAdapter;
    CountDownTimer timer;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        gameID = intent.getStringExtra(GAME_ID);
        blueID = intent.getStringExtra(BLUE_ID);
        blueName = intent.getStringExtra(BLUE_NAME);
        bluePhoto = intent.getStringExtra(BLUE_PHOTO);
        redID = intent.getStringExtra(RED_ID);
        redName = intent.getStringExtra(RED_NAME);
        redPhoto = intent.getStringExtra(RED_PHOTO);
        blueDesc = intent.getIntArrayExtra(BLUE_DESC);
        redDesc = intent.getIntArrayExtra(RED_DESC);

        gridView = findViewById(R.id.gridView);
        imageViewTurn = findViewById(R.id.imageViewTurn);
        imagePlayerRed = findViewById(R.id.imagePlayerRed);
        namePlayerRed = findViewById(R.id.namePlayerRed);
        timePlayerRed = findViewById(R.id.timePlayerRed);
        cardPlayerRed = findViewById(R.id.cardPlayerRed);
        imagePlayerBlue = findViewById(R.id.imagePlayerBlue);
        namePlayerBlue = findViewById(R.id.namePlayerBlue);
        timePlayerBlue = findViewById(R.id.timePlayerBlue);
        cardPlayerBlue = findViewById(R.id.cardPlayerBlue);

        namePlayerBlue.setText(blueName);
        Picasso.get().load(bluePhoto).into(imagePlayerBlue);
        namePlayerRed.setText(redName);
        Picasso.get().load(redPhoto).into(imagePlayerRed);

        gameReference = FirebaseDatabase.getInstance().getReference().child("games").child(gameID);
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        usersReference.child(blueID).child("game_entry").child(gameID).removeValue();
        usersReference.child(redID).child("game_entry").child(gameID).removeValue();

        countTurn = 0;
        board = new Board();
        action = new Action(this);
        pieceAdapter = new PieceAdapter(this, board);
        gridView.setAdapter(pieceAdapter);

        turn = blueID;

        if (currentUser.getUid().equals(turn)) {
            countTurn++;
            imageViewTurn.setVisibility(View.VISIBLE);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_turn);
            mediaPlayer.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageViewTurn.setVisibility(View.INVISIBLE);
                }
            }, 1000);

            board.updatePieceClickable(true);

            int sourceCard = action.createCard();
            if(turn.equals(blueID)) {
                cardPlayerBlue.setImageResource(sourceCard);
            }
            else {
                cardPlayerRed.setImageResource(sourceCard);
            }
        }

        timer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(turn.equals(blueID)) {
                    timePlayerBlue.setText(millisUntilFinished/1000+"");
                    if(millisUntilFinished <= 11000) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_time);
                        mediaPlayer.start();
                    }
                }
                else {
                    timePlayerRed.setText(millisUntilFinished/1000+"");
                    if(millisUntilFinished <= 11000) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_time);
                        mediaPlayer.start();
                    }
                }
            }

            @Override
            public void onFinish() {
                if(turn.equals(blueID)) {
                    timePlayerBlue.setText(0+"");
                    finishGame(false, true);
                    timer.cancel();
                }
                else {
                    timePlayerRed.setText(0+"");
                    finishGame(true, true);
                    timer.cancel();
                }
            }
        }.start();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(!currentUser.getUid().equals(turn)) return;
                int row = position/Board.SIZE;
                int col = position%Board.SIZE;

                if(!pieceSelected) {
                    if(board.isPieceClickable(row, col)) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_piece);
                        mediaPlayer.start();
                        pieceSelected = true;
                        action.sourceX = row;
                        action.sourceY = col;
                        board.updateItemClickable(row, col);
                    }
                }
                else {
                    if(board.isItemClickable(row, col)) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_piece);
                        mediaPlayer.start();
                        action.targetX = row;
                        action.targetY = col;
                        pieceSelected = false;
                        board.resetBoard();
                        action.playAction();
                        checkGameIsFinished();
                        gameReference.child("action").setValue(action.codeAction());
                    }
                    else {
                        if(board.isPieceClickable(row, col)) {
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_piece);
                            mediaPlayer.start();
                            action.sourceX = row;
                            action.sourceY = col;
                            board.updateItemClickable(row, col);
                        }
                    }
                }
            }
        });

        listenAction();
    }


    private void listenAction() {
        gameReference.child("action").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String actionCode = dataSnapshot.getValue(String.class);
                if(actionCode!=null) {
                    if(!currentUser.getUid().equals(turn)) {
                        action.decodeAction(actionCode);
                        int sourceCard = action.getCardResource();
                        if(turn.equals(blueID)) {
                            cardPlayerBlue.setImageResource(sourceCard);
                        }
                        else {
                            cardPlayerRed.setImageResource(sourceCard);
                        }
                        action.playAction();
                    }

                    checkGameIsFinished();

                    timer.cancel();
                    timePlayerBlue.setText("");
                    timePlayerRed.setText("");

                    if(turn.equals(blueID)){
                        turn = redID;
                    }
                    else {
                        turn = blueID;
                    }

                    if(currentUser.getUid().equals(turn)) {
                        countTurn++;
                        imageViewTurn.setVisibility(View.VISIBLE);
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_turn);
                        mediaPlayer.start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imageViewTurn.setVisibility(View.INVISIBLE);
                            }
                        }, 1000);
                        board.updatePieceClickable(true);
                        int sourceCard = action.createCard();
                        if(turn.equals(blueID)) {
                            cardPlayerBlue.setImageResource(sourceCard);
                        }
                        else {
                            cardPlayerRed.setImageResource(sourceCard);
                        }
                    }

                    timer = new CountDownTimer(31000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if(turn.equals(blueID)) {
                                timePlayerBlue.setText(millisUntilFinished/1000+"");
                                if(millisUntilFinished <= 11000) {
                                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_time);
                                    mediaPlayer.start();
                                }
                            }
                            else {
                                timePlayerRed.setText(millisUntilFinished/1000+"");
                                if(millisUntilFinished <= 11000) {
                                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_time);
                                    mediaPlayer.start();
                                }
                            }
                        }

                        @Override
                        public void onFinish() {
                            if(turn.equals(blueID)) {
                                timePlayerBlue.setText(0+"");
                                finishGame(false, true);
                                timer.cancel();
                            }
                            else {
                                timePlayerRed.setText(0+"");
                                finishGame(true, true);
                                timer.cancel();
                            }
                        }
                    }.start();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void checkGameIsFinished() {
        int check = board.checkGameFinished();
        if(check < 0)
            finishGame(false, false);
        if(check > 0)
            finishGame(true, false);
        if(board.redStarCount==10) {
            finishGame(false, false);
        }
        if(board.blueStarCount==10) {
            finishGame(true, false);
        }
    }


    private void finishGame(final boolean blueWin, final boolean isTimeOut) {
        if( (blueWin&&currentUser.getUid().equals(blueID)) || (!blueWin&&currentUser.getUid().equals(redID)) ) {
            //usersReference.child(blueID).child("game_entry").child(gameID).removeValue();
            //usersReference.child(redID).child("game_entry").child(gameID).removeValue();
            if(blueWin) {
                usersReference.child(blueID).child("games").child(gameID).setValue(true);
                usersReference.child(redID).child("games").child(gameID).setValue(false);
            }
            else {
                usersReference.child(blueID).child("games").child(gameID).setValue(false);
                usersReference.child(redID).child("games").child(gameID).setValue(true);
            }
            gameReference.child("blueID").setValue(blueID);
            gameReference.child("redID").setValue(redID);
            gameReference.child("winID").setValue(blueWin);
            if(isTimeOut) {
                gameReference.child("turns").setValue(Integer.toString(-1));
            }
            else {
                gameReference.child("turns").setValue(Integer.toString(countTurn));
            }
        }

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                intent.putExtra(BLUE_WIN, blueWin);
                intent.putExtra(TIME_OUT, isTimeOut);
                intent.putExtra(COUNT_TURN, countTurn);

                intent.putExtra(BLUE_ID, blueID);
                intent.putExtra(BLUE_NAME, blueName);
                intent.putExtra(BLUE_PHOTO, bluePhoto);
                intent.putExtra(BLUE_RANK,
                        Integer.parseInt(dataSnapshot.child(blueID).child("rank").getValue(String.class)));
                intent.putExtra(BLUE_SCORE,
                        Integer.parseInt(dataSnapshot.child(blueID).child("score").getValue(String.class)));
                intent.putExtra(BLUE_GAMES_PLAYED,
                        Integer.parseInt(dataSnapshot.child(blueID).child("games_played").getValue(String.class)));
                intent.putExtra(BLUE_GAMES_WON,
                        Integer.parseInt(dataSnapshot.child(blueID).child("games_won").getValue(String.class)));
                intent.putExtra(BLUE_GOLD,
                        Integer.parseInt(dataSnapshot.child(blueID).child("gold").getValue(String.class)));

                intent.putExtra(RED_ID, redID);
                intent.putExtra(RED_NAME, redName);
                intent.putExtra(RED_PHOTO, redPhoto);
                intent.putExtra(RED_RANK,
                        Integer.parseInt(dataSnapshot.child(redID).child("rank").getValue(String.class)));
                intent.putExtra(RED_SCORE,
                        Integer.parseInt(dataSnapshot.child(redID).child("score").getValue(String.class)));
                intent.putExtra(RED_GAMES_PLAYED,
                        Integer.parseInt(dataSnapshot.child(redID).child("games_played").getValue(String.class)));
                intent.putExtra(RED_GAMES_WON,
                        Integer.parseInt(dataSnapshot.child(redID).child("games_won").getValue(String.class)));
                intent.putExtra(RED_GOLD,
                        Integer.parseInt(dataSnapshot.child(redID).child("gold").getValue(String.class)));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY );
                startActivity(intent);
                GameActivity.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_title));
        builder.setMessage(getResources().getString(R.string.dialog_content));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                GameActivity.this.finish();
                GameActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.dialog_deccept), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;
            }
        });
        builder.show();
    }
}
