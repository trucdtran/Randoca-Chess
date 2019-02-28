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
import android.widget.TextView;
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

public class DescActivity extends AppCompatActivity{

    public static TextView textDescPower;
    private TextView textOK;
    public static ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12, image13;

    private RecyclerView recyclerView;
    public static ArrayList<Card> cards;
    public static int[] desc = new int[13];
    public static CardAdapter cardAdapter;

    private int descPower;

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
        setContentView(R.layout.activity_desc);

        textDescPower = findViewById(R.id.desc_textDescPower);
        textOK = findViewById(R.id.desc_textOK);
        image1 = findViewById(R.id.desc_image1);
        image2 = findViewById(R.id.desc_image2);
        image3 = findViewById(R.id.desc_image3);
        image4 = findViewById(R.id.desc_image4);
        image5 = findViewById(R.id.desc_image5);
        image6 = findViewById(R.id.desc_image6);
        image7 = findViewById(R.id.desc_image7);
        image8 = findViewById(R.id.desc_image8);
        image9 = findViewById(R.id.desc_image9);
        image10 = findViewById(R.id.desc_image10);
        image11 = findViewById(R.id.desc_image11);
        image12 = findViewById(R.id.desc_image12);
        image13 = findViewById(R.id.desc_image13);

        recyclerView = findViewById(R.id.desc_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");

        cards = new ArrayList<>();
        cardAdapter = new CardAdapter(this, cards, CardAdapter.MODE_DESC);
        recyclerView.setAdapter(cardAdapter);
        listenForMyCards();

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[0];
                desc[0] = 0;
                updateCards(x);
                image1.setImageDrawable(null);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[1];
                desc[1] = 0;
                updateCards(x);
                image2.setImageDrawable(null);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[2];
                desc[2] = 0;
                updateCards(x);
                image3.setImageDrawable(null);
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[3];
                desc[3] = 0;
                updateCards(x);
                image4.setImageDrawable(null);
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[4];
                desc[4] = 0;
                updateCards(x);
                image5.setImageDrawable(null);
            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[5];
                desc[5] = 0;
                updateCards(x);
                image6.setImageDrawable(null);
            }
        });

        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[6];
                desc[6] = 0;
                updateCards(x);
                image7.setImageDrawable(null);
            }
        });

        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[7];
                desc[7] = 0;
                updateCards(x);
                image8.setImageDrawable(null);
            }
        });

        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[8];
                desc[8] = 0;
                updateCards(x);
                image9.setImageDrawable(null);
            }
        });

        image10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[9];
                desc[9] = 0;
                updateCards(x);
                image10.setImageDrawable(null);
            }
        });

        image11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[10];
                desc[10] = 0;
                updateCards(x);
                image11.setImageDrawable(null);
            }
        });

        image12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[11];
                desc[11] = 0;
                updateCards(x);
                image12.setImageDrawable(null);
            }
        });

        image13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                int x = desc[12];
                desc[12] = 0;
                updateCards(x);
                image13.setImageDrawable(null);
            }
        });

        textOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                for (int i = 0; i < desc.length; i++) {
                    if(desc[i] == 0) {
                        Toast.makeText(getApplicationContext(),
                                getApplicationContext().getResources().getString(R.string.toast_desc_fail), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                updateDesc();
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
                        Intent intent = new Intent(DescActivity.this, GameActivity.class);
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


    private void listenForMyCards() {
        usersReference.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                descPower = Integer.valueOf(dataSnapshot.child("desc_power").getValue(String.class));
                for (DataSnapshot snapshot : dataSnapshot.child("card").getChildren()) {
                    int cardID = Integer.valueOf(snapshot.getKey());
                    int count = Integer.valueOf(snapshot.getValue(String.class));
                    cards.add(new Card(cardID, count));
                }
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.child("desc").getChildren()) {
                    desc[i] = Integer.valueOf(snapshot.getValue(String.class));
                    i++;
                }
                sortCards();
                cardAdapter.notifyDataSetChanged();
                setMyDesc();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static void sortCards() {
        for(int i=0; i < cards.size()-1; i++) {
            for (int j = 0; j < cards.size() - 1 - i; j++) {
                if (cards.get(j).getCount() < cards.get(j + 1).getCount()) {
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));
                    cards.set(j + 1, temp);
                }
            }
        }
    }


    public static void sortCardsById() {
        for(int i=0; i < cards.size()-1; i++) {
            for (int j = 0; j < cards.size() - 1 - i; j++) {
                if (cards.get(j).getCardID() > cards.get(j + 1).getCardID()) {
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));
                    cards.set(j + 1, temp);
                }
            }
        }
    }


    public static int getDescPower() {
        int sum = 0;
        for (int i = 0; i < desc.length; i++) {
            sum += Card.getCardPower(desc[i]);
        }
        return sum;
    }


    private void setMyDesc() {
        String buffer = getResources().getString(R.string.profile_desc) + ": " + descPower;
        textDescPower.setText(buffer);
        image1.setImageResource(Card.getCardResource(desc[0]));
        image2.setImageResource(Card.getCardResource(desc[1]));
        image3.setImageResource(Card.getCardResource(desc[2]));
        image4.setImageResource(Card.getCardResource(desc[3]));
        image5.setImageResource(Card.getCardResource(desc[4]));
        image6.setImageResource(Card.getCardResource(desc[5]));
        image7.setImageResource(Card.getCardResource(desc[6]));
        image8.setImageResource(Card.getCardResource(desc[7]));
        image9.setImageResource(Card.getCardResource(desc[8]));
        image10.setImageResource(Card.getCardResource(desc[9]));
        image11.setImageResource(Card.getCardResource(desc[10]));
        image12.setImageResource(Card.getCardResource(desc[11]));
        image13.setImageResource(Card.getCardResource(desc[12]));
    }


    private void updateCards(int cardID) {
        String buffer = getResources().getString(R.string.profile_desc) + ": " + getDescPower();
        textDescPower.setText(buffer);
        for (Card card : cards) {
            if(card.getCardID() == cardID) {
                card.setCount(card.getCount() + 1);
            }
        }
        sortCards();
        cardAdapter.notifyDataSetChanged();
    }


    private void updateDesc() {
        DatabaseReference ref = usersReference.child(currentUser.getUid());
        ref.child("desc").child("card01").setValue(String.valueOf(desc[0]));
        ref.child("desc").child("card02").setValue(String.valueOf(desc[1]));
        ref.child("desc").child("card03").setValue(String.valueOf(desc[2]));
        ref.child("desc").child("card04").setValue(String.valueOf(desc[3]));
        ref.child("desc").child("card05").setValue(String.valueOf(desc[4]));
        ref.child("desc").child("card06").setValue(String.valueOf(desc[5]));
        ref.child("desc").child("card07").setValue(String.valueOf(desc[6]));
        ref.child("desc").child("card08").setValue(String.valueOf(desc[7]));
        ref.child("desc").child("card09").setValue(String.valueOf(desc[8]));
        ref.child("desc").child("card10").setValue(String.valueOf(desc[9]));
        ref.child("desc").child("card11").setValue(String.valueOf(desc[10]));
        ref.child("desc").child("card12").setValue(String.valueOf(desc[11]));
        ref.child("desc").child("card13").setValue(String.valueOf(desc[12]));

        sortCardsById();
        ref.child("card").child("01").setValue(String.valueOf(cards.get(0).getCount()));
        ref.child("card").child("02").setValue(String.valueOf(cards.get(1).getCount()));
        ref.child("card").child("03").setValue(String.valueOf(cards.get(2).getCount()));
        ref.child("card").child("04").setValue(String.valueOf(cards.get(3).getCount()));
        ref.child("card").child("05").setValue(String.valueOf(cards.get(4).getCount()));
        ref.child("card").child("06").setValue(String.valueOf(cards.get(5).getCount()));
        ref.child("card").child("07").setValue(String.valueOf(cards.get(6).getCount()));
        ref.child("card").child("08").setValue(String.valueOf(cards.get(7).getCount()));
        ref.child("card").child("09").setValue(String.valueOf(cards.get(8).getCount()));
        ref.child("card").child("10").setValue(String.valueOf(cards.get(9).getCount()));
        ref.child("card").child("11").setValue(String.valueOf(cards.get(10).getCount()));
        ref.child("card").child("12").setValue(String.valueOf(cards.get(11).getCount()));
        ref.child("card").child("13").setValue(String.valueOf(cards.get(12).getCount()));
        ref.child("card").child("14").setValue(String.valueOf(cards.get(13).getCount()));
        ref.child("card").child("15").setValue(String.valueOf(cards.get(14).getCount()));
        ref.child("card").child("16").setValue(String.valueOf(cards.get(15).getCount()));
        ref.child("card").child("17").setValue(String.valueOf(cards.get(16).getCount()));
        ref.child("card").child("18").setValue(String.valueOf(cards.get(17).getCount()));
        ref.child("card").child("19").setValue(String.valueOf(cards.get(18).getCount()));
        ref.child("card").child("20").setValue(String.valueOf(cards.get(19).getCount()));
        ref.child("card").child("21").setValue(String.valueOf(cards.get(20).getCount()));
        ref.child("card").child("22").setValue(String.valueOf(cards.get(21).getCount()));
        ref.child("card").child("23").setValue(String.valueOf(cards.get(22).getCount()));
        ref.child("card").child("24").setValue(String.valueOf(cards.get(23).getCount()));
        ref.child("card").child("25").setValue(String.valueOf(cards.get(24).getCount()));
        ref.child("card").child("26").setValue(String.valueOf(cards.get(25).getCount()));
        ref.child("card").child("27").setValue(String.valueOf(cards.get(26).getCount()));

        ref.child("desc_power").setValue(String.valueOf(getDescPower()));

        Toast.makeText(getApplicationContext(),
                getApplicationContext().getResources().getString(R.string.toast_desc_success), Toast.LENGTH_LONG).show();
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
            startActivity(new Intent(DescActivity.this, LoginActivity.class));
            finish();
        }
        else if(item.getItemId() == R.id.menuGameRequests && user.getGameRequests().size() != 0) {
            Intent intent = new Intent(DescActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.GAME_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getGameRequests());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menuFriendRequests && user.getFriendRequests().size() != 0) {
            Intent intent = new Intent(DescActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.FRIEND_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getFriendRequests());
            startActivity(intent);
        }
        return true;
    }
}
