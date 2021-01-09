import java.util.Optional;
/**
Type representant les nombres decimaux
**/
public final class Nombre_Decimal extends Type {
	public Nombre_Decimal()
	{
		super("Nombre_Decimal");
	}
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
			return null;
		}
		return String.valueOf((Integer) obj);
	}
}
