# EstructurasArboreas

Repositorio con implementaciones de estructuras de equivalencia (Union-Find) para
la asignatura Estructuras de Datos (Universidad de Burgos).

Contenido principal
- `src/` : código fuente Java (implementaciones y la interfaz `ClasesEquivalencia`).
- `tst/` : pruebas unitarias con JUnit 5 (`TestClasesEquivalencia.java`).
- `lib/` : dependencias usadas para ejecutar las pruebas (JUnit y utilidades).
- `bin/` : clases compiladas (no es recomendable incluirlas en el repo a futuro).

Implementaciones incluidas
- `EquivalenciaTabla` : implementación simple basada en una tabla (mapa elemento -> clase).
- `EquivalenciaArbol` : implementación en árbol (padre por elemento, sin compresión).
- `EquivalenciaArbolComprimido` : implementación en árbol con compresión de caminos (path compression).

Cómo compilar y ejecutar las pruebas (Windows / PowerShell)

1. Compilar fuentes y tests (asegúrate de tener `lib/` con las dependencias JUnit):

```powershell
javac -cp "lib/*" -d bin src/es/ubu/gii/edat/P4/*.java tst/es/ubu/gii/edat/P4/*.java
```

2. Ejecutar las pruebas con el lanzador de consola de JUnit:

```powershell
java -cp "bin;lib/junit-platform-console-standalone-1.9.2.jar" org.junit.platform.console.ConsoleLauncher --scan-classpath bin --details summary
```

Notas y buenas prácticas
- Añade un `.gitignore` para excluir `bin/` y `lib/` si prefieres no versionar binarios/dependencias.
- Para un proyecto a largo plazo, se recomienda usar Maven o Gradle para gestionar dependencias y tareas de build/ejecución.

Contacto
- Autor: ALVAR

Licencia
- Revisa la licencia del repositorio remoto o añade una si quieres compartirlo públicamente.
