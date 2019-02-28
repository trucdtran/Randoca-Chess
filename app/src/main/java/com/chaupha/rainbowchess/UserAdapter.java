package com.chaupha.rainbowchess;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private ArrayList<User> users;
    private int mode;
    private DatabaseReference usersReference;
    private FirebaseUser currentUser;

    public final static int MODE_USERS = 0;
    public final static int MODE_FRIENDS = 1;
    public final static int MODE_VIEW = 2;


    public UserAdapter(Context context, ArrayList<User> users, int mode) {
        this.context = context;
        this.users = users;
        this.mode = mode;
        this.usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(userView);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final User user = users.get(position);
        if(position%2!=0) {
            holder.layout.setBackgroundResource(R.color.color_level_8);
        }
        if(mode == MODE_VIEW) {
            holder.textOrder.setVisibility(View.VISIBLE);
            holder.textOrder.setText(String.valueOf(position+1));
            String buffer = "";
            switch (RankActivity.category) {
                case RankActivity.CATEGORY_RANK: {
                    buffer = user.getRankString() + " (" + user.getScore() + "/" + user.getLimitScore() + ")";
                    holder.textRank.setText(buffer);
                    break;
                }

                case RankActivity.CATEGORY_WINPER: {
                    if(user.getGamesPlayed() < 50) {
                        buffer = "--.--";
                    }
                    else {
                        buffer = context.getResources().getString(R.string.profile_win_per) + ": " + user.getWinPercent() + "%";
                    }
                    holder.textRank.setText(buffer);
                    break;
                }

                case RankActivity.CATEGORY_DESCPOWER: {
                    buffer = context.getResources().getString(R.string.profile_desc) + ": " + user.getDescPower();
                    holder.textRank.setText(buffer);
                    break;
                }

                case RankActivity.CATEGORY_GOLD: {
                    buffer = context.getResources().getString(R.string.profile_gold) + ": " + user.getGold();
                    holder.textRank.setText(buffer);
                    break;
                }

                case RankActivity.CATEGORY_GAMESPLAYED: {
                    buffer = context.getResources().getString(R.string.profile_games_played) + ": " + user.getGamesPlayed();
                    holder.textRank.setText(buffer);
                    break;
                }

                case RankActivity.CATEGORY_BOSSA: {
                    if(user.getRecords().get(0) == 1000) {
                        holder.textRank.setText(context.getResources().getString(R.string.profile_fail));
                    }
                    else {
                        buffer = context.getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(0) + " " +
                                context.getResources().getString(R.string.profile_record_suffix);
                        holder.textRank.setText(buffer);
                    }
                    break;
                }

                case RankActivity.CATEGORY_BOSSB: {
                    if(user.getRecords().get(1) == 1000) {
                        holder.textRank.setText(context.getResources().getString(R.string.profile_fail));
                    }
                    else {
                        buffer = context.getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(1) + " " +
                                context.getResources().getString(R.string.profile_record_suffix);
                        holder.textRank.setText(buffer);
                    }
                    break;
                }

                case RankActivity.CATEGORY_BOSSC: {
                    if(user.getRecords().get(2) == 1000) {
                        holder.textRank.setText(context.getResources().getString(R.string.profile_fail));
                    }
                    else {
                        buffer = context.getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(2) + " " +
                                context.getResources().getString(R.string.profile_record_suffix);
                        holder.textRank.setText(buffer);
                    }
                    break;
                }

                case RankActivity.CATEGORY_BOSSD: {
                    if(user.getRecords().get(3) == 1000) {
                        holder.textRank.setText(context.getResources().getString(R.string.profile_fail));
                    }
                    else {
                        buffer = context.getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(3) + " " +
                                context.getResources().getString(R.string.profile_record_suffix);
                        holder.textRank.setText(buffer);
                    }
                    break;
                }

                case RankActivity.CATEGORY_BOSSE: {
                    if(user.getRecords().get(4) == 1000) {
                        holder.textRank.setText(context.getResources().getString(R.string.profile_fail));
                    }
                    else {
                        buffer = context.getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(4) + " " +
                                context.getResources().getString(R.string.profile_record_suffix);
                        holder.textRank.setText(buffer);
                    }
                    break;
                }

                case RankActivity.CATEGORY_BOSSF: {
                    if(user.getRecords().get(5) == 1000) {
                        holder.textRank.setText(context.getResources().getString(R.string.profile_fail));
                    }
                    else {
                        buffer = context.getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(5) + " " +
                                context.getResources().getString(R.string.profile_record_suffix);
                        holder.textRank.setText(buffer);
                    }
                    break;
                }

                case RankActivity.CATEGORY_BOSSG: {
                    if(user.getRecords().get(6) == 1000) {
                        holder.textRank.setText(context.getResources().getString(R.string.profile_fail));
                    }
                    else {
                        buffer = context.getResources().getString(R.string.profile_record_a) + ": " + user.getRecords().get(6) + " " +
                                context.getResources().getString(R.string.profile_record_suffix);
                        holder.textRank.setText(buffer);
                    }
                    break;
                }
            }
        }
        else {
            holder.textRank.setText(user.getRankString());
        }
        holder.textName.setText(user.getName());
        Picasso.get().load(user.getPhotoUri()).into(holder.imagePhoto);

        if(mode == MODE_VIEW) {
            if(user.hasFriend(currentUser.getUid())) {
                holder.imageFriendRequest.setImageResource(R.drawable.icon_delete_friend);
                holder.imageFriendRequest.setVisibility(View.VISIBLE);
                holder.imageFriendRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MediaPlayer.create(context, R.raw.sound_touch).start();
                        usersReference.child(currentUser.getUid()).child("friends").child(user.getUid()).removeValue();
                        usersReference.child(user.getUid()).child("friends").child(currentUser.getUid()).removeValue();
                        users.remove(position);
                    }
                });
            }
            else if(user.getUid().equals(currentUser.getUid()))
                holder.imageFriendRequest.setVisibility(View.INVISIBLE);
            else {
                if(user.hasFriendRequest(currentUser.getUid())) {
                    holder.imageFriendRequest.setVisibility(View.INVISIBLE);
                }
                else {
                    holder.imageFriendRequest.setImageResource(R.drawable.icon_add_friend);
                    holder.imageFriendRequest.setVisibility(View.VISIBLE);
                    holder.imageFriendRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MediaPlayer.create(context, R.raw.sound_touch).start();
                            usersReference.child(user.getUid()).child("friend_requests").child(currentUser.getUid()).setValue(getCurrentUTCTime());
                        }
                    });
                }
            }
            holder.imageGameRequest.setImageResource(R.drawable.icon_view_profile);
            holder.imageGameRequest.setVisibility(View.VISIBLE);
            holder.imageGameRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaPlayer.create(context, R.raw.sound_touch).start();
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra(ProfileActivity.PROFILE_ID, user.getUid());
                    intent.putExtra(ProfileActivity.PROFILE_NAME, user.getName());
                    intent.putExtra(ProfileActivity.PROFILE_PHOTO, user.getPhotoUri().toString());
                    intent.putExtra(ProfileActivity.PROFILE_RANK, user.getRank());
                    context.startActivity(intent);
                }
            });
        }
        else {
            if(user.getOnline()==0) {
                holder.imageGameRequest.setVisibility(View.INVISIBLE);
            }
            else if(user.getOnline()==2) {
                holder.imageGameRequest.setImageResource(R.drawable.icon_playing_game);
                holder.imageGameRequest.setVisibility(View.VISIBLE);
            }
            else {
                if(user.hasGameRequest(currentUser.getUid()) || FindOpponentActivity.user.hasGameRequest(user.getUid())) {
                    holder.imageGameRequest.setVisibility(View.INVISIBLE);
                }
                else {
                    holder.imageGameRequest.setImageResource(R.drawable.icon_request);
                    holder.imageGameRequest.setVisibility(View.VISIBLE);
                    holder.imageGameRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MediaPlayer.create(context, R.raw.sound_touch).start();
                            usersReference.child(user.getUid()).child("game_requests").child(currentUser.getUid()).setValue(getCurrentUTCTime());
                        }
                    });
                }
            }


            if(mode==MODE_USERS) {
                if(user.hasFriend(currentUser.getUid()) || user.hasFriendRequest(currentUser.getUid())) {
                    holder.imageFriendRequest.setVisibility(View.INVISIBLE);
                }
                else {
                    holder.imageFriendRequest.setImageResource(R.drawable.icon_add_friend);
                    holder.imageFriendRequest.setVisibility(View.VISIBLE);
                    holder.imageFriendRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MediaPlayer.create(context, R.raw.sound_touch).start();
                            usersReference.child(user.getUid()).child("friend_requests").child(currentUser.getUid()).setValue(getCurrentUTCTime());
                        }
                    });
                }
            }
            else {
                holder.imageFriendRequest.setImageResource(R.drawable.icon_delete_friend);
                holder.imageFriendRequest.setVisibility(View.VISIBLE);
                holder.imageFriendRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MediaPlayer.create(context, R.raw.sound_touch).start();
                        usersReference.child(currentUser.getUid()).child("friends").child(user.getUid()).removeValue();
                        usersReference.child(user.getUid()).child("friends").child(currentUser.getUid()).removeValue();
                        users.remove(position);
                    }
                });
            }
        }
    }


    private String getCurrentUTCTime() {
        Calendar cal = new GregorianCalendar();
        long time = cal.getTimeInMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        return sdf.format(new Date(time));
    }


    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public TextView textOrder;
        public ImageView imagePhoto;
        public TextView textName;
        public TextView textRank;
        public ImageView imageFriendRequest;
        public ImageView imageGameRequest;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.user_layout);
            textOrder = itemView.findViewById(R.id.user_textOrder);
            imagePhoto = itemView.findViewById(R.id.user_imagePhoto);
            textName = itemView.findViewById(R.id.user_textName);
            textRank = itemView.findViewById(R.id.user_textRank);
            imageFriendRequest = itemView.findViewById(R.id.user_imageFriendRequest);
            imageGameRequest = itemView.findViewById(R.id.user_imageGameRequest);
        }
    }

}
