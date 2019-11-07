import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainVCI {
    public static void main(String[] args) {
        HashMap<String, Integer> items = new HashMap<>();

        items.put("/", 60);
        items.put("*", 60);
        items.put("+", 50);
        items.put("-", 50);
        items.put("<", 40);
        items.put(">", 40);
        items.put("==", 40);
        items.put("<=", 40);
        items.put(">=", 40);
        items.put("not", 40);
        items.put("and", 40);
        items.put("or", 40);
        items.put("=", 40);
        items.put(";", 0);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = "";
        List<String> vci = new ArrayList<>();
        Stack<String> op = new Stack<>();

        System.out.println("Escribe un vci: \n");
        try {
            input = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] expr = input.split(" ");
        for (String item:
             expr) {

            boolean integer = false;

            // siguiente condición
            try {
                Integer.parseInt(item);
                integer = true;
            } catch (Exception e) {}


            if (item.equals("(")) {
                op.push("("); // sin preguntar
            } else if (item.equals(")")) {
                // vacia la pila hasta el primer parentesis
                while (!op.peek().equals("(")) {
                    vci.add(op.pop());
                }
                op.pop(); // quitar parentesis que cierra
            } else if (integer || !items.containsKey(item)) {
                // Estos items se agregan directamente a la pila
                vci.add(item);
            } else if (item.equals(";")) {
                // Vaciar pila de operadores
                while (!op.isEmpty()) {
                    vci.add(op.pop());
                }
            } else {
                // Para los operadores

                if (op.isEmpty()) {
                    op.push(item);
                    continue;
                }

                // si es el primero
                try {
                    while (!op.isEmpty() && items.get(item) <= items.get(op.peek())) {
                        vci.add(op.pop());
                    }
                } catch (NullPointerException e) { }
                op.push(item);
            }
        }
        System.out.println("VCI: \n" + vci);
    }
}
