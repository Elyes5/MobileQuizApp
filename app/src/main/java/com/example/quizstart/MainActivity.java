package com.example.quizstart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private Button mPlayButton;
    private EditText mNameEditText;
    private static String myName=new String();
    private Animation anim;
    private Animation anim2;
    private Animation anim3;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private TextView mTextViewCtrl;
    //On a déclaré l’ensemble des objets qui vont être utilisés.
    // La plupart de ces objets contiendront les références
    // des éléments graphiques existant au niveau d’activity_main.xml.
    //Il y a aussi la déclaration de deux objets de type
    // Animation que l’on utilisera afin de créer des animations au
    // niveau de l’application afin de la rendre plus interactive.

    private static boolean verifyString(EditText EdTxt) {
        int i = 0;
        int count=0;
        boolean verify=true;
        myName = EdTxt.getText().toString();
        for (i = 0; i < myName.length(); i++) {
            if (myName.charAt(i) >= 'A' && myName.charAt(i) <= 'z')
                count++;
            else
                break;
        }
        if (count==myName.length())
            verify=true;
        else
            verify=false;
        return verify;
    }
    /*
    Après cela, on a implémenté une méthode statique booléenne verifyString qui prend
    en paramètre un élément EditText.
Cette méthode prend en paramètre un élément de type EditText, accède à son contenu
à l’aide de getText et le converti en string à l’aide de la méthode toString.
A l’aide de cette méthode, on va vérifier si le nom entré par l’utilisateur
contient des symboles, des nombres ou des espaces.
 Si c’est le cas, la méthode retournera false sinon la méthode retournera true.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mTextViewCtrl = (TextView) findViewById(R.id.main_textview_ctrl);
        EditText mNameEditText = (EditText) findViewById(R.id.main_edittext_name);
        Button mPlayButton=(Button) findViewById(R.id.main_button_play);
        mPlayButton.setEnabled(false);
        /* On va créer la méthode onCreate (on va surcharger cette méthode).
Ensuite, on va utiliser super.onCreate afin de référencer les objets de la
classe parent.
Puis, on va utiliser setContentView afin d’associer à notre activité une
conception visuelle
qui est dans ce cas le fichier activity_main.xml expliqué précédemment.
Ensuite, on va référencer les éléments graphiques dans des objets à partir de leurs id.
On aura un objet mTextViewCtrl qui contiendra l’élément avec l’id main_text_view_ctrl
contenu dans activity_main.
Un objet mNameEditText qui contiendra l’élément avec l’id main_edittext_name
 contenu dans activity_main.
Un autre objet mPlayButton qui contiendra l’élément avec l’id main_button_play.
Le bouton mPlayButton a été désactivé car l’utilisateur doit taper son nom afin de
cliquer dessus.
*/
        anim2=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
        mNameEditText.startAnimation(anim2);
        /* anim2 va contenir l’animation translate qui a été créée dans
        res/anim/translate.xml
AnimationUtils définit les utilitaires courants pour travailler avec des animations.
On va ensuite charger l’application R.anim.translate à l’aide de la méthode
loadAnimation qui prend en paramètre le contexte de l’application
(getApplicationContext()) ainsi que l’animation à effectuer (R.anim.translate).
Cette animation sera lancée sur l’élément mNameEditText à l’aide de la méthode
startAnimation qui prend en paramètre l’animation qui sera réalisée.
*/
        anim3=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_button);
        mPlayButton.startAnimation(anim3);
        /*Il s’agit du même principe que l’animation précédente. Seulement, on va
         utiliser une autre animation présente dans le dossier anim
         (translate_button). Cette animation sera appliquée au bouton mPlayButton.*/
 mNameEditText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
