import java.util.function.*;
/**
<p>Classe representant une operation dans la calculatrice</p>
<p> Une instance de cette classe represente une operation dans la calculatrice.
Elle est caracterise par son nombre d'arguments et son symbole dans la calculatrice
</p>
**/
public abstract class Operation<T> {
  private final int arite;
  private final String symbole;
  private Operation(int arite, String symbole)
  {
    this.arite = arite;
    this.symbole = symbole;
  }
  /**
  Renvoie le nombre d'arguments attendu par l'operation
  @return Nombre d'argument de l'operation
  **/
  public final int getArite()
  {
    return this.arite;
  }
  /**
  Renvoie le symbole de l'operation
  @return Symbole de l'operation dans la calculatrice
  **/
  public final String getSymbole()
  {
    return this.symbole;
  }
  @SuppressWarnings("unchecked")
  public abstract T appliquer(T... args);
  public final static class UnArgument<T> extends Operation<T> {
	  private Function<T,T> f;
	  public UnArgument(String symbole,Function<T,T> f)
	  {
		  super(1,symbole);
		  this.f = f;
	  }
	  @Override
	  @SuppressWarnings("unchecked")
	  public T appliquer(T... args)
	  {
		  if (args.length != 1)
		  {
			  return null;
		  }
		  return f.apply(args[0]);
	  }

  }
  public final static class DeuxArgument<T> extends Operation<T> {
	  private BiFunction<T,T,T> f;
	  public DeuxArgument(String symbole,BiFunction<T,T,T> f)
	  {
		  super(2,symbole);
		  this.f = f;
	  }
	  @Override
	  @SuppressWarnings("unchecked")
	  public T appliquer(T... args)
	  {
		  if (args.length != 2)
		  {
			  return null;
		  }
		  return f.apply(args[0],args[1]);
	  }

  }
}
