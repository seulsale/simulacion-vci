import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.io.BufferedReader;

public class MainVCI {
    public static void main(String[] args) {
        HashMap<String, Integer> tokens = new HashMap<>();
        tokens.put("/", 60);
        tokens.put("*", 60);
        tokens.put("+", 50);
        tokens.put("-", 50);
        tokens.put("<", 40);
        tokens.put(">", 40);
        tokens.put("==", 40);
        tokens.put("<=", 40);
        tokens.put(">=", 40);
        tokens.put("not", 40);
        tokens.put("and", 40);
        tokens.put("or", 40);
        tokens.put("=", 40);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = "";

        System.out.println("Escribe un vci: \n");
        try {
            input = br.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }

        String[] expr = input.split(" ");
        for (String item:
             expr) {
            System.out.println(item);
        }

    }
}
