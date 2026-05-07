package es.ubu.gii.edat.P4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Implementación de una estructura de clases de equivalencia basada en tabla directa.
 * 
 * Esta clase mantiene una tabla simple (HashMap) donde cada elemento apunta directamente
 * a su representante de clase. Es la implementación más directa, sin estructura de árbol,
 * lo que la hace simple pero menos eficiente en operaciones de mezcla de clases.
 * 
 * @param <E> el tipo de elementos en las clases de equivalencia
 * @author EDAT
 * @version 1.0
 */
public class EquivalenciaTabla<E> implements ClasesEquivalencia<E> {

	// Tabla que almacena la relación elemento -> clase (representante)
	// Clave: elemento, Valor: representante de la clase a la que pertenece
	private Map<E, E> tabla;

	/**
	 * Constructor que inicializa la estructura de clases de equivalencia vacía.
	 */
	public EquivalenciaTabla() {
		// Inicializa la tabla como un HashMap vacío
		this.tabla = new HashMap<E, E>();
	}

	/**
	 * Inserta un elemento en una clase de equivalencia.
	 * 
	 * @param clase el representante de la clase
	 * @param elemento el elemento a insertar
	 */
	@Override
	public void insertar(E clase, E elemento) {
		// Asigna directamente el elemento a su clase (representante)
		tabla.put(elemento, clase);
	}

	/**
	 * Elimina un elemento de la estructura de clases de equivalencia.
	 * 
	 * @param elemento el elemento a eliminar
	 * @return la clase a la que pertenecía el elemento eliminado
	 */
	@Override
	public E eliminar(E elemento) {
		// Elimina el elemento de la tabla y retorna su clase
		return tabla.remove(elemento);
	}

	/**
	 * Obtiene el representante (clase) de un elemento.
	 * 
	 * En esta implementación, retorna directamente la clase almacenada en la tabla
	 * sin necesidad de búsqueda jerárquica.
	 * 
	 * @param elemento el elemento del cual obtener su clase
	 * @return el representante de la clase, o null si el elemento no existe
	 */
	@Override
	public E clasePertenencia(E elemento) {
		// Retorna directamente la clase almacenada en la tabla para el elemento
		return tabla.get(elemento);
	}

	/**
	 * Mezcla las clases de equivalencia de dos elementos.
	 * 
	 * Hace que todos los elementos de la clase del segundo elemento apunten ahora
	 * al representante de la clase del primer elemento.
	 * 
	 * @param elemento1 elemento cuya clase será el representante de la clase fusionada
	 * @param elemento2 elemento cuya clase se fusionará con la del primer elemento
	 * @return 1 si la mezcla fue exitosa, 0 si alguno de los elementos no existe
	 */
	@Override
	public int mezclarClases(E elemento1, E elemento2) {
		// Obtiene la clase a la que pertenece el segundo elemento (que se va a modificar)
		E claseQueSeModifica = clasePertenencia(elemento2);
		// Obtiene la clase a la que pertenece el primer elemento (que será el nuevo representante)
		E claseQueSeAsigna = clasePertenencia(elemento1);
		// Si alguno de los elementos no existe, no se puede mezclar
		if (claseQueSeModifica == null || claseQueSeAsigna == null) {
			return 0;
		}
		// Itera sobre todos los elementos en la tabla
		for (Entry<E, E> parValorActual : tabla.entrySet()) {
			// Si el elemento pertenece a la clase que se va a modificar
			if (parValorActual.getValue().equals(claseQueSeModifica)) {
				// Lo asigna a la nueva clase (fusión de clases)
				parValorActual.setValue(claseQueSeAsigna);
			}
		}
		// Retorna 1 indicando que la mezcla fue exitosa
		return 1;
	}

	/**
	 * Obtiene todos los elementos que pertenecen a la misma clase de equivalencia.
	 * 
	 * @param elemento el elemento de referencia
	 * @return un conjunto con todos los elementos de la misma clase
	 */
	@Override
	public Set<E> elementosMismaClase(E elemento) {
		// Crea un conjunto para almacenar los elementos de la misma clase
		Set<E> conjuntoDeElementos = new HashSet<>();
		// Obtiene la clase del elemento de referencia
		E claseBuscada = clasePertenencia(elemento);

		// Itera sobre todos los pares elemento-clase en la tabla
		for (Entry<E, E> parValorActual : tabla.entrySet()) {
			// Si el elemento actual pertenece a la misma clase buscada
			if (parValorActual.getValue().equals(claseBuscada)) {
				// Lo añade al conjunto resultado
				conjuntoDeElementos.add(parValorActual.getKey());
			}
		}
		// Retorna el conjunto con todos los elementos de la misma clase
		return conjuntoDeElementos;
	}

	/**
	 * Obtiene el número total de elementos en la estructura.
	 * 
	 * @return el número de elementos almacenados
	 */
	@Override
	public int numElementos() {
		// Retorna el tamaño de la tabla (número de elementos almacenados)
		return tabla.size();
	}

}
