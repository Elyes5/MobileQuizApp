package com.example.quizstart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FourthHint extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_hint);
        btn = (Button) findViewById(R.id.backToAct);
        TextView linkTextView = findViewById(R.id.lien);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        btn.setOnClickListener(v -> finish());
    }
}