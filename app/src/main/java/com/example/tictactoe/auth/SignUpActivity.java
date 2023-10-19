package com.example.tictactoe.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tictactoe.dbHelper.DBHelper;
import com.example.tictactoe.R;

public class SignUpActivity extends AppCompatActivity {
Button signBtn;
EditText gname,pass1,pass2,emailS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().hide();
        signBtn=findViewById(R.id.signBtn);
        gname=findViewById(R.id.gname);
        emailS=findViewById(R.id.emailS);
        pass1=findViewById(R.id.pass1);
        pass2=findViewById(R.id.pass2);

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pass1.getText().toString().equals(pass2.getText().toString()))
                {
                    Toast.makeText(SignUpActivity.this, "Password Didn't match", Toast.LENGTH_SHORT).show();
                    return;
                }

                String gnameS=gname.getText().toString();
                String EmailS=emailS.getText().toString();
                String PassS=pass1.getText().toString();

                if (gnameS.isEmpty() || EmailS.isEmpty() || PassS.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Fields Cannot be Empty !", Toast.LENGTH_SHORT).show();
                    return;
                }


                DBHelper db=new DBHelper(getApplicationContext());
                String msg=db.SignUp(gnameS,EmailS,PassS);



                if (msg.equals("Success"))
                {
                    Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }
            }
        });
    }




    public void onClick(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }
}