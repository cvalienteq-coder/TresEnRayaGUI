import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia el juego
 * SwingUtilities.invokeLater asegura que la GUI se cree en el hilo correcto de eventos
 */
public class Main {
    public static void main(String[] args) {
        // invokeLater pone la creación de la GUI en la cola de eventos de Swing
        // Esto es necesario para evitar problemas de concurrencia en la interfaz gráfica
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crear y mostrar la ventana principal del juego
                new VentanaJuego().setVisible(true);
            }
        });
    }
}