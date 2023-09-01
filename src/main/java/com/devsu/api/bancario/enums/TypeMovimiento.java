package com.devsu.api.bancario.enums;

public enum TypeMovimiento {

    DEBITO("Debito"), CREDITO("Credito");

    private final String value;

    TypeMovimiento(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
