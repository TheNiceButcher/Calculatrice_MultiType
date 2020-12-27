import java.util.*;
import java.util.function.*;
public class Main {
  public static class REPL {
    private List<Operation> operations;
    private Stack<String> pile;
    private Map<Operation,BiFunction<Double,Double,Double>> interpretations;
    public <T> REPL(List<Operation> op,List<BiFunction<Double,Double,Double>> applications)
    {
      if (op.size() != applications.size())
      {
        System.out.println("Ouille");
      }
      this.operations = new ArrayList<Operation>(op);
      this.pile = new Stack<String>();
      this.interpretations = new HashMap<Operation,BiFunction<Double,Double,Double>>();
      for(int i = 0; i < operations.size(); i++)
      {
        this.interpretations.put(operations.get(i),applications.get(i));
      }
    }
    public void boucle()
    {
      Scanner scan = new Scanner(System.in);
      while (true)
      {
        String cmd = scan.next();
        if (cmd.equals("exit"))
        {
          break;
        }
        boolean isOperation = false;
        for (Operation op : operations)
        {
          if (op.getSymbole().equals(cmd))
          {
            isOperation = true;
            if (pile.size() < op.getArite())
            {
              System.out.println("TROP PEU D'ARGUMENT");
            }
            else
            {
              Double arg1 = Double.parseDouble(pile.get(pile.size()-2));
              Double arg2 = Double.parseDouble(pile.get(pile.size()-1));
              pile.pop();
              pile.pop();
              Double result = interpretations.get(op).apply(arg1,arg2);
              System.out.println(result);
              pile.push(String.valueOf(result));
              //System.out.println(pile.pop()+op.getSymbole()+pile.pop());
            }
            break;
          }
        }
        if (isOperation == false)
        {
          pile.push(cmd);
        }
      }
      scan.close();
    }
  }
  public static void main(String[] args) {
    System.out.println("Bienvenue dans cette calculatrice");
    Operation plus = new Operation(2,"+");
    Operation moins = new Operation(2,"-");
    Operation fois = new Operation(2,"*");
    Operation div = new Operation(2,"/");
    BiFunction<Double,Double,Double> aplus = (a,b) -> a + b;
    BiFunction<Double,Double,Double> amoins = (a,b) -> a - b;
    BiFunction<Double,Double,Double> afois = (a,b) -> a * b;
    BiFunction<Double,Double,Double> adiv = (a,b) -> a / b;
    REPL repl = new REPL(List.of(plus,moins,fois,div),
    List.of(aplus,amoins,afois,adiv));
    repl.boucle();
    System.out.println("Au revoir et a bientot");
  }
}
