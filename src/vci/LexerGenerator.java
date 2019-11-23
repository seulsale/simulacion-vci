package vci;

import java.io.File;

public class LexerGenerator {

  public static void main(String[] args) {
    String ruta = "src/vci/Lexer.flex";
    generarLexer(ruta);
  }

  public static void generarLexer(String ruta) {
    File archivo = new File(ruta);
    JFlex.Main.generate(archivo);
  }
}
