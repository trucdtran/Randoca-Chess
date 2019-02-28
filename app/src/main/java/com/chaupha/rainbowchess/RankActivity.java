package com.chaupha.rainbowchess;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import java.util.Random;

public class RankActivity extends AppCompatActivity {

    private ImageView imageCategory;
    private ImageView imageOpenSelector;
    private TextView textCategory;

    private RecyclerView recyclerView;
    private User user;
    private UserAdapter userAdapter;
    private ArrayList<User> users;

    private ImageView imageMyPhoto;
    private TextView textMyOrder;
    private TextView textMyName;
    private TextView textMyStatistic;

    private LinearLayout layoutSelector;
    private RadioButton radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10, radio11, radio12;

    public static final String MY_NAME = "MY_NAME";
    public static final String MY_PHOTO = "MY_PHOTO";
    private String myName;
    private String myPhoto;

    public static final int CATEGORY_RANK = 1;
    public static final int CATEGORY_WINPER = 2;
    public static final int CATEGORY_DESCPOWER = 3;
    public static final int CATEGORY_GOLD = 4;
    public static final int CATEGORY_GAMESPLAYED = 5;
    public static final int CATEGORY_BOSSA = 6;
    public static final int CATEGORY_BOSSB = 7;
    public static final int CATEGORY_BOSSC = 8;
    public static final int CATEGORY_BOSSD = 9;
    public static final int CATEGORY_BOSSE = 10;
    public static final int CATEGORY_BOSSF = 11;
    public static final int CATEGORY_BOSSG = 12;
    public static int category = 1;
    public static boolean selectorOpened = false;


    FirebaseUser currentUser;
    DatabaseReference usersReference;

    private User xxxUser;

    private DatabaseReference ref;
    private ChildEventListener childEventListener;
    private int[] descBlue = new int[13];
    private int[] descRed = new int[13];

    private String gameID = "";
    private String blueID = "";
    private String redID = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        Intent intent = getIntent();
        myName = intent.getStringExtra(MY_NAME);
        myPhoto = intent.getStringExtra(MY_PHOTO);

