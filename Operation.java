/**
<p>Classe representant une operation dans la calculatrice</p>
Une instance de cette classe represente une operation dans la calculatrice.
Elle est caractérisé par son nombre d'arguments et son symbole dans la calculatrice
**/
public final class Operation {
  private final int arite;
  private final String symbole;
  public Operation(int arite, String symbole)
  {
    this.arite = arite;
    this.symbole = symbole;
  }
  /**
  Renvoie le nombre d'arguments attendu par l'operation
  @return Nombre d'argument de l'operation
  **/
  public int getArite()
  {
    return this.arite;
  }
  /**
  Renvoie le symbole de l'operation
  @return Symbole de l'operation dans la calculatrice
  **/
  public String getSymbole()
  {
    return this.symbole;
  }
}
