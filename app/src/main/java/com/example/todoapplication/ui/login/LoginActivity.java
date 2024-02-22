package com.example.todoapplication.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapplication.MainActivity;
import com.example.todoapplication.R;
import com.example.todoapplication.RegisterActivity;
import com.example.todoapplication.ui.login.LoginViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ValueEventListener;



public class LoginActivity extends AppCompatActivity {


    private LoginViewModel loginViewModel;
    private EditText edusername, edpassword;
    private Button btn;
    private ProgressBar loadingProgressBar;
    private static SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        edusername = findViewById(R.id.Email);
        edpassword = findViewById(R.id.password);
        btn = findViewById(R.id.login);
        TextView registerTV = findViewById(R.id.register);
        TextView forgotpasswordTV = findViewById(R.id.forgotpassword);
        loadingProgressBar = findViewById(R.id.loading);

        btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.black));

        if (isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInternetConnected()) {
                    String username = edusername.getText().toString();
                    String password = edpassword.getText().toString();

                    if (username.length() == 0 || password.length() == 0) {
                        Toast.makeText(getApplicationContext(), "All details should be filled!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Authenticate user with Firebase
                        firebaseAuth.signInWithEmailAndPassword(username, password)
                                .addOnCompleteListener(LoginActivity.this, task -> {
                                    if (task.isSuccessful()) {
                                        // Fetch user details from Realtime DB
                                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                                        if (currentUser != null) {
                                            getUserDetails(currentUser);

                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }else {
                    showNoInternetDialog();

                }
            }
        });


        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@NonNull LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                btn.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    edusername.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    edpassword.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@NonNull LoginResult loginResult) {
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult == null) {
                    return;
                }
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
            }
        });

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgotpasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordDialog();
            }
        });
    }

    private void showForgotPasswordDialog() {
        //logic for resetting the password
    }

    private void sendPasswordResetEmail(String email) {
        // Implement the logic for sending a password reset email
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String displayName = model.getDisplayName();
        Toast.makeText(getApplicationContext(), "Welcome " + displayName, Toast.LENGTH_LONG).show();
        saveLoginStatus(getApplicationContext(), true);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private boolean isLoggedIn() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            long loginTime = sharedPreferences.getLong("loginTime", 0);
            long currentTimeMillis = System.currentTimeMillis();

            long sessionDuration = 24 * 60 * 60 * 1000;

            isLoggedIn = (currentTimeMillis - loginTime) < sessionDuration;
        }

        return isLoggedIn;
    }


    private void getUserDetails(FirebaseUser currentUser) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String email = userSnapshot.child("email").getValue(String.class);
                    String name = userSnapshot.child("name").getValue(String.class);


                    if (email.equals(currentUser.getEmail()) || name.equals(currentUser.getDisplayName())) {
                        Toast.makeText(getApplicationContext(), "Welcome, " + name, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        return;
                    }
                }


                Toast.makeText(getApplicationContext(), "User not found in the database.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error fetching user details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void saveLoginStatus(Context context, boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        if (isLoggedIn) {
            long currentTimeMillis = System.currentTimeMillis();
            editor.putLong("loginTime", currentTimeMillis);
        } else {
            editor.remove("loginTime");
        }
        editor.apply();
    }

    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
            } else {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }
        }

        return false;
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("To use the app, turn on mobile data or connect to Wi-Fi.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

   public void togglePasswordVisibility(View view) {
        ImageButton passwordToggle = findViewById(R.id.passwordVisibilityToggle);
        EditText passwordEditText = findViewById(R.id.password);

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

