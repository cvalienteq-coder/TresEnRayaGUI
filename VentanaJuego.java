import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.io.File;
import java.util.Random;

/**
 * Ventana principal del juego "Tres en Raya - Quiz de Programación".
 *
 * Implementa ActionListener para manejar los eventos de los botones del tablero.
 * Características principales:
 *   - Registro de jugadores con avatar (emoji o imagen propia)
 *   - Tablero 3x3 con colores diferenciados para X (azul) y O (rojo)
 *   - Quiz de preguntas sobre Java: OOP, estructuras y Swing
 *   - Retroalimentación educativa al responder mal
 *   - Mensajes de motivación y esperanza
 *   - Sonidos MIDI sin necesidad de archivos externos
 *   - Desempate por tiempo de respuesta
 *   - Créditos visuales del proyecto
 *
 * @author Carmen Valiente Quiroz
 * @version 2.0
 */
public class VentanaJuego extends JFrame implements ActionListener {

    // ===== COMPONENTES DE LA INTERFAZ =====
    private JButton[][]  botonesTablero;     // Los 9 botones del tablero 3×3
    private JLabel[]     labelsPuntajes;     // Etiquetas de puntaje [0]=J1, [1]=J2
    private JLabel       labelTurno;         // Indicador del turno actual
    private JPanel       panelTablero;       // Panel contenedor del tablero

    // ===== JUGADORES =====
    private Jugador jugador1;               // Jugador 1 — símbolo X (azul)
    private Jugador jugador2;               // Jugador 2 — símbolo O (rojo)
    private Jugador jugadorActual;          // Jugador que tiene el turno activo

    // ===== LÓGICA DEL JUEGO =====
    private GestorPreguntas gestorPreguntas; // Gestor del banco de preguntas
    private int     rondaActual;             // Número de ronda en curso
    private int     marcasEnTablero;         // Casillas marcadas hasta ahora
    private boolean juegoTerminado;          // true cuando el juego ha concluido
    private long    tiempoInicioTurno;       // Marca de tiempo al iniciar un turno

    // ===== SONIDO MIDI =====
    private Synthesizer sintetizador;        // Sintetizador MIDI (no requiere archivos)

    // ===== COLORES DEL JUEGO =====
    private static final Color COLOR_X      = new Color(30,  130, 255); // Azul eléctrico — X
    private static final Color COLOR_O      = new Color(230,  60,  60); // Rojo intenso  — O
    private static final Color COLOR_FONDO  = new Color(18,   18,  35); // Fondo oscuro principal
    private static final Color COLOR_TARJETA= new Color(28,   28,  50); // Fondo de tarjetas
    private static final Color COLOR_CELDA  = new Color(38,   38,  68); // Fondo de celdas del tablero

    // ===== MENSAJES DE ANIMACIÓN Y ESPERANZA =====

    /** Mensajes cuando el jugador acierta */
    private static final String[] MENSAJES_EXITO = {
        "🌟 ¡Excelente! ¡Esa respuesta fue perfecta!",
        "💡 ¡Brillante! Tu conocimiento brilla con fuerza.",
        "🚀 ¡Increíble! Estás imparable en este juego.",
        "🏆 ¡Fantástico! Ese punto es completamente tuyo.",
        "🎯 ¡Puntería perfecta! Lo tenías bien aprendido.",
        "⚡ ¡Eres una máquina! Nadie puede contigo.",
        "🌈 ¡Wow! Qué nivel tan alto estás demostrando.",
        "🦅 ¡Extraordinario! Tu mente vuela bien alto."
    };

    /** Mensajes de esperanza cuando el jugador falla */
    private static final String[] MENSAJES_ERROR = {
        "💪 ¡No te rindas! Cada error te enseña algo nuevo.",
        "🌱 ¡Ánimo! Los grandes se levantan después de caer.",
        "🎮 ¡Sigue intentando! La práctica hace al maestro.",
        "🌊 ¡No importa! Las olas del éxito vienen después.",
        "🔥 ¡Tú puedes! Este error te hará más fuerte.",
        "⭐ ¡Adelante! Los errores son peldaños del éxito.",
        "🛡️ ¡Recupérate! Los campeones nunca se rinden.",
        "📚 ¡Aprende y avanza! El conocimiento siempre gana."
    };

    // ========================================================
    //  CONSTRUCTOR
    // ========================================================

