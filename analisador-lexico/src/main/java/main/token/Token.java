package main.token;

import lombok.*;

@Getter
@Setter
@Builder
public class Token {

    public Integer cod;

    public String simbolo;

    private Token(Integer cod, String simbolo) {
        this.cod = cod;
        this.simbolo = simbolo;
    }

    public static Token of(Integer cod, String simbolo) {
        return new Token(cod, simbolo);
    }

    public static Token identificador() {
        return new Token(25, "IDENTIFICADOR");
    }
}
