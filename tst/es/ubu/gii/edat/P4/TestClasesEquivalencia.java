package es.ubu.gii.edat.P4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.ubu.gii.edat.P4.ClasesEquivalencia;
import es.ubu.gii.edat.P4.EquivalenciaArbolComprimido;

public class TestClasesEquivalencia {

	protected ClasesEquivalencia<Integer> equivalencia;
	protected List<Integer> listaPruebas;

	@BeforeEach
	public void setUp() {
		// Alternativas para probar otras implementaciones:
		// equivalencia = new EquivalenciaTabla<Integer>();
		// equivalencia = new EquivalenciaArbol<Integer>();
		equivalencia = new EquivalenciaArbolComprimido<Integer>();
		listaPruebas = Arrays.asList(2, 3, 4, 5, 8, 12, 9, 11, 25);
	}

	protected void cargarDatosBase() {
		for (int elem : listaPruebas) {
			equivalencia.insertar(primerDivisor(elem), elem);
		}
	}

	protected int primerDivisor(int num) {
		for (int i = 2; i <= num; i++) {
			if (num % i == 0) {
				return i;
			}
		}
		return num;
	}

	/**
	 * Verifica que la carga inicial inserta todos los elementos previstos.
	 *
	 * Se espera que {@code numElementos()} coincida exactamente con el número
	 * de elementos de {@code listaPruebas} tras la inserción.
	 */
	@Test
	public void testInsertar() {
		cargarDatosBase();
		assertEquals(listaPruebas.size(), equivalencia.numElementos());
	}

	/**
	 * Comprueba que cada elemento queda asociado a su clase de pertenencia correcta.
	 *
	 * En este conjunto de datos, la clase esperada es el primer divisor del número.
	 */
	@Test
	public void testClasePertenencia() {
		cargarDatosBase();

		for (int elem : listaPruebas) {
			Integer clase = equivalencia.clasePertenencia(elem);
			assertEquals((Integer) primerDivisor(elem), clase);
		}
	}

	/**
	 * Valida la recuperación de todos los elementos equivalentes a uno dado.
	 *
	 * Se comprueba tanto contenido (que estén los elementos esperados) como
	 * cardinalidad (que no haya elementos de más o de menos).
	 */
	@Test
	public void testElementosMismaClase() {
		cargarDatosBase();

		Set<Integer> eq;

		eq = equivalencia.elementosMismaClase(2);
		assertTrue(eq.containsAll(Arrays.asList(2, 4, 8, 12)));
		assertEquals(4, eq.size());

		eq = equivalencia.elementosMismaClase(3);
		assertTrue(eq.containsAll(Arrays.asList(3, 9)));
		assertEquals(2, eq.size());

		eq = equivalencia.elementosMismaClase(5);
		assertTrue(eq.containsAll(Arrays.asList(5, 25)));
		assertEquals(2, eq.size());

		eq = equivalencia.elementosMismaClase(11);
		assertTrue(eq.containsAll(Arrays.asList(11)));
		assertEquals(1, eq.size());
	}

	/**
	 * Verifica el efecto de mezclar dos clases distintas.
	 *
	 * Tras mezclar las clases de 2 y 5, todos sus elementos deben reportar
	 * la misma clase de pertenencia (en este caso, 2).
	 */
	@Test
	public void testMezclarClases() {
		cargarDatosBase();

		equivalencia.mezclarClases(2, 5);

		assertEquals((Integer) 2, equivalencia.clasePertenencia(2));
		assertEquals((Integer) 2, equivalencia.clasePertenencia(4));
		assertEquals((Integer) 2, equivalencia.clasePertenencia(8));
		assertEquals((Integer) 2, equivalencia.clasePertenencia(12));
		assertEquals((Integer) 2, equivalencia.clasePertenencia(5));
		assertEquals((Integer) 2, equivalencia.clasePertenencia(25));
	}

	/**
	 * Comprueba la coherencia de {@code elementosMismaClase} tras una mezcla.
	 *
	 * Se espera que consultar por cualquier elemento de las clases fusionadas
	 * devuelva el mismo conjunto final de equivalencia.
	 */
	@Test
	public void testMezclaElementosMismaClase() {
		cargarDatosBase();
		equivalencia.mezclarClases(2, 5);

		Set<Integer> eq2 = equivalencia.elementosMismaClase(2);
		assertTrue(eq2.containsAll(Arrays.asList(2, 4, 8, 12, 5, 25)));
		assertEquals(6, eq2.size());

		Set<Integer> eq5 = equivalencia.elementosMismaClase(5);
		assertTrue(eq5.containsAll(Arrays.asList(2, 4, 8, 12, 5, 25)));
		assertEquals(6, eq5.size());
	}
}