    /**
     * Construye la ventana principal e inicializa toda la lógica del juego.
     */
    public VentanaJuego() {
        setTitle("Tres en Raya — Quiz de Programacion Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(680, 790);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout(10, 8));

        // Inicializar sonido MIDI (no requiere archivos externos)
        inicializarSonido();

        // Inicializar banco de preguntas
        gestorPreguntas = new GestorPreguntas();

        // Registrar jugadores con el diálogo mejorado de avatar
        jugador1 = registrarJugador(1, 'X');
        jugador2 = registrarJugador(2, 'O');

        // Configurar estado inicial del juego
        jugadorActual    = jugador1;
        rondaActual      = 1;
        marcasEnTablero  = 0;
        juegoTerminado   = false;

        // Construir la interfaz gráfica
        crearPanelSuperior();
        crearPanelTablero();
        crearPanelInferior();

        // Mensaje de bienvenida
        JOptionPane.showMessageDialog(this,
            "⚔️  ¡QUE EMPIECE EL DUELO!  ⚔️\n\n" +
            jugador1.getNombre() + "   VS   " + jugador2.getNombre() + "\n\n" +
            "Responde correctamente para marcar en el tablero.\n" +
            "¡El saber es tu arma!",
            "¡Bienvenidos al Quiz!",
            JOptionPane.INFORMATION_MESSAGE);

        tiempoInicioTurno = System.currentTimeMillis();
        tocarSonido(true); // Jingle de inicio
    }

    // ========================================================
    //  SONIDO MIDI
    // ========================================================

    /**
     * Inicializa el sintetizador MIDI del sistema.
     * Si no está disponible, los métodos de sonido usan el beep del sistema.
     */
    private void inicializarSonido() {
        try {
            sintetizador = MidiSystem.getSynthesizer();
            sintetizador.open();
        } catch (Exception e) {
            sintetizador = null; // Continuar sin MIDI si no está disponible
        }
    }

    /**
     * Toca una melodía corta en un hilo separado para no bloquear la UI.
     *
     * @param correcto true = melodía ascendente de éxito;
     *                 false = melodía descendente de error
     */
    private void tocarSonido(boolean correcto) {
        new Thread(() -> {
            try {
                if (sintetizador != null && sintetizador.isOpen()) {
                    MidiChannel canal = sintetizador.getChannels()[0];
                    canal.programChange(0); // Instrumento: Piano acústico

                    if (correcto) {
                        // Arpeggio ascendente: Do-Mi-Sol-Do alto
                        int[] notas     = {60, 64, 67, 72};
                        int[] duracion  = {140, 140, 140, 320};
                        for (int i = 0; i < notas.length; i++) {
                            canal.noteOn(notas[i], 90);
                            Thread.sleep(duracion[i]);
                            canal.noteOff(notas[i]);
                            Thread.sleep(25);
                        }
                    } else {
                        // Melodía descendente triste
                        int[] notas    = {67, 65, 62, 59};
                        int[] duracion = {200, 200, 200, 420};
                        for (int i = 0; i < notas.length; i++) {
                            canal.noteOn(notas[i], 75);
                            Thread.sleep(duracion[i]);
                            canal.noteOff(notas[i]);
                            Thread.sleep(30);
                        }
                    }
                } else {
                    Toolkit.getDefaultToolkit().beep(); // Fallback: beep del sistema
                }
            } catch (Exception ex) {
                Toolkit.getDefaultToolkit().beep();
            }
        }).start();
    }

    /**
     * Toca una fanfarria de victoria al ganar la partida.
     */
    private void tocarVictoria() {
        new Thread(() -> {
            try {
                if (sintetizador != null && sintetizador.isOpen()) {
                    MidiChannel canal = sintetizador.getChannels()[0];
                    canal.programChange(0);
                    // Escala ascendente + acorde final
                    int[] escala = {60, 62, 64, 65, 67, 69, 71, 72};
                    for (int nota : escala) {
                        canal.noteOn(nota, 100);
                        Thread.sleep(110);
                        canal.noteOff(nota);
                        Thread.sleep(15);
                    }
                    Thread.sleep(100);
                    // Acorde Do mayor final
                    int[] acorde = {60, 64, 67, 72};
                    for (int nota : acorde) canal.noteOn(nota, 95);
                    Thread.sleep(700);
                    for (int nota : acorde) canal.noteOff(nota);
                } else {
                    for (int i = 0; i < 3; i++) {
                        Toolkit.getDefaultToolkit().beep();
                        Thread.sleep(200);
                    }
                }
            } catch (Exception ex) {
                Toolkit.getDefaultToolkit().beep();
            }
        }).start();
    }

    // ========================================================
    //  REGISTRO DE JUGADORES — Diálogo con Avatar Moderno
    // ========================================================

    /**
     * Muestra un diálogo personalizado y moderno para registrar un jugador.
     * Permite ingresar nombre, elegir un emoji como avatar
     * o cargar una imagen desde el sistema de archivos.
     *
     * @param numero Número del jugador (1 o 2)
     * @param simbolo Símbolo asignado ('X' o 'O')
     * @return Objeto Jugador con nombre y avatar configurados
     */
    private Jugador registrarJugador(int numero, char simbolo) {

        // Variables mutables capturables por lambdas
        final String[]    nombreFinal = {"Jugador " + numero};
        final ImageIcon[] iconoFinal  = {null};
        final String[]    emojiFinal  = {numero == 1 ? "😊" : "😎"};

        Color colorJugador = (simbolo == 'X') ? COLOR_X : COLOR_O;

        JDialog dlg = new JDialog(this, "Registrar Jugador " + numero, true);
        dlg.setSize(500, 500);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(0, 0));
        dlg.setResizable(false);

        // ── ENCABEZADO COLOREADO ──────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(colorJugador);
        header.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel lblTitulo = new JLabel(
            "  Jugador " + numero + "  ( " + simbolo + " )",
            SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.CENTER);
        dlg.add(header, BorderLayout.NORTH);

        // ── CONTENIDO PRINCIPAL ───────────────────────────────
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(COLOR_TARJETA);
        main.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        // Campo de nombre
        JPanel fNombre = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        fNombre.setBackground(COLOR_TARJETA);
        JLabel lblN = new JLabel("Nombre:");
        lblN.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblN.setForeground(Color.WHITE);
        JTextField campoNombre = new JTextField(19);
        campoNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fNombre.add(lblN);
        fNombre.add(campoNombre);
        main.add(fNombre);
        main.add(Box.createRigidArea(new Dimension(0, 14)));

        // Separador
        JSeparator sep1 = new JSeparator();
        sep1.setForeground(new Color(70, 70, 110));
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        main.add(sep1);
        main.add(Box.createRigidArea(new Dimension(0, 12)));

        // Sección avatar
        JLabel lblSelAvatar = new JLabel("Elige tu avatar:");
        lblSelAvatar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSelAvatar.setForeground(Color.WHITE);
        lblSelAvatar.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(lblSelAvatar);
        main.add(Box.createRigidArea(new Dimension(0, 8)));

        // Panel de emojis como botones seleccionables
        String[] emojis = {"😊", "😎", "🤖", "👾", "🦊", "🐱", "🦁", "🐧"};
        JPanel gridEmojis = new JPanel(new GridLayout(2, 4, 6, 6));
        gridEmojis.setBackground(COLOR_TARJETA);
        gridEmojis.setMaximumSize(new Dimension(340, 100));

        // Vista previa del avatar seleccionado
        JLabel preview = new JLabel(renderAvatarColorido(emojiFinal[0], 80, colorJugador));
        preview.setPreferredSize(new Dimension(86, 86));
        preview.setBorder(BorderFactory.createLineBorder(colorJugador, 3));
        preview.setBackground(new Color(45, 45, 80));
        preview.setOpaque(true);
        preview.setHorizontalAlignment(SwingConstants.CENTER);

        ButtonGroup grupoBotones = new ButtonGroup();
        for (int i = 0; i < emojis.length; i++) {
            final String emoji = emojis[i];
            JToggleButton btn = new JToggleButton(emoji);
            btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
            btn.setBackground(new Color(48, 48, 85));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 3, 5, 3));
            if ((numero == 1 && i == 0) || (numero == 2 && i == 1)) {
                btn.setSelected(true);
            }
            grupoBotones.add(btn);
            btn.addActionListener(ae -> {
                emojiFinal[0] = emoji;
                iconoFinal[0] = null;
                preview.setText("");
                preview.setIcon(renderAvatarColorido(emoji, 80, colorJugador));
            });
            gridEmojis.add(btn);
        }

        // Fila: grid de emojis + preview
        JPanel filaAvatar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        filaAvatar.setBackground(COLOR_TARJETA);
        filaAvatar.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaAvatar.add(gridEmojis);
        filaAvatar.add(preview);
        main.add(filaAvatar);
        main.add(Box.createRigidArea(new Dimension(0, 12)));

        // Botón cargar imagen desde disco
        JButton btnCargar = new JButton("  Cargar mi foto...");
        btnCargar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCargar.setBackground(new Color(55, 55, 95));
        btnCargar.setForeground(Color.WHITE);
        btnCargar.setFocusPainted(false);
        btnCargar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnCargar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCargar.addActionListener(ae -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Seleccionar imagen de avatar");
            fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imagenes (JPG, PNG, GIF, BMP)", "jpg", "jpeg", "png", "gif", "bmp"));
            if (fc.showOpenDialog(dlg) == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage img = ImageIO.read(fc.getSelectedFile());
                    if (img != null) {
                        Image scaled = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                        iconoFinal[0] = new ImageIcon(scaled);
                        preview.setIcon(iconoFinal[0]);
                        preview.setText("");
                        grupoBotones.clearSelection();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dlg,
                        "No se pudo cargar la imagen seleccionada.\n" +
                        "Verifica que sea un formato válido (JPG/PNG/GIF).",
                        "Error al cargar imagen",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        main.add(btnCargar);
        dlg.add(main, BorderLayout.CENTER);

        // ── BOTÓN CONFIRMAR ────────────────────────────────────
        JPanel pieDlg = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pieDlg.setBackground(COLOR_TARJETA);
        pieDlg.setBorder(BorderFactory.createEmptyBorder(8, 0, 18, 0));

        JButton btnOK = new JButton("  Confirmar y Continuar");
        btnOK.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnOK.setBackground(colorJugador);
        btnOK.setForeground(Color.WHITE);
        btnOK.setFocusPainted(false);
        btnOK.setBorder(BorderFactory.createEmptyBorder(10, 28, 10, 28));
        btnOK.addActionListener(ae -> {
            String nombre = campoNombre.getText().trim();
            if (!nombre.isEmpty()) nombreFinal[0] = nombre;
            dlg.dispose();
        });
        pieDlg.add(btnOK);
        dlg.add(pieDlg, BorderLayout.SOUTH);

        dlg.setVisible(true); // Bloquea hasta que se cierre

        // Construir el icono final: imagen cargada o emoji renderizado como círculo
        ImageIcon icono = iconoFinal[0];
        if (icono == null) {
            icono = renderAvatarColorido(emojiFinal[0], 64, colorJugador);
        }

        // Si se cargó foto propia, no añadir el emoji al nombre
        String nombreCompleto = (iconoFinal[0] != null)
            ? nombreFinal[0]
            : emojiFinal[0] + " " + nombreFinal[0];

        return new Jugador(nombreCompleto, simbolo, icono);
    }

    /**
     * Renderiza un avatar vectorial colorido con detalles específicos según el emoji.
     * Esto proporciona un acabado mucho más moderno y atractivo que los emojis de texto
     * monocromo que por defecto renderiza Java Swing.
     *
     * @param avatarId   El emoji o identificador elegido por el jugador
     * @param size       Tamaño en píxeles de la imagen resultante
     * @param colorFondo Color de base para el fondo circular con gradiente
     * @return ImageIcon con el avatar vectorial coloreado
     */
    private ImageIcon renderAvatarColorido(String avatarId, int size, Color colorFondo) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Fondo circular con degradado suave
        GradientPaint gp = new GradientPaint(
            0, 0, colorFondo.brighter(),
            size, size, colorFondo.darker());
        g2d.setPaint(gp);
        g2d.fillOval(2, 2, size - 5, size - 5);

        // Borde circular blanco elegante
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawOval(2, 2, size - 5, size - 5);

        int c = size / 2; // Centro de la imagen

        if (avatarId.equals("🤖")) {
            // --- ROBOT COLORIDO ---
            // Cabeza metálica
            g2d.setColor(new Color(175, 189, 205));
            g2d.fillRoundRect(c - 16, c - 10, 32, 24, 6, 6);
            // Antena dorada
            g2d.setColor(new Color(255, 195, 0));
            g2d.setStroke(new BasicStroke(2.5f));
            g2d.drawLine(c, c - 10, c, c - 18);
            g2d.fillOval(c - 3, c - 22, 6, 6);
            // Ojos LED cyan brillante
            g2d.setColor(new Color(0, 255, 255));
            g2d.fillOval(c - 10, c - 4, 6, 6);
            g2d.fillOval(c + 4, c - 4, 6, 6);
            // Rejilla de boca oscura
            g2d.setColor(new Color(75, 80, 95));
            g2d.drawRect(c - 8, c + 6, 16, 4);
            g2d.setStroke(new BasicStroke(1.0f));
            g2d.drawLine(c - 4, c + 6, c - 4, c + 10);
            g2d.drawLine(c, c + 6, c, c + 10);
            g2d.drawLine(c + 4, c + 6, c + 4, c + 10);

        } else if (avatarId.equals("👾")) {
            // --- MONSTRUO/INVASOR MORADO ---
            g2d.setColor(new Color(185, 75, 245));
            int[] xAlien = {c-15, c-9, c-9, c-15, c-15, c-6, c-6, c+6, c+6, c+15, c+15, c+9, c+9, c+15};
            int[] yAlien = {c+12, c+12, c+6,  c+6,  c-4,  c-4,  c-10, c-10, c-4,  c-4,  c+6,  c+6,  c+12, c+12};
            g2d.fillPolygon(xAlien, yAlien, xAlien.length);
            // Ojos amarillos brillantes
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(c - 8, c - 2, 4, 4);
            g2d.fillOval(c + 4, c - 2, 4, 4);
            // Puntas de antenas
            g2d.setColor(new Color(185, 75, 245));
            g2d.fillOval(c - 13, c - 13, 4, 4);
            g2d.fillOval(c + 9, c - 13, 4, 4);

        } else if (avatarId.equals("🐱")) {
            // --- GATO ANARANJADO ---
            // Orejas naranjas
            g2d.setColor(new Color(255, 165, 55));
            int[] xO1 = {c - 16, c - 16, c - 6};
            int[] yO1 = {c - 4, c - 18, c - 8};
            g2d.fillPolygon(xO1, yO1, 3);
            int[] xO2 = {c + 16, c + 16, c + 6};
            int[] yO2 = {c - 4, c - 18, c - 8};
            g2d.fillPolygon(xO2, yO2, 3);
            // Interior oreja rosa
            g2d.setColor(new Color(255, 175, 175));
            int[] xO1_in = {c - 14, c - 14, c - 8};
            int[] yO1_in = {c - 5, c - 15, c - 8};
            g2d.fillPolygon(xO1_in, yO1_in, 3);
            int[] xO2_in = {c + 14, c + 14, c + 8};
            int[] yO2_in = {c - 5, c - 15, c - 8};
            g2d.fillPolygon(xO2_in, yO2_in, 3);
            // Cabeza
            g2d.setColor(new Color(255, 165, 55));
            g2d.fillOval(c - 16, c - 8, 32, 23);
            // Ojos verdes con pupilas negras
            g2d.setColor(new Color(80, 220, 80));
            g2d.fillOval(c - 10, c - 2, 6, 6);
            g2d.fillOval(c + 4, c - 2, 6, 6);
            g2d.setColor(Color.BLACK);
            g2d.fillOval(c - 8, c - 2, 2, 6);
            g2d.fillOval(c + 6, c - 2, 2, 6);
            // Hocico rosa
            g2d.setColor(new Color(255, 120, 120));
            g2d.fillOval(c - 2, c + 4, 4, 3);
            // Bigotes blancos
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(1.0f));
            g2d.drawLine(c - 18, c + 5, c - 10, c + 5);
            g2d.drawLine(c - 18, c + 8, c - 10, c + 7);
            g2d.drawLine(c + 10, c + 5, c + 18, c + 5);
            g2d.drawLine(c + 10, c + 7, c + 18, c + 8);

        } else if (avatarId.equals("🦊")) {
            // --- ZORRO ---
            // Cabeza naranja
            g2d.setColor(new Color(245, 95, 15));
            int[] xFox = {c - 18, c + 18, c};
            int[] yFox = {c - 8, c - 8, c + 16};
            g2d.fillPolygon(xFox, yFox, 3);
            // Orejas
            int[] xE1 = {c - 18, c - 18, c - 6};
            int[] yE1 = {c - 8, c - 20, c - 8};
            g2d.fillPolygon(xE1, yE1, 3);
            int[] xE2 = {c + 18, c + 18, c + 6};
            int[] yE2 = {c - 8, c - 20, c - 8};
            g2d.fillPolygon(xE2, yE2, 3);
            // Interior oreja negro
            g2d.setColor(Color.BLACK);
            int[] xE1_in = {c - 16, c - 16, c - 10};
            int[] yE1_in = {c - 9, c - 16, c - 9};
            g2d.fillPolygon(xE1_in, yE1_in, 3);
            int[] xE2_in = {c + 16, c + 16, c + 10};
            int[] yE2_in = {c - 9, c - 16, c - 9};
            g2d.fillPolygon(xE2_in, yE2_in, 3);
            // Mejillas blancas
            g2d.setColor(Color.WHITE);
            int[] xW1 = {c - 16, c - 6, c};
            int[] yW1 = {c - 5, c + 2, c + 10};
            g2d.fillPolygon(xW1, yW1, 3);
            int[] xW2 = {c + 16, c + 6, c};
            int[] yW2 = {c - 5, c + 2, c + 10};
            g2d.fillPolygon(xW2, yW2, 3);
            // Ojos
            g2d.setColor(Color.BLACK);
            g2d.fillOval(c - 10, c - 4, 3, 3);
            g2d.fillOval(c + 7, c - 4, 3, 3);
            // Nariz
            g2d.fillOval(c - 3, c + 11, 6, 5);

        } else if (avatarId.equals("🐧")) {
            // --- PINGÜINO ---
            // Cuerpo negro azulado
            g2d.setColor(new Color(25, 25, 35));
            g2d.fillOval(c - 16, c - 12, 32, 28);
            // Tummy blanco
            g2d.setColor(Color.WHITE);
            g2d.fillOval(c - 11, c - 6, 22, 22);
            // Ojos negros
            g2d.setColor(Color.BLACK);
            g2d.fillOval(c - 5, c - 6, 3, 4);
            g2d.fillOval(c + 2, c - 6, 3, 4);
            // Pico naranja brillante
            g2d.setColor(new Color(255, 150, 0));
            int[] xBeak = {c - 4, c + 4, c};
            int[] yBeak = {c - 2, c - 2, c + 4};
            g2d.fillPolygon(xBeak, yBeak, 3);
            // Patas naranjas
            g2d.fillOval(c - 12, c + 12, 8, 4);
            g2d.fillOval(c + 4, c + 12, 8, 4);

        } else if (avatarId.equals("🦁")) {
            // --- LEÓN ---
            // Melena abundante marrón
            g2d.setColor(new Color(165, 82, 20));
            g2d.fillOval(c - 20, c - 14, 40, 32);
            // Orejas amarillas
            g2d.setColor(new Color(245, 190, 60));
            g2d.fillOval(c - 14, c - 16, 8, 8);
            g2d.fillOval(c + 6, c - 16, 8, 8);
            // Rostro dorado
            g2d.setColor(new Color(245, 190, 60));
            g2d.fillOval(c - 15, c - 10, 30, 24);
            // Ojos negros
            g2d.setColor(Color.BLACK);
            g2d.fillOval(c - 8, c - 4, 4, 4);
            g2d.fillOval(c + 4, c - 4, 4, 4);
            // Hocico blanco y nariz marrón
            g2d.setColor(Color.WHITE);
            g2d.fillOval(c - 5, c + 1, 10, 6);
            g2d.setColor(new Color(110, 50, 10));
            int[] xNose = {c - 3, c + 3, c};
            int[] yNose = {c + 1, c + 1, c + 4};
            g2d.fillPolygon(xNose, yNose, 3);

        } else if (avatarId.equals("😎")) {
            // --- CARITA AMARILLA CON GAFAS DE SOL ---
            // Cara
            g2d.setColor(new Color(255, 215, 30));
            g2d.fillOval(c - 16, c - 14, 32, 28);
            // Gafas de sol negras
            g2d.setColor(new Color(15, 15, 15));
            int[] xG1 = {c - 14, c - 2, c - 2, c - 12};
            int[] yG1 = {c - 6,  c - 6, c + 1, c + 1};
            g2d.fillPolygon(xG1, yG1, 4);
            int[] xG2 = {c + 2,  c + 14, c + 12, c + 2};
            int[] yG2 = {c - 6,  c - 6,  c + 1,  c + 1};
            g2d.fillPolygon(xG2, yG2, 4);
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawLine(c - 4, c - 5, c + 4, c - 5);
            // Sonrisa confiada
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawArc(c - 8, c + 2, 16, 8, 180, 180);

        } else {
            // --- 😊 CARITA SONRIENTE CLÁSICA (AMARILLA) ---
            // Rostro
            g2d.setColor(new Color(255, 215, 30));
            g2d.fillOval(c - 16, c - 14, 32, 28);
            // Ojos felices
            g2d.setColor(new Color(85, 45, 10));
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawArc(c - 10, c - 5, 5, 4, 0, 180);
            g2d.drawArc(c + 5, c - 5, 5, 4, 0, 180);
            // Boca abierta y lengua rosa
            g2d.setColor(new Color(185, 35, 35));
            g2d.fillArc(c - 8, c + 1, 16, 10, 180, 180);
            g2d.setColor(new Color(255, 150, 170));
            g2d.fillArc(c - 5, c + 5, 10, 6, 180, 180);
        }

        g2d.dispose();
        return new ImageIcon(img);
    }

    // ========================================================
    //  CONSTRUCCIÓN DE LA INTERFAZ GRÁFICA
    // ========================================================

    /** Crea el panel superior con la información de ambos jugadores. */
    private void crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 14, 0));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(14, 14, 8, 14));
        panelSuperior.setBackground(COLOR_FONDO);

        labelsPuntajes = new JLabel[2];
        panelSuperior.add(crearTarjetaJugador(jugador1, 0));
        panelSuperior.add(crearTarjetaJugador(jugador2, 1));
        add(panelSuperior, BorderLayout.NORTH);
    }

    /**
     * Construye la tarjeta visual de información de un jugador.
     * Muestra avatar (64×64), nombre coloreado, símbolo y puntaje.
     *
     * @param jugador El objeto Jugador
     * @param indice  0 para J1, 1 para J2
     * @return JPanel con la tarjeta del jugador
     */
    private JPanel crearTarjetaJugador(Jugador jugador, int indice) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 0));
        tarjeta.setBackground(COLOR_TARJETA);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(jugador.getColor(), 2),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        // Avatar
        JLabel lblAvatar = new JLabel();
        if (jugador.getAvatarIcono() != null) {
            Image img = jugador.getAvatarIcono().getImage()
                               .getScaledInstance(52, 52, Image.SCALE_SMOOTH);
            lblAvatar.setIcon(new ImageIcon(img));
        }
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvatar.setBorder(BorderFactory.createLineBorder(jugador.getColor(), 1));
        tarjeta.add(lblAvatar, BorderLayout.WEST);

        // Info del jugador
        JPanel info = new JPanel(new GridLayout(3, 1, 0, 2));
        info.setBackground(COLOR_TARJETA);

        JLabel lblNombre = new JLabel(jugador.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombre.setForeground(jugador.getColor());

        JLabel lblSimbolo = new JLabel(String.valueOf(jugador.getSimbolo()));
        lblSimbolo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblSimbolo.setForeground(jugador.getColor());

        labelsPuntajes[indice] = new JLabel("0 puntos");
        labelsPuntajes[indice].setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelsPuntajes[indice].setForeground(new Color(180, 180, 210));

        info.add(lblNombre);
        info.add(lblSimbolo);
        info.add(labelsPuntajes[indice]);
        tarjeta.add(info, BorderLayout.CENTER);

        return tarjeta;
    }

    /** Crea el tablero 3×3 con botones estilizados y oscuros. */
    private void crearPanelTablero() {
        panelTablero = new JPanel(new GridLayout(3, 3, 7, 7));
        panelTablero.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        panelTablero.setBackground(COLOR_FONDO);

        botonesTablero = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int numero = i * 3 + j + 1;
                botonesTablero[i][j] = crearBotonCelda(numero);
                botonesTablero[i][j].addActionListener(this);
                panelTablero.add(botonesTablero[i][j]);
            }
        }
        add(panelTablero, BorderLayout.CENTER);
    }

    /**
     * Crea un botón de celda del tablero con estilo oscuro y redondeado.
     *
     * @param numero El número a mostrar en la celda (1-9)
     * @return JButton estilizado para el tablero
     */
    private JButton crearBotonCelda(int numero) {
        JButton btn = new JButton(String.valueOf(numero)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
                // Color de fondo según estado
                Color fondo;
                if (!isEnabled()) {
                    fondo = new Color(28, 28, 55);          // Marcada
                } else if (getModel().isPressed()) {
                    fondo = new Color(55, 55, 100);         // Presionada
                } else if (getModel().isRollover()) {
                    fondo = new Color(50, 50, 92);          // Hover
                } else {
                    fondo = COLOR_CELDA;                    // Normal
                }
                g2d.setColor(fondo);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 22));
        btn.setForeground(new Color(120, 120, 165));
        btn.setContentAreaFilled(false); // Delegado al paintComponent
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 150));
        return btn;
    }

    /** Crea el panel inferior con el indicador de turno y el botón de créditos. */
    private void crearPanelInferior() {
        JPanel panelInferior = new JPanel(new BorderLayout(10, 0));
        panelInferior.setBackground(COLOR_FONDO);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(4, 15, 16, 15));

        labelTurno = new JLabel("", SwingConstants.CENTER);
        labelTurno.setFont(new Font("Segoe UI", Font.BOLD, 15));
        actualizarLabelTurno();

        JButton btnCreditos = new JButton("Creditos");
        btnCreditos.setBackground(new Color(48, 48, 80));
        btnCreditos.setForeground(Color.WHITE);
        btnCreditos.setFocusPainted(false);
        btnCreditos.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btnCreditos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCreditos.addActionListener(e -> mostrarCreditos());

        panelInferior.add(labelTurno, BorderLayout.CENTER);
        panelInferior.add(btnCreditos, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /** Actualiza el label de turno con el jugador activo y su color. */
    private void actualizarLabelTurno() {
        labelTurno.setText("Turno de: " + jugadorActual.getNombre() +
                           "   [" + jugadorActual.getSimbolo() + "]");
        labelTurno.setForeground(jugadorActual.getColor());
    }

    /** Actualiza las etiquetas de puntaje de ambos jugadores. */
    private void actualizarPuntajes() {
        labelsPuntajes[0].setText(jugador1.getPuntos() + " puntos");
        labelsPuntajes[1].setText(jugador2.getPuntos() + " puntos");
    }

    // ========================================================
    //  CREDITOS — Ventana visual
    // ========================================================

    /**
     * Muestra la ventana de créditos del proyecto con diseño visual.
     * Incluye: Autora, Asesor, Curso, Año y Licencia.
     */
    private void mostrarCreditos() {
        JDialog dlg = new JDialog(this, "Creditos del Proyecto", true);
        dlg.setSize(440, 400);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout());
        dlg.setResizable(false);

        // Encabezado
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(new Color(35, 55, 130));
        header.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel titulo = new JLabel("TRES EN RAYA — Quiz de Java", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Proyecto Final — Creditos", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        subtitulo.setForeground(new Color(180, 200, 255));

        header.add(titulo);
        header.add(subtitulo);
        dlg.add(header, BorderLayout.NORTH);

        // Filas de créditos
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(COLOR_TARJETA);
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Datos: {icono, campo, valor}
        String[][] creditos = {
            {"\uD83D\uDC69\u200D\uD83D\uDCBB", "Autora",   "Carmen Valiente Quiroz"},
            {"\uD83D\uDC68\u200D\uD83C\uDFEB", "Asesor",   "Richart Escobedo Quispe"},
            {"\uD83D\uDCDA",                    "Curso",    "Lenguaje de Programacion I"},
            {"\uD83D\uDCC5",                    "Año",      "2026"},
            {"\u2696\uFE0F",                    "Licencia", "MIT — Uso Educativo"}
        };

        for (String[] fila : creditos) {
            JPanel linea = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
            linea.setBackground(COLOR_TARJETA);
            linea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

            JLabel icono = new JLabel(fila[0]);
            icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
            icono.setPreferredSize(new Dimension(28, 28));

            JLabel campo = new JLabel(fila[1] + ":");
            campo.setFont(new Font("Segoe UI", Font.BOLD, 13));
            campo.setForeground(new Color(130, 170, 255));
            campo.setPreferredSize(new Dimension(75, 24));

            JLabel valor = new JLabel(fila[2]);
            valor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            valor.setForeground(Color.WHITE);

            linea.add(icono);
            linea.add(campo);
            linea.add(valor);
            contenido.add(linea);
        }

        dlg.add(contenido, BorderLayout.CENTER);

        // Botón cerrar
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pie.setBackground(COLOR_TARJETA);
        pie.setBorder(BorderFactory.createEmptyBorder(5, 0, 14, 0));
        JButton btnCerrar = new JButton("  Cerrar");
        btnCerrar.setBackground(new Color(35, 55, 130));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        btnCerrar.addActionListener(e -> dlg.dispose());
        pie.add(btnCerrar);
        dlg.add(pie, BorderLayout.SOUTH);

        dlg.setVisible(true);
    }

    // ========================================================
    //  PREGUNTA Y RETROALIMENTACIÓN
    // ========================================================

    /**
     * Muestra el diálogo de pregunta para el jugador actual.
     * Registra el tiempo de inicio para el cálculo de velocidad de respuesta.
     *
     * @return true si respondió correctamente, false si falló o canceló
     */
    private boolean mostrarPregunta() {
        int numJugador = (jugadorActual == jugador1) ? 1 : 2;
        Pregunta pregunta = gestorPreguntas.getPregunta(numJugador, rondaActual);

        // Construir el panel de la pregunta
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Encabezado de la pregunta
        JLabel lblEnc = new JLabel(
            "Pregunta para: " + jugadorActual.getNombre());
        lblEnc.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEnc.setForeground(jugadorActual.getColor());
        lblEnc.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblEnc);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Texto de la pregunta con wrap HTML
        JLabel lblTexto = new JLabel(
            "<html><div style='width:360px'><b>" + pregunta.getTexto() + "</b></div></html>");
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTexto.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTexto);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Opciones de respuesta
        String[] letras = {"A", "B", "C"};
        String[] opciones = pregunta.getOpciones();
        ButtonGroup grupo = new ButtonGroup();
        JRadioButton[] radios = new JRadioButton[3];

        for (int i = 0; i < 3; i++) {
            radios[i] = new JRadioButton(letras[i] + ")  " + opciones[i]);
            radios[i].setFont(new Font("Segoe UI", Font.PLAIN, 13));
            radios[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            grupo.add(radios[i]);
            panel.add(radios[i]);
            panel.add(Box.createRigidArea(new Dimension(0, 4)));
        }

        String[] btns = {"Responder", "Cancelar"};
        int resultado = JOptionPane.showOptionDialog(
            this, panel,
            "Pregunta — Ronda " + rondaActual,
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, btns, btns[0]
        );

        if (resultado != 0) return false;

        // Identificar la opción seleccionada
        int seleccionada = -1;
        for (int i = 0; i < 3; i++) {
            if (radios[i].isSelected()) { seleccionada = i; break; }
        }

        if (seleccionada == -1) {
            // No seleccionó ninguna opción
            JOptionPane.showMessageDialog(this,
                "No seleccionaste ninguna opción.\n¡Pierdes tu turno esta vez!",
                "Sin respuesta",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        boolean correcto = pregunta.esCorrecta(seleccionada);

        if (correcto) {
            jugadorActual.sumarPuntos(30);
            actualizarPuntajes();
            tocarSonido(true);
        } else {
            tocarSonido(false);
        }

        // Siempre mostrar retroalimentación (éxito o error)
        mostrarRetroalimentacion(pregunta, seleccionada, correcto);

        return correcto;
    }

    /**
     * Muestra la retroalimentación educativa tras responder.
     * En caso de error: muestra la opción elegida, la correcta y una explicación.
     * En caso de acierto: muestra un mensaje motivador de éxito.
     *
     * @param pregunta    La pregunta respondida
     * @param seleccionada Índice 0-based de la opción elegida
     * @param correcto     true si la respuesta fue correcta
     */
    private void mostrarRetroalimentacion(Pregunta pregunta, int seleccionada, boolean correcto) {
        String[] letras  = {"A", "B", "C"};
        String[] opciones = pregunta.getOpciones();
        Random rand = new Random();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        if (correcto) {
            // ── ACIERTO ──
            JLabel lblTitulo = new JLabel("RESPUESTA CORRECTA  +30 puntos");
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
            lblTitulo.setForeground(new Color(0, 190, 90));
            lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblTitulo);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            JLabel lblAnimo = new JLabel(
                "<html><i>" + MENSAJES_EXITO[rand.nextInt(MENSAJES_EXITO.length)] + "</i></html>");
            lblAnimo.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            lblAnimo.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblAnimo);

            JOptionPane.showMessageDialog(this, panel,
                "Correcto!", JOptionPane.PLAIN_MESSAGE);

        } else {
            // ── ERROR ── mostrar respuesta del usuario, correcta y explicación
            JLabel lblTitulo = new JLabel("RESPUESTA INCORRECTA");
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
            lblTitulo.setForeground(new Color(230, 60, 60));
            lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblTitulo);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Respuesta elegida por el usuario
            JLabel lblTuResp = new JLabel(
                "Tu respuesta: " + letras[seleccionada] + ") " + opciones[seleccionada]);
            lblTuResp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lblTuResp.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblTuResp);
            panel.add(Box.createRigidArea(new Dimension(0, 4)));

            // Respuesta correcta resaltada en verde
            int idxCorrecta = pregunta.getRespuestaCorrecta();
            JLabel lblCorrecta = new JLabel(
                "Respuesta correcta: " + letras[idxCorrecta] + ") " + opciones[idxCorrecta]);
            lblCorrecta.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblCorrecta.setForeground(new Color(0, 180, 80));
            lblCorrecta.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblCorrecta);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Separador visual
            JSeparator sep = new JSeparator();
            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            panel.add(sep);
            panel.add(Box.createRigidArea(new Dimension(0, 8)));

            // Sección de explicación educativa
            JLabel lblExpTit = new JLabel("Por que es correcta:");
            lblExpTit.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblExpTit.setForeground(new Color(130, 170, 255));
            lblExpTit.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblExpTit);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));

            JLabel lblExp = new JLabel(
                "<html><div style='width:360px'>" + pregunta.getExplicacion() + "</div></html>");
            lblExp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblExp.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblExp);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Mensaje de esperanza y motivación
            JSeparator sep2 = new JSeparator();
            sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            panel.add(sep2);
            panel.add(Box.createRigidArea(new Dimension(0, 8)));

            JLabel lblAnimo = new JLabel(
                "<html><i>" + MENSAJES_ERROR[rand.nextInt(MENSAJES_ERROR.length)] + "</i></html>");
            lblAnimo.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            lblAnimo.setForeground(new Color(120, 160, 255));
            lblAnimo.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblAnimo);

            JOptionPane.showMessageDialog(this, panel,
                "Respuesta Incorrecta — Aprende y Sigue", JOptionPane.PLAIN_MESSAGE);
        }
    }

    // ========================================================
    //  LÓGICA DEL TABLERO
    // ========================================================

    /**
     * Marca la casilla en el tablero con el símbolo del jugador actual.
     * El símbolo X se muestra en azul y el O en rojo.
     * Luego verifica si hay ganador o si el tablero se llenó.
     *
     * @param fila    Fila de la celda (0-2)
     * @param columna Columna de la celda (0-2)
     */
    private void marcarCasilla(int fila, int columna) {
        JButton btn = botonesTablero[fila][columna];

        // Mostrar el símbolo grande con el color del jugador
        btn.setText(String.valueOf(jugadorActual.getSimbolo()));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 62));
        btn.setForeground(jugadorActual.getColor());
        btn.setEnabled(false);
        marcasEnTablero++;

        // Registrar tiempo de respuesta
        long finTurno = System.currentTimeMillis();
        jugadorActual.sumarTiempo(finTurno - tiempoInicioTurno);

        // Verificar si hay ganador por tres en línea
        if (verificarGanador()) {
            juegoTerminado = true;
            tocarVictoria();
            mostrarResultadoFinal(true);
            return;
        }

        // Verificar si el tablero está lleno (empate en tablero)
        if (marcasEnTablero >= 9) {
            juegoTerminado = true;
            mostrarResultadoFinal(false);
            return;
        }

        // Cambiar al siguiente turno
        cambiarTurno();
        if (jugadorActual == jugador1) {
            rondaActual++;
            gestorPreguntas.siguienteRonda();
        }
        tiempoInicioTurno = System.currentTimeMillis();
    }

    /**
     * Verifica si el jugador actual ha logrado tres en línea.
     * Comprueba filas, columnas y ambas diagonales.
     *
     * @return true si el jugador actual ganó, false en caso contrario
     */
    private boolean verificarGanador() {
        String s = String.valueOf(jugadorActual.getSimbolo());
        String[][] v = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                v[i][j] = botonesTablero[i][j].getText();

        // Filas
        for (int i = 0; i < 3; i++)
            if (v[i][0].equals(s) && v[i][1].equals(s) && v[i][2].equals(s)) return true;

        // Columnas
        for (int j = 0; j < 3; j++)
            if (v[0][j].equals(s) && v[1][j].equals(s) && v[2][j].equals(s)) return true;

        // Diagonales
        if (v[0][0].equals(s) && v[1][1].equals(s) && v[2][2].equals(s)) return true;
        if (v[0][2].equals(s) && v[1][1].equals(s) && v[2][0].equals(s)) return true;

        return false;
    }

    /** Alterna el turno entre jugador1 y jugador2. */
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
        actualizarLabelTurno();
    }

    // ========================================================
    //  RESULTADO FINAL Y DESEMPATE POR TIEMPO
    // ========================================================

    /**
     * Muestra el resultado final de la partida.
     * Si hay ganador en tablero, lo anuncia directamente.
     * Si hay empate en tablero: compara primero puntos,
     * luego tiempo de respuesta (menor tiempo = responde más rápido).
     *
     * @param hayGanadorEnTablero true si alguien logró tres en línea
     */
    private void mostrarResultadoFinal(boolean hayGanadorEnTablero) {
        String ganadorNombre;
        String motivoGanador;

        if (hayGanadorEnTablero) {
            // Ganó por tres en línea
            ganadorNombre = jugadorActual.getNombre();
            motivoGanador = "logro tres en linea";
        } else {
            // Empate en tablero — desempate por puntos y tiempo
            int  p1 = jugador1.getPuntos();
            int  p2 = jugador2.getPuntos();
            long t1 = jugador1.getTiempoTotal();
            long t2 = jugador2.getTiempoTotal();

            if (p1 > p2) {
                ganadorNombre = jugador1.getNombre();
                motivoGanador = "tiene mas puntos (" + p1 + " vs " + p2 + ")";
            } else if (p2 > p1) {
                ganadorNombre = jugador2.getNombre();
                motivoGanador = "tiene mas puntos (" + p2 + " vs " + p1 + ")";
            } else if (t1 < t2) {
                // Mismos puntos — gana quien respondió más rápido
                ganadorNombre = jugador1.getNombre();
                motivoGanador = "respondio mas rapido ("
                    + String.format("%.1f", t1 / 1000.0) + "s vs "
                    + String.format("%.1f", t2 / 1000.0) + "s)";
            } else if (t2 < t1) {
                ganadorNombre = jugador2.getNombre();
                motivoGanador = "respondio mas rapido ("
                    + String.format("%.1f", t2 / 1000.0) + "s vs "
                    + String.format("%.1f", t1 / 1000.0) + "s)";
            } else {
                ganadorNombre = null;
                motivoGanador = "Empate absoluto en puntos y tiempo";
            }
        }

        // Panel del resultado final
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Título del ganador
        String textoGanador = (ganadorNombre != null)
            ? "GANADOR: " + ganadorNombre + "!"
            : "EMPATE TOTAL!";
        JLabel lblGanador = new JLabel(textoGanador, SwingConstants.CENTER);
        lblGanador.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblGanador.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblGanador);

        if (ganadorNombre != null) {
            JLabel lblMotivo = new JLabel("Porque " + motivoGanador, SwingConstants.CENTER);
            lblMotivo.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            lblMotivo.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lblMotivo);
        }

        panel.add(Box.createRigidArea(new Dimension(0, 14)));

        // Tabla de puntuación con tiempos de respuesta
        JLabel lblTabla = new JLabel("Puntuacion Final", SwingConstants.CENTER);
        lblTabla.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTabla.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTabla);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));

        JLabel lJ1 = new JLabel(String.format(
            "%s :  %d pts  |  Tiempo: %.1fs",
            jugador1.getNombre(), jugador1.getPuntos(), jugador1.getTiempoTotal() / 1000.0));
        lJ1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lJ1.setForeground(COLOR_X);
        lJ1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lJ2 = new JLabel(String.format(
            "%s :  %d pts  |  Tiempo: %.1fs",
            jugador2.getNombre(), jugador2.getPuntos(), jugador2.getTiempoTotal() / 1000.0));
        lJ2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lJ2.setForeground(COLOR_O);
        lJ2.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lJ1);
        panel.add(lJ2);

        JOptionPane.showMessageDialog(this, panel, "Fin del Juego", JOptionPane.PLAIN_MESSAGE);

        // Preguntar si desean jugar otra partida
        int opcion = JOptionPane.showConfirmDialog(this,
            "Quieren jugar otra partida?\n¡La revancha espera!",
            "Nueva Partida", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            reiniciarJuego();
        } else {
            if (sintetizador != null && sintetizador.isOpen()) sintetizador.close();
            System.exit(0);
        }
    }

    // ========================================================
    //  REINICIO DEL JUEGO
    // ========================================================

    /**
     * Reinicia todos los estados del juego para una nueva partida
     * sin volver a registrar los jugadores.
     */
    private void reiniciarJuego() {
        rondaActual     = 1;
        marcasEnTablero = 0;
        juegoTerminado  = false;
        jugadorActual   = jugador1;

        gestorPreguntas.reiniciarRondas();
        jugador1.reiniciarPuntos();
        jugador2.reiniciarPuntos();
        jugador1.reiniciarTiempo();
        jugador2.reiniciarTiempo();
        actualizarPuntajes();

        // Restaurar botones del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num = i * 3 + j + 1;
                JButton btn = botonesTablero[i][j];
                btn.setText(String.valueOf(num));
                btn.setFont(new Font("Segoe UI", Font.BOLD, 22));
                btn.setForeground(new Color(120, 120, 165));
                btn.setEnabled(true);
            }
        }

        actualizarLabelTurno();
        tiempoInicioTurno = System.currentTimeMillis();
    }

    // ========================================================
    //  MANEJADOR DE EVENTOS (ActionListener)
    // ========================================================

    /**
     * Maneja los clics en los botones del tablero.
     * Si la casilla está libre, presenta la pregunta al jugador.
     * Si responde bien, marca la casilla con su símbolo.
     * Si falla, registra el tiempo y pasa el turno.
     *
     * @param e El evento de acción generado por el botón pulsado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (juegoTerminado) return;

        JButton clickeado = (JButton) e.getSource();
        String texto = clickeado.getText();

        // Casilla ya ocupada
        if (texto.equals("X") || texto.equals("O")) {
            JOptionPane.showMessageDialog(this,
                "Esa casilla ya esta ocupada!\nElige otra posicion.",
                "Casilla Ocupada",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Registrar inicio del turno y mostrar pregunta
        tiempoInicioTurno = System.currentTimeMillis();
        boolean acierto = mostrarPregunta();

        if (acierto) {
            // Marcar la casilla seleccionada antes de la pregunta
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (botonesTablero[i][j] == clickeado) {
                        marcarCasilla(i, j);
                        return;
                    }
                }
            }
        } else {
            // Falló — registrar tiempo y pasar el turno
            long fin = System.currentTimeMillis();
            jugadorActual.sumarTiempo(fin - tiempoInicioTurno);
            cambiarTurno();
            if (jugadorActual == jugador1) {
                rondaActual++;
                gestorPreguntas.siguienteRonda();
            }
        }
    }
}