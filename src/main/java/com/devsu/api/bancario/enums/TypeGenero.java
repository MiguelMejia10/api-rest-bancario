package com.devsu.api.bancario.enums;

public enum TypeGenero {

    MASCULINO("Masculino"), FEMENINO("Femenino"),NO_ESPECIFICA("otro");

    private final String value;

    TypeGenero(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

}
