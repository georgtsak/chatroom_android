package com.example.project2_p20191;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//one to one chat using email
public class MainActivity4 extends AppCompatActivity {
    TextView textView4, allMessages_1v1;
    FirebaseDatabase db2;
    DatabaseReference reference3;
    EditText message_input1v1;
    String email, selected_email,username,sender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity4a", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        textView4 = findViewById(R.id.textView4);
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email"); //email pompou
        selected_email = getIntent().getStringExtra("selected_email"); //email  dekth
        Log.d("MainActivity4a", "Received email: " + selected_email);
        textView4.setText("Chat with user: \n " + selected_email);
        allMessages_1v1 = findViewById(R.id.message1_1v1);
        allMessages_1v1.setText("");
        message_input1v1 = findViewById(R.id.message2);
        db2 = FirebaseDatabase.getInstance();
        String encoded_email = email.replace(".", ",");
        String encoded_selected_email = selected_email.replace(".", ",");
        String chat1v1 = (encoded_email + "_" + encoded_selected_email).compareTo(encoded_selected_email + "_" + encoded_email) > 0 ? encoded_selected_email + "_" + encoded_email : encoded_email + "_" + encoded_selected_email;
        reference3 = db2.getReference("message_input1v1/" + chat1v1);
        DatabaseReference db_ref = reference3.push();
        db_ref.setValue(new SaveMessageEmail());
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allMessages_1v1.setText("");
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    SaveMessageEmail message = messageSnapshot.getValue(SaveMessageEmail.class);

                    if (message != null && message.getGreenEmail() != null && message.getMessage() != null) {
                        String displayMessage = "";

                        if (message.getGreenEmail().equals(encoded_email)) {
                            displayMessage = "<font color='#20928f'>me: </font> " + message.getMessage() + "<br><br>";
                        } else {
                            String[] email_parsing = selected_email.split("@");
                            String usernameFromEmail = (email_parsing.length > 0) ? email_parsing[0] : "";

                            displayMessage = "<font color='#808080'>" + usernameFromEmail + ": </font> " + message.getMessage() + "<br><br>";
                        }

                        allMessages_1v1.append(HtmlCompat.fromHtml(displayMessage, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void send1v1(View view) {
        String message = message_input1v1.getText().toString();
        if (!message.trim().isEmpty()) {
            String encoded_email = email.replace(".", ",");
            String displayMessage = encoded_email + ": " + message + "\n";
            allMessages_1v1.append(displayMessage);
            message_input1v1.setText("");
            DatabaseReference newMessageRef = reference3.push();
            newMessageRef.setValue(new SaveMessageEmail(message, encoded_email));
        }
    }
    public static class SaveMessageEmail {
        private String message;
        private String greenEmail;
        public SaveMessageEmail() {
        }
        public SaveMessageEmail(String message, String greenEmail) {
            this.message = message;
            this.greenEmail = greenEmail;
        }
        public String getMessage() {
            return message;
        }
        public String getGreenEmail() {
            return greenEmail;
        }
    }
    public void remove(View view){
        allMessages_1v1.setText("");
    }
    public void logoutClick4(View view){
        Logout1.logout(this);
    }
}
