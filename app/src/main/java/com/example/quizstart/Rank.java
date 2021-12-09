package com.example.quizstart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Vector;

public class Rank extends AppCompatActivity {
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TableLayout TabLay;
    private ScrollView scr;
    private String a="";
    private Vector <Participants> vecParticipants;
    private int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        Intent In=getIntent();
        TabLay=(TableLayout) findViewById(R.id.myTable);
        Toast.makeText(getApplicationContext(), "NEW ONE", Toast.LENGTH_SHORT).show();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref =database.getReference("Participants");
        Query qr1=ref.orderByChild("myScore");

        qr1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dtSnap : dataSnapshot.getChildren()){
                    if (dtSnap!=null) {
                        Participants pt = new Participants(dtSnap.child("name").getValue().toString(), Integer.parseInt(dtSnap.child("myScore").getValue().toString()));
                            TextView txtRank=new TextView(getApplicationContext());
                            TextView txtName=new TextView(getApplicationContext());
                            TextView txtScore=new TextView(getApplicationContext());
                            TextView txtRank1=new TextView(getApplicationContext());
                            TextView txtName1=new TextView(getApplicationContext());
                            TextView txtScore1=new TextView(getApplicationContext());
                            TableRow tabRow= new TableRow(getApplicationContext());
                            TableRow tabRow1= new TableRow(getApplicationContext());
                            if (i==1){
                                TableRow.LayoutParams paramsExample = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                                paramsExample.setMargins(0, 0, 0, 5);
                                txtRank1.setText("Rank");
                                txtRank1.setTextColor(Color.YELLOW);
                                txtRank1.setWidth(450);
                                txtRank1.setBackground(getDrawable(R.color.bcl));
                                txtRank1.setPadding(0,150,0,150);
                                txtRank1.setLayoutParams(paramsExample);
                                tabRow1.addView(txtRank1);
                                txtName1.setText("Name");
                                txtName1.setTextColor(Color.YELLOW);
                                txtName1.setWidth(450);
                                txtName1.setBackground(getDrawable(R.color.bcl));
                                txtName1.setPadding(0,150,0,150);
                                txtName1.setLayoutParams(paramsExample);
                                tabRow1.addView(txtName1);
                                txtScore1.setText("Score");
                                txtScore1.setTextColor(Color.YELLOW);
                                txtScore1.setWidth(450);
                                txtScore1.setBackground(getDrawable(R.color.bcl));
                                txtScore1.setPadding(0,150,0,150);
                                txtScore1.setLayoutParams(paramsExample);
                                tabRow1.addView(txtScore1);
                                TabLay.addView(tabRow1);
                                txtName1.setTypeface(null, Typeface.BOLD);
                                txtScore1.setTypeface(null, Typeface.BOLD);
                                txtRank1.setTypeface(null, Typeface.BOLD);
                            }
                            TableRow.LayoutParams paramsExample = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                            paramsExample.setMargins(0, 0, 0, 5);
                            txtRank.setText(String.valueOf(i));
                            txtRank.setTextColor(Color.WHITE);
                            txtRank.setWidth(450);
                            txtRank.setBackground(getDrawable(R.color.bcl));
                            txtRank.setPadding(0,150,0,150);
                            txtRank.setLayoutParams(paramsExample);
                            tabRow.addView(txtRank);
                            txtName.setText(pt.name.toString());
                            txtName.setTextColor(Color.WHITE);
                            txtName.setWidth(450);
                            txtName.setBackground(getDrawable(R.color.bcl));
                            txtName.setPadding(0,150,0,150);
                            txtName.setLayoutParams(paramsExample);
                            tabRow.addView(txtName);
                            txtScore.setText(String.valueOf(pt.myScore));
                            txtScore.setTextColor(Color.WHITE);
                            txtScore.setWidth(450);
                            txtScore.setBackground(getDrawable(R.color.bcl));
                            txtScore.setPadding(0,150,0,150);
                            txtScore.setLayoutParams(paramsExample);
                            tabRow.addView(txtScore);
                            TabLay.addView(tabRow);
                            i++;
                        Log.d("msg",dtSnap.getKey());
                        Log.d("msg",In.getStringExtra("firebase_key"));
                            if (dtSnap.getKey().equals(In.getStringExtra("firebase_key"))){

                                txtScore.setTextColor(Color.YELLOW);
                                txtName.setTextColor(Color.YELLOW);
                                txtRank.setTextColor(Color.YELLOW);
                                txtName.setTypeface(null, Typeface.BOLD);
                                txtScore.setTypeface(null, Typeface.BOLD);
                                txtRank.setTypeface(null, Typeface.BOLD);
                            }
                        }

                    }

                    }




            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}