package com.chaupha.rainbowchess;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class LoginActivity extends AppCompatActivity {

    private LoginButton buttonFacebook;
    private SignInButton buttonGoogle;

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    private DatabaseReference databaseReference;

    GoogleSignInClient googleSignInClient;
    final int RC_SIGN_IN = 123;

    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.app_name));

        buttonFacebook = findViewById(R.id.loginButtonFacebook);
        buttonGoogle = findViewById(R.id.loginButtonGoogle);

        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        callbackManager = CallbackManager.Factory.create();
        buttonFacebook.setReadPermissions("email");
        buttonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid())
            .child("online");
            ref.setValue("1");
            ref.onDisconnect().setValue("0");
            startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        }
        catch (ApiException e) {
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = firebaseAuth.getCurrentUser();
                            updateUI();
                        }
                        else {
                        }
                    }
                });
    }


    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = firebaseAuth.getCurrentUser();
                            updateUI();
                        }
                        else {
                        }
                    }
                });
    }



    private void updateUI() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(currentUser.getUid())) {
                    databaseReference = databaseReference.child(currentUser.getUid());
                    databaseReference.child("email").setValue(currentUser.getEmail());
                    databaseReference.child("name").setValue(currentUser.getDisplayName());
                    databaseReference.child("photo_uri").setValue(currentUser.getPhotoUrl().toString());
                    databaseReference.child("date").setValue(getCurrentUTCTime());
                    databaseReference.child("games_played").setValue("0");
                    databaseReference.child("games_won").setValue("0");
                    databaseReference.child("score").setValue("0");
                    databaseReference.child("rank").setValue("1");
                    databaseReference.child("gold").setValue("0");
                    databaseReference.child("desc_power").setValue("44");
                    databaseReference.child("desc").child("card01").setValue("1");
                    databaseReference.child("desc").child("card02").setValue("2");
                    databaseReference.child("desc").child("card03").setValue("3");
                    databaseReference.child("desc").child("card04").setValue("4");
                    databaseReference.child("desc").child("card05").setValue("5");
                    databaseReference.child("desc").child("card06").setValue("6");
                    databaseReference.child("desc").child("card07").setValue("7");
                    databaseReference.child("desc").child("card08").setValue("8");
                    databaseReference.child("desc").child("card09").setValue("9");
                    databaseReference.child("desc").child("card10").setValue("10");
                    databaseReference.child("desc").child("card11").setValue("11");
                    databaseReference.child("desc").child("card12").setValue("12");
                    databaseReference.child("desc").child("card13").setValue("13");

                    databaseReference.child("card").child("01").setValue("0");
                    databaseReference.child("card").child("02").setValue("0");
                    databaseReference.child("card").child("03").setValue("0");
                    databaseReference.child("card").child("04").setValue("0");
                    databaseReference.child("card").child("05").setValue("0");
                    databaseReference.child("card").child("06").setValue("0");
                    databaseReference.child("card").child("07").setValue("0");
                    databaseReference.child("card").child("08").setValue("0");
                    databaseReference.child("card").child("09").setValue("0");
                    databaseReference.child("card").child("10").setValue("0");
                    databaseReference.child("card").child("11").setValue("0");
                    databaseReference.child("card").child("12").setValue("0");
                    databaseReference.child("card").child("13").setValue("0");
                    databaseReference.child("card").child("14").setValue("0");
                    databaseReference.child("card").child("15").setValue("0");
                    databaseReference.child("card").child("16").setValue("0");
                    databaseReference.child("card").child("17").setValue("0");
                    databaseReference.child("card").child("18").setValue("0");
                    databaseReference.child("card").child("19").setValue("0");
                    databaseReference.child("card").child("20").setValue("0");
                    databaseReference.child("card").child("21").setValue("0");
                    databaseReference.child("card").child("22").setValue("0");
                    databaseReference.child("card").child("23").setValue("0");
                    databaseReference.child("card").child("24").setValue("0");
                    databaseReference.child("card").child("25").setValue("0");
                    databaseReference.child("card").child("26").setValue("0");
                    databaseReference.child("card").child("27").setValue("0");

                    databaseReference.child("records").child("bossA").setValue("1000");
                    databaseReference.child("records").child("bossB").setValue("1000");
                    databaseReference.child("records").child("bossC").setValue("1000");
                    databaseReference.child("records").child("bossD").setValue("1000");
                    databaseReference.child("records").child("bossE").setValue("1000");
                    databaseReference.child("records").child("bossF").setValue("1000");
                    databaseReference.child("records").child("bossG").setValue("1000");
                    databaseReference.child("online").setValue("1");
                    databaseReference.child("online").onDisconnect().setValue("0");
                }
                else {
                    databaseReference = databaseReference.child(currentUser.getUid());
                    databaseReference.child("online").setValue("1");
                    databaseReference.child("online").onDisconnect().setValue("0");
                }
                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private String getCurrentUTCTime() {
        Calendar cal = new GregorianCalendar();
        long time = cal.getTimeInMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date(time));
    }
}
