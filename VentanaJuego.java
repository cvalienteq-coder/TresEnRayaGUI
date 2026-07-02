import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase principal de la interfaz gráfica
 * Extiende JFrame para crear una ventana
 * Implementa ActionListener para manejar eventos (clics de botones)
 */
public class VentanaJuego extends JFrame implements ActionListener {
    
    // Componentes de la interfaz
    private JButton[][] botonesTablero;     // Matriz 3x3 de botones
    private JLabel[] labelsPuntajes;        // Labels para mostrar puntajes
    private JLabel labelTurno;              // Label que muestra de quién es el turno
    private JPanel panelTablero;            // Panel que contiene los botones
    
    // Datos del juego
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;          // Referencia al jugador que tiene el turno
    private GestorPreguntas gestorPreguntas;
    private int rondaActual;
    private int marcasEnTablero;             // Contador de casillas ocupadas (0-9)
    private boolean juegoTerminado;
    
    /**
     * Constructor - Configura toda la ventana y el juego
     */
    public VentanaJuego() {
        // Configuración básica de la ventana
        setTitle("Tres en Raya - Edición Quiz");  // Título de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cerrar programa al cerrar ventana
        setSize(650,750);  // Ancho x Alto en píxeles
        setLocationRelativeTo(null);  // Centrar la ventana en la pantalla
        setResizable(false);  // No permitir redimensionar
        
        // Inicializar el gestor de preguntas
        gestorPreguntas = new GestorPreguntas();
        
        // Pedir nombres de los jugadores usando JOptionPane (ventana de diálogo)
        String nombre1 = JOptionPane.showInputDialog(this, "Ingresa el nombre del Jugador 1 (X):");
        if (nombre1 == null || nombre1.trim().isEmpty()) nombre1 = "Jugador 1";
        
        String nombre2 = JOptionPane.showInputDialog(this, "Ingresa el nombre del Jugador 2 (O):");
        if (nombre2 == null || nombre2.trim().isEmpty()) nombre2 = "Jugador 2";
        
        // Crear los objetos Jugador
        jugador1 = new Jugador(nombre1, 'X');
        jugador2 = new Jugador(nombre2, 'O');
        jugadorActual = jugador1;  // Empieza el jugador 1
        rondaActual = 1;
        marcasEnTablero = 0;
        juegoTerminado = false;
        
        // Configurar el layout (distribución) de la ventana
        setLayout(new BorderLayout(10, 10));  // BorderLayout con espacios de 10px
        
        // Crear los componentes de la interfaz
        crearPanelSuperior();    // Panel con nombres y puntajes
        crearPanelTablero();     // Panel con los 9 botones del tablero
        crearPanelInferior();    // Panel con información del turno
        
        // Mostrar mensaje de bienvenida
        JOptionPane.showMessageDialog(this, 
            "¡Perfecto! " + jugador1.getNombre() + " VS " + jugador2.getNombre() + "\n¡Que empiece el juego!",
            "Bienvenidos",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Crea el panel superior con nombres y puntajes
     */
    private void crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 20, 10));  // 1 fila, 2 columnas
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));  // Márgenes
        
        // Panel para Jugador 1
        JPanel panelP1 = new JPanel(new GridLayout(2, 1));
        panelP1.setBackground(new Color(200, 220, 255));  // Azul claro
        panelP1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        
        JLabel labelNombre1 = new JLabel(jugador1.getNombre() + " (X)", SwingConstants.CENTER);
        labelNombre1.setFont(new Font("comic", Font.BOLD, 18));
        labelNombre1.setForeground(Color.BLACK);
        
        labelsPuntajes = new JLabel[2];
        labelsPuntajes[0] = new JLabel("Puntos: 0", SwingConstants.CENTER);
        labelsPuntajes[0].setFont(new Font("Arial", Font.PLAIN, 14));
        
        panelP1.add(labelNombre1);
        panelP1.add(labelsPuntajes[0]);
        
        // Panel para Jugador 2
        JPanel panelP2 = new JPanel(new GridLayout(2, 1));
        panelP2.setBackground(new Color(255, 200, 200));  // Rojo claro
        panelP2.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        
        JLabel labelNombre2 = new JLabel(jugador2.getNombre() + " (O)", SwingConstants.CENTER);
        labelNombre2.setFont(new Font("Arial", Font.BOLD, 18));
        labelNombre2.setForeground(Color.RED);
        
        labelsPuntajes[1] = new JLabel("Puntos: 0", SwingConstants.CENTER);
        labelsPuntajes[1].setFont(new Font("Arial", Font.PLAIN, 14));
        
        panelP2.add(labelNombre2);
        panelP2.add(labelsPuntajes[1]);
        
        panelSuperior.add(panelP1);
        panelSuperior.add(panelP2);
        
        add(panelSuperior, BorderLayout.NORTH);  // Agregar al norte de la ventana
    }
    
    /**
     * Crea el panel del tablero con 9 botones (3x3)
     */
    private void crearPanelTablero() {
        panelTablero = new JPanel(new GridLayout(3, 3, 5, 5));  // 3x3 con espacios de 5px
        panelTablero.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelTablero.setBackground(Color.BLACK);
        
        botonesTablero = new JButton[3][3];
        
        // Crear los 9 botones
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int numero = i * 3 + j + 1;  // 1,2,3 en primera fila; 4,5,6 en segunda; etc.
                botonesTablero[i][j] = new JButton(String.valueOf(numero));
                botonesTablero[i][j].setFont(new Font("Comic", Font.BOLD, 40));
                botonesTablero[i][j].setBackground(Color.WHITE);
                botonesTablero[i][j].setFocusPainted(false);  // Quitar borde de foco
                botonesTablero[i][j].addActionListener(this);  // This = VentanaJuego (implementa ActionListener)
                panelTablero.add(botonesTablero[i][j]);
            }
        }
        
        add(panelTablero, BorderLayout.CENTER);  // Agregar al centro de la ventana
    }
    
    /**
     * Crea el panel inferior con información del turno
     */
    private void crearPanelInferior() {
        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        labelTurno = new JLabel();
        labelTurno.setFont(new Font("Arial", Font.BOLD, 16));
        actualizarLabelTurno();
        
        panelInferior.add(labelTurno);
        add(panelInferior, BorderLayout.SOUTH);  // Agregar al sur de la ventana
    }
    
    /**
     * Actualiza el texto del label que muestra el turno actual
     */
    private void actualizarLabelTurno() {
        labelTurno.setText("Turno de: " + jugadorActual.getNombre() + " (" + jugadorActual.getSimbolo() + ")");
        labelTurno.setForeground(jugadorActual.getColor());
    }
    
    /**
     * Actualiza los labels de puntajes en la interfaz
     */
    private void actualizarPuntajes() {
        labelsPuntajes[0].setText("Puntos: " + jugador1.getPuntos());
        labelsPuntajes[1].setText("Puntos: " + jugador2.getPuntos());
    }
    
    /**
     * Muestra una pregunta al jugador actual usando JOptionPane
     * @return true si respondió correctamente, false si no
     */
    private boolean mostrarPregunta() {
        // Obtener la pregunta según el jugador y la ronda actual
        int numeroJugador = (jugadorActual == jugador1) ? 1 : 2;
        Pregunta pregunta = gestorPreguntas.getPregunta(numeroJugador, rondaActual);
        
        // Crear un panel personalizado para la pregunta
        JPanel panelPregunta = new JPanel();
        panelPregunta.setLayout(new BoxLayout(panelPregunta, BoxLayout.Y_AXIS));
        
        JLabel labelPregunta = new JLabel(pregunta.getTexto());
        labelPregunta.setFont(new Font("Arial", Font.BOLD, 14));
        labelPregunta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPregunta.add(labelPregunta);
        panelPregunta.add(Box.createRigidArea(new Dimension(0, 10)));  // Espacio vertical
        
        // Crear botones para las opciones
        String[] opciones = pregunta.getOpciones();
        JButton[] botonesOpciones = new JButton[3];
        ButtonGroup grupo = new ButtonGroup();  // Grupo para que solo se pueda seleccionar uno
        JRadioButton[] radioButtons = new JRadioButton[3];
        
        for (int i = 0; i < 3; i++) {
            radioButtons[i] = new JRadioButton(opciones[i]);
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 12));
            grupo.add(radioButtons[i]);
            panelPregunta.add(radioButtons[i]);
        }
        
        // Mostrar diálogo con opción de confirmación
        int resultado = JOptionPane.showConfirmDialog(
            this,
            panelPregunta,
            "Pregunta - Ronda " + rondaActual,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        // Si el usuario canceló
        if (resultado != JOptionPane.OK_OPTION) {
            return false;
        }
        
        // Verificar qué opción seleccionó
        int respuestaSeleccionada = -1;
        for (int i = 0; i < 3; i++) {
            if (radioButtons[i].isSelected()) {
                respuestaSeleccionada = i;
                break;
            }
        }
        
        if (respuestaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "No seleccionaste ninguna respuesta. Pierdes el turno.");
            return false;
        }
        
        // Verificar si es correcta
        if (pregunta.esCorrecta(respuestaSeleccionada)) {
            jugadorActual.sumarPuntos(30);
            actualizarPuntajes();
            JOptionPane.showMessageDialog(this, 
                "¡Respuesta correcta! Ganaste 30 puntos.\nTotal: " + jugadorActual.getPuntos(),
                "¡Correcto!",
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(this,
                "¡Respuesta incorrecta! La respuesta correcta era: " + opciones[pregunta.getRespuestaCorrecta()] + "\nPierdes tu turno.",
                "Incorrecto",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Marca una casilla en el tablero
     * @param fila Fila de la casilla (0-2)
     * @param columna Columna de la casilla (0-2)
     */
    private void marcarCasilla(int fila, int columna) {
        // Cambiar el texto del botón al símbolo del jugador
        botonesTablero[fila][columna].setText(String.valueOf(jugadorActual.getSimbolo()));
        botonesTablero[fila][columna].setForeground(jugadorActual.getColor());
        botonesTablero[fila][columna].setEnabled(false);  // Deshabilitar el botón (no se puede volver a clickear)
        marcasEnTablero++;
        
        // Actualizar la matriz interna (opcional, podemos usar los botones directamente)
        // Pero para verificar ganador, verificamos los botones
        
        // Verificar si el jugador actual ganó
        if (verificarGanador()) {
            juegoTerminado = true;
            JOptionPane.showMessageDialog(this,
                "¡FELICITACIONES " + jugadorActual.getNombre() + "!\nGanaste al conseguir tres en línea.",
                "¡GANADOR!",
                JOptionPane.INFORMATION_MESSAGE);
            mostrarResultadoFinal();
            return;
        }
        
        // Verificar empate
        if (marcasEnTablero >= 9) {
            juegoTerminado = true;
            mostrarResultadoFinal();
            return;
        }
        
        // Cambiar de jugador
        cambiarTurno();
        
        // Avanzar de ronda después de cada 2 turnos (uno por jugador)
        if (jugadorActual == jugador1) {
            rondaActual++;
            gestorPreguntas.siguienteRonda();
        }
    }
    
    /**
     * Verifica si el jugador actual tiene tres en línea
     */
    private boolean verificarGanador() {
        char simbolo = jugadorActual.getSimbolo();
        
        // Extraer valores actuales de los botones
        String[][] valores = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                valores[i][j] = botonesTablero[i][j].getText();
            }
        }
        
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (valores[i][0].equals(String.valueOf(simbolo)) &&
                valores[i][1].equals(String.valueOf(simbolo)) &&
                valores[i][2].equals(String.valueOf(simbolo))) {
                return true;
            }
        }
        
        // Verificar columnas
        for (int j = 0; j < 3; j++) {
            if (valores[0][j].equals(String.valueOf(simbolo)) &&
                valores[1][j].equals(String.valueOf(simbolo)) &&
                valores[2][j].equals(String.valueOf(simbolo))) {
                return true;
            }
        }
        
        // Verificar diagonales
        if (valores[0][0].equals(String.valueOf(simbolo)) &&
            valores[1][1].equals(String.valueOf(simbolo)) &&
            valores[2][2].equals(String.valueOf(simbolo))) {
            return true;
        }
        
        if (valores[0][2].equals(String.valueOf(simbolo)) &&
            valores[1][1].equals(String.valueOf(simbolo)) &&
            valores[2][0].equals(String.valueOf(simbolo))) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Cambia el turno al otro jugador
     */
    private void cambiarTurno() {
        if (jugadorActual == jugador1) {
            jugadorActual = jugador2;
        } else {
            jugadorActual = jugador1;
        }
        actualizarLabelTurno();
    }
    
    /**
     * Muestra el resultado final del juego (ganador por puntos o empate)
     */
    private void mostrarResultadoFinal() {
        String mensaje;
        
        if (juegoTerminado && verificarGanador()) {
            // Ya mostramos el mensaje de ganador por 3 en línea
            mensaje = "\n--- PUNTUACIÓN FINAL ---\n" +
                      jugador1.getNombre() + ": " + jugador1.getPuntos() + " puntos.\n" +
                      jugador2.getNombre() + ": " + jugador2.getPuntos() + " puntos.";
        } else {
            // Empate por tablero lleno
            mensaje = "¡El tablero está lleno sin ningún 3 en línea!\nEl ganador se determina por puntos:\n\n" +
                      jugador1.getNombre() + ": " + jugador1.getPuntos() + " puntos.\n" +
                      jugador2.getNombre() + ": " + jugador2.getPuntos() + " puntos.\n\n";
            
            if (jugador1.getPuntos() > jugador2.getPuntos()) {
                mensaje += "¡" + jugador1.getNombre() + " ganó por tener más respuestas correctas!";
            } else if (jugador2.getPuntos() > jugador1.getPuntos()) {
                mensaje += "¡" + jugador2.getNombre() + " ganó por tener más respuestas correctas!";
            } else {
                mensaje += "¡Es un empate absoluto tanto en conocimiento como en estrategia!";
            }
        }
        
        JOptionPane.showMessageDialog(this, mensaje, "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
        
        // Preguntar si quieren jugar de nuevo
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Quieres jugar otra partida?",
            "Revancha",
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            reiniciarJuego();
        } else {
            System.exit(0);
        }
    }
    
    /**
     * Reinicia el juego para una nueva partida
     */
    private void reiniciarJuego() {
        // Reiniciar variables
        rondaActual = 1;
        marcasEnTablero = 0;
        juegoTerminado = false;
        jugadorActual = jugador1;
        gestorPreguntas.reiniciarRondas();
        
        // Reiniciar puntos si se desea (opcional, se pueden mantener)
        // jugador1.reiniciarPuntos();
        // jugador2.reiniciarPuntos();
        // actualizarPuntajes();
        
        // Limpiar y reiniciar botones
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int numero = i * 3 + j + 1;
                botonesTablero[i][j].setText(String.valueOf(numero));
                botonesTablero[i][j].setForeground(Color.BLACK);
                botonesTablero[i][j].setBackground(Color.WHITE);
                botonesTablero[i][j].setEnabled(true);
            }
        }
        
        actualizarLabelTurno();
    }
    
    /**
     * Método principal que maneja los eventos de los botones
     * Se llama automáticamente cuando se hace clic en un botón
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Si el juego ya terminó, no hacer nada
        if (juegoTerminado) return;
        
        // Identificar qué botón fue clickeado
        JButton botonClickeado = (JButton) e.getSource();
        
        // Verificar si la casilla ya está marcada (por si acaso)
        String textoBoton = botonClickeado.getText();
        if (textoBoton.equals("X") || textoBoton.equals("O")) {
            JOptionPane.showMessageDialog(this, "¡Esa casilla ya está ocupada! Elige otra.");
            return;
        }
        
        // Mostrar pregunta al jugador
        boolean respondioBien = mostrarPregunta();
        
        if (respondioBien) {
            // Encontrar la posición del botón clickeado
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (botonesTablero[i][j] == botonClickeado) {
                        marcarCasilla(i, j);
                        return;
                    }
                }
            }
        }
        // Si respondió mal, no marca la casilla y cambia el turno igualmente
        else {
            cambiarTurno();
            // Si era el turno del jugador 2, avanzamos ronda
            if (jugadorActual == jugador1) {
                rondaActual++;
                gestorPreguntas.siguienteRonda();
            }
        }
    }
}