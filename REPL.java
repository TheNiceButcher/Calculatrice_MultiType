import java.util.*;
import java.util.function.*;
/**
Classe qui realise la partie REPL (read,eval,print,loop) de la calculatrice
**/
public final class REPL {
  private List<Operation> operations;
  private Stack<String> pile;
  private List<String> historique;
  private Map<String,String> variables;
  private Map<Operation,BiFunction<Integer,Integer,Integer>> interpretations;
  /**
  Creation d'une instance de REPL
  **/
  public REPL(List<Operation> op,List<BiFunction<Integer,Integer,Integer>> applications)
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
    this.historique = new ArrayList<String>();
    this.variables = new HashMap<String,String>();
  }
  /**
  Effectue la boucle principale du programme, à savoir :
  -récuperer la commande,
  -évaluer cette commande
  -l'afficher
  -recommencer.
  On arrête cette boucle dès que l'utilisateur tape la commande "exit"
  **/
  public void boucle()
  {
    Scanner scan = new Scanner(System.in);
    while (true)
    {
      System.out.print(">");
      String cmd = scan.next();
      //On quitte la boucle dès que exit est tape par l'utilisateur
      if (cmd.equals("exit"))
      {
        break;
      }
      int cmd_len = cmd.length();
      //Commande hist ou pile
      if (cmd_len > 6)
      {
        String debut_cmd = cmd.substring(0,5);
        if (debut_cmd.equals("hist(") || debut_cmd.equals("pile("))
        {
          int pos = Integer.parseInt(cmd.substring(5,cmd_len - 1));
          if (debut_cmd.equals("hist("))
          {
            if (pos < 0)
            {
              pos = historique.size() + pos;
            }
            if (pos >= historique.size())
            {
              System.out.println("historique invalide");
            }
            else
            {
              pile.push(historique.get(pos));
            }
          }
          else
          {
            if (pos < 0)
            {
              pos = pile.size() + pos;
            }
            if (pos >= pile.size())
            {
              System.out.println("pile invalide");
            }
            else
            {
              pile.push(pile.get(pos));
            }
          }
          System.out.println(pile.peek());
          continue;
        }
      }
      //REalisation de la commande !x qui depile et met la valeur dans x
      if (cmd_len >= 2 && cmd.charAt(0) == '!')
      {
        String var = cmd.substring(1,cmd_len);
        variables.put(var,pile.pop());
        continue;
      }
      //REalisation de la commande ?x qui lit la valeur de x l'empile
      if (cmd_len >= 2 && cmd.charAt(0) == '?')
      {
        String var = cmd.substring(1,cmd_len);
        if (!variables.containsKey(var))
        {
          System.out.println("Variable "+var+ " inconnue");
        }
        else
        {
          pile.push(variables.get(var));
          System.out.println(pile.peek());
        }
        continue;
      }
      boolean isOperation = false;
      for (Operation op : operations)
      {
        if (op.getSymbole().equals(cmd))
        {
          isOperation = true;
          if (pile.size() < op.getArite())
          {
            System.out.println("Pas assez d'argument");
          }
          else
          {
            Integer arg1 = Integer.parseInt(pile.get(pile.size()-2));
            Integer arg2 = Integer.parseInt(pile.get(pile.size()-1));
            pile.pop();
            pile.pop();
            Integer result = interpretations.get(op).apply(arg1,arg2);
            pile.push(String.valueOf(result));
            historique.add(String.valueOf(result));
            System.out.println(pile.peek());
          }
          break;
        }
      }
      if (isOperation == false)
      {
        pile.push(cmd);
        historique.add(cmd);
        System.out.println(pile.peek());
      }
    }
    scan.close();
  }
}
