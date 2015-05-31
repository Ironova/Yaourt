package com.qualitylabs.yaourt;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class MainActivity extends Activity {

    EditText salaire = null;
    RadioGroup brut_net = null;
    RadioButton radius_brut = null;
    Button convertir = null;
    TextView conv_en = null;
    TextView resultat = null;
    TextView salaireHoraire = null;
    TextView salaireAnnuel = null;

    DecimalFormat df = new DecimalFormat("##.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Récuperation des vues
        salaire = (EditText)findViewById(R.id.salaire);
        brut_net = (RadioGroup)findViewById(R.id.brut_net);
        convertir = (Button)findViewById(R.id.button_convertir);
        conv_en = (TextView)findViewById(R.id.conv_en);
        resultat = (TextView)findViewById(R.id.resultat);
        salaireHoraire = (TextView)findViewById(R.id.salaireHoraire);
        salaireAnnuel = (TextView)findViewById(R.id.salaireAnnuel);

        //Association de Listener aux vues
        salaire.addTextChangedListener(saisieSalaire);
        convertir.setOnClickListener(convertionListener);
    }


    private TextWatcher saisieSalaire = new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           conv_en.setText("");
           resultat.setText("");
           salaireHoraire.setText("");
           salaireAnnuel.setText("");
       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {

       }

       @Override
       public void afterTextChanged(Editable s) {

       }
   };


    private View.OnClickListener convertionListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (brut_net.getCheckedRadioButtonId() == R.id.radius_brut) {
                conv_en.setText("Conversion en salaire net");
                Double result = Double.valueOf(salaire.getText().toString());
                result = 0.77 * result;
                double horaire = result / 151.67;
                double annuel = result * 12;
                resultat.setText(String.valueOf(df.format(result) + " €"));
                salaireHoraire.setText(String.valueOf(df.format(horaire) + " € Horaire"));
                salaireAnnuel.setText(String.valueOf(df.format(annuel) + " € Annuel"));

                //Création d'un Toast avec (NomActivité.this, "texte à afficher", Toast.LENGTH_[SHORT/LONG] pour la durée).show()
                Toast.makeText(MainActivity.this, "Salaire converti en net avec succès!", Toast.LENGTH_SHORT).show();
            } else {
                conv_en.setText("Conversion en salaire brut");
                Double result = Double.valueOf(salaire.getText().toString());
                result = 1.23 * result;
                double horaire = result / 151.67;
                double annuel = result * 12;
                resultat.setText(String.valueOf(df.format(result) + "€"));
                salaireHoraire.setText(String.valueOf(df.format(horaire) + " € Horaire"));
                salaireAnnuel.setText(String.valueOf(df.format(annuel) + " € Annuel"));
                //Création d'un Toast avec (NomActivité.this, "texte à afficher", Toast.LENGTH_[SHORT/LONG] pour la durée).show()
                Toast.makeText(MainActivity.this, "Salaire converti en brut avec succès!", Toast.LENGTH_SHORT).show();
            }
        }

    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void OpenAPropos(MenuItem item){
        Intent aPropos = new Intent(this, A_propos.class);
        startActivity(aPropos);
    }

    public void reset(MenuItem item){
        Intent reset = new Intent();
        conv_en.setText("");
        resultat.setText("");
        salaireAnnuel.setText("");
        salaireHoraire.setText("");
        salaire.setText("");
        brut_net.clearCheck();
        Toast.makeText(MainActivity.this, "Réinitialisé", Toast.LENGTH_SHORT).show();
    }
}
