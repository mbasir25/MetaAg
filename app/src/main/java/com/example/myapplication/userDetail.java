package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class userDetail extends AppCompatActivity {

    TinyDB tinyDB;
    FirebaseAuth mAuth;
    FirebaseFirestore mFstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        tinyDB = new TinyDB(getApplicationContext());

        TextView name = findViewById(R.id.uname);
        TextView email = findViewById(R.id.uemail);
        TextView contact = findViewById(R.id.cont);

        Button logout = findViewById(R.id.logout);


        mAuth = FirebaseAuth.getInstance();
        mFstore = FirebaseFirestore.getInstance();

        String Name = tinyDB.getString("name");
        String Email = tinyDB.getString("email");
        String ph = tinyDB.getString("phone");

        name.setText(Name);
        email.setText(Email);
        contact.setText(ph);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                tinyDB.remove("name");
                tinyDB.remove("email");
                tinyDB.remove("phone");
                tinyDB.remove("uid");

                startActivity(new Intent(getApplicationContext(), UserLogin.class));

            }
        });

    }
}