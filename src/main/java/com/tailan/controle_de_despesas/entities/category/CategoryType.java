package com.tailan.controle_de_despesas.entities.category;

public enum CategoryType {
    RECEITA("receita"),
    DESPESA("despesa");

    private String type;
    CategoryType(String type){
        this.type=type;
    }
    public String getType() {
        return type;
    }


}
