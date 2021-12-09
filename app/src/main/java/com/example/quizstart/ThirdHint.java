package com.example.quizstart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThirdHint extends AppCompatActivity {
    private Button btn;
    private TextView txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_hint);
        btn = (Button) findViewById(R.id.backToAct);
        txt1=(TextView) findViewById(R.id.hint);
        txt1=(TextView) findViewById(R.id.lien);
        txt1.setMovementMethod(LinkMovementMethod.getInstance());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    }