// This is where we'll check the user input
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });
        /*On va  ajouter un Listener pour le champ que doit
        remplir l’utilisateur.
         Ce Listener va permettre de déclencher une action
         lorsqu’un événement se produit.
L’événement qui doit se produire afin de déclencher
l’action (l’activation du bouton) est le changement
du texte contenu dans l’élément mNameEditText.
Chacune des méthodes BeforeTextChanged et onTextChanged a un rôle.
La méthode BeforeTextChanged par exemple nous informe qu’au
niveau de la chaîne s donnée en paramètre que le nombre
de caractères commençant au début est sur le point
d’être modifié par un autre texte avec une longueur après.
*/
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if  (!MainActivity.verifyString(mNameEditText))
                {   mTextViewCtrl.setText("Your name shouldn't contain digits and symbols");
                    anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                    mTextViewCtrl.startAnimation(anim);
                }/*On va créer un Listener qui va être déclenché lorsque l’utilisateur
            clique sur le bouton lorsqu’il est activé.
Lorsque le bouton est cliqué, L’ensemble des instructions présentes
dans la méthode onClick seront exécutées.
Et donc lorsque le bouton est cliqué, si le
nom saisi par l’utilisateur contient des symboles,
des espaces ou des nombres, on va modifier le texte
contenu dans l’élément mTextViewCtrl et on va aussi appliquer une animation.
L’animation consiste en une apparition brusque du
texte contenu dans l’élément mTextViewCtrl.
Cette animation a été réalisée à l’aide du fichier fade_in.xml présent
dans le dossier anim.
Cet élément mTextVirwCtrl va informerl’utilisateur que le nom ne doit
contenir que des caractères alphabétiques.
*/
                else if(MainActivity.verifyString(mNameEditText))
                {   rootNode=FirebaseDatabase.getInstance();
                    reference=rootNode.getReference().child("Participants");
                    Participants Participant=new Participants(mNameEditText.getText().toString(),0);
                    String Key = reference.push().getKey();
                    reference.child(Key).setValue(Participant);
                    Intent launchActivity=new Intent(getApplicationContext(), Success.class);
                    launchActivity.putExtra("firebase_key",Key);
                    launchActivity.putExtra("message_key", mNameEditText.getText().toString());
                    startActivity(launchActivity);
                    finish();}
                    }
        });
        /*
        Si le nom saisi est accepté (ce n’est ni une chaîne vide ni une chaîne
        de caractères contenant des symboles, des nombres ou des espaces).
On va créer une Intent nommée launchActivity qui va nous permettre de réaliser
la communication entre les différentes activités de l’application.
Ainsi grâce à l’objet Intent, MainActivity va communiquer avec l’activité Success.
On va ainsi stocker le nom contenu dans le champ mNameEditText dans l’objet Intent à
l’aide de la méthode PutExtra.
La méthode PutExtra prend deux paramètres :
-La clé qui est une chaîne de caractères et qui permet d’identifier l’objet stocké.
-L’objet à stocker.

         */
        mNameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() == 0)
                    mNameEditText.setBackground(getDrawable(R.drawable.borderred));
                else if (MainActivity.verifyString(mNameEditText))
                {   mNameEditText.setBackground(getDrawable(R.drawable.bordergreen));
                }
                else if (!MainActivity.verifyString(mNameEditText))
                { mNameEditText.setBackground(getDrawable(R.drawable.borderred));
                }
            }

        });
        /* On a créé un deuxième événement qui se produit lors du
        changement du texte du champ mNameEditText.
Cet événement permet de changer la couleur du « border » de mNameEditText
suivant la validité du nom saisi.
Si le nom est une chaîne vide, la couleur du « border » devient rouge
Sinon Si le nom est une chaîne de caractères contenant des nombres ou
des symboles, la couleur du « border » devient rouge
Sinon, la couleur du « border » devient verte.
Pour effectuer ces changements, il y a eu la création de deux fichiers
XML bordergreen et borderred dans le dossier drawable qui contiendront
les balises utilisées afin de créer ces borders.
Les deux fichiers bordergreen et borderred sont similaires au fichier
border.xml qui a été expliqué au début du compte-rendu.
Il y a seulement une différence au niveau de l’attribut android :color
Pour le fichier bordergreen :
L’attribut android : color a pour valeur @color/green (vert)
Pour le fichier borderred :
L’attribut android : color a pour valeur @color/red (rouge)
*/
    }
}


