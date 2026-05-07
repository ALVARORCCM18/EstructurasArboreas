package es.ubu.gii.edat.P4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * Implementación optimizada de clases de equivalencia usando árbol con compresión de caminos.
 * 
 * Esta clase implementa una estructura Union-Find con path compression (compresión de caminos).
 * La compresión de caminos optimiza la búsqueda de la raíz haciendo que cada elemento
 * encontrado durante la búsqueda apunte directamente a la raíz, reduciendo la altura del árbol
 * y mejorando el rendimiento en futuras búsquedas.
 * 
 * @param <E> el tipo de elementos en las clases de equivalencia
 * @author <a href="mailto:arc1049@alu.ubu.es">Alvaro Rodriguez Corral</a>
 * @author <a href="mailto:aaa1061@alu.ubu.es">Alejandro Abad Arroyo</a>
 * @version 1.0
 */
public class EquivalenciaArbolComprimido<E> implements ClasesEquivalencia<E> {

	// Mapa que almacena la relación padre-hijo entre elementos con compresión de caminos
	// Clave: elemento, Valor: padre del elemento (null si es raíz)
	private Map<E, E> padres;

	/**
	 * Constructor que inicializa la estructura de clases de equivalencia vacía.
	 */
	public EquivalenciaArbolComprimido() {
		// Inicializa el mapa de padres como un HashMap vacío
		this.padres = new HashMap<>();
	}

	/**
	 * Inserta un elemento en una clase de equivalencia.
	 * 
	 * @param clase el representante de la clase
	 * @param elemento el elemento a insertar
	 */
	@Override
	public void insertar(E clase, E elemento) {
		// Si el elemento es su propia clase (es un representante), lo marca como raíz
		if (clase.equals(elemento)) {
			// El elemento es un representante de clase, apunta a null (es raíz)
			padres.put(elemento, null);
		} else {
			// El elemento apunta a su clase (padre)
			padres.put(elemento, clase);
		}
	}

	/**
	 * Elimina un elemento de la estructura de clases de equivalencia.
	 * 
	 * @param elemento el elemento a eliminar
	 * @return la clase a la que pertenecía el elemento eliminado
	 */
	@Override
	public E eliminar(E elemento) {
		// Obtiene la clase (raíz) a la que pertenecía el elemento
		E raiz = clasePertenencia(elemento);
		// Obtiene el padre directo del elemento a eliminar
		E padre = padres.get(elemento);
		// Variable para almacenar la nueva raíz si el elemento es una raíz
		E nuevaRaiz = null;

		// Itera sobre todos los pares elemento-padre en el mapa
		for (Entry<E, E> parActual : padres.entrySet()) {
			// Si el elemento actual es hijo del elemento a eliminar
			if (elemento.equals(parActual.getValue())) {

				// Si el elemento a eliminar tiene un padre, lo asignamos como nuevo padre
				if (padre != null) {
					parActual.setValue(padre);
				} else {
					// Si el elemento era una raíz (no tiene padre)
					if (nuevaRaiz == null) {
						// El primer hijo se convierte en la nueva raíz
						nuevaRaiz = parActual.getKey();
						parActual.setValue(null);
					} else {
						// Los demás hijos apuntan a la nueva raíz
						parActual.setValue(nuevaRaiz);
					}
				}

			}
		}
		// Elimina el elemento del mapa
		padres.remove(elemento);
		// Retorna la clase a la que pertenecía
		return raiz;
	}

	/**
	 * Obtiene el representante (raíz) de la clase a la que pertenece un elemento.
	 * Implementa path compression para optimizar búsquedas futuras.
	 * 
	 * @param elemento el elemento del cual obtener su clase
	 * @return el representante de la clase, o null si el elemento no existe
	 */
	@Override
	public E clasePertenencia(E elemento) {
		// Si el elemento no está en la estructura, retorna null
		if (!padres.containsKey(elemento)) {
			return null;
		}

		// Inicializa raiz con el elemento actual
		E raiz = elemento;

		// Primera pasada: encuentra la raíz (padre null) siguiendo la cadena de padres
		while (padres.get(raiz) != null) {
			raiz = padres.get(raiz);
		}
		
		// Segunda pasada: compresión de caminos
		// Recorre nuevamente desde el elemento hasta la raíz, actualizando referencias
		while (elemento != null && !elemento.equals(raiz)) {
	        // Guarda el siguiente padre antes de modificar
	        E siguientePadre = padres.get(elemento);
	        // Hace que el elemento apunte directamente a la raíz (compresión)
	        insertar(raiz, elemento); 
	        // Avanza al siguiente elemento en el camino
	        elemento = siguientePadre;
	    }
		
		// Retorna la raíz encontrada
		return raiz;
	}

	/**
	 * Mezcla las clases de equivalencia de dos elementos.
	 * 
	 * @param elemento1 elemento cuya clase será el representante de la clase fusionada
	 * @param elemento2 elemento cuya clase se fusionará con la del primer elemento
	 * @return 1 si la mezcla fue exitosa, 0 si alguno de los elementos no existe
	 */
	@Override
	public int mezclarClases(E elemento1, E elemento2) {
		// Obtiene la raíz (representante) de la clase del primer elemento
		E raizAsignada = clasePertenencia(elemento1);
		// Obtiene la raíz (representante) de la clase del segundo elemento
		E raizModificada = clasePertenencia(elemento2);
		// Si alguno de los elementos no existe, no se puede mezclar
		if (raizAsignada == null || raizModificada == null) {
			return 0;
		}
		// Hace que la segunda raíz apunte a la primera raíz (mezcla las clases)
		insertar(raizAsignada, raizModificada);
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
		// Obtiene la raíz (clase) del elemento de referencia
		E raiz = clasePertenencia(elemento);

		// Itera sobre todos los pares elemento-padre en el mapa
		for (Entry<E, E> parActual : padres.entrySet()) {
			// Si el elemento actual pertenece a la misma clase
			if (clasePertenencia(parActual.getKey()).equals(raiz)) {
				// Lo añade al conjunto resultado
				conjuntoDeElementos.add(parActual.getKey());
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
		// Retorna el tamaño del mapa (número de elementos almacenados)
		return padres.size();
	}

}
