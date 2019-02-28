package com.chaupha.rainbowchess;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.util.ArrayList;

public class FindAIActivity extends AppCompatActivity {
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private LinearLayout layout4;
    private LinearLayout layout5;
    private LinearLayout layout6;
    private LinearLayout layout7;

    private LinearLayout imageLock2;
    private LinearLayout imageLock3;
    private LinearLayout imageLock4;
    private LinearLayout imageLock5;
    private LinearLayout imageLock6;
    private LinearLayout imageLock7;

    private TextView textRecord1;
    private TextView textRecord2;
    private TextView textRecord3;
    private TextView textRecord4;
    private TextView textRecord5;
    private TextView textRecord6;
    private TextView textRecord7;

    private TextView textMyRecord1;
    private TextView textMyRecord2;
    private TextView textMyRecord3;
    private TextView textMyRecord4;
    private TextView textMyRecord5;
    private TextView textMyRecord6;
    private TextView textMyRecord7;

    private ProgressBar progressBar;

    public static final String RECORDS = "RECORDS";
    public static final String MY_RECORDS = "MY_RECORDS";
    private int[] records;
    private int[] myRecords;
    private int[] desc = new int[13];

    private FirebaseUser currentUser;
    private DatabaseReference usersReference;
    private User user;

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
        setContentView(R.layout.activity_find_ai);

        Intent intent = getIntent();
        records = intent.getIntArrayExtra(RECORDS);
        myRecords = intent.getIntArrayExtra(MY_RECORDS);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");

        layout1 = findViewById(R.id.linearLayout7);
        layout2 = findViewById(R.id.linearLayout6);
        layout3 = findViewById(R.id.linearLayout8);
        layout4 = findViewById(R.id.linearLayout9);
        layout5 = findViewById(R.id.linearLayout10);
        layout6 = findViewById(R.id.linearLayout11);
        layout7 = findViewById(R.id.linearLayout12);

        imageLock2 = findViewById(R.id.findai_imageLock2);
        imageLock3 = findViewById(R.id.findai_imageLock3);
        imageLock4 = findViewById(R.id.findai_imageLock4);
        imageLock5 = findViewById(R.id.findai_imageLock5);
        imageLock6 = findViewById(R.id.findai_imageLock6);
        imageLock7 = findViewById(R.id.findai_imageLock7);

        textRecord1 = findViewById(R.id.findai_textRecord1);
        textRecord2 = findViewById(R.id.findai_textRecord2);
        textRecord3 = findViewById(R.id.findai_textRecord3);
        textRecord4 = findViewById(R.id.findai_textRecord4);
        textRecord5 = findViewById(R.id.findai_textRecord5);
        textRecord6 = findViewById(R.id.findai_textRecord6);
        textRecord7 = findViewById(R.id.findai_textRecord7);

        textMyRecord1 = findViewById(R.id.findai_textMyRecord1);
        textMyRecord2 = findViewById(R.id.findai_textMyRecord2);
        textMyRecord3 = findViewById(R.id.findai_textMyRecord3);
        textMyRecord4 = findViewById(R.id.findai_textMyRecord4);
        textMyRecord5 = findViewById(R.id.findai_textMyRecord5);
        textMyRecord6 = findViewById(R.id.findai_textMyRecord6);
        textMyRecord7 = findViewById(R.id.findai_textMyRecord7);

