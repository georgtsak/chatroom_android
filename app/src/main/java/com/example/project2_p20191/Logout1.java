package com.example.project2_p20191;
import android.content.Context;
import android.content.Intent;
//import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
public class Logout1 {
    public static void logout(Context context) {
        FirebaseAuth.getInstance().signOut();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            //Log.d("Logout1","the user is signed out");
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        } else {
            //Log.d("Logout1","the user is still signed in");
        }
    }
}