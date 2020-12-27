import java.util.*;
import java.util.function.*;
public class Main {
  public static void main(String[] args) {
    System.out.println("Bienvenue dans cette calculatrice");
    Operation plus = new Operation(2,"+");
    Operation moins = new Operation(2,"-");
    Operation fois = new Operation(2,"*");
    Operation div = new Operation(2,"/");
    BiFunction<Integer,Integer,Integer> aplus = (a,b) -> a + b;
    BiFunction<Integer,Integer,Integer> amoins = (a,b) -> a - b;
    BiFunction<Integer,Integer,Integer> afois = (a,b) -> a * b;
    BiFunction<Integer,Integer,Integer> adiv = (a,b) -> a / b;
    REPL repl = new REPL(List.of(plus,moins,fois,div),
    List.of(aplus,amoins,afois,adiv));
    repl.boucle();
    System.out.println("Au revoir et a bientot");
  }
}
