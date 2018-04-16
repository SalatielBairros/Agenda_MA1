package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models.UserPreferencesViewModel;

public class PreferencesActivity extends AppCompatActivity {

    public static PreferencesActivity getInstance(){
        return new PreferencesActivity();
    }

    public static final String PREF_NAME = "UserPreferencesMC1";
    Button btnCancelar;
    Button btnSalvar;
    EditText txtCodCidade;
    EditText txtCodOperadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        btnSalvar = findViewById(R.id.btnSalvar);
        btnCancelar = findViewById(R.id.btnCancelar);
        txtCodCidade = findViewById(R.id.edtCodCidade);
        txtCodOperadora = findViewById(R.id.edtCodOperadora);

        setPreferenciasOnFields();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarPreferencias();
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setPreferenciasOnFields(){
        SharedPreferences settings =
                getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        txtCodCidade.setText(settings.getString("codCidade", ""));
        txtCodOperadora.setText(settings.getString("codOperadora", ""));
    }

    private void salvarPreferencias(){
        SharedPreferences settings = getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("codCidade", txtCodCidade.getText().toString());
        editor.putString("codOperadora", txtCodOperadora.getText().toString());
        editor.commit();

        Toast.makeText(PreferencesActivity.this, getString(R.string.sucesso_preferencias), Toast.LENGTH_LONG).show();
    }
}
