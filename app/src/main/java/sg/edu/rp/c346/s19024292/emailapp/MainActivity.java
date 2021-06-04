package sg.edu.rp.c346.s19024292.emailapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText et;
    ImageView add;
    ListView lv;
    ArrayAdapter<String> aa;
    ArrayList<String> al;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);

        et = findViewById(R.id.addContactEmail);
        add = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.lvContacts);

        DBHelper db = new DBHelper(MainActivity.this);
//        al = db.getContactById(id);

        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,al);


        lv.setAdapter(aa);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);

                String email = et.getText().toString();
                if (!email.isEmpty()||!email.equals("")){

                    if (email.contains("@") && email.contains(".com")) {
                        Toast.makeText(MainActivity.this,email + id,Toast.LENGTH_SHORT).show();

                        db.insertContact(email, id);
                        Contact newContact = new Contact(email);
                        newContact.setEmail(email);

                        al.clear();
                        al = db.getContactById(id);

                        aa.notifyDataSetChanged();

                    } else {
                        Toast.makeText(MainActivity.this,"Please check that the contact email contains '@' and 'com'",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,"Please check that the email is not empty",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}