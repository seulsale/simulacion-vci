package vci;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainVCI {

    public static void main(String[] args) {

        List<ItemVCI> vci = new ArrayList<>();
        List<ItemVCI> ejecucion = new ArrayList<>();
        Stack<ItemVCI> op = new Stack<>();
        Stack<ItemVCI> dir = new Stack<>();
        Stack<ItemVCI> est = new Stack<>();

        try {
            Reader lector = new BufferedReader(new FileReader("/home/sergio/School/LyA/simulacion-vci/src/vci/archivo.txt"));
            Lexer lexer = new Lexer(lector);
            StringBuilder output = new StringBuilder();
            while (true) {
                Token token = lexer.yylex();
                String string = lexer.lexeme;


                if (token == null) {

                    vci.forEach(vciElement -> {
                        output.append(vciElement.getString());
                    });

                    output.append("Completado");

                    System.out.println(output.toString());

                    break;
                }


                ItemVCI vciElement = new ItemVCI(token, string, token.getPriority());
                System.out.println(vciElement);


                // Logica del VCI
                if (token.equals(Token.AbreParentesis)) {
                    op.push(vciElement); // sin preguntar
                } else if (token.equals(Token.CierraParentesis)) {
                    // vacia la pila hasta el primer parentesis
                    while (!op.peek().getToken().equals(Token.AbreParentesis)) {
                        ItemVCI operador = op.pop();
                        vci.add(operador);
                    }
                    op.pop(); // quitar parentesis que cierra
                } else if (vciElement.getToken().equals(Token.Identificador) || vciElement.getToken().equals(Token.Enteros)) {
                    // Estos tokens se agregan directamente a la pila
                    if (vciElement.getString().equals("write")) {
                        // implementar write
                    } else {
                        vci.add(vciElement);
                    }
                } else if (vciElement.getToken().equals(Token.PuntoYComa)) {
                    // Vaciar pila de operadores
                    while (!op.isEmpty()) {
                        ItemVCI operador = op.pop();
                        vci.add(operador);
                    }
                } else {
                    // Para los operadores

                    if (op.isEmpty()) {
                        op.push(vciElement);
                        continue;
                    }

                    // si es el primero
                    while (!op.isEmpty() && vciElement.getPriority() <= op.peek().getPriority()) {
                        ItemVCI operador = op.pop();
                        System.out.println("Popped: " + operador);
                        vci.add(operador);
                    }
                    op.push(vciElement);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
