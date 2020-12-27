import java.util.*;
import java.util.function.*;
public class Main {
  public static class REPL {
    private List<Operation> operations;
    private Stack<String> pile;
    private Map<Operation,BiFunction<Integer,Integer,Integer>> interpretations;
    public <T> REPL(List<Operation> op,List<BiFunction<Integer,Integer,Integer>> applications)
    {
      if (op.size() != applications.size())
      {
        System.out.println("Ouille");
      }
      this.operations = new ArrayList<Operation>(op);
      this.pile = new Stack<String>();
      this.interpretations = new HashMap<Operation,BiFunction<Integer,Integer,Integer>>();
      for(int i = 0; i < operations.size(); i++)
      {
        this.interpretations.put(operations.get(i),applications.get(i));
      }
    }
    //Boucle principal du programme
    public void boucle()
    {
      Scanner scan = new Scanner(System.in);
      while (true)
      {
        String cmd = scan.next();
        //On quitte la boucle dès que exit est tape par l'utilisateur
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
              Integer arg1 = Integer.parseInt(pile.get(pile.size()-2));
              Integer arg2 = Integer.parseInt(pile.get(pile.size()-1));
              pile.pop();
              pile.pop();
              Integer result = interpretations.get(op).apply(arg1,arg2);
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
    BiFunction<Integer,Integer,Integer> aplus = (a,b) -> a + b;
    BiFunction<Integer,Integer,Integer> amoins = (a,b) -> a - b;
    BiFunction<Integer,Integer,Integer> afois = (a,b) -> a * b;
    BiFunction<Integer,Integer,Integer> adiv = (a,b) -> a / b;
    REPL repl = new REPL(List.of(plus,moins,fois,div),
    List.of(aplus,amoins,afois,adiv));
    repl.boucle();
    System.out.println("Au revoir et a bientot");
  }
}
