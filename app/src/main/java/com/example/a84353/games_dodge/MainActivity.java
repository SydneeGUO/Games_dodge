package com.example.a84353.games_dodge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void GameBegin(View view){
        Button btn=findViewById(R.id.startGame);
        btn.setClickable(false);
        Intent gbn=new Intent(MainActivity.this,GameActivity.class);
        startActivity(gbn);
        finish();
    }
}
