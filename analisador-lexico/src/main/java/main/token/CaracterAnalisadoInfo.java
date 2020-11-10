package main.token;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaracterAnalisadoInfo {

    private Token token;
    private String valor;

    public static CaracterAnalisadoInfo copy(CaracterAnalisadoInfo toCopy) {
        return CaracterAnalisadoInfo.builder()
                .token(Token.of(toCopy.getToken().getCod(), toCopy.getToken().getSimbolo()))
                .valor(toCopy.getValor())
                .build();
    }
}
