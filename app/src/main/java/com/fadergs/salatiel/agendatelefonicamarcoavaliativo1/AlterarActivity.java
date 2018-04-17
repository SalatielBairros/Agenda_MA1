package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Controllers.BaseController;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models.ContatoModel;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models.UserPreferencesViewModel;

public class AlterarActivity extends AppCompatActivity {
    public static AlterarActivity GetInstance(){
        return new AlterarActivity();
    }

    private EditText txtNome;
    private EditText txtDdd;
    private EditText txtNumero;
    private Button alterar;
    private Button deletar;
    private Button cancelar;
    private Cursor cursor;
    private BaseController crud;
    private String codigo;
    public static final int REQUEST_PERMISSION_CODE = 10;

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
        cancelar = findViewById(R.id.btnCancelar);

        ContatoModel model = ContatoModel.getInstance(Integer.parseInt(codigo));
        cursor = crud.carregaDadoById(model);

        txtNome.setText(cursor.getString(cursor.getColumnIndexOrThrow(model.C_NOME)));
        txtDdd.setText(cursor.getString(cursor.getColumnIndexOrThrow(model.C_DDD)));
        txtNumero.setText(cursor.getString(cursor.getColumnIndexOrThrow(model.C_TELEFONE)));

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome =  txtNome.getText().toString();
                String ddd =  txtDdd.getText().toString();
                String numero =  txtNumero.getText().toString();

                if(MainActivity.validar(AlterarActivity.this, nome, ddd, numero)) {
                    ContatoModel model = ContatoModel.getInstance(Integer.parseInt(codigo),
                            nome, ddd, numero);
                    crud.alteraRegistro(model);

                    Intent intent = new Intent(AlterarActivity.this, ListaContatosActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        deletar = findViewById(R.id.btnDeletar);
        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AlterarActivity.this)
                        .setTitle(R.string.deletando_contato)
                        .setMessage(R.string.msg_confirmacao_exclusao)
                        .setPositiveButton(getString(R.string.sim_msg), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                crud.deletaRegistro(ContatoModel.getInstance(Integer.parseInt(codigo)));
                                Intent intent = new Intent(AlterarActivity.this, ListaContatosActivity.class);
                                startActivity(intent);
                                Toast.makeText(AlterarActivity.this, R.string.sucesso_removido_contato, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.nao_msg), null)
                        .show();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 finish();
            }
        });
    }

    public Intent callNumber(Context context, String ddd, String number){

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            SharedPreferences settings = context.getSharedPreferences(PreferencesActivity.PREF_NAME, Context.MODE_PRIVATE);
            UserPreferencesViewModel preferences =
                    new UserPreferencesViewModel(settings.getString("codCidade", ""),
                            settings.getString("codOperadora", ""));
            if(preferences.validate()) {
                Uri uri = Uri.parse("tel:0" +
                        (!preferences.getCodCidade().equals(ddd) ? preferences.getCodOperadora() : "")
                        + ddd
                        + number);

                Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);

                return callIntent;
            }
            else{
                Toast.makeText(context, R.string.erro_preferencias_usuario_cad, Toast.LENGTH_LONG)
                        .show();
            }
        }
        else {
            ActivityCompat.requestPermissions((Activity)context,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PERMISSION_CODE);
        }
        return null;
    }
}

