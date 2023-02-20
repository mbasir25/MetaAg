package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLogin extends AppCompatActivity {

    EditText email,password;
    Button login, signup;
    FirebaseAuth mAuth;
    TinyDB tinyDB;
    FirebaseFirestore mFstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        login = findViewById(R.id.login_button);
        signup = findViewById(R.id.signup_button);

        mAuth = FirebaseAuth.getInstance();
        mFstore = FirebaseFirestore.getInstance();


        tinyDB = new TinyDB(getApplicationContext());

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), registration.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailinput = email.getText().toString();
                String pass = password.getText().toString();

                mAuth.signInWithEmailAndPassword(emailinput, pass)
                        .addOnCompleteListener(UserLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String uid = mAuth.getCurrentUser().getUid();
                                    mFstore.collection("users").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if  (documentSnapshot.exists()){
                                                String name = (String) documentSnapshot.get("Name");
                                                String em = (String) documentSnapshot.get("Email");
                                                String pn = (String) documentSnapshot.get("Phone");

                                                tinyDB.putString("name", name);
                                                tinyDB.putString("email", em);
                                                tinyDB.putString("phone", pn);



                                                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                                            }else{
                                                Toast.makeText(UserLogin.this, "name doesn't exist", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UserLogin.this, "Failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });


                                    tinyDB.putString("uid", uid);


                                } else {
                                email.setText("");
                                password.setText("");
                                    Toast.makeText(UserLogin.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });



    }
}