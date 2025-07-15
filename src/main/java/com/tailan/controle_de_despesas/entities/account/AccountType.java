package com.tailan.controle_de_despesas.entities.account;

public enum AccountType {
    CHECKING("checking"),
    SAVINGS("savings"),
    INVESTIMENT("investiment"),
    CREDIT("credit");
    private String type;
    AccountType(String type){
        this.type=type;
    }
    public String getType() {
        return type;
    }
}
