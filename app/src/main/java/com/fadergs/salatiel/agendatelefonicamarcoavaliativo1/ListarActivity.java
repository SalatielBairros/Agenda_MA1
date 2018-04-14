package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Controllers.BaseController;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models.ContatoModel;

public class ListarActivity extends AppCompatActivity {
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        BaseController crud = new BaseController(ListarActivity.this);
        ContatoModel model = ContatoModel.getInstance();
        final Cursor cursor = crud.carregaDados(model);
        String[] nomeCampos = model.getColumns().toArray(new String[0]);
        int[] idViews = new int[] {R.id.idContato, R.id.nomeContato};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(ListarActivity.this,
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
                        cursor.getString(cursor.getColumnIndexOrThrow("ID"));

                Intent intent = new Intent(ListarActivity.this, AlterarActivity.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);
                finish();
            }
        });
    }
}
