package com.example.quizstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstQuestion extends AppCompatActivity {
    private Animation anim1;
    private Animation anim2;
    private Animation anim3;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private TextView txt5;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    public int CalculateScore=0;
    private Button nextQuestion;
    private TextView hint;
    private String value;
    private int value_int;
    private TextView timer;
    private CountDownTimer T,T1;
    private DatabaseReference reference;
    static long a=30000;
    
    /* On va déclarer 12 objets
    3 animations.
    4 TextView.
    5 boutons.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first_question);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        btn1=(Button) findViewById(R.id.firstButton);
        btn2=(Button) findViewById(R.id.secondButton);
        btn3=(Button) findViewById(R.id.thirdButton);
        btn4=(Button) findViewById(R.id.fourthButton);
        nextQuestion=(Button) findViewById(R.id.nextQuestion);
        Intent in=getIntent();
        String firebaseKey=in.getStringExtra("firebase_key");
        txt1=(TextView) findViewById(R.id.firstQuestion);
        txt2=(TextView) findViewById(R.id.theQuestion);
        txt3=(TextView) findViewById(R.id.goodAnswer);
        txt4=(TextView) findViewById(R.id.wrongAnswer);
        txt5=(TextView) findViewById(R.id.score);
        hint=(TextView) findViewById(R.id.hint);
        timer=(TextView) findViewById(R.id.timer);
        T=new CountDownTimer(a, 1000) {
            public void onTick(long millisUntilFinished) {
                a=millisUntilFinished;
                timer.setText("00:" + millisUntilFinished / 1000);
                if (millisUntilFinished/1000<10){
                    timer.setText("00:0" +millisUntilFinished/1000);
                }
            }
            public void onFinish() {
                Intent Int=new Intent(getApplicationContext(),SecondQuestion.class);
                Int.putExtra("firebase_key",in.getStringExtra("firebase_key"));
                startActivity(Int);
                finish();
            }

        }.start();
        /* On va créer la méthode onCreate (on va surcharger cette méthode).
On va utiliser super.onCreate afin de référencer les objets de la classe parent.
Puis, on va utiliser setContentView afin d’associer à notre activité une conception visuelle
qui est dans ce cas le fichier activity_first_question.xml expliqué précédemment.
Ensuite, on va référencer les éléments graphiques dans des objets à partir de leurs id.*/
        anim1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
        anim2= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        anim3= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_up);
        /* on va affecter à chaque objet de type Animation un style d'animation
        On va affecter translate à anim1. Translate permet de réaliser
         une translation du bas vers la position initiale de l'élément sur une durée
         de 2 secondes.
         (description présente dans le fichier XML translate.xml)
         On va affecter fade_in à anim2. fade_in permet de réaliser un effet d'apparition
         brusque (description présente dans le fichier XML fade_in.xml) sur une durée
         de 2 secondes.
         Enfin, on va affecter translate_up à anim3. translate_up permet de réaliser
         un translation de la position initiale de l'objet vers le haut sur une durée
         de 2 secondes.
         */
        btn1.startAnimation(anim1);
        btn2.startAnimation(anim1);
        btn3.startAnimation(anim1);
        btn4.startAnimation(anim1);
        txt1.startAnimation(anim1);
        txt2.startAnimation(anim1);
        /* On va lancer l'animation anim1 sur les objets btn1,btn2,btn3,btn4,txt1
        et txt2. Tous ces objets, vont réaliser une translation du bas vers leur
        position initiale.
         */
        String Participant=in.getStringExtra("firebase_key");
        reference=FirebaseDatabase.getInstance().getReference("Participants").child(Participant);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value=String.valueOf(snapshot.child("myScore").getValue());
                txt5.setText("Score : "+value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                        txt5.setText("Score : "+value_int);
                        Intent myIntent = new Intent(getApplicationContext(),FirstHint.class);
                        myIntent.putExtra("firebase_key",Participant);
                        startActivity(myIntent);
                    }});
                alert.show();



            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt4.setVisibility(View.VISIBLE);
                txt4.startAnimation(anim2);
                value_int=Integer.parseInt(value);
                value_int=value_int-5;
                reference.child("myScore").setValue(value_int);
                txt5.setText("Score : "+value_int);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt4.setVisibility(View.VISIBLE);
                txt4.startAnimation(anim2);
                value_int=Integer.parseInt(value);
                value_int=value_int-5;
                reference.child("myScore").setValue(value_int);
                txt5.setText("Score : "+value_int);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt4.setVisibility(View.VISIBLE);
                txt4.startAnimation(anim2);
                value_int=value_int-5;
                value_int=Integer.parseInt(value);
                value_int=value_int-5;
                reference.child("myScore").setValue(value_int);
                txt5.setText("Score : "+value_int);
            }
        });
        /* Puisqu'il s'agit d'un quiz, l'utilisateur devra choisir la bonne réponse sinon
        il aura un message l'informant que sa réponse est fausse.
        Dans ce cas, les 3 boutons btn1,btn3 et btn4 ne contiennent pas la bonne réponse
        donc si l'utilisateur clique sur ces boutons, on va changer la visibilité
        de la textView txt4 qui deviendra visible au niveau de l'interface à l'aide
        de l'instruction txt4.setVisibility(View.VISIBLE).
        ensuite, on va lancer l'animation anim2 sur l'objet txt4 à l'aide de la méthode
        startAnimation.Ainsi, l'objet txt4 va apparaître d'une manière brusque.
        Ce traitement sera réalisé à l'aide de l'événement onClick qui permet de spécifier
        l'ensemble des traitements réalisés lors du clic du bouton.

         */
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim2.setDuration(4000);
                txt4.setText("");
                anim3.setFillAfter(true);
                btn1.startAnimation(anim3);
                btn2.startAnimation(anim3);
                btn3.startAnimation(anim3);
                btn4.startAnimation(anim3);
                txt1.startAnimation(anim3);
                txt2.startAnimation(anim3);
                txt4.startAnimation(anim3);
                txt3.startAnimation(anim2);
                txt3.setVisibility(View.VISIBLE);
                nextQuestion.setVisibility(View.VISIBLE);
                nextQuestion.startAnimation(anim1);
                value_int=Integer.parseInt(value);
                value_int=value_int+10;
                hint.startAnimation(anim3);
                reference.child("myScore").setValue(value_int);
                txt5.setText("Score : "+value_int);
                T.cancel();
                T1.cancel();

            }
        });
        /* On va créer un Listener pour le bouton btn2 , ensuite on va surcharger la méthode
        onClick qui permet de spécifier l'ensemble des traitements réalisés après avoir
        cliqué sur le bouton.
        Le bouton btn2 contient la bonne réponse , donc lorsqu'il sera cliqué , on
        va déclencher une animation sur plusieurs objets.
        A l'aide de la méthode setFillAfter avec le paramétre true, l'objet pourra
        conserver la position qu'il aura après l'animation qui est dans ce cas
        une translation vers le haut.
        On va aussi changer la durée de l'animation anim2 à 4 secondes.
        On va donc appliquer cette animation sur les objets btn1,btn2,btn3,btn4,txt1,
        txt2,txt4.
        On va ensuite appliquer l'animation anim2 sur l'objet txt3
        qui va permettre de réaliser apparition brusque de l'objet txt3 (txt3.startAnimation(anim2))
         et on va le rendre visible (txt3.setVisibility(View.VISIBLE)).
        Finalement,on va appliquer l'animation anim1 sur l'objet nextQuestion
        qui va permettre de le translater du bas vers sa position initiale définie
        dans le fichier activity_first_question.xml(nextQuestion.startAnimation(anim1)).
        Aussi on va rendre le bouton nextQuestion visible.
        (nextQuestion.setVisibility(View.VISIBLE)).


         */
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity=new Intent(getApplicationContext(),SecondQuestion.class);
                launchActivity.putExtra("firebase_key",Participant);
                startActivity(launchActivity);
                finish();
            }
        });
        /* On va créer un ClickListener ensuite on va créer une méthode OnClick.
        Dans la méthode onClick, on va créer un objet Intent sous le nom launchActivity
        L'objet Intent prend en paramètre le contexte de l'application ainsi que l'activité
        à laquelle l'utilisateur sera redirigé.
        Enfin à l'aide de startActivity(launchActivity),on va créer une nouvelle
        activité en utilisant l'intent prise en paramètre.
         */
    }
    protected void onResume() {
        Intent in=getIntent();
        super.onResume();
        T1=new CountDownTimer(a, 1000) {
            public void onTick(long millisUntilFinished) {
                a=millisUntilFinished;
                timer.setText("00:" + millisUntilFinished / 1000);
                if (millisUntilFinished/1000<10){
                    timer.setText("00:0" +millisUntilFinished/1000);
                }
            }
            public void onFinish() {
                Intent Int=new Intent(getApplicationContext(),SecondQuestion.class);
                Int.putExtra("firebase_key",in.getStringExtra("firebase_key"));
                startActivity(Int);
                finish();
            }

        }.start();
    }
    protected void onPause() {
        super.onPause();
        T.cancel();
        T1.cancel();
        Toast.makeText(getApplicationContext(), "You have "+a/1000+" seconds left", Toast.LENGTH_SHORT).show();

    }
    }