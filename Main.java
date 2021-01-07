import java.util.*;
import java.util.function.*;
/**
Classe contenant la fonction main du projet
**/
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
	Type Nombre_Decimal = new Type("Nombre_Decimal"){
		@Override
		public Optional<Integer> convert(String str)
		{
			Integer nb = 0;
			try
			{
				nb = Integer.parseInt(str);
			}
			catch(NumberFormatException e)
			{
				return Optional.empty();
			}
			return Optional.of(nb);
		}
	};
	Integer nb = (Integer) Nombre_Decimal.convert("92").get();
	System.out.println(nb);
    REPL repl = new REPL(Arrays.asList(plus,moins,fois,div),
    Arrays.asList(aplus,amoins,afois,adiv));
    repl.boucle();
    System.out.println("Au revoir et a bientot");
  }
}
