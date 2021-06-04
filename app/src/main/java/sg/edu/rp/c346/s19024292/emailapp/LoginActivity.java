package sg.edu.rp.c346.s19024292.emailapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button btnLogin, btnRegister;
    ImageView imageView;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView = findViewById(R.id.ivLogo);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                DB = new DBHelper(LoginActivity.this);
                int checkemailpass = DB.checkemailpassword(mail,pass);

                if (checkemailpass > 0){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("id",checkemailpass);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this,"Please check your email or password is correct",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}