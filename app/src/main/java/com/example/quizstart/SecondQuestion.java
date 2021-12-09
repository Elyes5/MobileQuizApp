package com.example.quizstart;

import
        androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SecondQuestion extends AppCompatActivity {
    ImageButton php;
    ImageButton kotlin;
    ImageButton scala;
    ImageButton js;
    ImageButton r;
    ImageButton python;
    TextView wrongAnswer;
    TextView rightAnswer;
    TextView secondQuestion;
    TextView theQuestion2;
    Animation fade_in;
    Animation translate_up;
    Animation translate_xy;
    Animation translate;
    HorizontalScrollView scroll_view;
    TextView score;
    String value;
    Integer value_int = 0;
    Button nextQuestion;
    DatabaseReference reference;
    TextView hint;
    TextView timer;
    static long b = 30000;
    private CountDownTimer T, T1;

    /* On va déclarer 16 objets:
    4 objets de type Animation
    6 objets de type ImageButton
    4 objets de type TextView
    1 objet de type Button
    1 objet de type HorizontalScrollView
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_question);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        hint = (TextView) findViewById(R.id.hint);
        php = (ImageButton) findViewById(R.id.php);
        kotlin = (ImageButton) findViewById(R.id.kotlin);
        js = (ImageButton) findViewById(R.id.js);
        scala = (ImageButton) findViewById(R.id.scala);
        r = (ImageButton) findViewById(R.id.r);
        python = (ImageButton) findViewById(R.id.python);
        wrongAnswer = (TextView) findViewById(R.id.wrongAnswer);
        rightAnswer = (TextView) findViewById(R.id.goodAnswer);
        secondQuestion = (TextView) findViewById(R.id.secondQuestion);
        rightAnswer = (TextView) findViewById(R.id.goodAnswer);
        nextQuestion = (Button) findViewById(R.id.nextQuestion);
        theQuestion2 = (TextView) findViewById(R.id.theQuestion2);
        scroll_view = (HorizontalScrollView) findViewById(R.id.scrollview);
        score = (TextView) findViewById(R.id.score);
        Intent in = getIntent();
        String Participant = in.getStringExtra("firebase_key");
        reference = FirebaseDatabase.getInstance().getReference("Participants").child(Participant);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value = String.valueOf(snapshot.child("myScore").getValue());
                score.setText("Score : " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /* Encore une fois on va créer la méthode onCreate et on va la surcharger.
        La méthode setContentView nous permettra de définir le fichier XML
        qui va générer l'interface graphique relative à cette activité.
        Ensuite , on va référencer l'ensemble des éléments définis au niveau du fichier
        XML dans les objets ImageButton,TextView et Button déclarés précédemment et
        ceci à partir de leurs identifiants qui ont été définis au niveau du fichier XML.
         */
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        translate_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);
        translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        /* Les 3 animations précédentes ont été expliquées au niveau de la classe
        FirstQuestion.
         */
        translate_xy = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translationxy);
        /* l'objet translate_xy de type Animation va contenir l'animation présente
        dans le fichier translationxy.xml)
        L'animation est en fait une translation suivant l'axe horizontal sur une durée de
        2 secondes.
         */
        timer = (TextView) findViewById(R.id.timer);
        T = new CountDownTimer(b, 1000) {
            public void onTick(long millisUntilFinished) {
                b = millisUntilFinished;
                timer.setText("00:" + millisUntilFinished / 1000);
                if (millisUntilFinished / 1000 < 10) {
                    timer.setText("00:0" + millisUntilFinished / 1000);
                }
            }

            public void onFinish() {
                Intent Int = new Intent(getApplicationContext(), ThirdQuestion.class);
                Int.putExtra("firebase_key", in.getStringExtra("firebase_key"));
                startActivity(Int);
                finish();
            }

        }.start();
        hint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alert.setTitle("Hint !");
                alert.setMessage("Are you sure you want to get a hint ? 2 points will be taken from your score");
                alert.setNegativeButton("No", null);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in = getIntent();
                        String Participant = in.getStringExtra("firebase_key");
                        reference = FirebaseDatabase.getInstance().getReference("Participants").child(Participant);
                        value_int = Integer.parseInt(value);
                        value_int = value_int - 2;
                        reference.child("myScore").setValue(value_int);
                        score.setText("Score : " + value_int);
                        Intent myIntent = new Intent(getApplicationContext(), SecondHint.class);
                        myIntent.putExtra("firebase_key", Participant);
                        startActivity(myIntent);
                    }
                });
                alert.show();


            }
        });
        php.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongAnswer.startAnimation(fade_in);
                wrongAnswer.setVisibility(View.VISIBLE);
                value_int = Integer.parseInt(value);
                value_int = value_int - 5;
                reference.child("myScore").setValue(value_int);
                score.setText("Score : " + value_int);
            }
        });
        python.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongAnswer.startAnimation(fade_in);
                wrongAnswer.setVisibility(View.VISIBLE);
                value_int = Integer.parseInt(value);
                value_int = value_int - 5;
                reference.child("myScore").setValue(value_int);
                score.setText("Score : " + value_int);
            }
        });
        scala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongAnswer.startAnimation(fade_in);
                wrongAnswer.setVisibility(View.VISIBLE);
                value_int = Integer.parseInt(value);
                value_int = value_int - 5;
                reference.child("myScore").setValue(value_int);
                score.setText("Score : " + value_int);
            }
        });
        js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongAnswer.startAnimation(fade_in);
                wrongAnswer.setVisibility(View.VISIBLE);
                value_int = Integer.parseInt(value);
                value_int = value_int - 5;
                reference.child("myScore").setValue(value_int);
                score.setText("Score : " + value_int);
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongAnswer.startAnimation(fade_in);
                wrongAnswer.setVisibility(View.VISIBLE);
                value_int = value_int - 5;
                reference.child("myScore").setValue(value_int);
                score.setText("Score : " + value_int);

            }
        });
        /* Les 5 objets ImageButton vont donner une réponse fausse à la question
        donnée au niveau de l'interface graphique donc lorsque ces objets sont cliqués,il
        faut afficher un message pour informer l'utilisateur que sa réponse n'est
        pas exacte.
         Le message est le contenu de l'objet TextView wrongAnswer.
        On va ainsi créer un ClickListener et ensuite on va créer une méthode
        OnClick qui sera la même pour les 5 objets (l'objet surlequel est appelé
        la méthode différera à chaque fois).La méthode onClick permettra de spécifier
        l'ensemble des traitements réalisés lorsqu'un utilisateur clique sur le bouton.
        Après cela, on va utiliser la méthode startAnimation sur l'objet wrongAnswer
        afin de lancer l'animation fade_in (wrongAnswer.startAnimation(fade_in))
        L'animation permet de faire apparaître le contenu de l'élément wrongAnswer
        d'une manière brusque.
        Enfin , on va rendre le texte de l'objet TextView visible en utilisant
        wrongAnswer.setVisibility(View.VISIBLE).

         */

        kotlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translate_xy.setFillAfter(true);
                translate_up.setFillAfter(true);
                scroll_view.startAnimation(translate_xy);
                wrongAnswer.setText("");
                wrongAnswer.startAnimation(translate_up);
                secondQuestion.startAnimation(translate_up);
                theQuestion2.startAnimation(translate_up);
                rightAnswer.startAnimation(translate);
                rightAnswer.startAnimation(fade_in);
                rightAnswer.setVisibility(View.VISIBLE);
                nextQuestion.startAnimation(translate);
                nextQuestion.setVisibility(View.VISIBLE);
                value_int = Integer.parseInt(value);
                value_int = value_int + 10;
                hint.startAnimation(translate_up);
                reference.child("myScore").setValue(value_int);
                score.setText("Score : " + value_int);
                T.cancel();
                T1.cancel();

            }
        });
        /* Lorsque l'utilisateur appuie sur la bonne réponse. On va l'informer
        que sa réponse est juste et qu'il peut passer à la question suivante.
        Pour cela, on a encore une fois créer un clickListener et une méthode
        Onclick.
        Il existe plusieurs traitements dans la méthode OnClick:
        -En premier lieu , on va utiliser la méthode setFillAfter avec un paramètre
        true sur les deux objets translate_xy et translate_up.
        Ceci va permettre à l'objet de conserver sa position même après une animation
        (l'objet ne retournera pas à sa position initiale).
        Ensuite, on va lancer l'animation translate_xy sur l'objet scrollview
        l'objet scrollView fera ainsi une translation suivant l'axe horizontal
        vers la droite.
        Puis, on va lancer une animation fade_in sur l'objet rightAnswer.L'animation
        fade_in permet dans ce cas de faire apparaître le contenu de la TextView
        rightAnswer d'une manière brusque.
        On va aussi rendre la TextView rightAnswer visible à l'aide de la méthode
        SetVisibility.
        Ensuite, on va lancer l'animation translate_up sur l'objet secondQuestion.
        cet élément fera ainsi une translation vers le haut.
        Ensuite , l'objet nextQuestion va réaliser l'animation translate.
        Il va donc faire une translation du bas vers sa position initiale qui a été
        défini au niveau du document XML.
        On va aussi rendre l'objet nextQuestion visible.
        Enfin, on va appliquer l'animation translate_up sur l'objet theQuestion2.
        L'objet theQuestion2 va ainsi réaliser une translation vers le haut.



         */
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity1 = new Intent(getApplicationContext(), ThirdQuestion.class);
                launchActivity1.putExtra("firebase_key", Participant);
                startActivity(launchActivity1);
                finish();
            }
        });
    }

    protected void onResume() {
        Intent in = getIntent();
        super.onResume();
        T1 = new CountDownTimer(b, 1000) {
            public void onTick(long millisUntilFinished) {
                b = millisUntilFinished;
                timer.setText("00:" + millisUntilFinished / 1000);
                if (millisUntilFinished / 1000 < 10) {
                    timer.setText("00:0" + millisUntilFinished / 1000);
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
        Toast.makeText(getApplicationContext(), "You have " + b / 1000 + " seconds left", Toast.LENGTH_SHORT).show();
    }
}