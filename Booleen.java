import java.util.Optional;
/**
Type representant les booleens dans la calculatrice
**/
public final class Booleen extends Type {
	public Booleen()
	{
		super("Booleen");
	}
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
			return null;
		}
		Boolean val = (Boolean) obj;
		return (val?"VRAI":"FAUX");
	}
}
