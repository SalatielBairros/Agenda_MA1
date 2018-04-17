package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Controllers.BaseController;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models.ContatoModel;

public class ListaContatosActivity extends AppCompatActivity {
    private ListView lista;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);

        btnAdd = findViewById(R.id.btnAdd);
        carregarLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onRestart() {
        carregarLista();
        super.onRestart();
    }

    public void preferencesClick(MenuItem item){
        startActivity(new Intent(ListaContatosActivity.this, PreferencesActivity.class));
    }

    private void carregarLista(){
        BaseController crud = new BaseController(ListaContatosActivity.this);
        ContatoModel model = ContatoModel.getInstance();
        final Cursor cursor = crud.carregaDados(model);
        String[] nomeCampos = model.getColumns().toArray(new String[0]);
        int[] idViews = new int[] {R.id.idContato, R.id.nomeContato, R.id.tvDdd, R.id.tvNumero};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(ListaContatosActivity.this,
                R.layout.item_lista,
                cursor,
                nomeCampos,
                idViews, 0);

        lista = findViewById(R.id.listView);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                cursor.moveToPosition(position);

                String codigo =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContatoModel.C_ID));

                Intent intent = new Intent(ListaContatosActivity.this, AlterarActivity.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                cursor.moveToPosition(position);

                String ddd =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContatoModel.C_DDD));
                String nro =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContatoModel.C_TELEFONE));

                Intent retIntent = AlterarActivity.GetInstance().callNumber(ListaContatosActivity.this, ddd, nro);
                if(retIntent != null) startActivity(retIntent);
                return true;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaContatosActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AlterarActivity.REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, R.string.permissao_concedida_msg, Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    Toast.makeText(this, R.string.erro_sem_permissao, Toast.LENGTH_LONG)
                            .show();
                }
                return;
            }
        }
    }
}
