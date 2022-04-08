package br.edu.utfpr.parkineasy.model.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum TipoVaga {
    COMUM(1),
    DEFICIENTE(2),
    IDOSO(3);

    private static final Map<Integer, TipoVaga> map = new HashMap<>();

    static {
        for (TipoVaga tipoVaga : TipoVaga.values()) {
            map.put(tipoVaga.value, tipoVaga);
        }
    }

    private final int value;

    TipoVaga(int value) {
        this.value = value;
    }

    public static TipoVaga valueOf(int tipoVaga) {
        return map.get(tipoVaga);
    }

    public int getValue() {
        return value;
    }
}
