package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.DataAccess.AgendaContext;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Interfaces.IEntityModel;

public class BaseController {

    private SQLiteDatabase db;
    private AgendaContext banco;

    public BaseController(Context context){
        banco = new AgendaContext(context);
    }

    public String insereDado(IEntityModel model){
        ContentValues valores;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        for (int i = 1; i < model.getColumns().size(); i++) {
            valores.put(model.getColumns().get(i), model.getValues().get(i));
        }

        long resultado = db.insert(model.getTableName(), null, valores);
        db.close();
        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro Inserido com sucesso";
        }
    }

    public Cursor carregaDados(IEntityModel model){
        Cursor cursor;
        String[] campos = model.getColumns().toArray(new String[0]);
        db = banco.getReadableDatabase();
        cursor = db.query(model.getTableName(), campos, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadoById(IEntityModel model){
        Cursor cursor;
        String[] campos = model.getColumns().toArray(new String[0]);
        String where = "_id=" + model.get_id();
        db = banco.getReadableDatabase();
        cursor = db.query(model.getTableName() ,campos, where, null, null, null, null,
                null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void alteraRegistro(IEntityModel model){
        ContentValues valores;
        String where = "_id=" + model.get_id();
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        for (int i = 0; i < model.getColumns().size(); i++) {
            valores.put(model.getColumns().get(i), model.getValues().get(i));
        }
        db.update(model.getTableName(),valores,where,null);
        db.close();
    }

    public void deletaRegistro(IEntityModel model){
        String where = "_id = " + model.get_id();
        db = banco.getReadableDatabase();
        db.delete(model.getTableName(),where,null);
        db.close();
    }
}
