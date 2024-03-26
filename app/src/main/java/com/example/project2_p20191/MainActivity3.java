package com.example.project2_p20191;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity3 extends AppCompatActivity {

    private String username,email;
    EditText selected_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        email = getIntent().getStringExtra("email");
        selected_email = findViewById(R.id.selected_email);
        username = getIntent().getStringExtra("username");
    }
    public void privatechat (View view) {
        if (!selected_email.getText().toString().isEmpty()) {
            String selected_email1 = selected_email.getText().toString();
            Log.d("MainActivity3","Ektelesh privatechat()");
            Intent intent2 = new Intent(this, MainActivity4.class);
            intent2.putExtra("username", username);
            intent2.putExtra("selected_email", selected_email1);
            intent2.putExtra("email", email);
            startActivity(intent2);
        }
        else{
            showMessage("Error","Empty field");
        }
    }
    public void chatroom(View view){
        Intent intent3 = new Intent(this,MainActivity2.class);
        Log.d("MainActivity3","Ektelesh chatroom()");
        intent3.putExtra("username", username);
        startActivity(intent3);
    }
    private void showMessage(String title, String message) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(true).show();
    }
    public void logoutClick3(View view){
        Logout1.logout(this);
    }
}