# 🎮 Tres en Raya: Edición Quiz (v2.0)

**Donde el conocimiento es tu mejor jugada**

<img width="832" height="982" alt="Captura de juego V 02" src="https://github.com/user-attachments/assets/3ce150ce-bb83-4d5b-b349-16c0d803d791" />


---

## 📖 ¿Qué es este juego?

Imagina combinar el clásico Tres en Raya con un quiz de programación en Java. No puedes marcar una casilla sin antes responder correctamente una pregunta sobre POO, Swing o estructuras de datos. ¡Eso es exactamente lo que hemos creado!

---

## ✨ Características (v2.0)

| Característica | Descripción |
|---|---|
| 🎯 Doble Desafío | Estrategia + Conocimiento = Victoria |
| 🧑‍🎨 Perfiles con Avatar | Elige entre 8 emojis ilustrados o sube tu propia foto |
| 📚 Preguntas Temáticas | Jugador 1: POO en Java · Jugador 2: Swing y Estructuras de Datos |
| 🎓 Retroalimentación Educativa | Al fallar, se explica automáticamente la respuesta correcta |
| 🔊 Sonido MIDI en tiempo real | Melodías generadas por código, sin archivos de audio externos |
| ⏱️ Desempate por Tiempo | Si hay empate en puntos, gana quien respondió más rápido |
| 📊 Puntuación Dinámica | +30 puntos por respuesta correcta |
| 🏆 Créditos del Proyecto | Ventana con autora, asesor, curso y licencia |

---

## 🕹️ Cómo Jugar

1. **Registro:** cada jugador ingresa su nombre y elige un avatar (emoji o foto propia).
2. **Selecciona una casilla** del 1 al 9.
3. **Responde la pregunta** de opción múltiple.
   - ✅ Correcta → la casilla es tuya, +30 puntos.
   - ❌ Incorrecta → pierdes el turno y ves la explicación educativa.
4. **Gana el juego:**
   - 🎯 Tres en línea (horizontal, vertical o diagonal) → victoria inmediata.
   - 📚 Tablero lleno sin ganador → gana quien tenga más puntos.
   - ⏱️ Empate en puntos → gana quien respondió más rápido en total.

---

## 🏗️ Arquitectura del Código

```
TresEnRayaGUI/
│
├── .project / .classpath / .gitignore   # Configuración del proyecto Eclipse
├── Main.java                            # Punto de entrada
├── VentanaJuego.java                    # Interfaz gráfica, sonido MIDI y lógica del juego
├── Jugador.java                         # Datos del jugador: nombre, puntos, tiempo, avatar
├── Pregunta.java                        # Pregunta + opciones + explicación educativa
└── GestorPreguntas.java                 # Banco de preguntas temático por jugador
```

---

## 🚀 Instalación y Ejecución

### Requisitos Previos
- Java JDK 17 o superior
- Windows / Linux / macOS

### Pasos

```bash
git clone https://github.com/tu-usuario/TresEnRayaGUI.git
cd TresEnRayaGUI

javac -encoding UTF-8 *.java

java Main
```

> ¿Ves errores de codificación? La bandera `-encoding UTF-8` es obligatoria: el código usa tildes, eñes y emojis.

---

## 📄 Informe Técnico (LaTeX)

El informe completo del proyecto, elaborado en LaTeX según el formato de la Universidad La Salle, incluye marco teórico, diseño (diagramas de clases y flujo actualizados a la v2.0), implementación comentada, análisis de complejidad Big O y rúbrica de autoevaluación.

👉 [Ver Informe Completo (PDF)](latex/informe.pdf)

---

## 🐛 Solución de Problemas Comunes

| Problema | Solución |
|---|---|
| `javac` no se reconoce | Instala Java JDK o configura el PATH |
| Caracteres extraños o emojis rotos | Compila con `javac -encoding UTF-8` |
| No suena la música | El sintetizador MIDI no está disponible en el sistema; el juego usa un beep como respaldo automático |
| No se puede marcar casilla | Verifica que la pregunta se respondió correctamente |

---

## 🎓 Para Estudiantes

**Conceptos que demuestra este proyecto:**
- Programación Orientada a Objetos (encapsulamiento, herencia, polimorfismo, abstracción)
- Interfaz gráfica con Swing (`JFrame`, `JDialog`, `JOptionPane`, `Graphics2D`)
- Manejo de eventos (`ActionListener`, expresiones lambda)
- Estructuras de datos (`ArrayList`, matrices bidimensionales)
- Concurrencia básica (hilo separado para el sonido MIDI)
- API de sonido de Java (`javax.sound.midi`)

---

## 👥 Créditos

- **Desarrolladora:** Carmen Aida Valiente Quiroz
- **Asesor:** Richart Escobedo Quispe
- **Curso:** Lenguaje de Programación I — Universidad La Salle
- **Licencia:** MIT — Uso educativo

---

## 📄 Licencia

```
MIT License - 2026
Puedes usar este código para lo que quieras,
solo intenta no hacer trampa en el juego 😉
```

> *"El conocimiento no ocupa espacio, pero en este juego, ¡te da la victoria!"*
