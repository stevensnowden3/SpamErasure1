package com.example.spamdetect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;


public class LogoutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    TextView nameTV;
    TextView emailTV;
    TextView idTV;
    ImageView photoIV;
    Uri gotstring;
    //ImageView navhead;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //for drawer
        drawer = findViewById(R.id.drawer_layout);
        //seting photos in nav_header
        sign_out = findViewById(R.id.log_out);
        nameTV = findViewById(R.id.name);
        emailTV = findViewById(R.id.email);
        idTV = findViewById(R.id.id);
        photoIV = findViewById(R.id.photo);
        //navhead = findViewById(R.id.navhead);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       View hview = navigationView.getHeaderView(0);
       TextView name = (TextView) hview.findViewById(R.id.myname);
       TextView useremali = (TextView) hview.findViewById(R.id.useremali);
       ImageView steven1 = (ImageView) hview.findViewById(R.id.navhead);
       name.setText("Kwesiga Steven");
        useremali.setText("stevensnowden3@gmail.com");
        String ur = "https://lh3.googleusercontent.com/ogw/ADea4I4VCXERDx2cC" +
                "IbXjrMesM7Mg7VkVWUHI99pNQJC=s83-c-mo";

        Glide.with(this).load(gotstring).apply(new RequestOptions().
                circleCrop()).into(steven1);

        //Glide.with(this).load(personPhoto).into(photoIV);


        //drawer navigation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_profile);


        getSignIn();


    }
    //Side navigation view
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_message:
                startActivity(new Intent(LogoutActivity.this, SendMessage.class));

                Toast.makeText(this, "mesage", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_spam:
                startActivity(new Intent(LogoutActivity.this, SpamDetectorActivity.class));
                Toast.makeText(this, "Welcome to spam prediction", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_account:
               Intent account = new Intent(Settings.ACTION_ADD_ACCOUNT);
               startActivity(account);
                break;
            case R.id.nav_share:
                startActivity(new Intent(LogoutActivity.this, ShareActivity.class));
                Toast.makeText(this, "Thanks for Sharing", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                startActivity(new Intent(LogoutActivity.this, AboutActivity.class));
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_stats:
                startActivity(new Intent(LogoutActivity.this, StatisticsActivity.class));
                Toast.makeText(this, "Stats", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_settins:
                startActivity(new Intent(LogoutActivity.this, SettingsActivity.class));
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_profile:
                startActivity(new Intent(LogoutActivity.this, LogoutActivity.class));
                break;

            case R.id.nav_feedback:
                Uri uri1 = Uri.parse("https://spamerasure.herokuapp.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(intent);
                break;
            case R.id.nav_exit: {
                AlertDialog.Builder builder = new AlertDialog.Builder(LogoutActivity.this);
                builder.setMessage("Do you want to Exit ?");
                builder.setCancelable(true);
                builder.setNegativeButton("YES,", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You clicked Exit", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });
                builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }//End case Exit

        }
        return true;
    }

    //navigation drawer
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }


    private void getSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(LogoutActivity.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            gotstring =personPhoto;

            nameTV.setText("Name: "+personName);
            emailTV.setText("Email: "+personEmail);
            idTV.setText("ID: "+personId);
           // Glide.with(this).load(personPhoto).into(photoIV);
            Glide.with(this).load(personPhoto).apply(new RequestOptions().
                    circleCrop()).into(photoIV);
            //for nav
            NavigationView navigationView = findViewById(R.id.nav_view);

            navigationView.setNavigationItemSelectedListener(this);
            View hview = navigationView.getHeaderView(0);
            TextView name = (TextView) hview.findViewById(R.id.myname);
            TextView useremali = (TextView) hview.findViewById(R.id.useremali);
            ImageView steven1 = (ImageView) hview.findViewById(R.id.navhead);
            name.setText(personName);
            useremali.setText(personEmail);
           

            Glide.with(this).load(gotstring).apply(new RequestOptions().
                    circleCrop()).into(steven1);


        }

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LogoutActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogoutActivity.this, MainActivity.class));
                        finish();
                       // startActivity(new Intent(LogoutActivity.this,HomeActivity.class));

                    }
                });
    }



}