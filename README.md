
# 🎮 Tres en Raya: Edición Élite

**Donde el conocimiento es tu mejor jugada**



  <img width="792" height="917" alt="Captura de pantalla juego" src="https://github.com/user-attachments/assets/52de534f-a122-471e-a82c-f34096c4e9bb" />


---
**VIDEO DE EJECUCION DEL JUEGO**
https://youtu.be/Luj4Q_-Iunc?si=Wci4dsoJTZQXgQ0y

---
## 📖 ¿Qué es este juego?

Imagina combinar el clásico Tres en Raya que conoces desde niño con un quiz de preguntas que pondrá a prueba tu inteligencia. ¡Eso es exactamente lo que hemos creado!

🧠 **La premisa es simple pero desafiante:** no puedes marcar una casilla sin antes responder correctamente una pregunta. ¿Crees que tienes lo que se necesita?

---

## ✨ Características Únicas

| Característica | Descripción |
|---|---|
| 🎯 Doble Desafío | Estrategia + Conocimiento = Victoria |
| 📚 20+ Preguntas | Cada jugador tiene su propio banco de preguntas |
| 🎨 Diseño Premium | Interfaz moderna con efectos visuales |
| 🔄 Sistema de Revancha | ¿Perdiste? ¡Juega de nuevo inmediatamente! |
| 📊 Puntuación Dinámica | +30 puntos por respuesta correcta |
| 🏆 Dos formas de ganar | Tres en línea o mayor puntuación |

---

## 🕹️ Cómo Jugar

### Paso 1: Registro de Jugadores
Ingresa el nombre de cada jugador antes de comenzar:
- **Jugador 1 (X)**
- **Jugador 2 (O)**

### Paso 2: El Tablero

```
 1 | 2 | 3
-----------
 4 | 5 | 6
-----------
 7 | 8 | 9
```

### Paso 3: Selecciona una Casilla
1. Haz clic en cualquier número del 1 al 9.
2. Responde la pregunta que aparece.
3. Si aciertas → ¡la casilla es tuya!
4. Si fallas → ¡pierdes el turno!

### Paso 4: Gana el Juego
- 🎯 **Victoria Rápida:** consigue 3 en línea (horizontal, vertical o diagonal).
- 📚 **Victoria por Puntos:** si el tablero se llena sin ganador, gana quien tenga más puntos.

---

## 🧪 Ejemplo de Preguntas

**Para el Jugador 1 (X)**

> 🔍 **Pregunta:** ¿Quién es el padre de la POO?
> 1. Alan Turing
> 2. **Alan Kay** ✓ ¡Correcta! +30 puntos
> 3. Bill Gates

**Para el Jugador 2 (O)**

> 🧩 **Acertijo:** ¿Qué tiene teclas pero no puede abrir ninguna puerta?
> 1. **Un piano** ✓ ¡Correcta! +30 puntos
> 2. Un candado roto
> 3. Un mapa

---

## 📄 Informe Técnico (LaTeX)

El informe completo del proyecto, elaborado en LaTeX según el formato de la Universidad La Salle, incluye marco teórico, diseño (diagramas de clases y flujo), implementación comentada línea por línea, análisis de complejidad Big O y rúbrica de autoevaluación.

