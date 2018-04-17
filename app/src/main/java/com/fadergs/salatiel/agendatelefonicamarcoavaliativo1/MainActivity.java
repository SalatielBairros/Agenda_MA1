package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Controllers.BaseController;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models.ContatoModel;

public class MainActivity extends AppCompatActivity {
    private Button btnCadastrar;
    private EditText nome;
    private EditText ddd;
    private EditText numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        nome = findViewById(R.id.edtNome);
        ddd = findViewById((R.id.edtDdd));
        numero = findViewById(R.id.edtNumero);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar(MainActivity.this, nome.getText().toString(), ddd.getText().toString(), numero.getText().toString())) {
                    BaseController crud = new BaseController(MainActivity.this);
                    ContatoModel contato = ContatoModel.getInstance(
                            nome.getText().toString(), ddd.getText().toString(), numero.getText().toString());

                    String resultado = crud.insereDado(contato);
                    Toast.makeText(getApplicationContext(), resultado,
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    public void btnListar(View view) {
        finish();
    }

    public static boolean validar(Context context, String nome, String ddd, String numero){
        if(nome.trim().length() == 0){
            Toast.makeText(context, R.string.erro_campo_nome, Toast.LENGTH_SHORT).show();
        }
        else if(ddd.trim().length() == 0){
            Toast.makeText(context, R.string.erro_campo_ddd, Toast.LENGTH_SHORT).show();
        }
        else if(numero.trim().length() == 0){
            Toast.makeText(context, R.string.erro_campo_numero, Toast.LENGTH_SHORT).show();
        }
        else{
            return true;
        }
        return false;
    }
}