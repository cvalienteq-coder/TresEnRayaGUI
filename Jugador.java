import java.awt.Color;

/**
 * Clase que representa a un jugador del juego
 * Almacena nombre, símbolo, puntaje y color asociado
 */
public class Jugador {
    // Atributos privados (encapsulamiento)
    private String nombre;      // Nombre del jugador
    private char simbolo;       // 'X' o 'O'
    private int puntos;         // Puntuación acumulada
    private Color color;        // Color para la interfaz (azul para X, rojo para O)
    
    /**
     * Constructor - se llama al crear un nuevo jugador
     * @param nombre Nombre del jugador
     * @param simbolo Carácter que usará en el tablero ('X' o 'O')
     */
    public Jugador(String nombre, char simbolo) {
        this.nombre = nombre;     // this.nombre se refiere al atributo, nombre es el parámetro
        this.simbolo = simbolo;
        this.puntos = 0;          // Inicializa puntos en 0
        
        // Asigna color según el símbolo (para diferenciar visualmente)
        if (simbolo == 'X') {
            this.color = Color.GREEN;
        } else {
            this.color = Color.YELLOW;
        }
    }
    
    // Métodos getter (para acceder a atributos privados)
    public String getNombre() { return nombre; }
    public char getSimbolo() { return simbolo; }
    public int getPuntos() { return puntos; }
    public Color getColor() { return color; }
    
    /**
     * Añade puntos al jugador
     * @param puntos Cantidad de puntos a sumar
     */
    public void sumarPuntos(int puntos) {
        this.puntos += puntos;    // += es equivalente a: this.puntos = this.puntos + puntos
    }
    
    /**
     * Reinicia los puntos (para una nueva partida)
     */
    public void reiniciarPuntos() {
        this.puntos = 0;
    }
}