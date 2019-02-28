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
import java.util.Random;

public class ShopActivity extends AppCompatActivity {

    private TextView textGold;
    private LinearLayout layoutCost1;
    private LinearLayout layoutCost2;
    private LinearLayout layoutCost3;
    private ImageView imageReview;
    private TextView textReview;

    private RecyclerView recyclerView;
    private ArrayList<Card> cards;
    private CardAdapter cardAdapter;

    public static final String MY_GOLDS = "MY_GOLDS";
    private long myGolds;

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
        setContentView(R.layout.activity_shop);

        Intent intent = getIntent();
        myGolds = intent.getLongExtra(MY_GOLDS, 0);

        textGold = findViewById(R.id.shop_textGold);
        layoutCost1 = findViewById(R.id.shop_layoutCost1);
        layoutCost2 = findViewById(R.id.shop_layoutCost2);
        layoutCost3 = findViewById(R.id.shop_layoutCost3);
        imageReview = findViewById(R.id.shop_imageReview);
        textReview = findViewById(R.id.shop_textReview);

        recyclerView = findViewById(R.id.shop_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");

        textGold.setText(String.valueOf(myGolds));
        setStateOfShop();

        cards = new ArrayList<>();
        cardAdapter = new CardAdapter(this, cards, CardAdapter.MODE_SHOP);
        recyclerView.setAdapter(cardAdapter);
        listenForMyCards();

        layoutCost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                if(myGolds < 200)
                    return;
                myGolds -= 200;
                textGold.setText(String.valueOf(myGolds));
                setStateOfShop();
                Random random = new Random();
                int cardID = random.nextInt(11) + 1;
                addNewCard(cardID);
            }
        });

        layoutCost2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                if(myGolds < 1000)
                    return;
                myGolds -= 1000;
                textGold.setText(String.valueOf(myGolds));
                setStateOfShop();
                Random random = new Random();
                int num = random.nextInt(11);
                int cardID;
                if(num != 10)
                    cardID = num + 12;
                else
                    cardID = 26;
                addNewCard(cardID);
            }
        });

        layoutCost3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(getApplicationContext(), R.raw.sound_touch).start();
                if(myGolds < 5000)
                    return;
                myGolds -= 5000;
                textGold.setText(String.valueOf(myGolds));
                setStateOfShop();
                Random random = new Random();
                int num = random.nextInt(5);
                int cardID;
                if(num != 4)
                    cardID = num + 22;
                else
                    cardID = 27;
                addNewCard(cardID);
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
                        Intent intent = new Intent(ShopActivity.this, GameActivity.class);
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


    private void setStateOfShop() {
        if(myGolds < 5000) {
            layoutCost3.setBackgroundResource(R.color.color_level_12);
            layoutCost3.setClickable(false);
        }
        if(myGolds < 1000) {
            layoutCost2.setBackgroundResource(R.color.color_level_12);
            layoutCost2.setClickable(false);
        }
        if(myGolds < 200) {
            layoutCost1.setBackgroundResource(R.color.color_level_12);
            layoutCost1.setClickable(false);
        }
    }


    private void listenForMyCards() {
        usersReference.child(currentUser.getUid()).child("card").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cards.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int cardID = Integer.valueOf(snapshot.getKey());
                    int count = Integer.valueOf(snapshot.getValue(String.class));
                    cards.add(new Card(cardID, count));
                }
                sortCards();
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sortCards() {
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


    private void addNewCard(int cardID) {
        usersReference.child(currentUser.getUid()).child("gold").setValue(String.valueOf(myGolds));
        for(Card card : cards) {
            if(card.getCardID() == cardID) {
                String buffer = "";
                imageReview.setImageResource(card.getCardResource());
                buffer = getResources().getString(R.string.profile_desc) + ": " + String.valueOf(card.getCardPower());
                textReview.setText(buffer);
                if(cardID < 10)
                    buffer = "0" + String.valueOf(cardID);
                else
                    buffer = String.valueOf(cardID);
                usersReference.child(currentUser.getUid()).child("card").child(buffer).setValue(String.valueOf(card.getCount()+1));
            }
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
            startActivity(new Intent(ShopActivity.this, LoginActivity.class));
            finish();
        }
        else if(item.getItemId() == R.id.menuGameRequests && user.getGameRequests().size() != 0) {
            Intent intent = new Intent(ShopActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.GAME_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getGameRequests());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menuFriendRequests && user.getFriendRequests().size() != 0) {
            Intent intent = new Intent(ShopActivity.this, NotificationActivity.class);
            intent.putExtra(NotificationActivity.REQUEST_NAME, NotificationActivity.FRIEND_REQUESTS);
            intent.putParcelableArrayListExtra(NotificationActivity.REQUEST_ARRAYLIST, user.getFriendRequests());
            startActivity(intent);
        }
        return true;
    }
}
