package com.example.project2_p20191;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class MainActivity2 extends AppCompatActivity {
    TextView textView2,allMessages;
    EditText message_general;
    String username,email;
    FirebaseDatabase db;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        email = getIntent().getStringExtra("email");
        textView2 = findViewById(R.id.textView2);
        username = getIntent().getStringExtra("username");
        textView2.setText("Hello, " + username);
        allMessages = findViewById(R.id.textView3);
        allMessages.setText("");
        message_general = findViewById(R.id.message1);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("message_general");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String previousMessages = allMessages.getText().toString();
                if (snapshot.getValue()!=null)
                    allMessages.setText(previousMessages+"\n"+snapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }
    public void send (View view){
        if(!message_general.getText().toString().trim().isEmpty()){
            reference.setValue("\n"+username+":"+message_general.getText().toString());
            message_general.setText("");
        }
        else{
            showMessage("Error","Please write a message_general first!...");
        }
    }
    void showMessage(String title,String message_general){
        new AlertDialog.Builder(this).setTitle(title).setMessage(message_general).setCancelable(true).show();
    }
    public void activity3(View view){
        Intent intent = new Intent(this,MainActivity3.class);
        intent.putExtra("username",username);
        intent.putExtra("email", email);
        startActivity(intent);
    }
    public void logoutClick2(View view){
        Logout1.logout(this);
    }
}