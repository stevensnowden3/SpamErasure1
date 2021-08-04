package com.example.spamdetect;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SpamDetectorActivity extends AppCompatActivity {
    Button btnpred;
    EditText imessage;
    TextView viewResult;
    String msg;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spam_detector);
        btnpred = findViewById(R.id.btnpredict);
        imessage = findViewById(R.id.txtmessage);
        viewResult = findViewById(R.id.viewResult);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnpred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                msg = String.valueOf(imessage.getText());
                msg=msg.replaceAll("\\s+","%20");

                String url = "https://spamerasure.herokuapp.com/?msg="+msg;
                System.out.println("https://spamerasure.herokuapp.com/?msg="+msg);

                RequestQueue queue = Volley.newRequestQueue(SpamDetectorActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("response",response.getString("prediction"));
                                    String pred = response.getString("prediction");
                                    result = Integer.parseInt(pred);
                                    if (result == 0) {
                                        viewResult.setText("The Email message is Ham \nMessageNot Spam");
                                        viewResult.setTextColor(Color.parseColor("#12E3CB"));
                                        viewResult.setTextSize(20);
                                        viewResult.setGravity(Gravity.CENTER);


                                    }else if (result == 1){
                                        viewResult.setText("The Email is Spam message!!\nBe safe!!");
                                        viewResult.setTextColor(Color.parseColor("#ff0000"));
                                        viewResult.setTextSize(20);
                                        viewResult.setGravity(Gravity.CENTER);

                                    }else { viewResult.setText("something is wrong");
                                        System.out.println("the value returned is"+pred);
                                        viewResult.setTextColor(Color.parseColor("#ff0000"));
                                        viewResult.setTextSize(20);
                                        imessage.setText("");



                                    }

                                    imessage.setText("");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Log.d("response","Received the error");
                                viewResult.setText("Oops!!! Check out your internet");
                                viewResult.setTextSize(18);
                                imessage.setText("");



                            }
                        });
                queue.add(jsonObjectRequest);
            }
        });

    }
}


