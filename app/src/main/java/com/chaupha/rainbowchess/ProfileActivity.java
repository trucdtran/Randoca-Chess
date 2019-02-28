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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ProfileActivity extends AppCompatActivity {

    private ImageView imagePhoto;
    private TextView textName;

    private RecyclerView recyclerView;
    private User user;
    private ArrayList<User> users;
    private ArrayList<User> friends;
    private UserAdapter userAdapter;

    private Button buttonFriends;
    private Button buttonStatistics;
    private Button buttonRanks;
    private Button buttonRecords;

    private ArrayList<ImageView> listStars;
    private ImageView imageStar1;
    private ImageView imageStar2;
    private ImageView imageStar3;
    private ImageView imageStar4;
    private ImageView imageStar5;
    private ImageView imageStar6;
    private ImageView imageStar7;
    private ImageView imageStar8;
    private ImageView imageStar9;
    private ImageView imageStar10;

    private TextView text11;
    private TextView text1;
    private TextView text12;
    private TextView text2;
    private TextView text13;
    private TextView text3;
    private TextView text14;
    private TextView text4;
    private TextView text15;
    private TextView text5;
    private TextView text16;
    private TextView text6;
    private TextView text17;
    private TextView text7;
    private ImageView imageGold;

    private LinearLayout layoutAll;
    private LinearLayout layoutDelete1;
    private LinearLayout layoutDelete2;

    public static final String PROFILE_ID = "PROFILE_ID";
    public static final String PROFILE_NAME = "PROFILE_NAME";
    public static final String PROFILE_PHOTO = "PROFILE_PHOTO";
    public static final String PROFILE_RANK = "PROFILE_RANK";
    private String profileID;
    private String profileName;
    private String profilePhoto;
    private int profileRank;

    private FirebaseUser currentUser;
    private DatabaseReference usersReference;

    int mode;
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
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        profileID = intent.getStringExtra(PROFILE_ID);
        profileName = intent.getStringExtra(PROFILE_NAME);
        profilePhoto = intent.getStringExtra(PROFILE_PHOTO);
        profileRank = intent.getIntExtra(PROFILE_RANK, 0);

        imagePhoto = findViewById(R.id.profile_imagePhoto);
        textName = findViewById(R.id.profile_textName);

        recyclerView = findViewById(R.id.profile_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        buttonFriends = findViewById(R.id.profile_buttonMyFrieds);
        buttonStatistics = findViewById(R.id.profile_buttonStatistics);
        buttonRanks = findViewById(R.id.profile_buttonRanks);
        buttonRecords = findViewById(R.id.profile_buttonRecords);

        listStars = new ArrayList<>();
        imageStar1 = findViewById(R.id.profile_imageStar1);
        imageStar2 = findViewById(R.id.profile_imageStar2);
        imageStar3 = findViewById(R.id.profile_imageStar3);
        imageStar4 = findViewById(R.id.profile_imageStar4);
        imageStar5 = findViewById(R.id.profile_imageStar5);
        imageStar6 = findViewById(R.id.profile_imageStar6);
        imageStar7 = findViewById(R.id.profile_imageStar7);
        imageStar8 = findViewById(R.id.profile_imageStar8);
        imageStar9 = findViewById(R.id.profile_imageStar9);
        imageStar10 = findViewById(R.id.profile_imageStar10);
        listStars.add(imageStar1);
        listStars.add(imageStar2);
        listStars.add(imageStar3);
        listStars.add(imageStar4);
        listStars.add(imageStar5);
        listStars.add(imageStar6);
        listStars.add(imageStar7);
        listStars.add(imageStar8);
        listStars.add(imageStar9);
        listStars.add(imageStar10);

        text1 = findViewById(R.id.profile_text1);
        text2 = findViewById(R.id.profile_text2);
        text3 = findViewById(R.id.profile_text3);
        text4 = findViewById(R.id.profile_text4);
        text5 = findViewById(R.id.profile_text5);
        text6 = findViewById(R.id.profile_text6);
        text7 = findViewById(R.id.profile_text7);
        text11 = findViewById(R.id.profile_text11);
        text12 = findViewById(R.id.profile_text12);
        text13 = findViewById(R.id.profile_text13);
        text14 = findViewById(R.id.profile_text14);
        text15 = findViewById(R.id.profile_text15);
        text16 = findViewById(R.id.profile_text16);
        text17 = findViewById(R.id.profile_text17);
        imageGold = findViewById(R.id.profile_imageGold);

        layoutAll = findViewById(R.id.profile_layoutAll);
        layoutDelete1 = findViewById(R.id.profile_layoutDelele1);
        layoutDelete2 = findViewById(R.id.profile_layoutDelete2);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");

        Picasso.get().load(profilePhoto).into(imagePhoto);
        textName.setText(profileName);
        displayRankStar();

        users = new ArrayList<>();
        friends = new ArrayList<>();
        mode = UserAdapter.MODE_VIEW;
        userAdapter = new UserAdapter(this,  friends, mode);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        listenForAllUsers();
        userAdapter.notifyDataSetChanged();

        buttonFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetButtons();
                buttonFriends.setTextColor(getResources().getColor(R.color.color_text));
                buttonFriends.setBackgroundColor(getResources().getColor(R.color.color_level_7));
                recyclerView.setVisibility(View.VISIBLE);
                userAdapter.notifyDataSetChanged();
            }
        });

        buttonStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetButtons();
                buttonStatistics.setTextColor(getResources().getColor(R.color.color_text));
                buttonStatistics.setBackgroundColor(getResources().getColor(R.color.color_level_7));

                text11.setText(getResources().getString(R.string.result_rank));
                text12.setText(getResources().getString(R.string.result_score));
                text13.setText(getResources().getString(R.string.result_win_per));
                text14.setText(getResources().getString(R.string.result_games_won));
                text15.setText(getResources().getString(R.string.result_games_played));
                text16.setText(getResources().getString(R.string.result_desc_power));
                text17.setText(getResources().getString(R.string.result_gold));

                text1.setText(user.getRankString());
                text2.setText(String.valueOf(user.getScore()));
                String buffer = String.valueOf(user.getWinPercent()) + "%";
                text3.setText(buffer);
                text4.setText(String.valueOf(user.getGamesWon()));
                text5.setText(String.valueOf(user.getGamesPlayed()));
                text6.setText(String.valueOf(user.getDescPower()));
                text7.setText(String.valueOf(user.getGold()));
            }
        });

        buttonRanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetButtons();
                buttonRanks.setTextColor(getResources().getColor(R.color.color_text));
                buttonRanks.setBackgroundColor(getResources().getColor(R.color.color_level_7));
                layoutDelete1.setVisibility(View.GONE);
                layoutDelete2.setVisibility(View.GONE);
                imageGold.setVisibility(View.GONE);
                layoutAll.setWeightSum(6);

                text11.setText(getResources().getString(R.string.profile_rank));
                text12.setText(getResources().getString(R.string.profile_win_per));
                text13.setText(getResources().getString(R.string.profile_desc));
                text14.setText(getResources().getString(R.string.profile_gold));
                text15.setText(getResources().getString(R.string.profile_games_played));

                int index = 1;
                String buffer;
                for (User x : users) {
                    if(x.getRank() > user.getRank())
                        index++;
                    if((x.getRank() == user.getRank()) && (x.getScore() > user.getScore()))
                        index++;
                    if((x.getRank() == user.getRank()) && (x.getScore() == user.getScore() && x.getName().compareTo(user.getName()) < 0))
                        index++;
                }
                buffer = getResources().getString(R.string.profile_rank_prefix) + " " + index;
                text1.setText(buffer);

                if(user.getGamesPlayed() < 50)
                    text2.setText(getResources().getString(R.string.profile_50_games));
                else {
                    index = 1;
                    for (User x : users) {
                        if (x.getGamesPlayed() < 50)
                            continue;
                        if (x.getWinPercent() > user.getWinPercent())
                            index++;
                        if ((x.getWinPercent() == user.getWinPercent()) && (x.getGamesPlayed() > user.getGamesPlayed()))
                            index++;
                        if ((x.getWinPercent() == user.getWinPercent()) && (x.getGamesPlayed() == user.getGamesPlayed()) &&
                                x.getName().compareTo(user.getName()) < 0)
                            index++;
                    }
                    buffer = getResources().getString(R.string.profile_rank_prefix) + " " + index;
                    text2.setText(buffer);
                }

                index = 1;
                for (User x : users) {
                    if(x.getDescPower() > user.getDescPower())
                        index++;
                    if((x.getDescPower() == user.getDescPower()) && x.getName().compareTo(user.getName()) < 0)
                        index++;
                }
                buffer = getResources().getString(R.string.profile_rank_prefix) + " " + index;
                text3.setText(buffer);

                index = 1;
                for (User x : users) {
                    if(x.getGold() > user.getGold())
                        index++;
                    if((x.getGold() == user.getGold()) && x.getName().compareTo(user.getName()) < 0)
                        index++;
                }
                buffer = getResources().getString(R.string.profile_rank_prefix) + " " + index;
                text4.setText(buffer);

                index = 1;
                for (User x : users) {
                    if(x.getGamesPlayed() > user.getGamesPlayed())
                        index++;
                    if((x.getGamesPlayed() == user.getGamesPlayed()) && x.getName().compareTo(user.getName()) < 0)
                        index++;
                }
                buffer = getResources().getString(R.string.profile_rank_prefix) + " " + index;
                text5.setText(buffer);
            }
        });

        buttonRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                resetButtons();
                buttonRecords.setTextColor(getResources().getColor(R.color.color_text));
                buttonRecords.setBackgroundColor(getResources().getColor(R.color.color_level_7));
                imageGold.setVisibility(View.GONE);

                text11.setText(getResources().getString(R.string.profile_record_a));
                text12.setText(getResources().getString(R.string.profile_record_b));
                text13.setText(getResources().getString(R.string.profile_record_c));
                text14.setText(getResources().getString(R.string.profile_record_d));
                text15.setText(getResources().getString(R.string.profile_record_e));
                text16.setText(getResources().getString(R.string.profile_record_f));
                text17.setText(getResources().getString(R.string.profile_record_g));

                String buffer;
                String prefix = getResources().getString(R.string.profile_record_prefix);
                String suffix = getResources().getString(R.string.profile_record_suffix);
                if(user.getRecords().get(0) == 1000)
                    text1.setText(getResources().getString(R.string.profile_fail));
                else {
                    buffer = prefix + " " + user.getRecords().get(0) + " " + suffix;
                    text1.setText(buffer);
                }
                if(user.getRecords().get(1) == 1000)
                    text2.setText(getResources().getString(R.string.profile_fail));
                else {
                    buffer = prefix + " " + user.getRecords().get(1) + " " + suffix;
                    text2.setText(buffer);
                }
                if(user.getRecords().get(2) == 1000)
                    text3.setText(getResources().getString(R.string.profile_fail));
                else {
                    buffer = prefix + " " + user.getRecords().get(2) + " " +  suffix;
                    text3.setText(buffer);
                }
                if(user.getRecords().get(3) == 1000)
                    text4.setText(getResources().getString(R.string.profile_fail));
                else {
                    buffer = prefix + " " + user.getRecords().get(3) + " " + suffix;
                    text4.setText(buffer);
                }
                if(user.getRecords().get(4) == 1000)
                    text5.setText(getResources().getString(R.string.profile_fail));
                else {
                    buffer = prefix + " " + user.getRecords().get(4) + " " + suffix;
                    text5.setText(buffer);
                }
                if(user.getRecords().get(5) == 1000)
                    text6.setText(getResources().getString(R.string.profile_fail));
                else {
                    buffer = prefix + " " + user.getRecords().get(5) + " " + suffix;
                    text6.setText(buffer);
                }
                if(user.getRecords().get(6) == 1000)
                    text7.setText(getResources().getString(R.string.profile_fail));
                else {
                    buffer = prefix + " " + user.getRecords().get(6) + " " + suffix;
                    text7.setText(buffer);
                }
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
                        Intent intent = new Intent(ProfileActivity.this, GameActivity.class);
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
                    if (profileID.equals(uid))
                        ProfileActivity.this.user = user;
                    else
                        users.add(user);
                }
                loadFriends();
                invalidateOptionsMenu();
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void resetButtons() {
        buttonStatistics.setTextColor(getResources().getColor(R.color.color_text_2));
        buttonRanks.setTextColor(getResources().getColor(R.color.color_text_2));
        buttonRecords.setTextColor(getResources().getColor(R.color.color_text_2));
        buttonFriends.setTextColor(getResources().getColor(R.color.color_text_2));
        buttonStatistics.setBackgroundColor(getResources().getColor(R.color.color_level_9));
        buttonRanks.setBackgroundColor(getResources().getColor(R.color.color_level_9));
        buttonRecords.setBackgroundColor(getResources().getColor(R.color.color_level_9));
        buttonFriends.setBackgroundColor(getResources().getColor(R.color.color_level_9));

        layoutDelete1.setVisibility(View.VISIBLE);
        layoutDelete2.setVisibility(View.VISIBLE);
        imageGold.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        layoutAll.setWeightSum(8);
    }


    private void displayRankStar() {
        int a = profileRank / 10;
        int a2 = profileRank % 10;
        int b = a2 / 5;
        int c = a2 % 5;
        while (a > 0) {
            listStars.get(0).setImageResource(R.drawable.icon_star_10);
            listStars.remove(0);
            a--;
        }
        while (b > 0) {
            listStars.get(0).setImageResource(R.drawable.icon_star_5);
            listStars.remove(0);
            b--;
        }
        while (c > 0) {
            listStars.get(0).setImageResource(R.drawable.icon_star_1);
            listStars.remove(0);
            c--;
        }
        while (listStars.size() > 0) {
            listStars.get(0).setVisibility(View.INVISIBLE);
            listStars.remove(0);
        }
    }

    public void loadFriends() {
        friends.clear();
        for(User x : users) {
            if(x.hasFriend(user.getUid())) {
                friends.add(x);
            }
        }
        invalidateOptionsMenu();
        if(userAdapter != null)
            userAdapter.notifyDataSetChanged();
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
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }
        else if(item.getItemId() == R.id.menuGameRequests && xxxUser.getGameRequests().size() != 0) {
            Intent intent = new Intent(ProfileActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.GAME_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getGameRequests());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menuFriendRequests && xxxUser.getFriendRequests().size() != 0) {
            Intent intent = new Intent(ProfileActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.FRIEND_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getFriendRequests());
            startActivity(intent);
        }
        return true;
    }
}
