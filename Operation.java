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
  /**
  Cree une instance d'Operation, avec le nombre d'argument en argument et represente
  par la chaine de caractere en argument dans la calculatrice
  @param arite Nombre d'arguments de l'Operation
  @param symbole Chaine de caractere representant l'Operation dans la calculatrice
  **/
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
  /**
  Realise l'operation sur les arguments
  @param args Tableau des arguments de l'operation
  @return Resultat de l'operation sur la liste des arguments
  **/
  @SuppressWarnings("unchecked")
  public abstract T appliquer(T... args);
  /**
  Classe qui represente une operation unaire sur un element de T
  **/
  public final static class UnArgument<T> extends Operation<T> {
	  private Function<T,T> f;
	  /**
	  Cree un operation unaire de T vers T, avec la lambda expression en argument
	  @param symbole Symbole de l'operation dans la calculatrice
	  @param f Lambda-expression correspondant a l'action de l'operation
	  **/
	  public UnArgument(String symbole,Function<T,T> f)
	  {
		  super(1,symbole);
		  this.f = f;
	  }
	  /**
	  Execute la fonction correspondant a l'operation courante et renvoie le resultat,
	  si il y a un seul argument. Sinon, il renvoie null
	  **/
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
  /**
  Classe qui represente une operation binaire sur deux elements de T
  **/
  public final static class DeuxArgument<T> extends Operation<T> {
	  private BiFunction<T,T,T> f;
	  /**
	  Cree un operation binaire de T vers T, avec la lambda expression en argument
	  @param symbole Symbole de l'operation dans la calculatrice
	  @param f Lambda-expression correspondant a l'action de l'operation
	  **/
	  public DeuxArgument(String symbole,BiFunction<T,T,T> f)
	  {
		  super(2,symbole);
		  this.f = f;
	  }
	  /**
	 Execute la fonction correspondant a l'operation courante et renvoie le resultat,
	 si il y a un seul argument. Sinon, il renvoie null
	 **/
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
