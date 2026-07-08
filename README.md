# Tres en Raya — Quiz de Programación Java

Juego de Tres en Raya con trivia educativa enfocada en **Programación Orientada a Objetos con Java**, estructuras de datos y Java Swing. Desarrollado como proyecto final del curso de Lenguaje de Programación I.

---

## Descripción

"Tres en Raya Quiz" combina el clásico juego de Tres en Raya con un sistema de preguntas de trivia. Para marcar una casilla en el tablero, el jugador debe responder correctamente una pregunta de programación. Si falla, pierde el turno y se muestra retroalimentación educativa.

---

## Características Principales

- **Registro de jugadores con avatar**: elige un emoji o carga tu propia foto
- **Tablero 3×3 interactivo**: X en azul eléctrico, O en rojo intenso
- **Banco de preguntas educativas**: OOP con Java, estructuras de datos y Java Swing
- **Retroalimentación educativa**: al fallar, se muestra la respuesta correcta y su explicación
- **Mensajes motivadores**: frases de ánimo tanto al acertar como al fallar
- **Sonidos MIDI**: melodías de éxito, error y victoria (sin archivos externos)
- **Desempate por tiempo de respuesta**: si hay empate en puntos, gana quien respondió más rápido
- **Interfaz oscura y moderna**: diseño con colores oscuros y componentes estilizados
- **Ventana de créditos**: autora, asesor, curso, año y licencia

---

## Tecnología

| Elemento | Detalle |
|---|---|
| Lenguaje | Java SE (JDK 8+) |
| GUI | Java Swing (JFrame, JPanel, JDialog...) |
| Sonido | javax.sound.midi (sin archivos externos) |
| Imágenes | javax.imageio.ImageIO |
| Datos | ArrayList para banco de preguntas |

---

## Estructura del Proyecto
 
```
TresEnRayaGUI/
│
├── Main.java              # Punto de entrada de la aplicación
├── VentanaJuego.java      # Ventana principal, lógica del juego y GUI
├── Jugador.java           # Clase que representa a un jugador
├── Pregunta.java          # Clase que representa una pregunta con explicación
├── GestorPreguntas.java   # Banco de preguntas (OOP, Swing, estructuras)
├── README.md              # Este archivo
├── .gitignore             # Archivos ignorados por Git
└── latex/                 # Directorio del informe del proyecto
    ├── informe.tex        # Archivo de informe técnico en formato LaTeX
    └── img/               # Capturas y recursos gráficos para el informe
```

---

## Compilación y Ejecución

### Requisitos
- Java Development Kit (JDK) 8 o superior
- Terminal / Símbolo del sistema

### Compilar
```bash
cd TresEnRayaGUI
javac -encoding UTF-8 *.java
```

### Ejecutar
```bash
java Main
```

---

## Temas del Banco de Preguntas

### Jugador 1 — Programación Orientada a Objetos con Java
1. Herencia (extends)
2. Polimorfismo y @Override
3. Encapsulación (private, getters, setters)
4. Interfaces (implements)
5. Palabra clave super()
6. Clases abstractas
7. Sobrecarga de métodos

### Jugador 2 — Java Swing y Estructuras de Datos
1. JFrame (ventana principal)
2. BorderLayout (5 zonas)
3. ArrayList vs LinkedList
4. ActionListener y eventos
5. HashMap (pares clave-valor)
6. JOptionPane
7. GridLayout

---

## Reglas del Juego

1. Cada jugador elige una casilla del tablero (1-9)
2. Aparece una pregunta de programación
3. Si responde **correctamente**: marca la casilla con su símbolo (X o O)
4. Si responde **incorrectamente**: pierde el turno, aprende la explicación
5. Gana quien logre **tres en línea** (fila, columna o diagonal)
6. Si el tablero se llena sin tres en línea: gana quien tenga **más puntos**
7. Si hay empate en puntos: gana quien tuvo el **menor tiempo de respuesta**

---

## Creditos

| Rol | Nombre |
|---|---|
| **Autora** | Carmen Valiente Quiroz |
| **Asesor** | Richart Escobedo Quispe |
| **Curso** | Lenguaje de Programación I |
| **Año** | 2026 |
| **Licencia** | MIT — Uso Educativo |

---

## Licencia MIT

```
Copyright (c) 2026 Carmen Valiente Quiroz

Se concede permiso, de forma gratuita, a cualquier persona que obtenga
una copia de este software y de los archivos de documentación asociados,
para utilizar el software sin restricciones, incluyendo sin limitación
los derechos de usar, copiar, modificar, fusionar, publicar, distribuir,
sublicenciar y/o vender copias del software, y para permitir a las personas
a quienes se les proporcione el software que lo hagan, sujeto a las
siguientes condiciones:

El aviso de copyright anterior y este aviso de permiso se incluirán en
todas las copias o partes sustanciales del software.

EL SOFTWARE SE PROPORCIONA "TAL CUAL", SIN GARANTÍA DE NINGÚN TIPO.
```
