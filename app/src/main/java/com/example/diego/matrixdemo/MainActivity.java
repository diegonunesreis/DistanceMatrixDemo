package com.example.diego.matrixdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements DistanceFinder.DF{
    EditText editTextFrom, editTextTo;
    Button buttonGet;
    String origin, destination;
    String searchLanguage = "pt-BR"; //"en"
    String APIKEY = "AIzaSyDDTNXhkFDJKh5-em6fVXcDpT4Wp5aA7ZI";
    TextView resultTextView1, resultTextView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Atribuindo as coisas
        editTextFrom = (EditText) findViewById(R.id.editText_from);
        editTextTo = (EditText) findViewById(R.id.editText_to);
        buttonGet = (Button) findViewById(R.id.button_go);
        resultTextView1 = (TextView) findViewById(R.id.resultTextView1);
        resultTextView2 = (TextView) findViewById(R.id.resultTextView2);

        //a parte do onClick
        buttonGet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                origin = editTextFrom.getText().toString();
                destination = editTextTo.getText().toString();
                try {
                    origin = Encoder(origin);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    destination = Encoder(destination);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin + "&destinations=" + destination + "&mode=driving&language=" + searchLanguage + "&avoid=tolls&key=" + APIKEY;
                new DistanceFinder(MainActivity.this).execute(url);

            }
        });
    }

    String Encoder (String place) throws UnsupportedEncodingException{
        return URLEncoder.encode(place, "utf-8");
    }

    @Override
    public void setDouble(String result) {
        String res[] = result.split(",");
        Double minutes = (Double.parseDouble(res[0]) / 60);
        int distance = (Integer.parseInt(res[1]) / 1000);
        resultTextView1.setText("Duração: "  + (int) (minutes / 60) + " horas e " + (int) (minutes % 60) + " min");
        resultTextView2.setText("Distância: " + distance + " km");
    }

}
