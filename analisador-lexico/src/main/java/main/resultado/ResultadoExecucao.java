package main.resultado;

import lombok.*;
import main.token.CaracterAnalisadoInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultadoExecucao {

    private List<String> palavras;

    private List<String> mensagensValidacao = new ArrayList<>();

    private Stack<CaracterAnalisadoInfo> stack;

    private StatusAnalise statusAnalise;

    public void addMessage(String message) {
        mensagensValidacao.add(message);
    }
}
