package com.example.quizstart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThirdQuestion extends AppCompatActivity {
     CountDownTimer T,T1;
    RadioButton Radio1;
    RadioButton Radio2;
    RadioButton Radio3;
    RadioButton Radio4;
    RadioGroup Rg;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4,timer;
    Animation animFade;
    Animation animTranslate_up;
    Animation animTranslate_xy;
    Animation animTranslate;
    Button btn1;
    Button btn2;
    TextView hint;
    DatabaseReference reference;
    String value;
    int value_int;
    TextView score;
    static long c=30000;
    protected void onCreate(Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_question);
        Radio1=(RadioButton)findViewById(R.id.Pascal);
        Radio2=(RadioButton)findViewById(R.id.React_Native);
        Radio3=(RadioButton)findViewById(R.id.Java);
        Radio4=(RadioButton)findViewById(R.id.SQL);
        txt1=(TextView)findViewById(R.id.firstQuestion);
        txt2=(TextView)findViewById(R.id.theQuestion);
        txt3=(TextView)findViewById(R.id.wrongAnswer);
        txt4=(TextView)findViewById(R.id.goodAnswer);
        Rg=(RadioGroup)findViewById(R.id.rdg);
        btn1=(Button) findViewById(R.id.submit);
        btn2=(Button) findViewById(R.id.nextQuestion1);
        score=(TextView) findViewById(R.id.score);
        timer=(TextView) findViewById(R.id.timer);
        hint=(TextView) findViewById(R.id.hint);
        Intent in=getIntent();
        T=new CountDownTimer(c, 1000) {
            public void onTick(long millisUntilFinished) {
                c=millisUntilFinished;
                timer.setText("00:" + millisUntilFinished / 1000);
                if (millisUntilFinished/1000<10){
                    timer.setText("00:0" +millisUntilFinished/1000);
                }
            }
            public void onFinish() {
                Intent Int=new Intent(getApplicationContext(),FourthQuestion.class);
                Int.putExtra("firebase_key",in.getStringExtra("firebase_key"));
                startActivity(Int);
                finish();
            }

        }.start();
        String Participant=in.getStringExtra("firebase_key");
        reference=FirebaseDatabase.getInstance().getReference("Participants").child(Participant);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value=String.valueOf(snapshot.child("myScore").getValue());
                score.setText("Score : "+value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        animFade= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        animTranslate_up=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_up);
        animTranslate_xy=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translationxy);
        animTranslate=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Radio2.isChecked()){
                    txt3.startAnimation(animFade);
                    txt3.setVisibility(View.VISIBLE);
                    value_int=Integer.parseInt(value);
                    value_int=value_int-5;
                    reference.child("myScore").setValue(value_int);
                    score.setText("Score : "+value_int);

                }
                else
                {   animTranslate_xy.setFillAfter(true);
                    animTranslate_up.setFillAfter(true);
                    animTranslate.setFillAfter(true);
                    txt3.setText("");
                    Rg.startAnimation(animTranslate_xy);
                    txt1.startAnimation(animTranslate_up);
                    txt2.startAnimation(animTranslate_up);
                    hint.startAnimation(animTranslate_up);
                    txt4.startAnimation(animFade);
                    txt4.setVisibility(View.VISIBLE);
                    btn1.setVisibility(View.GONE);
                    btn2.startAnimation(animTranslate);
                    btn2.setVisibility(View.VISIBLE);
                    value_int=Integer.parseInt(value);
                    value_int=value_int+5;
                    reference.child("myScore").setValue(value_int);
                    score.setText("Score : "+value_int);
                    T.cancel();
                    T1.cancel();
                }
            }

        });
        hint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alert.setTitle("Hint !");
                alert.setMessage("Are you sure you want to get a hint ? 2 points will be taken from your score");
                alert.setNegativeButton("No",null);
                alert.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in=getIntent();
                        String Participant=in.getStringExtra("firebase_key");
                        reference=FirebaseDatabase.getInstance().getReference("Participants").child(Participant);
                        value_int=Integer.parseInt(value);
                        value_int=value_int-2;
                        reference.child("myScore").setValue(value_int);
                        score.setText("Score : "+value_int);
                        Intent myIntent = new Intent(getApplicationContext(),ThirdHint.class);
                        myIntent.putExtra("firebase_key",Participant);
                        startActivity(myIntent);
                    }});
                alert.show();



            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1=new Intent(getApplicationContext(),FourthQuestion.class);
                launchActivity1.putExtra("firebase_key",Participant);
                startActivity(launchActivity1);
                finish();
            }
        });
        }
    protected void onResume() {
        Intent in=getIntent();
        super.onResume();
        T1=new CountDownTimer(c, 1000) {
            public void onTick(long millisUntilFinished) {
                c=millisUntilFinished;
                timer.setText("00:" + millisUntilFinished / 1000);
                if (millisUntilFinished/1000<10){
                    timer.setText("00:0" +millisUntilFinished/1000);
                }
            }
            public void onFinish() {
            }

        }.start();
    }

    protected void onPause() {
        super.onPause();
        T.cancel();
        T1.cancel();
        Toast.makeText(getApplicationContext(), "You have "+c/1000+" seconds left", Toast.LENGTH_SHORT).show();
    }
}