package sg.edu.rp.c346.s19024292.emailapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText username,password,repassword,email;
    Button btnConfirm;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editAddPassword);
        repassword = findViewById(R.id.editAddPassword1);
        email = findViewById(R.id.editAddEmail);
        btnConfirm = findViewById(R.id.btnConfirm);
        DB = new DBHelper(this);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String mail = email.getText().toString();

                if (user.equals("")||pass.equals("")||repass.equals("")||mail.equals("")){
                    Toast.makeText(SignupActivity.this,"Fill in all components.",Toast.LENGTH_SHORT);
                } else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkusername(user);
                        Boolean checkmail = DB.checkemail(mail);
                        if (checkuser == false && checkmail==false) {
                            Boolean insert = DB.insertUser(user, pass, mail);

                            if ((insert == true) && (mail.contains("@")) && (mail.contains(".com")))
                            {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("Username",user);
                                intent.putExtra("Password",pass);
                                intent.putExtra("Email",mail);

                                startActivity(intent);
                            } else {
                                Toast.makeText(SignupActivity.this,"Something went wrong.",Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(SignupActivity.this,"This user already exists.",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this,"Confirm your password again.",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });
    }
}