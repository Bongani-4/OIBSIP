package com.example.todoapplication;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapplication.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    private TextView tv;
    private EditText names, email, password, passwordconfirm;

    private Button regbtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        regbtn = findViewById(R.id.Rgisterbtn);
        tv = findViewById(R.id.haveaccount);
        names = findViewById(R.id.names);
        email = findViewById(R.id.Emailregister);
        password = findViewById(R.id.regpassword);
        passwordconfirm = findViewById(R.id.confirmpassword);



        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        regbtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.black));
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String userName = names.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String confirmPassword = passwordconfirm.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!userPassword.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             rrr               @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Save user data to Realtime Database
                                    saveUserDataToDatabase(userName, userEmail, userPassword);

                                    Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Firebase Authentication failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }

            });
        }
    private void saveUserDataToDatabase(String userName, String userEmail, String userPassword) {
        //  a unique user id using the user's email
        String userId = userEmail.replace(".", "_");

        //  a map to store user data
        Map<String, Object> user = new HashMap<>();
        user.put("name", userName);
        user.put("email", userEmail);
        user.put("password", userPassword);

        // Save the user data to Realtime Database
        usersRef.child(userId).setValue(user);
    }

        public void togglePasswordVisibilitys(View view) {
        ImageButton passwordToggle = findViewById(R.id.passwordVisibilityToggleR);
        EditText passwordEditText = findViewById(R.id.regpassword);

        int currentVisibility = passwordEditText.getInputType();
        int newVisibility;

        if (currentVisibility == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            newVisibility = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            passwordToggle.setImageResource(R.drawable.icovisibilityon);
        } else {
            newVisibility = InputType.TYPE_TEXT_VARIATION_PASSWORD;
            passwordToggle.setImageResource(R.drawable.icoisibility);
        }

        passwordEditText.setInputType(newVisibility);
        passwordEditText.setSelection(passwordEditText.length());
    }
    public void togglePasswordVisibilitysi(View view) {
        ImageButton passwordToggle = findViewById(R.id.passwordVisibilityToggleConfirmpass);
        EditText passwordEditText = findViewById(R.id.confirmpassword);

        int currentVisibility = passwordEditText.getInputType();
        int newVisibility;

        if (currentVisibility == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            newVisibility = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            passwordToggle.setImageResource(R.drawable.icovisibilityon);
        } else {
            newVisibility = InputType.TYPE_TEXT_VARIATION_PASSWORD;
            passwordToggle.setImageResource(R.drawable.icoisibility);
        }

        passwordEditText.setInputType(newVisibility);
        passwordEditText.setSelection(passwordEditText.length());
    }


}