        recyclerView = findViewById(R.id.rank_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        imageCategory = findViewById(R.id.rank_imageCategory);
        imageOpenSelector = findViewById(R.id.rank_imageOpenSelector);
        textCategory = findViewById(R.id.rank_textCategory);

        imageMyPhoto = findViewById(R.id.rank_imageMyPhoto);
        textMyOrder = findViewById(R.id.rank_textMyOrder);
        textMyName = findViewById(R.id.rank_textMyName);
        textMyStatistic = findViewById(R.id.rank_textMyStatistic);

        layoutSelector = findViewById(R.id.layoutSelector);
        radio1 = findViewById(R.id.rank_radio1);
        radio2 = findViewById(R.id.rank_radio2);
        radio3 = findViewById(R.id.rank_radio3);
        radio4 = findViewById(R.id.rank_radio4);
        radio5 = findViewById(R.id.rank_radio5);
        radio6 = findViewById(R.id.rank_radio6);
        radio7 = findViewById(R.id.rank_radio7);
        radio8 = findViewById(R.id.rank_radio8);
        radio9 = findViewById(R.id.rank_radio9);
        radio10 = findViewById(R.id.rank_radio10);
        radio11 = findViewById(R.id.rank_radio11);
        radio12 = findViewById(R.id.rank_radio12);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");

        textMyName.setText(myName);
        Picasso.get().load(myPhoto).into(imageMyPhoto);

        users = new ArrayList<>();
        listenForAllUsers();
        userAdapter = new UserAdapter(this, users, UserAdapter.MODE_VIEW);
        recyclerView.setAdapter(userAdapter);
        radio1.setChecked(true);

        imageOpenSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                if(selectorOpened) {
                    layoutSelector.setVisibility(View.INVISIBLE);
                    imageOpenSelector.setImageResource(R.drawable.icon_open);
                }
                else {
                    imageOpenSelector.setImageResource(R.drawable.icon_close);
                    layoutSelector.setVisibility(View.VISIBLE);
                }
                selectorOpened = !selectorOpened;
            }
        });

        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio1.setChecked(true);
                category = CATEGORY_RANK;
                textCategory.setText(getResources().getString(R.string.profile_rank));
                imageCategory.setImageResource(R.drawable.icon_star_10);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio2.setChecked(true);
                category = CATEGORY_WINPER;
                textCategory.setText(getResources().getString(R.string.profile_win_per));
                imageCategory.setImageResource(R.drawable.icon_star_10);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio3.setChecked(true);
                category = CATEGORY_DESCPOWER;
                textCategory.setText(getResources().getString(R.string.profile_desc));
                imageCategory.setImageResource(R.drawable.icon_star_10);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio4.setChecked(true);
                category = CATEGORY_GOLD;
                textCategory.setText(getResources().getString(R.string.profile_gold));
                imageCategory.setImageResource(R.drawable.icon_star_10);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio5.setChecked(true);
                category = CATEGORY_GAMESPLAYED;
                textCategory.setText(getResources().getString(R.string.profile_games_played));
                imageCategory.setImageResource(R.drawable.icon_star_10);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio6.setChecked(true);
                category = CATEGORY_BOSSA;
                textCategory.setText(getResources().getString(R.string.profile_record_a));
                imageCategory.setImageResource(R.drawable.icon_boss_red);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio7.setChecked(true);
                category = CATEGORY_BOSSB;
                textCategory.setText(getResources().getString(R.string.profile_record_b));
                imageCategory.setImageResource(R.drawable.icon_boss_orange);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio8.setChecked(true);
                category = CATEGORY_BOSSC;
                textCategory.setText(getResources().getString(R.string.profile_record_c));
                imageCategory.setImageResource(R.drawable.icon_boss_yellow);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio9.setChecked(true);
                category = CATEGORY_BOSSD;
                textCategory.setText(getResources().getString(R.string.profile_record_d));
                imageCategory.setImageResource(R.drawable.icon_boss_green);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio10.setChecked(true);
                category = CATEGORY_BOSSE;
                textCategory.setText(getResources().getString(R.string.profile_record_e));
                imageCategory.setImageResource(R.drawable.icon_boss_blue);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio11.setChecked(true);
                category = CATEGORY_BOSSF;
                textCategory.setText(getResources().getString(R.string.profile_record_f));
                imageCategory.setImageResource(R.drawable.icon_boss_indigo);
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
            }
        });

        radio12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetRadio();
                radio12.setChecked(true);
                category = CATEGORY_BOSSG;
                textCategory.setText(getResources().getString(R.string.profile_record_g));
                imageCategory.setImageResource(R.drawable.icon_boss_violet );
                imageOpenSelector.performClick();
                sortListByCategory();
                setCurrentUser();
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
                blueID = str.substring(0, indexStr);
                redID = str.substring(indexStr+1);

                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent intent = new Intent(RankActivity.this, GameActivity.class);
                        intent.putExtra(GameActivity.GAME_ID, gameID);
                        intent.putExtra(GameActivity.BLUE_ID, blueID);
                        intent.putExtra(GameActivity.BLUE_NAME, dataSnapshot.child(blueID).child("name").getValue(String.class));
                        intent.putExtra(GameActivity.BLUE_PHOTO, dataSnapshot.child(blueID).child("photo_uri").getValue(String.class));
                        intent.putExtra(GameActivity.RED_ID, redID);
                        intent.putExtra(GameActivity.RED_NAME, dataSnapshot.child(redID).child("name").getValue(String.class));
                        intent.putExtra(GameActivity.RED_PHOTO, dataSnapshot.child(redID).child("photo_uri").getValue(String.class));
                        int i = 0;
                        for (DataSnapshot snapshot : dataSnapshot.child(blueID).child("desc").getChildren()) {
                            descBlue[i] = Integer.valueOf(snapshot.getValue(String.class));
                            i++;
                        }
                        i = 0;
                        for (DataSnapshot snapshot : dataSnapshot.child(redID).child("desc").getChildren()) {
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


    public void listenForAllUsers() {
        usersReference.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    if (snapshot.child("name").getValue(String.class) == null)
                        continue;
                    if (snapshot.child("photo_uri").getValue(String.class) == null)
                        continue;
                    if (snapshot.child("rank").getValue(String.class) == null)
                        continue;
                    if (snapshot.child("score").getValue(String.class) == null)
                        continue;
                    if (snapshot.child("games_played").getValue(String.class) == null)
                        continue;
                    if (snapshot.child("games_won").getValue(String.class) == null)
                        continue;
                    if (snapshot.child("gold").getValue(String.class) == null)
                        continue;
                    if (snapshot.child("desc_power").getValue(String.class) == null)
                        continue;
                    String name = snapshot.child("name").getValue(String.class);
                    Uri photo_uri = Uri.parse(snapshot.child("photo_uri").getValue(String.class));
                    int rank = Integer.valueOf(snapshot.child("rank").getValue(String.class));
                    int score = Integer.valueOf(snapshot.child("score").getValue(String.class));
                    int games_played = Integer.valueOf(snapshot.child("games_played").getValue(String.class));
                    int games_won = Integer.valueOf(snapshot.child("games_won").getValue(String.class));
                    long gold = Long.valueOf(snapshot.child("gold").getValue(String.class));
                    int desc_power = Integer.valueOf(snapshot.child("desc_power").getValue(String.class));
                    ArrayList<Integer> records = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.child("records").getChildren()) {
                        int record = Integer.valueOf(snap.getValue(String.class));
                        records.add(record);
                    }
                    ArrayList<String> friends = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.child("friends").getChildren()) {
                        String friend = snap.getKey();
                        friends.add(friend);
                    }
                    ArrayList<Request> gameRequests = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.child("game_requests").getChildren()) {
                        String sourceUid = snap.getKey();
                        String time = snap.getValue(String.class);
                        String targetUid = currentUser.getUid();
                        Request gameRequest = new Request(sourceUid, targetUid, time);
                        gameRequests.add(gameRequest);
                    }
                    ArrayList<Request> friendRequests = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.child("friend_requests").getChildren()) {
                        String sourceUid = snap.getKey();
                        String time = snap.getValue(String.class);
                        String targetUid = currentUser.getUid();
                        Request friendRequest = new Request(sourceUid, targetUid, time);
                        friendRequests.add(friendRequest);
                    }

                    User user = new User(getApplicationContext(), name, photo_uri, uid, rank, score, games_played, games_won,
                            gold, desc_power, records, friends, gameRequests, friendRequests);
                    if (currentUser.getUid().equals(uid))
                        RankActivity.this.user = user;
                    users.add(user);
                }
                sortListByCategory();
                setCurrentUser();
                invalidateOptionsMenu();
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void sortListByCategory() {
        switch (category) {
            case CATEGORY_RANK: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getRank() < users.get(j+1).getRank()) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                        else if((users.get(j).getRank() == users.get(j+1).getRank()) &&
                                (users.get(j).getScore() < users.get(j+1).getScore())) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_WINPER: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getWinPercent() < users.get(j+1).getWinPercent()) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                        else if((users.get(j).getWinPercent() == users.get(j+1).getWinPercent()) &&
                                (users.get(j).getGamesPlayed() < users.get(j+1).getGamesPlayed())) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                        if(users.get(j).getGamesPlayed() < 50) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_DESCPOWER: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getDescPower() < users.get(j+1).getDescPower()) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_GOLD: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getGold() < users.get(j+1).getGold()) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_GAMESPLAYED: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getGamesPlayed() < users.get(j+1).getGamesPlayed()) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_BOSSA: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getRecords().get(0) > users.get(j+1).getRecords().get(0)) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_BOSSB: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getRecords().get(1) > users.get(j+1).getRecords().get(1)) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_BOSSC: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getRecords().get(2) > users.get(j+1).getRecords().get(2)) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_BOSSD: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getRecords().get(3) > users.get(j+1).getRecords().get(3)) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_BOSSE: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getRecords().get(4) > users.get(j+1).getRecords().get(4)) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_BOSSF: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getRecords().get(5) > users.get(j+1).getRecords().get(5)) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }

            case CATEGORY_BOSSG: {
                for(int i=0; i < users.size()-1; i++) {
                    for(int j=0; j < users.size()-1-i; j++) {
                        if(users.get(j).getRecords().get(6) > users.get(j+1).getRecords().get(6)) {
                            User temp = users.get(j);
                            users.set(j, users.get(j+1));
                            users.set(j+1, temp);
                        }
                    }
                }
                break;
            }
        }
        userAdapter.notifyDataSetChanged();
    }


    private void setCurrentUser() {
        int order = -1;
        String buffer = "";
        for(User user : users) {
            if(user.getUid().equals(this.user.getUid())) {
                order = users.indexOf(user) + 1;
                switch (RankActivity.category) {
                    case RankActivity.CATEGORY_RANK: {
                        buffer = user.getRankString() + " (" + user.getScore() + "/" + user.getLimitScore() + ")";
                        textMyStatistic.setText(buffer);
                        break;
                    }

                    case RankActivity.CATEGORY_WINPER: {
                        if(user.getGamesPlayed() < 50) {
                            buffer = "--.--";
                        }
                        else {
                            buffer = getResources().getString(R.string.profile_win_per) + ": " + user.getWinPercent() + "%";
                        }
                        textMyStatistic.setText(buffer);
                        break;
                    }

                    case RankActivity.CATEGORY_DESCPOWER: {
                        buffer = getResources().getString(R.string.profile_desc) + ": " + user.getDescPower();
                        textMyStatistic.setText(buffer);
                        break;
                    }

                    case RankActivity.CATEGORY_GOLD: {
                        buffer = getResources().getString(R.string.profile_gold) + ": " + user.getGold();
                        textMyStatistic.setText(buffer);
                        break;
                    }

                    case RankActivity.CATEGORY_GAMESPLAYED: {
                        buffer = getResources().getString(R.string.profile_games_played) + ": " + user.getGamesPlayed();
                        textMyStatistic.setText(buffer);
                        break;
                    }

                    case RankActivity.CATEGORY_BOSSA: {
                        if(user.getRecords().get(0) == 1000) {
                            textMyStatistic.setText(getResources().getString(R.string.profile_fail));
                        }
                        else {
                            buffer = getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(0) + " " +
                                    getResources().getString(R.string.profile_record_suffix);
                            textMyStatistic.setText(buffer);
                        }
                        break;
                    }

                    case RankActivity.CATEGORY_BOSSB: {
                        if(user.getRecords().get(1) == 1000) {
                            textMyStatistic.setText(getResources().getString(R.string.profile_fail));
                        }
                        else {
                            buffer = getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(1) + " " +
                                    getResources().getString(R.string.profile_record_suffix);
                            textMyStatistic.setText(buffer);
                        }
                        break;
                    }

                    case RankActivity.CATEGORY_BOSSC: {
                        if(user.getRecords().get(2) == 1000) {
                            textMyStatistic.setText(getResources().getString(R.string.profile_fail));
                        }
                        else {
                            buffer = getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(2) + " " +
                                    getResources().getString(R.string.profile_record_suffix);
                            textMyStatistic.setText(buffer);
                        }
                        break;
                    }

                    case RankActivity.CATEGORY_BOSSD: {
                        if(user.getRecords().get(3) == 1000) {
                            textMyStatistic.setText(getResources().getString(R.string.profile_fail));
                        }
                        else {
                            buffer = getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(3) + " " +
                                    getResources().getString(R.string.profile_record_suffix);
                            textMyStatistic.setText(buffer);
                        }
                        break;
                    }

                    case RankActivity.CATEGORY_BOSSE: {
                        if(user.getRecords().get(4) == 1000) {
                            textMyStatistic.setText(getResources().getString(R.string.profile_fail));
                        }
                        else {
                            buffer = getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(4) + " " +
                                    getResources().getString(R.string.profile_record_suffix);
                            textMyStatistic.setText(buffer);
                        }
                        break;
                    }

                    case RankActivity.CATEGORY_BOSSF: {
                        if(user.getRecords().get(5) == 1000) {
                            textMyStatistic.setText(getResources().getString(R.string.profile_fail));
                        }
                        else {
                            buffer = getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(5) + " " +
                                    getResources().getString(R.string.profile_record_suffix);
                            textMyStatistic.setText(buffer);
                        }
                        break;
                    }

                    case RankActivity.CATEGORY_BOSSG: {
                        if(user.getRecords().get(6) == 1000) {
                            textMyStatistic.setText(getResources().getString(R.string.profile_fail));
                        }
                        else {
                            buffer = getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(6) + " " +
                                    getResources().getString(R.string.profile_record_suffix);
                            textMyStatistic.setText(buffer);
                        }
                        break;
                    }
                }
            }
        }
        textMyOrder.setText(String.valueOf(order));
        textMyStatistic.setText(buffer);
    }


    private void resetRadio() {
        radio1.setChecked(false);
        radio2.setChecked(false);
        radio3.setChecked(false);
        radio4.setChecked(false);
        radio5.setChecked(false);
        radio6.setChecked(false);
        radio7.setChecked(false);
        radio8.setChecked(false);
        radio9.setChecked(false);
        radio10.setChecked(false);
        radio11.setChecked(false);
        radio12.setChecked(false);
    }


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

                xxxUser = new User(getApplicationContext(), name, photo_uri, uid, rank, online, games, friends,
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
        if(xxxUser == null || xxxUser.getGameRequests().size() == 0) {
            itemGameRequest.setIcon(R.drawable.icon_notification_off);
        }
        else {
            itemGameRequest.setIcon(R.drawable.icon_notification_on);
        }

        MenuItem itemFriendRequest = menu.findItem(R.id.menuFriendRequests);
        if(xxxUser == null || xxxUser.getFriendRequests().size() == 0) {
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
            xxxUser = null;
            startActivity(new Intent(RankActivity.this, LoginActivity.class));
            finish();
        }
        else if(item.getItemId() == R.id.menuGameRequests && xxxUser.getGameRequests().size() != 0) {
            Intent intent = new Intent(RankActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.GAME_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getGameRequests());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menuFriendRequests && xxxUser.getFriendRequests().size() != 0) {
            Intent intent = new Intent(RankActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.FRIEND_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getFriendRequests());
            startActivity(intent);
        }
        return true;
    }
}
