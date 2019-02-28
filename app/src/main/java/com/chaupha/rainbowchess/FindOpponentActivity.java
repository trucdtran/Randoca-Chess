package com.chaupha.rainbowchess;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

public class FindOpponentActivity extends AppCompatActivity {
    Button buttonRankMode;
    Button buttonAll;
    Button buttonFriends;

    private RecyclerView recyclerView;
    public static User user;
    private UserAdapter userAdapter;
    private ArrayList<User> users;
    private ArrayList<User> friends;

    private FirebaseUser currentUser;
    private DatabaseReference usersReference;
    private DatabaseReference ref;
    private ChildEventListener childEventListener;

    private int mode;

    private int[] descBlue = new int[13];
    private int[] descRed = new int[13];

    private String gameID = "";
    private String blueID = "";
    private String redID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_opponent);
        setTitle(getResources().getString(R.string.app_name));

        buttonRankMode = findViewById(R.id.findopponentButtonRankMode);
        buttonAll = findViewById(R.id.findopponentButtonAll);
        buttonFriends = findViewById(R.id.findopponentButtonFriends);

        recyclerView = findViewById(R.id.findopponentRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");

        users = new ArrayList<>();
        friends = new ArrayList<>();
        mode = UserAdapter.MODE_USERS;
        listenForAllUsers();
        userAdapter = new UserAdapter(this, users, mode);
        recyclerView.setAdapter(userAdapter);

        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                buttonAll.setTextColor(getResources().getColor(R.color.color_text));
                buttonAll.setBackgroundColor(getResources().getColor(R.color.color_level_7));
                buttonFriends.setTextColor(getResources().getColor(R.color.color_text_2));
                buttonFriends.setBackgroundColor(getResources().getColor(R.color.color_level_9));
                if(mode==UserAdapter.MODE_FRIENDS) {
                    mode=UserAdapter.MODE_USERS;
                    userAdapter = new UserAdapter(FindOpponentActivity.this, users, mode);
                    recyclerView.setAdapter(userAdapter);
                    listenForAllUsers();
                }
            }
        });

        buttonFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                buttonAll.setTextColor(getResources().getColor(R.color.color_text_2));
                buttonAll.setBackgroundColor(getResources().getColor(R.color.color_level_9));
                buttonFriends.setTextColor(getResources().getColor(R.color.color_text));
                buttonFriends.setBackgroundColor(getResources().getColor(R.color.color_level_7));
                if(mode==UserAdapter.MODE_USERS) {
                    mode=UserAdapter.MODE_FRIENDS;
                    userAdapter = new UserAdapter(FindOpponentActivity.this, friends, mode);
                    recyclerView.setAdapter(userAdapter);
                    loadFriends();
                }
            }
        });

        buttonRankMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                sendRequestsForAll();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("game_entry");
        //listenForAcceptedGameRequests();
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
                        Intent intent = new Intent(FindOpponentActivity.this, GameActivity.class);
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


    /*private void listenForAcceptedGameRequests() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(user!=null) {
                    gameID = dataSnapshot.getKey();
                    String str = dataSnapshot.getValue(String.class);
                    int indexStr = str.indexOf("-");
                    blueID = str.substring(0, indexStr);
                    redID = str.substring(indexStr+1);

                    usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Intent intent = new Intent(FindOpponentActivity.this, GameActivity.class);
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
                            FindOpponentActivity.this.finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
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
    }*/


    public void listenForAllUsers() {
        usersReference.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                ArrayList<User> onlineUsers = new ArrayList<>();
                ArrayList<User> offlineUsers = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    if(snapshot.child("name").getValue(String.class) == null)
                        continue;
                    if(snapshot.child("photo_uri").getValue(String.class) == null)
                        continue;
                    if(snapshot.child("rank").getValue(String.class) == null)
                        continue;
                    if(snapshot.child("online").getValue(String.class) == null)
                        continue;
                    String name = snapshot.child("name").getValue(String.class);
                    Uri photo_uri = Uri.parse(snapshot.child("photo_uri").getValue(String.class));
                    int rank = Integer.valueOf(snapshot.child("rank").getValue(String.class));
                    int online = Integer.valueOf(snapshot.child("online").getValue(String.class));
                    ArrayList<String> games = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.child("games").getChildren()) {
                        String game = snap.getKey();
                        games.add(game);
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

                    User user = new User(getApplicationContext(), name, photo_uri, uid, rank, online, games, friends,
                    gameRequests, friendRequests);
                    if(currentUser.getUid().equals(uid)) {
                        FindOpponentActivity.this.user = user;
                        continue;
                    }
                    if(online!=0) {
                        onlineUsers.add(user);
                    }
                    else {
                        offlineUsers.add(user);
                    }
                }
                users.addAll(onlineUsers);
                users.addAll(offlineUsers);
                invalidateOptionsMenu();
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void loadFriends() {
        friends.clear();
        for(User user : users) {
            if(user.hasFriend(currentUser.getUid())) {
                friends.add(user);
            }
        }
        invalidateOptionsMenu();
        userAdapter.notifyDataSetChanged();
    }


    public void sendRequestsForAll() {
        for(User user : users) {
            if((user.getOnline()==1) && (!user.hasGameRequest(currentUser.getUid())) && (!this.user.hasGameRequest(user.getUid())) ) {
                usersReference.child(user.getUid()).child("game_requests").child(currentUser.getUid()).setValue(getCurrentUTCTime());
            }
        }
        invalidateOptionsMenu();
        userAdapter.notifyDataSetChanged();
    }


    private String getCurrentUTCTime() {
        Calendar cal = new GregorianCalendar();
        long time = cal.getTimeInMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        return sdf.format(new Date(time));
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
            startActivity(new Intent(FindOpponentActivity.this, LoginActivity.class));
            finish();
        }
        else if(item.getItemId() == R.id.menuGameRequests && user.getGameRequests().size() != 0) {
            Intent intent = new Intent(FindOpponentActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.GAME_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getGameRequests());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menuFriendRequests && user.getFriendRequests().size() != 0) {
            Intent intent = new Intent(FindOpponentActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.FRIEND_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getFriendRequests());
            startActivity(intent);
        }
        return true;
    }
}
