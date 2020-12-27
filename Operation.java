/**
Une operation est constitue d'une arité et d'une symbole
**/
public class Operation {
  private final int arite;
  private final String symbole;
  public Operation(int arite, String symbole)
  {
    this.arite = arite;
    this.symbole = symbole;
  }
  public int getArite()
  {
    return this.arite;
  }
  public String getSymbole()
  {
    return this.symbole;
  }
}
