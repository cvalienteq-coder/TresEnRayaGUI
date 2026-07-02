/**
 * Clase que representa una pregunta del juego
 * Contiene el texto, las opciones y la respuesta correcta
 */
public class Pregunta {
    private String texto;           // El texto de la pregunta
    private String[] opciones;      // Array de 3 opciones
    private int respuestaCorrecta;  // Índice de la opción correcta (0, 1 o 2)
    
    /**
     * Constructor para crear una pregunta
     * @param texto El enunciado de la pregunta
     * @param opcion1 Primera opción
     * @param opcion2 Segunda opción
     * @param opcion3 Tercera opción
     * @param respuestaCorrecta Número de la opción correcta (1, 2 o 3)
     */
    public Pregunta(String texto, String opcion1, String opcion2, String opcion3, int respuestaCorrecta) {
        this.texto = texto;
        this.opciones = new String[3];  // Crear array de 3 posiciones
        this.opciones[0] = opcion1;      // Índice 0 = opción 1
        this.opciones[1] = opcion2;      // Índice 1 = opción 2
        this.opciones[2] = opcion3;      // Índice 2 = opción 3
        this.respuestaCorrecta = respuestaCorrecta - 1;  // Convertir a índice (1→0, 2→1, 3→2)
    }
    
    // Getters
    public String getTexto() { return texto; }
    public String[] getOpciones() { return opciones; }
    public int getRespuestaCorrecta() { return respuestaCorrecta; }
    
    /**
     * Verifica si la respuesta es correcta
     * @param respuesta Índice de la respuesta seleccionada (0, 1, 2)
     * @return true si es correcta, false si no
     */
    public boolean esCorrecta(int respuesta) {
        return respuesta == respuestaCorrecta;
    }
}