package com.example.patpatchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    View view;

    TextView displayName;
    TextView displayEmail;
    ImageView displayProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerToggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        view = navigationView.getHeaderView(0);
        displayName = view.findViewById(R.id.name);
        displayEmail = view.findViewById(R.id.email);
        displayProfileImage = view.findViewById(R.id.profileImage);

        toolbar.setTitleTextColor(Color.WHITE);

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, homeFragment, "composeFragment");
        fragmentTransaction.commit();


        checkUserLogin();


    }

    public void checkUserLogin(){

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        else {
           setupNavBar();
        }

    }

    public void setupNavBar(){

        sharedPreferences = getSharedPreferences("SaveData",MODE_PRIVATE);
        String uid = sharedPreferences.getString("uiduser",null);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();

                setTitle("Hai, " + name);
                displayName.setText(name);
                displayEmail.setText(email);
                Picasso.get().load(profileImageUrl).into(displayProfileImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void handleLogout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getItemId() == R.id.home){
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,homeFragment,"homeFragment");
            fragmentTransaction.commit();
            setTitle("Chat");
        }

        else if (menuItem.getItemId() == R.id.compose){
            ComposeFragment composeFragment = new ComposeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, composeFragment, "composeFragment");
            fragmentTransaction.commit();
            setTitle("Compose a message");
        }

        else if(menuItem.getItemId() == R.id.logout){
            handleLogout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else{
            super.onBackPressed();
        }
    }
}
