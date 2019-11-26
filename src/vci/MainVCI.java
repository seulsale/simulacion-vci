package vci;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import sun.applet.Main;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainVCI {

   // private static void handleRequest(HttpExchange exchange) throws IOException {
   //     String response = "Hola";
   //     exchange.sendResponseHeaders(200, response.getBytes().length);
   //     OutputStream os = exchange.getResponseBody();
   //     os.write(response.getBytes());
   //     os.close();
   // }

    public static void main(String[] args) throws IOException {
        // HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        // HttpContext context = server.createContext("/");
        // context.setHandler(MainVCI::handleRequest);
        // server.start();

        List<ItemVCI> vci = new ArrayList<>();
        List<ItemVCI> ejecucion = new ArrayList<>();
        Stack<ItemVCI> op = new Stack<>();
        Stack<Integer> dir = new Stack<>();
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

                    System.out.println(vci);

                    break;
                }


                ItemVCI vciElement = new ItemVCI(token, string, token.getPriority());


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
                    if (vciElement.getString().equals("if")) {
                        est.add(vciElement);
                    } else if (vciElement.getString().equals("then")) {
                        op.clear();
                        dir.push(vci.size());
                        vci.add(null);
                        vci.add(vciElement);
                    } else if (vciElement.getString().equals("else")) {
                        est.push(vciElement);
                        int position = dir.pop();
                        vci.set(position, new ItemVCI(null, String.valueOf(vci.size() + 2), -1));
                        dir.push(vci.size());
                        vci.add(null); // token falso
                        vci.add(vciElement);
                    } else if (vciElement.getString().equals("end")) {
                        ItemVCI poppedElement = est.pop();

                        int position = dir.pop();
                        vci.set(position, new ItemVCI(null, String.valueOf(vci.size()), null));
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
