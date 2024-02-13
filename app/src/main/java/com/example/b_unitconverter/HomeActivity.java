package com.example.b_unitconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        CardView length = findViewById(R.id.Length);
        CardView  data = findViewById(R.id.Data);
        CardView time = findViewById(R.id.time);
        CardView volume = findViewById(R.id.Volume);
        View  Tip = findViewById(R.id.Tip);
        CardView Temp = findViewById(R.id.Temperature);
        CardView speed = findViewById(R.id.Speed);
        CardView mass = findViewById(R.id.Mass);



        length.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(HomeActivity.this, ConverterActivity.class));
            }
        });

        Tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(HomeActivity.this, Equipment.class));
            }
        });
        Temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(HomeActivity.this, Equipment.class));
            }
        });
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(HomeActivity.this, Equipment.class));
            }
        });



        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // startActivity(new Intent(HomeActivity.this,animalCaretaker.class));

            }

        });
        mass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(HomeActivity.this,NewsActivity.class));
            }
        });


        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }


}