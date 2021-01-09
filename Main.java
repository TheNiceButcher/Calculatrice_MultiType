import java.util.*;
import java.util.function.*;
/**
Classe contenant la fonction main du projet
**/
public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		System.out.println("Bienvenue dans cette calculatrice");
	    BiFunction<Integer,Integer,Integer> aplus = (a,b) -> a + b;
	    BiFunction<Integer,Integer,Integer> amoins = (a,b) -> a - b;
	    BiFunction<Integer,Integer,Integer> afois = (a,b) -> a * b;
	    BiFunction<Integer,Integer,Integer> adiv = (a,b) -> a / b;
		Function<Boolean,Boolean> neg = (a) -> (!a);
		BiFunction<Boolean,Boolean,Boolean> or = (a,b) -> (a || b);
		BiFunction<Boolean,Boolean,Boolean> and = (a,b) -> (a && b);
		BiFunction<Set,Set,Set> un = (a,b) -> {
			Set result = new HashSet(a);
			result.addAll(b);
			return result;
		};
		BiFunction<Set,Set,Set> inter = (a,b) -> {
			Set result = new HashSet();
			for (Object o : a)
			{
				if (b.contains(o))
				{
					result.add(o);
				}
			}
			return result;
		};
		BiFunction<Set,Set,Set> diff = (a,b) -> {
			Set result = new HashSet(a);
			result.removeAll(b);
			return result;
		};
		Operation<Integer> plus = new Operation.DeuxArgument<Integer>("+",aplus);
		Operation<Integer> moins = new Operation.DeuxArgument<Integer>("-",amoins);
	    Operation<Integer> fois = new Operation.DeuxArgument<Integer>("*",afois);
	    Operation<Integer> div = new Operation.DeuxArgument<Integer>("/",adiv);
		Operation<Boolean> non = new Operation.UnArgument<Boolean>("NON",neg);
		Operation<Boolean> ou = new Operation.DeuxArgument<Boolean>("OU",or);
		Operation<Boolean> et = new Operation.DeuxArgument<Boolean>("ET",and);
		Operation<Set> union = new Operation.DeuxArgument<Set>("UNION",un);
		Operation<Set> inters = new Operation.DeuxArgument<Set>("INTER",inter);
		Operation<Set> diffe = new Operation.DeuxArgument<Set>("DIFF",diff);
		Type nombre_Decimal = new Nombre_Decimal();
		Type booleen = new Booleen();
		Type ensemble = new Ensemble();
		Map<Type,List<Operation>> t = new HashMap<>();
		List<Operation> op_nd = Arrays.asList(plus,moins,fois,div);
		List<Operation> op_bool = Arrays.asList(non,ou,et);
		List<Operation> op_ens = Arrays.asList(union,inters,diffe);
		t.put(nombre_Decimal,op_nd);
		t.put(booleen,op_bool);
		t.put(ensemble,op_ens);
	    REPL repl = new REPL(t);
	    repl.boucle();
	    System.out.println("Au revoir et a bientot");
	  }
}
