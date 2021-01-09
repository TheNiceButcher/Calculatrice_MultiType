import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
/**
Classe qui realise la partie REPL (read,eval,print,loop) de la calculatrice

**/
public final class REPL {
  private final List<Operation> operations;
  private final Stack<String> pile;
  private final List<String> historique;
  private final Map<String,String> variables;
  private final Map<Type,List<Operation>> hello;
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
  Execute les commandes hist et pile de la boucle, ajoutees lors de l'extension 3.
  Elle prend en argument la commande a executer et sa longueur et affiche l'information
  souhaitee
  @param cmd Commande a executer
  @param cmd_len Longueur de la commande a exécuter
  **/
 	public void history(String cmd,int cmd_len)
 	{
	  String debut_cmd = cmd.substring(0,5);
	  int pos = Integer.parseInt(cmd.substring(5,cmd_len - 1));
	  //Commande hist
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
	  //Commande pile
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
			history(cmd,cmd_len);
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
	//Regroupe toutes les operations avec le symbole de cmd
	  List<Operation> compatible = operations.stream()
	  .filter(op -> op.getSymbole().equals(cmd)).collect(Collectors.toList());
	  //Si la commande n'est pas une operation, on la met dans la pile si elle est valide
	  if (compatible.isEmpty())
	  {
		  boolean isValide = false;
		  for (Type t : hello.keySet())
		  {
			  Optional operande = t.convert(cmd);
			  if (operande.isPresent())
			  {
				  isValide = true;
				  pile.push(cmd);
				  historique.add(cmd);
				  System.out.println(pile.peek());
				  break;
			  }
		  }
		  if (isValide == false)
		  {
			  System.out.println(cmd + ": Operande de type non supporte");
			  continue;
		  }
	  }
	  /*
	  Sinon on execute l'operation demandee si le nombre d'arguments et leur type
	  sont compatibles
	  */
	  else
	  {
		  String sommet_pile = pile.peek();
		  Type type_sommet_pile = null;
		  for (Type t : hello.keySet())
		  {
			  Optional argn = t.convert(sommet_pile);
			  if (argn.isPresent())
			  {
				  type_sommet_pile = t;
				  break;
			  }
		  }
		  Optional<Operation> opt_op =
		  hello.get(type_sommet_pile).stream().
		  filter(op -> op.getSymbole().equals(cmd)).findAny();
		  if (!opt_op.isPresent())
		  {
			  System.out.println(cmd + " Incompatible avec " + sommet_pile);
			  continue;
		  }
		  Operation op = opt_op.get();
		  if (op.getArite() > pile.size())
		  {
			  System.out.println(op.getSymbole() + " Attend " + op.getArite()+  " arguments " + "Nombre actuel " + pile.size());
			  continue;
		  }
		  for (Type t : hello.keySet())
		  {
			  if (hello.get(t).contains(op))
			  {
				  int nb_arg = op.getArite();
				  Object[] list_arg = new Object[nb_arg];
				  boolean type_error = false;
				  int index_err = -1;
				  for (int i = 0; i < nb_arg; i++)
				  {
					  Object arg = t.value(pile.get(pile.size()-nb_arg + i));
					  if (arg == null)
					  {
						  type_error = true;
						  index_err = pile.size()-nb_arg + i;
						  break;
					  }
					  else
					  {
						  list_arg[i] = arg;
					  }
				  }
				  if (type_error == true)
				  {
					  Type type_error_arg = null;
					  String arg_erreur = pile.get(index_err);
					  for (Type t_e : hello.keySet())
					  {
						  Object arg = t_e.value(pile.get(index_err));
						  if (arg != null)
						  {
							  type_error_arg = t_e;
							  break;
						  }
					  }
					  System.out.println("Erreur typage : type attendu " + t.getName() + " type recu "+ type_error_arg.getName() + "(" +
					  pile.get(index_err) + ")");
					  break;
				  }
				  for(int i = 0; i < nb_arg; i++)
				  {
					  pile.pop();
				  }
				  Object result = op.appliquer(list_arg);
				  String str_result = t.toStack(result);
				  pile.push(str_result);
				  historique.add(str_result);
				  System.out.println(pile.peek());

			  }
		  }
	  }
	  continue;
  }
  scan.close();
	}
}
