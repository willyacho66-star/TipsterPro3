TIPSTER PRO 3 — Android
=======================

Qué hace
--------
- Carga automáticamente los partidos de HOY y MAÑANA.
- Filtra únicamente las competiciones solicitadas.
- Muestra 3 pronósticos al costado de cada partido:
  1) Principal
  2) Secundario
  3) Tercera opción
- NO usa hándicap.
- NO crea combinadas.
- NO fuerza un mercado concreto.
- Mercados contemplados: ganador/1X2, doble oportunidad, más/menos goles y BTTS.
- Al tocar un partido, amplía el análisis con forma reciente, local/visitante, H2H y fuentes.

Fuente de datos
---------------
La versión 1.0 usa API-Football (API-Sports):
https://www.api-football.com/

Necesitas una API key propia. La app te la pide la primera vez y la guarda localmente
en el dispositivo. No está incluida dentro del código para que siga siendo privada.

IMPORTANTE SOBRE LA API
-----------------------
API-Football cubre más de 1.200 ligas y copas, pero la disponibilidad de estadísticas,
H2H, predicciones y lesiones puede variar por competición/temporada. La app maneja
"datos no disponibles" sin inventarlos.

Cómo compilar el APK
--------------------
Método Android Studio:
1. Abre esta carpeta en Android Studio.
2. Espera la sincronización de Gradle.
3. Build > Build APK(s).
4. APK: app/build/outputs/apk/debug/app-debug.apk

Método GitHub Actions:
1. Sube esta carpeta a un repositorio de GitHub.
2. Ve a Actions > Build APK.
3. Ejecuta el workflow.
4. Descarga el artefacto "TipsterPro3-APK".

Propiedad
---------
El código de esta carpeta se entrega para tu uso y modificación. Los datos de fútbol
siguen sujetos a los términos/licencia del proveedor API-Football.

Nota de precisión
-----------------
Los pronósticos son probabilísticos. Ningún software puede garantizar un resultado
deportivo "sí o sí".
