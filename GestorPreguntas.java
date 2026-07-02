import java.util.ArrayList;

/**
 * Clase que gestiona todas las preguntas del juego
 * Usa ArrayList (similar a vector en C++) para almacenar preguntas
 */
public class GestorPreguntas {
    // ArrayList es un contenedor dinámico que puede crecer
    private ArrayList<Pregunta> preguntasJugador1;
    private ArrayList<Pregunta> preguntasJugador2;
    private int rondaActual;
    
    /**
     * Constructor - inicializa todas las preguntas
     */
    public GestorPreguntas() {
        preguntasJugador1 = new ArrayList<Pregunta>();
        preguntasJugador2 = new ArrayList<Pregunta>();
        rondaActual = 0;
        
        // ===== PREGUNTAS PARA JUGADOR 1 (X) =====
        
        // Ronda 1
        preguntasJugador1.add(new Pregunta(
            "¿Quién es el padre de la Programación Orientada a Objetos?",
            "Alan Turing",
            "Alan Kay",
            "Bill Gates",
            2  // Respuesta correcta: opción 2 (Alan Kay)
        ));
        
        // Ronda 2
        preguntasJugador1.add(new Pregunta(
            "¿Cuándo comenzó la Segunda Guerra Mundial?",
            "1914",
            "1939",
            "1945",
            2  // Respuesta correcta: opción 2 (1939)
        ));
        
        // Ronda 3
        preguntasJugador1.add(new Pregunta(
            "¿Cuál es la raíz cuadrada de 81?",
            "9",
            "8",
            "7",
            1  // Respuesta correcta: opción 1 (9)
        ));
        
        // Ronda 4 en adelante (preguntas adicionales para partidas largas)
        preguntasJugador1.add(new Pregunta(
            "¿Qué significa POO?",
            "Programación Orientada a Objetos",
            "Proceso Operativo Optimizado",
            "Programación con Ordenadores",
            1
        ));
        
        preguntasJugador1.add(new Pregunta(
            "¿En qué año se creó Java?",
            "1991",
            "1995",
            "1998",
            2
        ));
        
        // ===== PREGUNTAS PARA JUGADOR 2 (O) =====
        
        // Ronda 1
        preguntasJugador2.add(new Pregunta(
            "¿Quién es conocido como el 'Brujo de los Andes'?",
            "Miguel Grau",
            "Andrés Avelino Cáceres",
            "Francisco Bolognesi",
            2
        ));
        
        // Ronda 2
        preguntasJugador2.add(new Pregunta(
            "¿Cuál es la fórmula para hallar el área de un cuadrado?",
            "Base x Altura / 2",
            "Lado x Lado",
            "Pi x Radio^2",
            2
        ));
        
        // Ronda 3
        preguntasJugador2.add(new Pregunta(
            "Acertijo: ¿Qué tiene teclas pero no puede abrir ninguna puerta?",
            "Un piano",
            "Un candado roto",
            "Un mapa",
            1
        ));
        
        // Ronda 4 en adelante
        preguntasJugador2.add(new Pregunta(
            "¿Qué es un algoritmo?",
            "Un tipo de música",
            "Una secuencia de pasos para resolver un problema",
            "Un lenguaje de programación",
            2
        ));
        
        preguntasJugador2.add(new Pregunta(
            "¿Cuál es el símbolo del Jugador 2?",
            "X",
            "O",
            "Ninguno",
            2
        ));
    }
    
    /**
     * Obtiene la pregunta actual para un jugador según la ronda
     * @param jugador 1 para Jugador1, 2 para Jugador2
     * @param ronda Número de ronda (1, 2, 3...)
     * @return La pregunta correspondiente
     */
    public Pregunta getPregunta(int jugador, int ronda) {
        int indice = ronda - 1;  // Convertir ronda a índice (ronda 1 = índice 0)
        
        if (jugador == 1) {
            // Si nos quedamos sin preguntas, usar la última (efecto circular)
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
    
    /**
     * Avanza a la siguiente ronda
     */
    public void siguienteRonda() {
        rondaActual++;
    }
    
    /**
     * Reinicia las rondas (para nueva partida)
     */
    public void reiniciarRondas() {
        rondaActual = 0;
    }
    
    public int getRondaActual() { return rondaActual + 1; }
}