👉 [Ver Informe Completo (PDF)] [Tres_En_Raya_omegaup__latex__Aida_ (1) (1).pdf](https://github.com/user-attachments/files/29608248/Tres_En_Raya_omegaup__latex__Aida_.1.1.pdf)




---

## 🏗️ Arquitectura del Código

```
TresEnRayaGUI/
│
├── 🎮 Main.java              # Punto de entrada
├── 🖼️ VentanaJuego.java      # Interfaz gráfica completa
├── 👤 Jugador.java           # Datos de cada jugador
├── ❓ Pregunta.java          # Estructura de preguntas
└── 📚 GestorPreguntas.java   # Banco de preguntas
```

---

## 🚀 Instalación y Ejecución

### Requisitos Previos
- Java JDK 17 o superior
- Windows / Linux / macOS

### Instalación Paso a Paso

**1. Clona o descarga el proyecto**
```bash
git clone https://github.com/tu-usuario/TresEnRayaGUI.git
cd TresEnRayaGUI
```

**2. Compila el código**
```bash
javac -encoding UTF-8 *.java
```
> ¿Ves errores de codificación? Usa `-encoding UTF-8` para los caracteres especiales.

**3. ¡A Jugar!**
```bash
java Main
```

### Script Rápido para Windows
```batch
@echo off
echo Iniciando Tres en Raya: Edicion Elite...
javac -encoding UTF-8 *.java
if %errorlevel% equ 0 (
    java Main
) else (
    echo Error en compilacion
    pause
)
```

---

## 🎨 Personalización

**Cambiar colores** (en `VentanaJuego.java`)
```java
panelSuperior.setBackground(new Color(240, 248, 255)); // AliceBlue
botonesTablero[i][j].setBackground(Color.WHITE);
```

**Agregar nuevas preguntas** (en `GestorPreguntas.java`)
```java
preguntasJugador1.add(new Pregunta(
    "¿Tu pregunta aquí?",
    "Opción 1",
    "Opción 2",
    "Opción 3",
    2  // Número de respuesta correcta (1-3)
));
```

**Cambiar puntos por acierto** (en `VentanaJuego.java`, método `mostrarPregunta()`)
```java
jugadorActual.sumarPuntos(50);  // Cambia 30 por 50
```

---

## 🐛 Solución de Problemas Comunes

| Problema | Solución |
|---|---|
| `javac` no se reconoce | Instala Java JDK o configura el PATH |
| Caracteres extraños (�) | Compila con `javac -encoding UTF-8` |
| La ventana no se ve completa | Ajusta `setSize(650, 750)` |
| No se puede marcar casilla | Verifica que la pregunta se respondió correctamente |

---

## 🎯 Estrategias para Ganar

**Para Principiantes**
- 🎯 Prioriza el centro (casilla 5): ofrece más combinaciones ganadoras.
- 📚 Estudia las preguntas: son las mismas en cada ronda.
- 🔄 Controla el ritmo: responde con calma, no hay límite de tiempo.

**Para Expertos**
- 🧠 Memoriza respuestas: después de 3 rondas, las preguntas se repiten.
- 🎭 Bloquea al oponente: mira hacia dónde va y bloquea sus líneas.
- ⚡ Acierta siempre: el conocimiento es poder, ¡no falles!

---

## 📈 Próximas Características (Roadmap)

- 🎵 Efectos de sonido al marcar casilla y al ganar
- ⏱️ Modo contrarreloj (10 segundos por pregunta)
- 🌈 Temas visuales (modo oscuro/claro)
- 🏆 Sistema de logros y récords
- 🤖 Modo vs IA
- 📱 Versión móvil (JavaFX)

---

## 🤝 Contribuciones

¿Quieres mejorar el juego? ¡Eres bienvenido!

1. Haz un fork del proyecto
2. Crea tu rama: `git checkout -b feature/NuevaFuncionalidad`
3. Haz commit de tus cambios: `git commit -m 'Agrega algo increíble'`
4. Sube la rama: `git push origin feature/NuevaFuncionalidad`
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT — siéntete libre de usarlo, modificarlo y compartirlo.

```
MIT License - 2024
Puedes usar este código para lo que quieras,
solo intenta no hacer trampa en el juego 😉
```

---

## 👥 Créditos

- **Desarrolladora:** Aida Valiente Quiroz
- **Inspiración:** Proyecto universitario + amor por los juegos de mesa
- **Preguntas:** Cultura general, historia y algo de humor
- **Beta testers:** Compañeros de clase

---

## 🎓 Para Estudiantes

Si este es tu proyecto universitario, estos son los conceptos que demuestra:

**Conceptos incluidos**
- Programación Orientada a Objetos
- Interfaz gráfica con Swing
- Manejo de eventos
- Estructuras de datos (ArrayList, matrices)
- Encapsulamiento y herencia

**Puntos extra que puedes agregar**
- Patrón de diseño MVC
- Serialización para guardar partidas
- JUnit para pruebas unitarias
- Documentación JavaDoc

---

## 🏁 ¡Listo para Jugar!

```bash
# Compila
javac -encoding UTF-8 *.java

# Ejecuta
java Main
```

¡Diviértete y demuestra quién sabe más! Hecho con ☕ y mucho código en Java.

> *"El conocimiento no ocupa espacio, pero en este juego, ¡te da la victoria!"*
