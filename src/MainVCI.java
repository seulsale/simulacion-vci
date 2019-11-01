import java.util.HashMap;

public class MainVCI {
    public static void main(String[] args) {
        HashMap<String, int> tokens = new HashMap<String, int>();
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


    }
}
