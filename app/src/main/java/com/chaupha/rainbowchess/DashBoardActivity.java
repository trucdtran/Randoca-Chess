package com.chaupha.rainbowchess;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DashBoardActivity extends AppCompatActivity {

    private LinearLayout layoutProfile;
    private LinearLayout layoutRank;
    private LinearLayout layoutOffline;
    private LinearLayout layoutOnline;
    private LinearLayout layoutShop;
    private LinearLayout layoutDesc;
    private LinearLayout layoutCup;

    private ProgressBar progressBar;

    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private DatabaseReference usersReference;
    private DatabaseReference ref;
    private ChildEventListener childEventListener;
    private User user;

    private int[] descBlue = new int[13];
    private int[] descRed = new int[13];

    private String gameID = "";
    private String blueID = "";
    private String redID = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle(getResources().getString(R.string.app_name));

        progressBar = findViewById(R.id.dashboard_progressBar);

        layoutProfile = findViewById(R.id.dashboardProfile);
        layoutRank = findViewById(R.id.dashboardRank);
        layoutOffline = findViewById(R.id.dashboardOffline);
        layoutOnline = findViewById(R.id.dashboardOnline);
        layoutShop = findViewById(R.id.dashboardShop);
        layoutDesc = findViewById(R.id.dashboardDesc);
        layoutCup = findViewById(R.id.dashboardCup);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");

        layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Intent intent = new Intent(DashBoardActivity.this, ProfileActivity.class);
                        intent.putExtra(ProfileActivity.PROFILE_ID, currentUser.getUid());
                        intent.putExtra(ProfileActivity.PROFILE_NAME,
                                dataSnapshot.child(currentUser.getUid()).child("name").getValue(String.class));
                        intent.putExtra(ProfileActivity.PROFILE_PHOTO,
                                dataSnapshot.child(currentUser.getUid()).child("photo_uri").getValue(String.class));
                        intent.putExtra(ProfileActivity.PROFILE_RANK,
                                Integer.valueOf(dataSnapshot.child(currentUser.getUid()).child("rank").getValue(String.class)));
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        layoutRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Intent intent = new Intent(DashBoardActivity.this, RankActivity.class);
                        intent.putExtra(RankActivity.MY_NAME,
                                dataSnapshot.child(currentUser.getUid()).child("name").getValue(String.class));
                        intent.putExtra(RankActivity.MY_PHOTO,
                                dataSnapshot.child(currentUser.getUid()).child("photo_uri").getValue(String.class));
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        layoutOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int[] records = new int[7];
                        for(int i=0; i < records.length; i++) {
                            records[i] = 1000;
                        }
                        if(dataSnapshot.child("records").child("bossA").getValue(String.class) != null)
                            records[0] = Integer.parseInt(dataSnapshot.child("records").child("bossA").getValue(String.class));
                        if(dataSnapshot.child("records").child("bossB").getValue(String.class) != null)
                            records[1] = Integer.parseInt(dataSnapshot.child("records").child("bossB").getValue(String.class));
                        if(dataSnapshot.child("records").child("bossC").getValue(String.class) != null)
                            records[2] = Integer.parseInt(dataSnapshot.child("records").child("bossC").getValue(String.class));
                        if(dataSnapshot.child("records").child("bossD").getValue(String.class) != null)
                            records[3] = Integer.parseInt(dataSnapshot.child("records").child("bossD").getValue(String.class));
                        if(dataSnapshot.child("records").child("bossE").getValue(String.class) != null)
                            records[4] = Integer.parseInt(dataSnapshot.child("records").child("bossE").getValue(String.class));
                        if(dataSnapshot.child("records").child("bossF").getValue(String.class) != null)
                            records[5] = Integer.parseInt(dataSnapshot.child("records").child("bossF").getValue(String.class));
                        if(dataSnapshot.child("records").child("bossG").getValue(String.class) != null)
                            records[6] = Integer.parseInt(dataSnapshot.child("records").child("bossG").getValue(String.class));

                        int[] myRecords = new int[7];
                        myRecords[0] = Integer.parseInt(dataSnapshot.child("users").child(currentUser.getUid()).
                                child("records").child("bossA").getValue(String.class));
                        myRecords[1] = Integer.parseInt(dataSnapshot.child("users").child(currentUser.getUid()).
                                child("records").child("bossB").getValue(String.class));
                        myRecords[2] = Integer.parseInt(dataSnapshot.child("users").child(currentUser.getUid()).
                                child("records").child("bossC").getValue(String.class));
                        myRecords[3] = Integer.parseInt(dataSnapshot.child("users").child(currentUser.getUid()).
                                child("records").child("bossD").getValue(String.class));
                        myRecords[4] = Integer.parseInt(dataSnapshot.child("users").child(currentUser.getUid()).
                                child("records").child("bossE").getValue(String.class));
                        myRecords[5] = Integer.parseInt(dataSnapshot.child("users").child(currentUser.getUid()).
                                child("records").child("bossF").getValue(String.class));
                        myRecords[6] = Integer.parseInt(dataSnapshot.child("users").child(currentUser.getUid()).
                                child("records").child("bossG").getValue(String.class));

                        Intent intent = new Intent(DashBoardActivity.this, FindAIActivity.class);
                        intent.putExtra(FindAIActivity.RECORDS, records);
                        intent.putExtra(FindAIActivity.MY_RECORDS, myRecords);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        layoutOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(DashBoardActivity.this, FindOpponentActivity.class);
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(intent);
            }
        });

        layoutShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Intent intent = new Intent(DashBoardActivity.this, ShopActivity.class);
                        intent.putExtra(ShopActivity.MY_GOLDS,
                                Long.valueOf(dataSnapshot.child(currentUser.getUid()).child("gold").getValue(String.class)));
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        layoutDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(DashBoardActivity.this, DescActivity.class));
            }
        });

        layoutCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_future), Toast.LENGTH_LONG).show();
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
                        Intent intent = new Intent(DashBoardActivity.this, GameActivity.class);
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
            startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
            finish();
        }
        else if(item.getItemId() == R.id.menuGameRequests && user.getGameRequests().size() != 0) {
            Intent intent = new Intent(DashBoardActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.GAME_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getGameRequests());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menuFriendRequests && user.getFriendRequests().size() != 0) {
            Intent intent = new Intent(DashBoardActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.FRIEND_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getFriendRequests());
            startActivity(intent);
        }
        return true;
    }
}
