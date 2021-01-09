import java.util.*;
import java.util.function.*;
/**
Classe contenant la fonction main du projet
**/
public class Main {
	public static void creation_Type()
	{

	}
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
			@Override
			public Integer value(String str)
			{
				Optional<Integer> opt_int = convert(str);
				if (!opt_int.isPresent())
				{
					return null;
				}
				return (Integer) opt_int.get();
			}
			public String toStack(Object obj)
			{
				if (!(obj instanceof Integer))
				{
					System.out.println("WTF");
					return null;
				}
				return String.valueOf((Integer) obj);
			}
		};
		Type Booleen = new Type("Booleen"){
			@Override
			public Optional<Boolean> convert(String str)
			{
				if (str.equals("VRAI"))
					return Optional.of(true);
				else if (str.equals("FAUX"))
					return Optional.of(false);
				else
					return Optional.empty();
			}
			@Override
			public Boolean value(String str)
			{
				Optional<Boolean> opt_bool = convert(str);
				if (!opt_bool.isPresent())
				{
					return null;
				}
				return (Boolean) opt_bool.get();
			}
			public String toStack(Object obj)
			{
				if (!(obj instanceof Boolean))
				{
					System.out.println("WTF");
					return null;
				}
				Boolean val = (Boolean) obj;
				return (val?"VRAI":"FAUX");
			}
		};
		Type Ensemble = new Type("Ensemble"){
			@Override
			public Optional<Set> convert(String str)
			{
				if (str.charAt(0) != '{' || str.charAt(str.length()-1) != '}')
				{
					return Optional.empty();
				}
				Set<String> new_set = new HashSet<String>();
				int prev_index = 1;
				for (int i = 1; i < str.length() - 1; i++)
				{
					if (str.charAt(i) == ',')
					{
						new_set.add(str.substring(prev_index,i));
						prev_index = i+1;
					}
				}
				new_set.add(str.substring(prev_index,str.length()-1));
				return Optional.of(new_set);
			}
			@Override
			public Set value(String str)
			{
				Optional<Set> opt_set = convert(str);
				if (!opt_set.isPresent())
				{
					return null;
				}
				return (Set) opt_set.get();
			}
			public String toStack(Object obj)
			{
				if (!(obj instanceof Set))
				{
					System.out.println("WTF");
					return null;
				}
				Set<String> val = (Set) obj;
				String str_result = val.stream().
				reduce("{",(a,elt) -> a + (a.equals("{")?"":",") + elt,(a,b)-> a + b);
				String true_result = str_result.concat("}");
				return true_result;
			}
		};
		Map<Type,List<Operation>> t = new HashMap<>();
		List<Operation> op_nd = Arrays.asList(plus,moins,fois,div);
		List<Operation> op_bool = Arrays.asList(non,ou,et);
		List<Operation> op_ens = Arrays.asList(union,inters,diffe);
		t.put(Nombre_Decimal,op_nd);
		t.put(Booleen,op_bool);
		t.put(Ensemble,op_ens);
	    REPL repl = new REPL(t);
	    repl.boucle();
	    System.out.println("Au revoir et a bientot");
	  }
}
