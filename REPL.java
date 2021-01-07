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
  private Map<Type,List<Operation>> hello;
  //private Map<Operation,BiFunction<Integer,Integer,Integer>> interpretations;
  /**
  Creation d'une instance de REPL
  **/
  public REPL(Map<Type,List<Operation>> dico)
  {
    this.operations = new ArrayList<Operation>();
	for (Type t : dico.keySet())
	{
		operations.addAll(dico.get(t));
	}
	this.hello = new HashMap<>(dico);
    this.pile = new Stack<String>();
    //this.interpretations = new HashMap<Operation,BiFunction<Integer,Integer,Integer>>();
    this.historique = new ArrayList<String>();
    this.variables = new HashMap<String,String>();
  }
  /**
  Effectue la boucle principale du programme, a savoir :
  		<p>-recuperer la commande,</p>
  		<p>-evaluer cette commande </p>
  		<p>-l'afficher </p>
  		<p>-recommencer. </p>
  	On arrete cette boucle des que l'utilisateur tape la commande "exit"
  **/
  @SuppressWarnings("unchecked")
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
      //Realisation de la commande !x qui depile et met la valeur dans x
      if (cmd_len >= 2 && cmd.charAt(0) == '!')
      {
        String var = cmd.substring(1,cmd_len);
        variables.put(var,pile.pop());
        continue;
      }
      //Realisation de la commande ?x qui lit la valeur de x l'empile
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
	  for (Type t : hello.keySet())
	  {
	      for (Operation op : hello.get(t))
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
				if (t.getName().equals("Nombre_Decimal"))
				{
					//
					int nb_arg = op.getArite();
					Integer[] list_arg = new Integer[nb_arg];
					boolean type_error = false;
					for(int i = 0; i < nb_arg; i++)
					{
						Optional arg = t.convert(pile.get(pile.size()-nb_arg + i));
						if (arg.isPresent())
						{
							list_arg[i] = (Integer) arg.get();
						}
						else
						{
							type_error = true;
						}
					}
					if (type_error)
					{
						System.out.println("Erreur typage");
						break;
					}
					for(int i = 0; i < nb_arg; i++)
					{
		            	pile.pop();
		            }
					Integer result = (Integer) ((Operation<Integer>)op).appliquer(list_arg);
		            pile.push(String.valueOf(result));
		            historique.add(String.valueOf(result));
		            System.out.println(pile.peek());
				}
				else if (t.getName().equals("Booleen"))
				{
					int nb_arg = op.getArite();
					Boolean[] list_arg = new Boolean[nb_arg];
					boolean type_error = false;
					for(int i = 0; i < nb_arg; i++)
					{
						Optional arg = t.convert(pile.get(pile.size()-nb_arg + i));
						if (arg.isPresent())
						{
							list_arg[i] = (Boolean) arg.get();
						}
						else
						{
							type_error = true;
						}
					}
					if (type_error)
					{
						System.out.println("Erreur typage");
						break;
					}
					for(int i = 0; i < nb_arg; i++)
					{
		            	pile.pop();
		            }
					Boolean result = (Boolean) ((Operation<Boolean>)op).appliquer(list_arg);
					String value;
					if (result == true)
					{
						value = "VRAI";
					}
					else
					{
		            	value = "FAUX";
					}
					pile.push(String.valueOf(value));
		            historique.add(String.valueOf(value));
		            System.out.println(pile.peek());
				}
				else if (t.getName().equals("Ensemble"))
				{
					int nb_arg = op.getArite();
					Set[] list_arg = new Set[nb_arg];
					boolean type_error = false;
					for(int i = 0; i < nb_arg; i++)
					{
						Optional arg = t.convert(pile.get(pile.size()-nb_arg + i));
						if (arg.isPresent())
						{
							list_arg[i] = (Set) arg.get();
						}
						else
						{
							type_error = true;
						}
					}
					if (type_error)
					{
						System.out.println("Erreur typage");
						break;
					}
					for(int i = 0; i < nb_arg; i++)
					{
		            	pile.pop();
		            }
					Set<String> result = (Set) ((Operation<Set>)op).appliquer(list_arg);
					String str_result = result.stream().
					reduce("{",(a,elt) -> a + (a.equals("{")?"":",") + elt,(a,b)-> a + b);
 					String true_result = str_result.concat("}");
					pile.push(true_result);
		            historique.add(true_result);
		            System.out.println(pile.peek());
				}
				else
				{
					System.out.println("Type inconnu");
				}
	          }
	          break;
	        }
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
