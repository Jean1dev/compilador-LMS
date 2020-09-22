import main.analisador.Executor;
import org.junit.Test;

public class ExecutorTest {

    @Test
    public void testeGramatica1() {
        String gramatica = "Program testeproc1;\n" +
                "Var\n" +
                " X, y, z :integer;\n" +
                "Procedure P;\n" +
                "Var\n" +
                " A :integer;\n" +
                "Begin\n" +
                " Readln(a);\n" +
                " If a=x then\n" +
                " z:=z+x\n" +
                " Else begin\n" +
                " Z:=z-x;\n" +
                " Call p;\n" +
                " End;\n" +
                "End;\n" +
                "Begin\n" +
                " Z:=0;\n" +
                " Readln(x,y);\n" +
                " If x>y then\n" +
                " Call p\n" +
                " Else\n" +
                " Z:=z+x+y";

        new Executor(gramatica)
                .lerString(gramatica)
                .analisarTexto()
                .validarTexto()
                .consolidarResultado();

    }
}