        progressBar = findViewById(R.id.findai_progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        setStatistics();

        usersReference.child(currentUser.getUid()).child("desc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    desc[i] = Integer.valueOf(snapshot.getValue(String.class));
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usersReference.child(currentUser.getUid()).child("online").setValue("2");
                        Intent intent = new Intent(FindAIActivity.this, GameOfflineActivity.class);
                        intent.putExtra(GameOfflineActivity.BLUE_NAME,
                                dataSnapshot.child(currentUser.getUid()).child("name").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_PHOTO,
                                dataSnapshot.child(currentUser.getUid()).child("photo_uri").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_DESC, desc);
                        intent.putExtra(GameOfflineActivity.MY_GOLD,
                                Integer.valueOf(dataSnapshot.child(currentUser.getUid()).child("gold").getValue(String.class)));
                        intent.putExtra(GameOfflineActivity.RED_NAME, GameOfflineActivity.RED);
                        intent.putExtra(GameOfflineActivity.RECORD, records[0]);
                        intent.putExtra(GameOfflineActivity.MY_RECORD, myRecords[0]);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usersReference.child(currentUser.getUid()).child("online").setValue("2");
                        Intent intent = new Intent(FindAIActivity.this, GameOfflineActivity.class);
                        intent.putExtra(GameOfflineActivity.BLUE_NAME,
                                dataSnapshot.child(currentUser.getUid()).child("name").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_PHOTO,
                                dataSnapshot.child(currentUser.getUid()).child("photo_uri").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_DESC, desc);
                        intent.putExtra(GameOfflineActivity.MY_GOLD,
                                Integer.valueOf(dataSnapshot.child(currentUser.getUid()).child("gold").getValue(String.class)));
                        intent.putExtra(GameOfflineActivity.RED_NAME, GameOfflineActivity.ORANGE);
                        intent.putExtra(GameOfflineActivity.RECORD, records[1]);
                        intent.putExtra(GameOfflineActivity.MY_RECORD, myRecords[1]);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usersReference.child(currentUser.getUid()).child("online").setValue("2");
                        Intent intent = new Intent(FindAIActivity.this, GameOfflineActivity.class);
                        intent.putExtra(GameOfflineActivity.BLUE_NAME,
                                dataSnapshot.child(currentUser.getUid()).child("name").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_PHOTO,
                                dataSnapshot.child(currentUser.getUid()).child("photo_uri").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_DESC, desc);
                        intent.putExtra(GameOfflineActivity.MY_GOLD,
                                Integer.valueOf(dataSnapshot.child(currentUser.getUid()).child("gold").getValue(String.class)));
                        intent.putExtra(GameOfflineActivity.RED_NAME, GameOfflineActivity.YELLOW);
                        intent.putExtra(GameOfflineActivity.RECORD, records[2]);
                        intent.putExtra(GameOfflineActivity.MY_RECORD, myRecords[2]);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usersReference.child(currentUser.getUid()).child("online").setValue("2");
                        Intent intent = new Intent(FindAIActivity.this, GameOfflineActivity.class);
                        intent.putExtra(GameOfflineActivity.BLUE_NAME,
                                dataSnapshot.child(currentUser.getUid()).child("name").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_PHOTO,
                                dataSnapshot.child(currentUser.getUid()).child("photo_uri").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_DESC, desc);
                        intent.putExtra(GameOfflineActivity.MY_GOLD,
                                Integer.valueOf(dataSnapshot.child(currentUser.getUid()).child("gold").getValue(String.class)));
                        intent.putExtra(GameOfflineActivity.RED_NAME, GameOfflineActivity.GREEN);
                        intent.putExtra(GameOfflineActivity.RECORD, records[3]);
                        intent.putExtra(GameOfflineActivity.MY_RECORD, myRecords[3]);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usersReference.child(currentUser.getUid()).child("online").setValue("2");
                        Intent intent = new Intent(FindAIActivity.this, GameOfflineActivity.class);
                        intent.putExtra(GameOfflineActivity.BLUE_NAME,
                                dataSnapshot.child(currentUser.getUid()).child("name").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_PHOTO,
                                dataSnapshot.child(currentUser.getUid()).child("photo_uri").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_DESC, desc);
                        intent.putExtra(GameOfflineActivity.MY_GOLD,
                                Integer.valueOf(dataSnapshot.child(currentUser.getUid()).child("gold").getValue(String.class)));
                        intent.putExtra(GameOfflineActivity.RED_NAME, GameOfflineActivity.BLUE);
                        intent.putExtra(GameOfflineActivity.RECORD, records[4]);
                        intent.putExtra(GameOfflineActivity.MY_RECORD, myRecords[4]);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usersReference.child(currentUser.getUid()).child("online").setValue("2");
                        Intent intent = new Intent(FindAIActivity.this, GameOfflineActivity.class);
                        intent.putExtra(GameOfflineActivity.BLUE_NAME,
                                dataSnapshot.child(currentUser.getUid()).child("name").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_PHOTO,
                                dataSnapshot.child(currentUser.getUid()).child("photo_uri").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_DESC, desc);
                        intent.putExtra(GameOfflineActivity.MY_GOLD,
                                Integer.valueOf(dataSnapshot.child(currentUser.getUid()).child("gold").getValue(String.class)));
                        intent.putExtra(GameOfflineActivity.RED_NAME, GameOfflineActivity.INDIGO);
                        intent.putExtra(GameOfflineActivity.RECORD, records[5]);
                        intent.putExtra(GameOfflineActivity.MY_RECORD, myRecords[5]);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        layout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                progressBar.setVisibility(View.VISIBLE);
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usersReference.child(currentUser.getUid()).child("online").setValue("2");
                        Intent intent = new Intent(FindAIActivity.this, GameOfflineActivity.class);
                        intent.putExtra(GameOfflineActivity.BLUE_NAME,
                                dataSnapshot.child(currentUser.getUid()).child("name").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_PHOTO,
                                dataSnapshot.child(currentUser.getUid()).child("photo_uri").getValue(String.class));
                        intent.putExtra(GameOfflineActivity.BLUE_DESC, desc);
                        intent.putExtra(GameOfflineActivity.MY_GOLD,
                                Integer.valueOf(dataSnapshot.child(currentUser.getUid()).child("gold").getValue(String.class)));
                        intent.putExtra(GameOfflineActivity.RED_NAME, GameOfflineActivity.VIOLET);
                        intent.putExtra(GameOfflineActivity.RECORD, records[6]);
                        intent.putExtra(GameOfflineActivity.MY_RECORD, myRecords[6]);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
                        Intent intent = new Intent(FindAIActivity.this, GameActivity.class);
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


    private void setStatistics() {
        float dimAttribute = 0.5f;
        String buffer;

        if(myRecords[0] == 1000)
            textMyRecord1.setText(getResources().getString(R.string.profile_fail));
        else {
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + myRecords[0] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textMyRecord1.setText(buffer);
        }
        if(myRecords[1] == 1000) {
            layout2.setAlpha(dimAttribute);
            layout2.setClickable(false);
            textMyRecord2.setText(getResources().getString(R.string.profile_fail));
        }
        else {
            imageLock2.setVisibility(View.INVISIBLE);
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + myRecords[1] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textMyRecord2.setText(buffer);
        }

        if(myRecords[2] == 1000) {
            layout3.setAlpha(dimAttribute);
            layout3.setClickable(false);
            textMyRecord3.setText(getResources().getString(R.string.profile_fail));
        }
        else {
            imageLock3.setVisibility(View.INVISIBLE);
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + myRecords[2] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textMyRecord3.setText(buffer);
        }

        if(myRecords[3] == 1000) {
            layout4.setAlpha(dimAttribute);
            layout4.setClickable(false);
            textMyRecord4.setText(getResources().getString(R.string.profile_fail));
        }
        else {
            imageLock4.setVisibility(View.INVISIBLE);
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + myRecords[3] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textMyRecord4.setText(buffer);
        }

        if(myRecords[4] == 1000) {
            layout5.setAlpha(dimAttribute);
            layout5.setClickable(false);
            textMyRecord5.setText(getResources().getString(R.string.profile_fail));

        }
        else {
            imageLock5.setVisibility(View.INVISIBLE);
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + myRecords[4] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textMyRecord5.setText(buffer);
        }

        if(myRecords[5] == 1000) {
            layout6.setAlpha(dimAttribute);
            layout6.setClickable(false);
            textMyRecord6.setText(getResources().getString(R.string.profile_fail));
        }
        else {
            imageLock6.setVisibility(View.INVISIBLE);
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + myRecords[5] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textMyRecord6.setText(buffer);
        }

        if(myRecords[6] == 1000) {
            layout7.setAlpha(dimAttribute);
            layout7.setClickable(false);
            textMyRecord7.setText(getResources().getString(R.string.profile_fail));
        }
        else {
            imageLock7.setVisibility(View.INVISIBLE);
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + myRecords[6] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textMyRecord7.setText(buffer);
        }


        if(records[0] == 1000)
            textRecord1.setText(getResources().getString(R.string.profile_fail));
        else {
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + records[0] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textRecord1.setText(buffer);
        }

        if(records[1] == 1000)
            textRecord2.setText(getResources().getString(R.string.profile_fail));
        else {
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + records[1] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textRecord2.setText(buffer);
        }

        if(records[2] == 1000)
            textRecord3.setText(getResources().getString(R.string.profile_fail));
        else {
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + records[2] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textRecord3.setText(buffer);
        }

        if(records[3] == 1000)
            textRecord4.setText(getResources().getString(R.string.profile_fail));
        else {
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + records[3] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textRecord4.setText(buffer);
        }

        if(records[4] == 1000)
            textRecord5.setText(getResources().getString(R.string.profile_fail));
        else {
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + records[4] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textRecord5.setText(buffer);
        }

        if(records[5] == 1000)
            textRecord6.setText(getResources().getString(R.string.profile_fail));
        else {
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + records[5] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textRecord6.setText(buffer);
        }

        if(records[6] == 1000)
            textRecord7.setText(getResources().getString(R.string.profile_fail));
        else {
            buffer = getResources().getString(R.string.profile_record_prefix) + " " + records[6] + " " +
                    getResources().getString(R.string.profile_record_suffix);
            textRecord7.setText(buffer);
        }
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
            startActivity(new Intent(FindAIActivity.this, LoginActivity.class));
            finish();
        }
        else if(item.getItemId() == R.id.menuGameRequests && user.getGameRequests().size() != 0) {
            Intent intent = new Intent(FindAIActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.GAME_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getGameRequests());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menuFriendRequests && user.getFriendRequests().size() != 0) {
            Intent intent = new Intent(FindAIActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.FRIEND_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getFriendRequests());
            startActivity(intent);
        }
        return true;
    }
}
