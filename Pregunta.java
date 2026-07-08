/**
 * Clase que representa una pregunta de trivia del juego.
 * Contiene el texto, las tres opciones, la respuesta correcta
 * y una explicación educativa para retroalimentar al jugador cuando falla.
 *
 * @author Carmen Valiente Quiroz
 * @version 2.0
 */
public class Pregunta {

    private String texto;             // Enunciado de la pregunta
    private String[] opciones;        // Las tres opciones de respuesta
    private int respuestaCorrecta;    // Índice 0-based de la opción correcta
    private String explicacion;       // Explicación educativa para refuerzo

    /**
     * Constructor completo de una pregunta con retroalimentación.
     *
     * @param texto           Enunciado de la pregunta
     * @param opcion1         Primera opción de respuesta
     * @param opcion2         Segunda opción de respuesta
     * @param opcion3         Tercera opción de respuesta
     * @param respuestaCorrecta Número de la opción correcta (1, 2 o 3)
     * @param explicacion     Explicación educativa mostrada al fallar
     */
    public Pregunta(String texto, String opcion1, String opcion2, String opcion3,
                    int respuestaCorrecta, String explicacion) {
        this.texto = texto;
        this.opciones = new String[]{opcion1, opcion2, opcion3};
        this.respuestaCorrecta = respuestaCorrecta - 1; // Convertir a índice 0-based
        this.explicacion = explicacion;
    }

    // ===== GETTERS =====

    /** @return Texto/enunciado de la pregunta */
    public String getTexto()          { return texto; }

    /** @return Array con las tres opciones de respuesta */
    public String[] getOpciones()     { return opciones; }

    /** @return Índice 0-based de la respuesta correcta */
    public int getRespuestaCorrecta() { return respuestaCorrecta; }

    /** @return Explicación educativa de la respuesta correcta */
    public String getExplicacion()    { return explicacion; }

    /**
     * Verifica si la opción seleccionada por el jugador es la correcta.
     *
     * @param respuesta Índice 0-based de la opción elegida
     * @return true si es correcta, false si no
     */
    public boolean esCorrecta(int respuesta) {
        return respuesta == respuestaCorrecta;
    }
}