package com.example.a84353.games_dodge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent it=getIntent();
        String str= it.getStringExtra("score");
        int sc=0;
        for(int i=0;i<str.length();i++)sc=sc*10+str.charAt(i)-'0';
        TextView conc=findViewById(R.id.Conc),tv_score=findViewById(R.id.score);
        if(sc>500)
            conc.setText("Unblievable! You get");
        else if(sc>400)
            conc.setText("Fantastic! You get");
        else if(sc>300)
            conc.setText("Amazing! You get");
        else if(sc>200)
            conc.setText("Excellent! You get");
        else if(sc>100)
            conc.setText("Congratulations! You get ");

        else
            conc.setText("Try harder! You get ");
        tv_score.setText(""+sc);
    }
    public void launchMain(View view){
        Button btn=findViewById(R.id.restart);
        btn.setClickable(false);
        Intent im=new Intent(ScoreActivity.this,MainActivity.class);
        startActivity(im);
        finish();
    }
}
