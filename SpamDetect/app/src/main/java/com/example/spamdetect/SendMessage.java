package com.example.spamdetect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SendMessage extends AppCompatActivity {
    Button btnsend;
    EditText textTo,textsubject,txtmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        //toolbars
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // assign variables
        textTo=findViewById(R.id.textTo);
        textsubject=findViewById(R.id.textsubject);
        txtmessage=findViewById(R.id.txtmessage);
        btnsend=findViewById(R.id.btnsend);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{ textTo.getText().toString().trim()});
                i.putExtra(Intent.EXTRA_SUBJECT, textsubject.getText().toString().trim());
                i.putExtra(Intent.EXTRA_TEXT   , txtmessage.getText().toString().trim());

                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                    txtmessage.setText("");
                    textTo.setText("");
                    textsubject.setText("");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendMessage.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

               /* //send mail this failed
                try {
                    GMailSender sender = new GMailSender("mandraked2525@gmail.com", "steven,,.");
                    sender.sendMail("This is Subject",
                            "This is Body from my app",
                            "stevensnowden3@gmail.com",
                            "stevensnowden3@gmail.com");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }*/

            }


        });



    }



}

