import java.util.Optional;
import java.util.*;
/**
Type representant les ensembles
**/
public final class Ensemble extends Type {
	public Ensemble()
	{
		super("Ensemble");
	}
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
	@Override
	@SuppressWarnings("unchecked")
	public String toStack(Object obj)
	{
		if (!(obj instanceof Set))
		{
			return null;
		}
		Set<String> val = (Set) obj;
		String str_result = val.stream().
		reduce("{",(a,elt) -> a + (a.equals("{")?"":",") + elt,(a,b)-> a + b);
		String true_result = str_result.concat("}");
		return true_result;
	}
}
