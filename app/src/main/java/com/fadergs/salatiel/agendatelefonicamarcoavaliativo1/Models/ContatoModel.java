package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models;

import java.util.ArrayList;
import java.util.List;

public class ContatoModel extends BaseModel{

    private String nome;
    public static final String C_NOME = "NOME";
    private String ddd;
    public static final String C_DDD = "DDD";
    private String telefone;
    public static final String C_TELEFONE = "TELEFONE";

    private ContatoModel(int id) {
        super(id);
    }

    public static ContatoModel getInstance(int id, String nome, String ddd, String telefone){
        ContatoModel contato = getInstance(id);
        contato.nome = nome;
        contato.ddd = ddd;
        contato.telefone = telefone;
        return contato;
    }

    public static ContatoModel getInstance(String nome, String ddd, String telefone){
        ContatoModel contato = getInstance();
        contato.nome = nome;
        contato.ddd = ddd;
        contato.telefone = telefone;
        return contato;
    }

    public static ContatoModel getInstance(){
        return new ContatoModel(0);
    }

    public static ContatoModel getInstance(int id){
        return new ContatoModel(id);
    }

    @Override
    public String getTableName() {
        return "contato";
    }

    @Override
    public int get_id() {
        return _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String CreateTable() {
        return "CREATE TABLE "+ getTableName() +" ("
                + C_ID + " integer primary key autoincrement,"
                + C_NOME + " text,"
                + C_DDD + " text,"
                + C_TELEFONE + " text"
                +")";

    }

    @Override
    public String DropTable() {
        return "DROP TABLE IF EXISTS " + getTableName();
    }

    @Override
    public List<String> getValues() {
        List<String> retList = new ArrayList<>();
        retList.add(Integer.toString(this.get_id()));
        retList.add(this.getNome());
        retList.add(this.getDdd());
        retList.add(this.getTelefone());
        return retList;
    }

    @Override
    public List<String> getColumns() {
        List<String> retList = new ArrayList<>();
        retList.add(C_ID);
        retList.add(C_NOME);
        retList.add(C_DDD);
        retList.add(C_TELEFONE);
        return retList;
    }
}
