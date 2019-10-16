package com.example.patpatchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPass;
    Button btnLogin;

    FirebaseAuth Auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPass);
        btnLogin = findViewById(R.id.btnLogin);

        Auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString();
                String pass = inputPass.getText().toString();

                if (inputEmail.getText().toString().isEmpty()){
                    inputEmail.setError("Email not filled");
                }

                else if (inputPass.getText().toString().isEmpty()){
                    inputPass.setError("Pass cannot be empty");
                }

                else if (!inputEmail.getText().toString().contains("@")){
                    inputEmail.setError("Put correct email format");
                }

                else {
                    signIn(email,pass);

                }

            }
        });

    }

    public void createUser(String Email,String Pass){
        Auth.createUserWithEmailAndPassword(Email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            //check if successful
                            if (task.isSuccessful()) {
                                //User is successfully registered and logged in
                                //start Profile Activity here
                                Toast.makeText(LoginActivity.this, "registration successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else{
                                Toast.makeText(LoginActivity.this, "Couldn't register, try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void signIn(String Email, String Pass){

        Auth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String uid = Auth.getCurrentUser().getUid();

                    sharedPreferences = getSharedPreferences("SaveData",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("uiduser", uid);
                    editor.apply();

                    finish();


                    Toast.makeText(LoginActivity.this, "Login Succesfull", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class ));
                }
                else {
                    Toast.makeText(LoginActivity.this, "Couldn't register, try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
