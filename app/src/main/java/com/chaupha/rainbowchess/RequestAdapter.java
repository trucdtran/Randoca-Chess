package com.chaupha.rainbowchess;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Request> requests;
    private int mode;
    private DatabaseReference usersReference;


    public RequestAdapter(Context context, ArrayList<Request> requests, int mode) {
        this.context = context;
        this.requests = requests;
        this.mode = mode;
        this.usersReference = FirebaseDatabase.getInstance().getReference().child("users");
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View notificationItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(notificationItem);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Request request = requests.get(position);
        if(position%2!=0) {
            holder.layout.setBackgroundResource(R.color.color_level_8);
        }
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(request.getSourceUid())) {
                    String name = dataSnapshot.child(request.getSourceUid()).child("name").getValue(String.class);
                    String photoUri = dataSnapshot.child(request.getSourceUid()).child("photo_uri").getValue(String.class);
                    String online = dataSnapshot.child(request.getSourceUid()).child("online").getValue(String.class);
                    String time = convertUTCtoSimpleTime(request.getTime());
                    String status = context.getResources().getString(R.string.format_status_online);
                    if (online.equals("0")) {
                        status = context.getResources().getString(R.string.format_status_offline);
                    }
                    if (online.equals("2")) {
                        status = context.getResources().getString(R.string.format_status_playing);
                    }
                    String fullName = name + " (" + status + ")";
                    holder.textName.setText(fullName);
                    Picasso.get().load(photoUri).into(holder.image);
                    holder.textTime.setText(time);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(context, R.raw.sound_touch).start();
                if(mode==NotificationActivity.GAME_REQUESTS) {
                    String name = (String) holder.textName.getText();
                    String statusOffline = context.getResources().getString(R.string.format_status_offline);
                    String statusPlaying = context.getResources().getString(R.string.format_status_playing);
                    if (name.contains(statusOffline)) {
                        Toast.makeText(context, statusOffline, Toast.LENGTH_SHORT).show();
                    } else if (name.contains(statusPlaying)) {
                        Toast.makeText(context, statusPlaying, Toast.LENGTH_SHORT).show();
                    } else {
                        requests.remove(position);
                        notifyDataSetChanged();
                        removeGameRequest(request);
                        createGame(request);
                    }
                }
                else {
                    requests.remove(position);
                    notifyDataSetChanged();
                    removeFriendRequest(request);
                    addFriend(request);
                }
            }
        });

        holder.buttonDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer.create(context, R.raw.sound_touch).start();
                requests.remove(position);
                notifyDataSetChanged();
                if(mode==NotificationActivity.GAME_REQUESTS) {
                    removeGameRequest(request);
                }
                else {
                    removeFriendRequest(request);
                }
            }
        });

    }


    private void createGame(Request request) {
        DatabaseReference gameReference = FirebaseDatabase.getInstance().getReference().child("games").push();
        String gameID = gameReference.getKey();
        String blueID;
        String redID;
        Random random = new Random();
        if(random.nextBoolean()) {
            blueID = request.getSourceUid();
            redID = request.getTargetUid();
        }
        else {
            blueID = request.getTargetUid();
            redID = request.getSourceUid();
        }
        usersReference.child(blueID).child("online").setValue("2");
        usersReference.child(redID).child("online").setValue("2");
        usersReference.child(blueID).child("game_entry").child(gameID).setValue(blueID + "-" + redID);
        usersReference.child(redID).child("game_entry").child(gameID).setValue(blueID + "-" + redID);
    }


    private void addFriend(Request request) {
        usersReference.child(request.getSourceUid()).child("friends").child(request.getTargetUid()).setValue(request.getTime());
        usersReference.child(request.getTargetUid()).child("friends").child(request.getSourceUid()).setValue(request.getTime());
    }


    private void removeGameRequest(Request request) {
        usersReference.child(request.getTargetUid()).child("game_requests").child(request.getSourceUid()).removeValue();
    }


    private void removeFriendRequest(Request request) {
        usersReference.child(request.getTargetUid()).child("friend_requests").child(request.getSourceUid()).removeValue();
    }


    private String convertUTCtoSimpleTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));

        try {
            Date date = sdf.parse(time);

            int hour = date.getHours();
            int min = date.getMinutes();
            int day = date.getDay();
            String month = new DateFormatSymbols().getMonths()[date.getMonth()];
            String ampm = "";
            if (hour == 12) {
                ampm = "PM";
            }
            else if(hour >= 12){
                ampm = "PM";
                hour = hour%12;
            }
            else {
                ampm = "AM";
            }

            if (hour == 0) {
                hour = 12;
            }

            return hour + ":" + min + " " + ampm + " " + context.getResources().getString(R.string.format_time) + " " + day + " " + month;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    @Override
    public int getItemCount() {
        return requests.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public ImageView image;
        public TextView textName;
        public TextView textTime;
        public Button buttonAccept;
        public Button buttonDecline;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.request_layout);
            image = itemView.findViewById(R.id.request_image);
            textName = itemView.findViewById(R.id.request_textName);
            textTime = itemView.findViewById(R.id.request_textTime);
            buttonAccept = itemView.findViewById(R.id.request_buttonAccept);
            buttonDecline = itemView.findViewById(R.id.request_buttonDecline);
        }
    }
}

