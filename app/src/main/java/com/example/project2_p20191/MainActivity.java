package com.example.project2_p20191;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {
    EditText email,password,username;
    Button button2;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null){
            Button button2 = findViewById(R.id.button2);
            button2.setVisibility(View.GONE);
        }
    }
    //enhmerwnontai ta  stoixeia tou xrhsth etsi wste na mhn emfanizei to username tou prohgoumenou xrhsth
    protected void onStart(){
        super.onStart();
        user = auth.getCurrentUser();
        if (user != null) {
            Button button2 = findViewById(R.id.button2);
            button2.setVisibility(View.GONE);
        }
    }
    void showMessage(String title,String message){
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(true).show();
    }
    public void signup(View view){
        if(!email.getText().toString().isEmpty() &&
        !password.getText().toString().isEmpty() &&
        !username.getText().toString().isEmpty()) {
            auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = auth.getCurrentUser();
                                updateUser(user, username.getText().toString());
                                showMessage("Success", "User profile created!");
                            } else {
                                showMessage("Error", task.getException().getLocalizedMessage());
                            }
                        }
                    });
        }
        else {
            showMessage("Error","There are empty fields. Make sure you have filled in all the necessary information.");
        }
    }
    private void updateUser(FirebaseUser user, String username){
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();
        user.updateProfile(request);
    }
    public void login(View view){
        if(!email.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty()){
            auth.signInWithEmailAndPassword(email.getText().toString(),
                    password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        showMessage("Success","User signed in successfully!");
                    }else {
                        showMessage("Error",task.getException().getLocalizedMessage());
                        //ean uparksei error sto login emfanizei ksana to button2 "Create Account"
                        button2 = findViewById(R.id.button2);
                        button2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
    public void chatroom(View view){
        user = auth.getCurrentUser();
        if (user!=null){
            Intent intent =  new Intent(this,MainActivity2.class);
            intent.putExtra("username",user.getDisplayName());
            String email1 = email.getText().toString();
            intent.putExtra("email", email1);
            startActivity(intent);
        }
        else {
            showMessage("Error","Please log-in or create an account first!");
        }
    }
}