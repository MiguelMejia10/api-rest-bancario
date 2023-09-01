package com.devsu.api.bancario.enums;

public enum TypeCuenta {

    CORRIENTE("Corriente"), AHORROS("ahorros");

    private final String value;

    TypeCuenta(String value)
    {
        this.value= value;
    }

    public String getValue()
    {
        return value;
    }
}
