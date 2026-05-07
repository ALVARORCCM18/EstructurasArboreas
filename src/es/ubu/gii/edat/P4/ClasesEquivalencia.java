package es.ubu.gii.edat.P4;

import java.util.Set;

/**
 * Define las operaciones básicas para gestionar una partición de elementos
 * en clases de equivalencia.
 *
 * Una clase de equivalencia puede identificarse por uno de sus elementos
 * (representante). Dos elementos son equivalentes si pertenecen a la misma
 * clase.
 * 
 * https://es.wikipedia.org/wiki/Relaci%C3%B3n_de_equivalencia
 * 
 * @author bbaruque
 *
 * @param <E> tipo de los elementos gestionados
 */

public interface ClasesEquivalencia <E>{

	/**
	 * Inserta un elemento en la clase indicada.
	 *
	 * @param clase representante (o identificador) de la clase destino
	 * @param elemento elemento que se quiere insertar en esa clase
	 */
	public void insertar (E clase, E elemento);

	/**
	 * Elimina un elemento de su clase de equivalencia.
	 *
	 * @param elemento elemento a eliminar
	 * @return representante de la clase a la que pertenecía antes de eliminarlo,
	 *         o {@code null} si el elemento no estaba almacenado
	 */
	public E eliminar (E elemento);
	
	/**
	 * Obtiene el representante de la clase a la que pertenece un elemento.
	 *
	 * @param elemento elemento del que se consulta su clase
	 * @return representante de su clase, o {@code null} si el elemento no existe
	 */
	public E clasePertenencia (E elemento);

	/**
	 * Fusiona las clases de equivalencia de dos elementos.
	 * Tras la operación, ambos elementos deben pertenecer a la misma clase.
	 *
	 * @param elemento1 primer elemento
	 * @param elemento2 segundo elemento
	 * @return valor numérico de control de la fusión (según implementación)
	 */
	public int mezclarClases (E elemento1, E elemento2);
//	public List<E> elementosClase (E clase);
	
	/**
	 * Devuelve los elementos que pertenecen a la misma clase que el elemento dado.
	 *
	 * @param elemento elemento de referencia
	 * @return conjunto de elementos de su misma clase; vacío o {@code null}
	 *         según la implementación si el elemento no existe
	 */
	public Set<E> elementosMismaClase (E elemento);
	
	/**
	 * Devuelve el número total de elementos almacenados en la estructura.
	 *
	 * @return número de elementos almacenados
	 */
	public int numElementos();
	
}
