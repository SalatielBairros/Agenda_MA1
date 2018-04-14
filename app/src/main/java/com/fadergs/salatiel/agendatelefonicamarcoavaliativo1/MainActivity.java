package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1;

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
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseController crud = new BaseController(MainActivity.this);
                nome = findViewById(R.id.edtNome);
                ddd = findViewById((R.id.edtDdd));
                numero = findViewById(R.id.edtNumero);
                ContatoModel contato = ContatoModel.getInstance(
                        nome.getText().toString(), ddd.getText().toString(), numero.getText().toString());

                String resultado = crud.insereDado(contato);
                Toast.makeText(getApplicationContext(), resultado,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void btnListar(View view) {
        Intent intent = new Intent(this, ListarActivity.class);
        startActivity(intent);
    }
}