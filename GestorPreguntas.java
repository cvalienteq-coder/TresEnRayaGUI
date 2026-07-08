import java.util.ArrayList;

/**
 * Clase que gestiona el banco de preguntas del juego.
 * Las preguntas están enfocadas en:
 *   - Programación Orientada a Objetos (POO) con Java
 *   - Estructuras de datos en Java (ArrayList, HashMap, etc.)
 *   - Java Swing (interfaces gráficas)
 *
 * Usa ArrayList (contenedor dinámico equivalente a vector en C++)
 * para almacenar las preguntas de cada jugador.
 *
 * @author Carmen Valiente Quiroz
 * @version 2.0
 */
public class GestorPreguntas {

    /** Banco de preguntas para Jugador 1 (símbolo X) — OOP con Java */
    private ArrayList<Pregunta> preguntasJugador1;

    /** Banco de preguntas para Jugador 2 (símbolo O) — Swing y Estructuras */
    private ArrayList<Pregunta> preguntasJugador2;

    /** Índice interno de ronda actual (base 0) */
    private int rondaActual;

    /**
     * Constructor — inicializa y carga todas las preguntas del banco.
     */
    public GestorPreguntas() {
        preguntasJugador1 = new ArrayList<Pregunta>();
        preguntasJugador2 = new ArrayList<Pregunta>();
        rondaActual = 0;
        cargarPreguntasJugador1();
        cargarPreguntasJugador2();
    }

    // ==========================================================
    //  PREGUNTAS JUGADOR 1 — POO con Java
    // ==========================================================
    private void cargarPreguntasJugador1() {

        // Pregunta 1 — Herencia
        preguntasJugador1.add(new Pregunta(
            "¿Qué permite la HERENCIA en la Programación Orientada a Objetos?",
            "Ocultar los datos internos de una clase",
            "Que una clase hija reutilice atributos y métodos de una clase padre",
            "Ejecutar varios métodos con el mismo nombre",
            2,
            "La herencia (extends en Java) permite que una clase hija herede " +
            "atributos y métodos de su clase padre, promoviendo la reutilización " +
            "de código. Ejemplo: class Perro extends Animal { ... }"
        ));

        // Pregunta 2 — Polimorfismo
        preguntasJugador1.add(new Pregunta(
            "¿Qué es el POLIMORFISMO en Java?",
            "La capacidad de un objeto de tomar múltiples formas",
            "Usar muchos atributos en una sola clase",
            "Compilar el código de varias formas distintas",
            1,
            "El polimorfismo permite que un mismo método se comporte de manera " +
            "diferente según el objeto que lo ejecute. En Java se logra con " +
            "sobreescritura (@Override) y con referencias de tipo padre que " +
            "apuntan a objetos hijos."
        ));

        // Pregunta 3 — Encapsulación
        preguntasJugador1.add(new Pregunta(
            "¿Cuál es el propósito principal de la ENCAPSULACIÓN?",
            "Heredar métodos de otra clase",
            "Crear múltiples objetos de una clase",
            "Proteger los datos internos usando modificadores private y getters/setters",
            3,
            "La encapsulación protege el estado interno de un objeto declarando " +
            "sus atributos como 'private' y exponiendo solo lo necesario mediante " +
            "métodos públicos (getters y setters). Esto evita accesos indebidos."
        ));

        // Pregunta 4 — Interfaz vs Clase Abstracta
        preguntasJugador1.add(new Pregunta(
            "¿Qué palabra clave se usa en Java para IMPLEMENTAR una interfaz?",
            "extends",
            "implements",
            "inherits",
            2,
            "En Java, 'implements' se usa para que una clase concrete los métodos " +
            "definidos en una interfaz. Ejemplo: class Pato implements Volador { ... } " +
            "Una clase puede implementar múltiples interfaces a la vez."
        ));

        // Pregunta 5 — super()
        preguntasJugador1.add(new Pregunta(
            "¿Qué hace la instrucción super() en Java?",
            "Crea un nuevo objeto de la clase actual",
            "Llama al constructor de la clase padre",
            "Elimina la herencia de la clase",
            2,
            "super() llama al constructor de la clase padre (superclase). " +
            "Debe ser la primera instrucción dentro del constructor hijo. " +
            "También se usa como super.metodo() para invocar métodos heredados " +
            "que han sido sobreescritos."
        ));

        // Pregunta 6 — Clase abstracta
        preguntasJugador1.add(new Pregunta(
            "¿Cuál es una característica exclusiva de las CLASES ABSTRACTAS " +
            "(no de las interfaces)?",
            "Pueden tener métodos con implementación (código)",
            "Se usan con la palabra clave 'implements'",
            "No pueden tener atributos",
            1,
            "A diferencia de las interfaces (antes de Java 8), las clases " +
            "abstractas pueden tener métodos implementados y métodos abstractos " +
            "(sin cuerpo). No pueden instanciarse directamente. " +
            "Se declaran con: abstract class NombreClase { ... }"
        ));

        // Pregunta extra — Sobrecarga
        preguntasJugador1.add(new Pregunta(
            "¿Qué es la SOBRECARGA de métodos (method overloading) en Java?",
            "Reemplazar un método heredado con una nueva implementación",
            "Definir varios métodos con el mismo nombre pero distintos parámetros",
            "Llamar al método de la clase padre con super",
            2,
            "La sobrecarga permite tener múltiples métodos con el mismo nombre " +
            "pero diferente firma (número o tipo de parámetros). Se resuelve en " +
            "tiempo de compilación. Ejemplo: calcular(int a) y calcular(double a)."
        ));
    }

