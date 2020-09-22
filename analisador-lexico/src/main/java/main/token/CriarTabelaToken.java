package main.token;

import java.util.ArrayList;
import java.util.List;

public final class CriarTabelaToken {

    private final List<Token> tabelaTokens;

    public CriarTabelaToken() {
        tabelaTokens = new ArrayList<Token>();
        tabelaTokens.add(Token.of(1, "PROGRAM"));
        tabelaTokens.add(Token.of(2, "LABEL"));
        tabelaTokens.add(Token.of(3, "CONST"));
        tabelaTokens.add(Token.of(4, "VAR"));
        tabelaTokens.add(Token.of(6, "BEGIN"));
        tabelaTokens.add(Token.of(7, "END"));
        tabelaTokens.add(Token.of(7, "END"));
        tabelaTokens.add(Token.of(8, "INTEGER"));
        tabelaTokens.add(Token.of(9, "ARRAY"));
        tabelaTokens.add(Token.of(10, "OF"));
        tabelaTokens.add(Token.of(11, "CALL"));
        tabelaTokens.add(Token.of(12, "GOTO"));
        tabelaTokens.add(Token.of(13, "IF"));
        tabelaTokens.add(Token.of(14, "THEN"));
        tabelaTokens.add(Token.of(15, "ELSE"));
        tabelaTokens.add(Token.of(16, "WHILE"));
        tabelaTokens.add(Token.of(17, "DO"));
        tabelaTokens.add(Token.of(18, "REPEAT"));
        tabelaTokens.add(Token.of(19, "UNTIL"));
        tabelaTokens.add(Token.of(20, "READLN"));
        tabelaTokens.add(Token.of(21, "WRITELN"));
        tabelaTokens.add(Token.of(22, "OR"));
        tabelaTokens.add(Token.of(23, "AND"));
        tabelaTokens.add(Token.of(24, "NOT"));
        tabelaTokens.add(Token.of(25, "IDENTIFICADOR"));
        tabelaTokens.add(Token.of(26, "INTEIRO"));
        tabelaTokens.add(Token.of(27, "FOR"));
        tabelaTokens.add(Token.of(28, "TO"));
        tabelaTokens.add(Token.of(29, "CASE"));
        tabelaTokens.add(Token.of(30, "+"));
        tabelaTokens.add(Token.of(31, "-"));
        tabelaTokens.add(Token.of(32, "*"));
        tabelaTokens.add(Token.of(33, "/"));
        tabelaTokens.add(Token.of(34, "["));
        tabelaTokens.add(Token.of(35, "]"));
        tabelaTokens.add(Token.of(36, "("));
        tabelaTokens.add(Token.of(37, ")"));
        tabelaTokens.add(Token.of(38, ":="));
        tabelaTokens.add(Token.of(39, ":"));
        tabelaTokens.add(Token.of(40, "="));
        tabelaTokens.add(Token.of(41, ">"));
        tabelaTokens.add(Token.of(42, ">="));
        tabelaTokens.add(Token.of(43, "<"));
        tabelaTokens.add(Token.of(44, "<="));
        tabelaTokens.add(Token.of(45, "<>"));
        tabelaTokens.add(Token.of(46, ","));
        tabelaTokens.add(Token.of(47, ";"));
        tabelaTokens.add(Token.of(48, "LITERAL"));
        tabelaTokens.add(Token.of(49, "."));
        tabelaTokens.add(Token.of(50, ".."));
        tabelaTokens.add(Token.of(51, "$"));
    }

    public List<Token> getTabelaTokens() {
        return tabelaTokens;
    }
}
