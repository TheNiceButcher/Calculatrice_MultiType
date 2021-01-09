import java.util.*;
import java.util.function.*;
import java.util.Optional;
/**
Classe representant un type.
**/
public abstract class Type {
	private final String name;
	private final List<Type> sub_types;
	/**
	Construit un type avec un name et une liste de sous-types vide
	@param name nom du type
	**/
	public Type(String name)
	{
		this.name = name;
		this.sub_types = new ArrayList<Type>();
	}
	/**
	Construit un type avec un name et une liste de sous-types contenant les elements
	de sub_types
	@param name nom du type
	@param sub_types les sous types du type
	**/
	public Type(String name, List<Type> sub_types)
	{
		this.name = name;
		this.sub_types = new ArrayList<Type>(sub_types);
	}
	/**
	Renvoie le nom du type courant
	@return Nom du type
	**/
	public final String getName()
	{
		return name;
	}
	/**
	Renvoie les sous-types du type courant
	@return Sous-Types du type
	**/
	public final List<Type> getSubType()
	{
		List<Type> copie = new ArrayList<>(sub_types);
		return copie;
	}
	/**
	Prend une chaine de caractere, et renvoie un Optional contenant une instance
	du type, si elle est convertible dans le type. Sinon on renvoie l'Optional
	vide.
	@param str Chaine de caractere que l'on veut convertir dans le type courant
	@return Optional qui contient l'objet correspondant a la chaine de caractere
	si elle est convertible, Optional vide sinon
	**/
	public abstract Optional<? extends Object> convert(String str);
	/**
	Prend une chaine de caractere et renvoie l'objet correspondant si elle est
	convertible dans le type courant ou null
	**/
	public abstract Object value(String str);
	/**
	Renvoie la chaine de caractere representant l'objet en argument dans la
	calculatrice.

	**/
	public abstract String toStack(Object obj);
}
