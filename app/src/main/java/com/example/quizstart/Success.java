package com.example.quizstart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;


public class Success extends AppCompatActivity {
    private ImageView imgV;
    private Animation animRotation,animFade;
    private TextView success1TxtView;
    private TextView success2TxtView;
    private ProgressBar pb;
    private Thread th;
    private Button startQuiz;
    private Intent in;
    int i;
    /*on a déclaré 7 objets :
    Un objet ImageView sous le nom imgV.
    Un objet Animation sous le nom animRotation.
    Un objet Animation sous le nom animFade.
    Un objet TextView sous le nom success1TxtView.
    Un objet Thread sous le nom th.
    Un objet ProgressBar sous le nom pb.
    Un objet Button sous le nom startQuiz.
    Un objet Intent sous le nom in.
    Un autre objet TextView sous le nom success2TxtView.*/

    private void incBar(){
        for (i=0;i<=100;i=i+10) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pb.setProgress(i);

        }

    }
    /* La méthode incBar permet d'incrémenter l'objet progressBar ,
    L'objet thread va en fait permettre d'arrêter le programme pendant 2 secondes
    afin que l'incrémentation de la progressBar soit réalisé progressivement.
    La boucle va permettre d'incrémenter la barre de 0 à 100 à l'aide du compteur i
    de la boucle.
     */
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in=getIntent();
        setContentView(R.layout.activity_success);
        success1TxtView = (TextView) findViewById(R.id.success1_text_view);
        success2TxtView = (TextView) findViewById(R.id.success2_text_view);
        pb=(ProgressBar) findViewById(R.id.determinateBar);
        imgV=(ImageView) findViewById(R.id.square);
        startQuiz=(Button) findViewById(R.id.startbutton);
        animRotation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        animFade= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        /*On passe ainsi à la méthode surchargée onCreate.
    On utilise en premier lieu super.onCreate afin de référencer les objets de
    la classe parent.
    Ensuite il y a eu la création d’un objet de type Intent sous le nom in à l’aide
     de la méthode getIntent() afin de réaliser la communication entre l’activité
     Main_Activity et Success.
    A l’aide de la méthode setContentView, on va associer notre activité
    au fichier XML afin d’obtenir les éléments graphiques souhaités sur l’interface.
    Après cela, on va référencer les éléments graphiques à partir de leurs
    id dans les objets déclarés précédemment.
    pb va contenir la barre de progression ayant l'id determinateBar.
    imgV va contenir l’image ayant l’id square.
    success1TxtView va contenir l’élément TextView ayant l’id success1_text_view.
    success2TxtView va contenir l’élément TextView ayant l’id success2_text_view.
    startQuiz va contenir l'élément Button ayant l'id startbutton.
    Ensuite à l’aide de l’objet animRotation déclaré, on va créer une animation
    qui permet de réaliser la rotation d’un élément. L’animation a été réalisée
    dans le fichier XML rotate présent dans le dossier anim.
    Il ya aussi eu la création d'une animation animFade qui permet de réaliser
    un effet d'apparition brusque. L'animation a été réalisé dans le fichier XML
    fade_in présent dans le dossier anim.
    Enfin, on a appliqué l'animation animRotation sur l'objet ImgV.
    ImgV va ainsi faire des rotations de 0 à 360° indéfiniment.
*/
        String str=in.getStringExtra("message_key");
        String str1=in.getStringExtra("firebase_key");
        success1TxtView.setText("Welcome "+ str + " ! ");
        /*Enfin, on va créer un objet de type String qui contiendra le contenu
        du champ mNameEditText de l’activité MainActivity. Puisqu’on a ajouté
        les données à l’intent dans MainActivity à l’aide de la méthode putExtra
        expliqué précedemment.
        On va donc rechercher les données existantes dans l’Intent à l’aide de la
        méthode getStringExtra relative à l’objet in de type Intent. On passera en
        paramètre la clé qui nous permettra d’identifier l’objet recherché dans
        l’Intent et on va ainsi modifier le contenu de l’élément TextView
        success1TextView et on va y mettre le mot Welcome suivi du nom saisi dans
        l’activité MainActivity. Ce changement au niveau du contenu de
        success1TextView sera réalisé à l’aide de la méthode setText qui prend en
        paramétre la nouvelle chaîne de caractères.
         */
        th= new Thread(){
            public void run() {
                incBar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startQuiz.startAnimation(animFade);
                        startQuiz.setVisibility(View.VISIBLE);

                    }
                });


            }

        };
                th.start();
          /* On va créer un Thread. Le rôle du Thread est d'arrêter le processus
          d'éxecution pendant une durée de 2 secondes.
          Le thread va contenir une méthode run.
          A l'intérieur de la méthode run, on va faire appel à la méthode incBar()
          qui va permettre d'incrémenter la barre de progression et d'interrompre
          le thread pendant 2 secondes (20 secondes d'interruption au total car
          la boucle dans la méthode incBar va faire 10 itérations).
          Ensuite, on va créer une méthode RunOnUiThread.
          En créant un thread, ce dernier sera le seul à réaliser des modifications
          sur les vues de cette activité donc il faudra utiliser la méthode RunOnUiThread
          qui va permettre de faire des changements graphiques et de retourner les
          changements au thread principal.
          Au niveau de la méthode runOnUiThread,on va lancer une animation de fade_in
          sur le bouton startQuiz.
          On va aussi rendre le bouton startQuiz visible à l'aide de la méthode SetVisibility
          (startQuiz.setVisibility(View.VISIBLE)).
           */
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent in=new Intent(getApplicationContext(),FirstQuestion.class);
             in.putExtra("firebase_key",str1);
             startActivity(in);
             finish();
            }
        });
        /*On va créer une nouvelle Intent, cette intent va prendre en premier
         paramètre le contexte de l'application et en deuxième paramètre l'activité
         à laquelle l'utilisateur sera redirigé.
         Enfin, à l'aide de startActivity, on pourra créer une activité à partir
         de l'intent donnée en paramètre.
         */




    }
}
