package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.DataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models.ContatoModel;

public class AgendaContext extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "agenda.db";

    public AgendaContext(Context context) {
        super(context, NOME_BANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ContatoModel.getInstance().CreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ContatoModel.getInstance().DropTable());
        onCreate(sqLiteDatabase);
    }
}
