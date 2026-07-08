import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * Clase que representa a un jugador del juego Tres en Raya Quiz.
 * Almacena nombre, símbolo (X u O), puntaje acumulado,
 * tiempo total de respuesta y el avatar seleccionado.
 *
 * @author Carmen Valiente Quiroz
 * @version 2.0
 */
public class Jugador {

    private String    nombre;       // Nombre del jugador (incluye emoji si aplica)
    private char      simbolo;      // 'X' o 'O'
    private int       puntos;       // Puntos acumulados en la partida
    private Color     color;        // Color asociado al símbolo (azul=X, rojo=O)
    private long      tiempoTotal;  // Tiempo total de respuesta en milisegundos
    private ImageIcon avatarIcono;  // Imagen del avatar (64×64 px)

    /**
     * Constructor del jugador con avatar.
     *
     * @param nombre      Nombre del jugador
     * @param simbolo     Símbolo asignado ('X' o 'O')
     * @param avatarIcono ImageIcon con el avatar del jugador (puede ser null)
     */
    public Jugador(String nombre, char simbolo, ImageIcon avatarIcono) {
        this.nombre       = nombre;
        this.simbolo      = simbolo;
        this.puntos       = 0;
        this.tiempoTotal  = 0;
        this.avatarIcono  = avatarIcono;

        // Colores vibrantes y diferenciados para X y O
        if (simbolo == 'X') {
            this.color = new Color(30, 130, 255);   // Azul eléctrico para X
        } else {
            this.color = new Color(230, 60, 60);    // Rojo intenso para O
        }
    }

    // ===== GETTERS =====

    /** @return Nombre completo del jugador */
    public String    getNombre()      { return nombre; }

    /** @return Símbolo del jugador ('X' u 'O') */
    public char      getSimbolo()     { return simbolo; }

    /** @return Puntos acumulados en la partida actual */
    public int       getPuntos()      { return puntos; }

    /** @return Color asociado al símbolo del jugador */
    public Color     getColor()       { return color; }

    /** @return Tiempo total de respuestas en milisegundos */
    public long      getTiempoTotal() { return tiempoTotal; }

    /** @return ImageIcon del avatar (puede ser null) */
    public ImageIcon getAvatarIcono() { return avatarIcono; }

    // ===== MÉTODOS DE ESTADO =====

    /**
     * Suma puntos al jugador.
     * @param puntos Cantidad de puntos a añadir
     */
    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
    }

    /** Reinicia los puntos a cero (nueva partida) */
    public void reiniciarPuntos() {
        this.puntos = 0;
    }

    /**
     * Acumula el tiempo de respuesta de un turno.
     * @param tiempo Tiempo en milisegundos del turno
     */
    public void sumarTiempo(long tiempo) {
        this.tiempoTotal += tiempo;
    }

    /** Reinicia el tiempo acumulado (nueva partida) */
    public void reiniciarTiempo() {
        this.tiempoTotal = 0;
    }
}