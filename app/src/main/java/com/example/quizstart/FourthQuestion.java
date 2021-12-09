package com.example.quizstart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.CheckBox;
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

import org.w3c.dom.Text;

public class FourthQuestion extends AppCompatActivity {
    CheckBox CheckBox1;
    CheckBox CheckBox2;
    CheckBox CheckBox3;
    CheckBox CheckBox4;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    Animation animFade;
    Animation animTranslate_up;
    Animation animTranslate_xy;
    Animation animTranslate;
    Button btn1;
    Button btn2;
    int value_int;
    String value;
    TextView score,hint,timer;
    DatabaseReference reference;
    CountDownTimer T,T1;
    static long d=30000;
    private void passValue(){
        Intent in =getIntent();
        Intent Passed=new Intent(getApplicationContext(),Rank.class);
        Passed.putExtra("firebase_key",in.getStringExtra("firebase_key"));
        startActivity(Passed);
        finish();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_question);
        CheckBox1 = (CheckBox) findViewById(R.id.Oreo);
        CheckBox2 = (CheckBox) findViewById(R.id.Iron);
        CheckBox3 = (CheckBox) findViewById(R.id.Kitkat);
        CheckBox4 = (CheckBox) findViewById(R.id.Gingerbread);
        hint = (TextView) findViewById(R.id.hint);
        txt1 = (TextView) findViewById(R.id.firstQuestion);
        txt2 = (TextView) findViewById(R.id.theQuestion);
        txt3 = (TextView) findViewById(R.id.wrongAnswer);
        txt4 = (TextView) findViewById(R.id.goodAnswer);
        btn1 = (Button) findViewById(R.id.submit);
        btn2 = (Button) findViewById(R.id.Rank);
        score = (TextView) findViewById(R.id.score);
        timer = (TextView) findViewById(R.id.timer);
        animFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animTranslate_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);
        animTranslate_xy = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translationxy);
        animTranslate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        Intent in=getIntent();
        String Participant=in.getStringExtra("firebase_key");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
                        Intent myIntent = new Intent(getApplicationContext(),FourthHint.class);
                        myIntent.putExtra("firebase_key",Participant);
                        startActivity(myIntent);
                    }});
                alert.show();



            }
        });
        T=new CountDownTimer(d, 1000) {
            public void onTick(long millisUntilFinished) {
                d=millisUntilFinished;
                timer.setText("00:" + millisUntilFinished / 1000);
                if (millisUntilFinished/1000<10){
                    timer.setText("00:0" +millisUntilFinished/1000);
                }
            }
            public void onFinish() {
                Intent Int=new Intent(getApplicationContext(),Rank.class);
                Int.putExtra("firebase_key",in.getStringExtra("firebase_key"));
                startActivity(Int);
                finish();
            }

        }.start();
        reference= FirebaseDatabase.getInstance().getReference("Participants").child(Participant);
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

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckBox2.isChecked()) {
                    txt3.setText("Wrong Answer !");
                    txt3.startAnimation(animFade);
                    txt3.setVisibility(View.VISIBLE);
                    value_int=Integer.parseInt(value);
                    value_int=value_int-5;
                    reference.child("myScore").setValue(value_int);
                    score.setText("Score : "+value_int);
                } else {
                    if (CheckBox1.isChecked() && CheckBox3.isChecked() && CheckBox4.isChecked()) {
                        animTranslate_xy.setFillAfter(true);
                        animTranslate_up.setFillAfter(true);
                        animTranslate.setFillAfter(true);
                        txt3.setText("");
                        CheckBox1.startAnimation(animTranslate_xy);
                        CheckBox2.startAnimation(animTranslate_xy);
                        CheckBox3.startAnimation(animTranslate_xy);
                        CheckBox4.startAnimation(animTranslate_xy);
                        txt1.startAnimation(animTranslate_up);
                        txt2.startAnimation(animTranslate_up);
                        txt4.startAnimation(animFade);
                        txt4.setText("Perfect");
                        txt4.setVisibility(View.VISIBLE);
                        btn1.setVisibility(View.GONE);
                        btn2.startAnimation(animTranslate);
                        btn2.setVisibility(View.VISIBLE);
                        value_int=Integer.parseInt(value);
                        hint.startAnimation(animTranslate_up);
                        value_int=value_int+15;
                        reference.child("myScore").setValue(value_int);
                        score.setText("Score : "+value_int);
                        T.cancel();
                        T1.cancel();
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                passValue();
                            }
                        });
                    } else if (CheckBox1.isChecked() && CheckBox3.isChecked() && !CheckBox4.isChecked()) {
                        animTranslate_xy.setFillAfter(true);
                        animTranslate_up.setFillAfter(true);
                        animTranslate.setFillAfter(true);
                        txt3.setText("");
                        CheckBox1.startAnimation(animTranslate_xy);
                        CheckBox2.startAnimation(animTranslate_xy);
                        CheckBox3.startAnimation(animTranslate_xy);
                        CheckBox4.startAnimation(animTranslate_xy);
                        txt1.startAnimation(animTranslate_up);
                        txt2.startAnimation(animTranslate_up);
                        txt4.startAnimation(animFade);
                        txt4.setVisibility(View.VISIBLE);
                        btn1.setVisibility(View.GONE);
                        btn2.startAnimation(animTranslate);
                        btn2.setVisibility(View.VISIBLE);
                        txt4.setText("2 Good Answers");
                        txt4.setTextSize(30);
                        value_int=Integer.parseInt(value);
                        value_int=value_int+10;
                        T.cancel();
                        T1.cancel();
                        hint.startAnimation(animTranslate_up);
                        reference.child("myScore").setValue(value_int);
                        score.setText("Score : "+value_int);
                    } else if (CheckBox1.isChecked() && !CheckBox3.isChecked() && CheckBox4.isChecked()) {
                        animTranslate_xy.setFillAfter(true);
                        animTranslate_up.setFillAfter(true);
                        animTranslate.setFillAfter(true);
                        txt3.setText("");
                        CheckBox1.startAnimation(animTranslate_xy);
                        CheckBox2.startAnimation(animTranslate_xy);
                        CheckBox3.startAnimation(animTranslate_xy);
                        CheckBox4.startAnimation(animTranslate_xy);
                        txt1.startAnimation(animTranslate_up);
                        txt2.startAnimation(animTranslate_up);
                        txt4.startAnimation(animFade);
                        txt4.setVisibility(View.VISIBLE);
                        btn1.setVisibility(View.GONE);
                        btn2.startAnimation(animTranslate);
                        txt4.setText("2 Good Answers");
                        txt4.setTextSize(30);
                        btn2.setVisibility(View.VISIBLE);
                        value_int=Integer.parseInt(value);
                        value_int=value_int+5;
                        reference.child("myScore").setValue(value_int);
                        score.setText("Score : "+value_int);
                        hint.startAnimation(animTranslate_up);
                        T.cancel();
                        T1.cancel();
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                passValue();
                            }
                        });
                    } else if (CheckBox1.isChecked() && !CheckBox3.isChecked() && !CheckBox4.isChecked()) {
                        animTranslate_xy.setFillAfter(true);
                        animTranslate_up.setFillAfter(true);
                        animTranslate.setFillAfter(true);
                        txt3.setText("");
                        CheckBox1.startAnimation(animTranslate_xy);
                        CheckBox2.startAnimation(animTranslate_xy);
                        CheckBox3.startAnimation(animTranslate_xy);
                        CheckBox4.startAnimation(animTranslate_xy);
                        txt1.startAnimation(animTranslate_up);
                        txt2.startAnimation(animTranslate_up);
                        txt4.startAnimation(animFade);
                        txt4.setText("1 Good Answer");
                        txt4.setTextSize(30);
                        txt4.setVisibility(View.VISIBLE);
                        btn1.setVisibility(View.GONE);
                        btn2.startAnimation(animTranslate);
                        btn2.setVisibility(View.VISIBLE);
                        value_int=Integer.parseInt(value);
                        value_int=value_int+5;
                        hint.startAnimation(animTranslate_up);
                        reference.child("myScore").setValue(value_int);
                        T.cancel();
                        T1.cancel();
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                passValue();
                            }
                        });
                        score.setText("Score : "+value_int);
                    } else if (!CheckBox1.isChecked() && CheckBox3.isChecked() && CheckBox4.isChecked()) {
                        animTranslate_xy.setFillAfter(true);
                        animTranslate_up.setFillAfter(true);
                        animTranslate.setFillAfter(true);
                        txt3.setText("");
                        CheckBox1.startAnimation(animTranslate_xy);
                        CheckBox2.startAnimation(animTranslate_xy);
                        CheckBox3.startAnimation(animTranslate_xy);
                        CheckBox4.startAnimation(animTranslate_xy);
                        txt1.startAnimation(animTranslate_up);
                        txt2.startAnimation(animTranslate_up);
                        txt4.startAnimation(animFade);
                        txt4.setVisibility(View.VISIBLE);
                        btn1.setVisibility(View.GONE);
                        btn2.startAnimation(animTranslate);
                        txt4.setText("2 Good Answers");
                        txt4.setTextSize(30);
                        btn2.setVisibility(View.VISIBLE);
                        value_int=Integer.parseInt(value);
                        value_int=value_int+10;
                        reference.child("myScore").setValue(value_int);
                        hint.startAnimation(animTranslate_up);
                        T.cancel();
                        T1.cancel();
                        score.setText("Score : "+value_int);
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                passValue();
                            }
                        });
                    } else if (!CheckBox1.isChecked() && CheckBox3.isChecked() && !CheckBox4.isChecked()) {
                        animTranslate_xy.setFillAfter(true);
                        animTranslate_up.setFillAfter(true);
                        animTranslate.setFillAfter(true);
                        txt3.setText("");
                        txt4.setText("1 Good Answer");
                        txt4.setTextSize(30);
                        CheckBox1.startAnimation(animTranslate_xy);
                        CheckBox2.startAnimation(animTranslate_xy);
                        CheckBox3.startAnimation(animTranslate_xy);
                        CheckBox4.startAnimation(animTranslate_xy);
                        txt1.startAnimation(animTranslate_up);
                        txt2.startAnimation(animTranslate_up);
                        txt4.startAnimation(animFade);
                        txt4.setVisibility(View.VISIBLE);
                        btn1.setVisibility(View.GONE);
                        btn2.startAnimation(animTranslate);
                        btn2.setVisibility(View.VISIBLE);
                        hint.startAnimation(animTranslate_up);
                        value_int=Integer.parseInt(value);
                        value_int=value_int+5;
                        T.cancel();
                        T1.cancel();
                        reference.child("myScore").setValue(value_int);
                        score.setText("Score : "+value_int);
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                passValue();
                            }
                        });
                    } else if (!CheckBox1.isChecked() && !CheckBox3.isChecked() && CheckBox4.isChecked()) {
                        animTranslate_xy.setFillAfter(true);
                        animTranslate_up.setFillAfter(true);
                        animTranslate.setFillAfter(true);
                        txt3.setText("");
                        txt4.setText("2 Good Answers");
                        txt4.setTextSize(30);
                        CheckBox1.startAnimation(animTranslate_xy);
                        CheckBox2.startAnimation(animTranslate_xy);
                        CheckBox3.startAnimation(animTranslate_xy);
                        CheckBox4.startAnimation(animTranslate_xy);
                        txt1.startAnimation(animTranslate_up);
                        txt2.startAnimation(animTranslate_up);
                        txt4.startAnimation(animFade);
                        txt4.setVisibility(View.VISIBLE);
                        btn1.setVisibility(View.GONE);
                        btn2.startAnimation(animTranslate);
                        btn2.setVisibility(View.VISIBLE);
                        value_int=Integer.parseInt(value);
                        value_int=value_int+5;
                        hint.startAnimation(animTranslate_up);
                        reference.child("myScore").setValue(value_int);
                        T.cancel();
                        T1.cancel();
                        score.setText("Score : "+value_int);
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                passValue();
                            }
                        });
                    } else if (!CheckBox1.isChecked() && !CheckBox3.isChecked() && !CheckBox4.isChecked()) {
                        txt3.setText("You must Choose a value !");
                        txt3.startAnimation(animFade);
                        txt3.setVisibility(View.VISIBLE);
                    }


                }
            }
        });

        }
    protected void onResume() {
        Intent in=getIntent();
        super.onResume();
        T1=new CountDownTimer(d, 1000) {
            public void onTick(long millisUntilFinished) {
                d=millisUntilFinished;
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
        Toast.makeText(getApplicationContext(), "You have "+d/1000+" seconds left", Toast.LENGTH_SHORT).show();
    }
    }