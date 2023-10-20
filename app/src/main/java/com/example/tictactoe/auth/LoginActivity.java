package com.example.tictactoe.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tictactoe.dbHelper.DBHelper;
import com.example.tictactoe.home.PlayersActivity;
import com.example.tictactoe.R;

public class LoginActivity extends AppCompatActivity {
Button loginbtn;
EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        loginbtn=findViewById(R.id.logBtn);
        email=findViewById(R.id.emaill);
        pass=findViewById(R.id.passl);

        loadUserDetails();

        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("") || pass.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Field's Can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Email=email.getText().toString();
                String Pass=pass.getText().toString();
                DBHelper dbh=new DBHelper(getApplicationContext());
                String msg=dbh.Login(Email,Pass);
                if(msg.equals("INVALID"))
                {
                    Toast.makeText(LoginActivity.this, "INVALID CREDENTIALS", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    saveUserDetails(Email, Pass);
                    Toast.makeText(LoginActivity.this, "Login In Successfull", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(LoginActivity.this, PlayersActivity.class);
                    i.putExtra("game_name",msg);
                    startActivity(i);
                }

            }
        });
    }

    private void saveUserDetails(String userEmail, String userPassword) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", userEmail);
        editor.putString("password", userPassword);
        editor.apply();
    }

    private void loadUserDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        if (!username.equals("") && !password.equals("")) {
            Intent i = new Intent(LoginActivity.this, PlayersActivity.class);
            i.putExtra("username", username);
            i.putExtra("password", password);
            startActivity(i);
        }
    }

    public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }
}