    // ==========================================================
    //  PREGUNTAS JUGADOR 2 — Java Swing y Estructuras de Datos
    // ==========================================================
    private void cargarPreguntasJugador2() {

        // Pregunta 1 — JFrame
        preguntasJugador2.add(new Pregunta(
            "¿Qué es un JFrame en Java Swing?",
            "Un tipo de base de datos",
            "La ventana principal de una aplicación gráfica de escritorio",
            "Un método para dibujar figuras",
            2,
            "JFrame es la ventana contenedora principal en Java Swing. " +
            "Representa la ventana con barra de título, bordes y botones " +
            "de minimizar/maximizar/cerrar. Todo componente visual se agrega " +
            "sobre el JFrame o sus paneles internos."
        ));

        // Pregunta 2 — BorderLayout
        preguntasJugador2.add(new Pregunta(
            "¿Qué LayoutManager organiza componentes en 5 zonas " +
            "(Norte, Sur, Este, Oeste, Centro)?",
            "GridLayout",
            "FlowLayout",
            "BorderLayout",
            3,
            "BorderLayout divide el contenedor en 5 regiones: " +
            "NORTH, SOUTH, EAST, WEST y CENTER. " +
            "Es el layout predeterminado del JFrame. " +
            "Se usa con: panel.add(componente, BorderLayout.NORTH);"
        ));

        // Pregunta 3 — ArrayList vs LinkedList
        preguntasJugador2.add(new Pregunta(
            "¿Cuál es la principal ventaja de ArrayList sobre LinkedList?",
            "Inserción al inicio más rápida",
            "Acceso por índice más rápido (O(1))",
            "Usa menos memoria en todos los casos",
            2,
            "ArrayList almacena elementos en un arreglo interno, permitiendo " +
            "acceso directo por índice en O(1). LinkedList usa nodos enlazados, " +
            "lo que hace que el acceso por índice sea O(n). Sin embargo, " +
            "LinkedList es más rápido para inserciones al inicio o en el medio."
        ));

        // Pregunta 4 — ActionListener
        preguntasJugador2.add(new Pregunta(
            "¿Qué interfaz debes implementar para manejar clics en botones " +
            "de Java Swing?",
            "MouseListener",
            "KeyListener",
            "ActionListener",
            3,
            "ActionListener es la interfaz de eventos para componentes " +
            "como JButton, JMenuItem, JTextField. " +
            "Debes implementar el método: " +
            "public void actionPerformed(ActionEvent e) { ... } " +
            "y registrar el listener con: boton.addActionListener(this);"
        ));

        // Pregunta 5 — HashMap
        preguntasJugador2.add(new Pregunta(
            "¿Qué estructura de datos representa un HashMap en Java?",
            "Una lista ordenada de elementos secuenciales",
            "Una pila que funciona con LIFO (último en entrar, primero en salir)",
            "Una colección de pares clave-valor con búsqueda en O(1)",
            3,
            "HashMap almacena pares (clave → valor) usando una función hash. " +
            "Permite búsqueda, inserción y eliminación en tiempo O(1) promedio. " +
            "No garantiza orden. Ejemplo: " +
            "HashMap<String, Integer> mapa = new HashMap<>(); " +
            "mapa.put(\"Ana\", 90);"
        ));

        // Pregunta 6 — JOptionPane
        preguntasJugador2.add(new Pregunta(
            "¿Qué hace JOptionPane.showMessageDialog() en Java Swing?",
            "Crea una nueva ventana principal de la aplicación",
            "Muestra un cuadro de diálogo emergente con un mensaje al usuario",
            "Abre un archivo del sistema operativo",
            2,
            "JOptionPane.showMessageDialog() muestra un diálogo modal " +
            "(bloquea la interacción hasta cerrarlo) con un mensaje e ícono. " +
            "Parámetros: componente padre, mensaje, título y tipo de ícono " +
            "(INFORMATION_MESSAGE, WARNING_MESSAGE, ERROR_MESSAGE)."
        ));

        // Pregunta extra — GridLayout
        preguntasJugador2.add(new Pregunta(
            "¿Qué LayoutManager organiza componentes en filas y columnas " +
            "de igual tamaño?",
            "GridLayout",
            "CardLayout",
            "BoxLayout",
            1,
            "GridLayout organiza los componentes en una cuadrícula de filas y " +
            "columnas donde todos los celdas tienen el mismo tamaño. " +
            "Se construye con: new GridLayout(filas, columnas, hGap, vGap). " +
            "Es ideal para tableros como el 3x3 de este juego."
        ));
    }

    // ==========================================================
    //  MÉTODOS DE ACCESO
    // ==========================================================

    /**
     * Obtiene la pregunta para el jugador y ronda indicados.
     * Si se agotan las preguntas, reutiliza la última del banco.
     *
     * @param jugador 1 para Jugador1 (X), 2 para Jugador2 (O)
     * @param ronda   Número de ronda actual (base 1)
     * @return La pregunta correspondiente
     */
    public Pregunta getPregunta(int jugador, int ronda) {
        int indice = ronda - 1; // Convertir ronda 1-based a índice 0-based

        if (jugador == 1) {
            // Si se agotaron las preguntas, reutilizar la última
            if (indice >= preguntasJugador1.size()) {
                indice = preguntasJugador1.size() - 1;
            }
            return preguntasJugador1.get(indice);
        } else {
            if (indice >= preguntasJugador2.size()) {
                indice = preguntasJugador2.size() - 1;
            }
            return preguntasJugador2.get(indice);
        }
    }

    /** Avanza al contador interno de ronda */
    public void siguienteRonda() {
        rondaActual++;
    }

    /** Reinicia el contador de rondas para una nueva partida */
    public void reiniciarRondas() {
        rondaActual = 0;
    }

    /** @return Número de ronda actual (base 1) */
    public int getRondaActual() {
        return rondaActual + 1;
    }
}
