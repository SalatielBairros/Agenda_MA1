package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Controllers.BaseController;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models.ContatoModel;

public class AlterarActivity extends AppCompatActivity {
    private EditText txtNome;
    private EditText txtDdd;
    private EditText txtNumero;
    private Button alterar;
    private Button deletar;
    private Button chamar;
    private Cursor cursor;
    private BaseController crud;
    private String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);
        codigo = this.getIntent().getStringExtra("codigo");
        crud = new BaseController(this);
        txtNome = findViewById(R.id.edtNome);
        txtDdd = findViewById(R.id.edtDdd);
        txtNumero = findViewById(R.id.edtNumero);
        alterar = findViewById(R.id.btnAlterar);
        chamar = findViewById(R.id.btnChamar);

        ContatoModel model = ContatoModel.getInstance(Integer.parseInt(codigo));
        cursor = crud.carregaDadoById(model);

        txtNome.setText(cursor.getString(cursor.getColumnIndexOrThrow(model.C_NOME)));
        txtDdd.setText(cursor.getString(cursor.getColumnIndexOrThrow(model.C_DDD)));
        txtNumero.setText(cursor.getString(cursor.getColumnIndexOrThrow(model.C_TELEFONE)));

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContatoModel model = ContatoModel.getInstance(Integer.parseInt(codigo),
                        txtNome.getText().toString(), txtDdd.getText().toString(), txtNumero.getText().toString());
                crud.alteraRegistro(model);

                Intent intent = new Intent(AlterarActivity.this, ListaContatosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        deletar = findViewById(R.id.btnDeletar);
        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crud.deletaRegistro(ContatoModel.getInstance(Integer.parseInt(codigo)));
                Intent intent = new Intent(AlterarActivity.this, ListaContatosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        chamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:" + txtDdd.getText().toString() + txtNumero.getText().toString());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);

                if (ActivityCompat.checkSelfPermission(AlterarActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AlterarActivity.this, "Sem permissão para realizar esta operação.", Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    startActivity(callIntent);
                }
            }
        });
    }
}

