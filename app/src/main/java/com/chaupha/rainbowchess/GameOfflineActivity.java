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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameOfflineActivity extends Activity{
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

    private LinearLayout layoutMain;
    private LinearLayout layoutResult;
    private TextView textResult;
    private Button buttonTurns;
    private TextView textRecord;
    private TextView textMyRecord;
    private TextView textBonusGold;
    private Button buttonResultAccept;

    public static final String BLUE_NAME = "BLUE_NAME";
    public static final String BLUE_PHOTO = "BLUE_PHOTO";
    public static final String BLUE_DESC = "BLUE_DESC";
    public static final String RED_NAME = "RED_NAME";
    public static final String RECORD = "RECORD";
    public static final String MY_RECORD = "MY_RECORD";
    public static final String MY_GOLD = "MY_GOLD";
    private String blueName;
    private String bluePhoto;
    private String redName;
    public static int[] blueDesc;
    public static int[] redDesc;
    private int record;
    private int myRecord;
    private int myGold;

    public static final String RED = "RED";
    public static final String ORANGE = "ORANGE";
    public static final String YELLOW = "YELLOW";
    public static final String GREEN = "GREEN";
    public static final String BLUE = "BLUE";
    public static final String INDIGO = "INDIGO";
    public static final String VIOLET = "VIOLET";

    public static boolean pieceSelected = false;
    private int countTurn = 0;
    private String nameBoss;
    private int exitable = 0;

    public static final String blueID = "blueID";
    public static final String redID = "redID";
    public static String turn = blueID;

    FirebaseUser currentUser;
    DatabaseReference databaseReference;
    DatabaseReference usersReference;

    public static BoardOffline board;
    ActionOffline action;
    public static PieceOfflineAdapter pieceAdapter;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        blueName = intent.getStringExtra(BLUE_NAME);
        bluePhoto = intent.getStringExtra(BLUE_PHOTO);
        blueDesc = intent.getIntArrayExtra(BLUE_DESC);
        redName = intent.getStringExtra(RED_NAME);
        record = intent.getIntExtra(RECORD, -1);
        myRecord = intent.getIntExtra(MY_RECORD, -1);
        myGold = intent.getIntExtra(MY_GOLD, -1);

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

        layoutMain = findViewById(R.id.layoutMain);
        layoutResult = findViewById(R.id.layoutResult);
        textResult = findViewById(R.id.textResult);
        buttonTurns = findViewById(R.id.buttonTurns);
        textRecord = findViewById(R.id.textRecord);
        textMyRecord = findViewById(R.id.textMyRecord);
        textBonusGold = findViewById(R.id.textBonusGold);
        buttonResultAccept = findViewById(R.id.buttonResultAccept);

        namePlayerBlue.setText(blueName);
        Picasso.get().load(bluePhoto).into(imagePlayerBlue);
        namePlayerRed.setText(redName);
        if(redName.equals(RED))
            imagePlayerRed.setImageResource(R.drawable.icon_boss_red);
        else if(redName.equals(ORANGE))
            imagePlayerRed.setImageResource(R.drawable.icon_boss_orange);
        else if(redName.equals(YELLOW))
            imagePlayerRed.setImageResource(R.drawable.icon_boss_yellow);
        else if(redName.equals(GREEN))
            imagePlayerRed.setImageResource(R.drawable.icon_boss_green);
        else if(redName.equals(BLUE))
            imagePlayerRed.setImageResource(R.drawable.icon_boss_blue);
        else if(redName.equals(INDIGO))
            imagePlayerRed.setImageResource(R.drawable.icon_boss_indigo);
        else if(redName.equals(VIOLET))
            imagePlayerRed.setImageResource(R.drawable.icon_boss_violet);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        usersReference = databaseReference.child("users");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        getRedDesc();

        board = new BoardOffline();
        action = new ActionOffline(this);
        pieceAdapter = new PieceOfflineAdapter(this, board);
        gridView.setAdapter(pieceAdapter);

        MediaPlayer.create(getApplicationContext(), R.raw.sound_turn).start();
        board.updatePieceClickable();

        int sourceCard = action.createCard();
        cardPlayerBlue.setImageResource(sourceCard);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
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
                        gridView.setClickable(false);
                        listenAction();
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
    }


    private void listenAction() {
        countTurn++;
        turn = redID;
        int sourceCard = action.createCard();
        cardPlayerRed.setImageResource(sourceCard);

        int maxPiecesDeleted = -1;
        int okSourceX = -1;
        int okSourceY = -1;
        int okTargetX = -1;
        int okTargetY = -1;
        ArrayList<BoardOffline.Position> listSource = new ArrayList<>();
        ArrayList<BoardOffline.Position> listTarget = new ArrayList<>();

        board.updatePieceClickable();
        listSource = board.pieceClickable;
        checkGameIsFinished();

        boolean exit = false;
        for (BoardOffline.Position source : listSource) {
            if(exit) break;
            board.updateItemClickable(source.row, source.col);
            listTarget = board.itemClickable;
            for (BoardOffline.Position target : listTarget) {
                action.sourceX = source.row;
                action.sourceY = source.col;
                action.targetX = target.row;
                action.targetY = target.col;

                if((action.targetX == BoardOffline.SIZE-1)) {
                    okSourceX = source.row;
                    okSourceY = source.col;
                    okTargetX = target.row;
                    okTargetY = target.col;
                    exit = true;
                    break;
                }
                if(action.getPieceDelete() > maxPiecesDeleted) {
                    maxPiecesDeleted = action.getPieceDelete();
                    okSourceX = source.row;
                    okSourceY = source.col;
                    okTargetX = target.row;
                    okTargetY = target.col;
                }
            }
        }

        action.sourceX = okSourceX;
        action.sourceY = okSourceY;
        action.targetX = okTargetX;
        action.targetY = okTargetY;
        pieceSelected = true;
        board.updateItemClickable(action.sourceX, action.sourceY);
        pieceSelected = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                action.playAction();
                turn = blueID;
                board.updatePieceClickable();
                checkGameIsFinished();
                int sourceCard = action.createCard();
                cardPlayerBlue.setImageResource(sourceCard);
                gridView.setClickable(true);
                MediaPlayer.create(getApplicationContext(), R.raw.sound_turn).start();
            }
        }, 1000);
    }


    private void checkGameIsFinished() {
        if(board.pieceClickable.size() == 0)
            if(turn.equals(blueID))
                finishGame(false);
            else
                finishGame(true);
        if(board.redStarCount==10)
            finishGame(false);
        if(board.blueStarCount==10)
            finishGame(true);
    }


    private void finishGame(final boolean won) {
        if(won)
            MediaPlayer.create(this, R.raw.sound_win).start();
        else
            MediaPlayer.create(this, R.raw.sound_lose).start();
        layoutMain.setAlpha(0.5f);
        layoutResult.setVisibility(View.VISIBLE);
        exitable = 1;
        defineBoss();
        if(!won) {
            textResult.setText(getResources().getString(R.string.result_lose));
            textBonusGold.setText("+0");
        }
        else if(countTurn < record) {
            textResult.setText(getResources().getString(R.string.result_new_record));
            databaseReference.child("records").child(nameBoss).setValue(String.valueOf(countTurn));
            usersReference.child(currentUser.getUid()).child("gold").setValue(String.valueOf(myGold + 100));
            textBonusGold.setText("+100");
            usersReference.child(currentUser.getUid()).child("records").child(nameBoss).setValue(String.valueOf(countTurn));
        }
        else if(countTurn < myRecord) {
            textResult.setText(getResources().getString(R.string.result_new_archive));
            usersReference.child(currentUser.getUid()).child("records").child(nameBoss).setValue(String.valueOf(countTurn));
            usersReference.child(currentUser.getUid()).child("gold").setValue(String.valueOf(myGold + 10));
            textBonusGold.setText("+10");
        }
        else {
            textResult.setText(getResources().getString(R.string.result_win));
            usersReference.child(currentUser.getUid()).child("gold").setValue(String.valueOf(myGold + 10));
            textBonusGold.setText("+10");
        }
        buttonTurns.setText(String.valueOf(countTurn));
        String buffer = "";
        if(record == 1000)
            buffer = "--.--";
        else
            buffer = String.valueOf(record);
        textRecord.setText(buffer);
        if(myRecord == 1000)
            buffer = "--.--";
        else
            buffer = String.valueOf(myRecord);
        textMyRecord.setText(buffer);
        buttonResultAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                usersReference.child(currentUser.getUid()).child("online").setValue("1");
                GameOfflineActivity.this.finish();
            }
        });
    }


    private void getRedDesc() {
        if(redName.equals(RED))
            redDesc = new int[] {1,2,3,4,5,6,7,8,9,10,1,2,3};
        else if(redName.equals(ORANGE))
            redDesc = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13};
        else if(redName.equals(YELLOW))
            redDesc = new int[] {1,2,3,4,5,6,7,8,9,10,20,21,26};
        else if(redName.equals(GREEN))
            redDesc = new int[] {5,6,7,8,9,10,11,12,13,14,15,20,21};
        else if(redName.equals(BLUE))
            redDesc = new int[] {9,10,11,12,13,14,15,16,17,18,19,20,21};
        else if(redName.equals(INDIGO))
            redDesc = new int[] {12,13,14,15,16,17,18,19,20,21,22,23,24};
        else
            redDesc = new int[] {16,17,18,19,20,21,22,23,24,25,26,27,11};
    }


    private void defineBoss() {
        if(redName.equals(RED)) {
            nameBoss = "bossA";
            buttonTurns.setBackgroundResource(R.drawable.icon_boss_red);
        }
        else if(redName.equals(ORANGE)) {
            nameBoss = "bossB";
            buttonTurns.setBackgroundResource(R.drawable.icon_boss_orange);
        }
        else if(redName.equals(YELLOW)) {
            nameBoss = "bossC";
            buttonTurns.setBackgroundResource(R.drawable.icon_boss_yellow);
        }
        else if(redName.equals(GREEN)) {
            nameBoss = "bossD";
            buttonTurns.setBackgroundResource(R.drawable.icon_boss_green);
        }
        else if(redName.equals(BLUE)) {
            nameBoss = "bossE";
            buttonTurns.setBackgroundResource(R.drawable.icon_boss_blue);
        }
        else if(redName.equals(INDIGO)) {
            nameBoss = "bossF";
            buttonTurns.setBackgroundResource(R.drawable.icon_boss_indigo);
        }
        else {
            nameBoss = "bossG";
            buttonTurns.setBackgroundResource(R.drawable.icon_boss_violet);
        }
    }


    @Override
    public void onBackPressed() {
        if(exitable == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.dialog_title));
            builder.setMessage(getResources().getString(R.string.dialog_content));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    usersReference.child(currentUser.getUid()).child("online").setValue("1");
                    GameOfflineActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.dialog_deccept), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            });
            builder.show();
        }
        if(exitable == 1) {
            layoutMain.setAlpha(1.0f);
            layoutResult.setVisibility(View.INVISIBLE);
            exitable = 2;
            return;
        }
        else if(exitable == 2) {
            GameOfflineActivity.super.onBackPressed();
        }
    }
}
