package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models;

public class UserPreferencesViewModel {
    private String codCidade;
    private String codOperadora;

    public UserPreferencesViewModel(String codCidade, String codOperadora){
        setCodCidade(codCidade);
        setCodOperadora(codOperadora);
    }

    public String getCodCidade() {
        return codCidade;
    }

    public void setCodCidade(String codCidade) {
        this.codCidade = codCidade;
    }

    public String getCodOperadora() {
        return codOperadora;
    }

    public void setCodOperadora(String codOperadora) {
        this.codOperadora = codOperadora;
    }
}
