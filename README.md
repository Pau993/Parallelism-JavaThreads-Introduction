## Escuela Colombiana de Ingeniería
### Arquitecturas de Software
### Introducción al paralelismo - hilos
## Integrantes
#### Paula Natalia Paez Vega
#### Manuel Felipe Barrera Barrera

## Introducción
En el presente repositorio se realiza un análisis de los hilos en Java. Estos son unidades ligeras de ejecución que permiten realizar múltiples tareas de manera concurrente dentro de un mismo programa. Comparten el mismo espacio de memoria del proceso, requiriendo mecanismos de sincronización para evitar problemas en su desarrollo.

**Parte I: Hilos en Java**

1. De acuerdo con lo revisado en las lecturas, complete las clases `CountThread` para que definan el ciclo de vida de un hilo que imprima por pantalla los números entre A y B.
   - **Explicación**: En el constructor se reciben los límites A y B (A inferior del rango, B superior del rango), permitiendo crear un hilo con un rango específico.
   - En el método `run()`, se sobrescribe el método de la clase `Thread`. Si A es mayor que B, se intercambian los valores e imprime los números entre A y B (excluyendo estos mismos).

   **Fragmento de código**:
   ![image](https://github.com/user-attachments/assets/1827a4e6-d447-44c4-8075-98d18778c8b8)

2. Complete el método `main` de la clase `CountMainThreads` para que:
   1. Cree 3 hilos de tipo `CountThread`, asignándole al primero el intervalo [0..99], al segundo [99..199] y al tercero [200..299].
   2. Inicie los tres hilos con `start()`.
   3. Ejecute y revise la salida por pantalla.

   **Salida**:
   ![image](https://github.com/user-attachments/assets/488d2f13-61aa-44d4-be4a-fa5da5a90481)

   4. Cambie el inicio con `start()` por `run()`. ¿Cómo cambia la salida y por qué?
   - No existe paralelismo, ya que el código se ejecuta de forma secuencial. El tiempo de ejecución es similar a un solo hilo, perdiendo el beneficio de concurrencia.

   **Fragmento de código**:
   ![image](https://github.com/user-attachments/assets/f0d5f0af-2642-47b5-b42a-92460d66dd89)

**Parte II: Hilos en Java**

Para este ejercicio se quiere calcular, en el menor tiempo posible, al menos el primer millón de dígitos de PI (en base 16) en una sola máquina, aprovechando las características multi-core.

1. Cree una clase de tipo `Thread` que represente el ciclo de vida de un hilo que calcule una parte de los dígitos requeridos.
   **Fragmento de código**:
   ![image](https://github.com/user-attachments/assets/ce056140-3534-4511-8c83-6a2f9e886814)
   - Se generan los atributos donde tendrá su punto de inicio, la cantidad de dígitos a calcular y el array.
   - El método `run()` recalcula la suma cada `DigitsPerSum` iteraciones y realiza el cálculo del dígito hexadecimal.

3. Haga que la función `PiDigits.getDigits()` reciba como parámetro adicional un valor N, correspondiente al número de hilos entre los que se va a paralelizar la solución. Haga que dicha función espere hasta que los N hilos terminen de resolver el problema para combinar las respuestas y retornar el resultado.
   - Se modifica el método principal para soportar múltiples hilos, calculando el tamaño de cada chunk y creando un arreglo para almacenar los hilos.

   **Fragmento de código**:
   ![image](https://github.com/user-attachments/assets/85ef3466-7ba6-4e64-9568-2b7ab7647742)

4. Ajuste las pruebas de JUnit, considerando los casos de usar 1, 2 o 3 hilos (este último para considerar un número impar de hilos).
   - En el ajuste de las pruebas se adecúa para los diferentes hilos, además de incluir una salida para ver el proceso de cada uno de los hilos.

   **Salida de las pruebas**:
   ![image](https://github.com/user-attachments/assets/0a535c56-a71b-4df6-86d8-2dc718008798)

**Parte III: Evaluación de Desempeño**

A partir de lo anterior, implemente la siguiente secuencia de experimentos para calcular el millón de dígitos (hex) de PI, tomando los tiempos de ejecución:

1. Un solo hilo.
2. Tantos hilos como núcleos de procesamiento (haga que el programa determine esto usando el [API Runtime](https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html)).
3. Tantos hilos como el doble de núcleos de procesamiento.
4. 200 hilos.
5. 500 hilos.

   - Para la implementación se creó `PiDigitsPerformanceTests`, donde se verifica el rendimiento con configuraciones variadas de hilos. Se determina el número de núcleos del procesador y se define un arreglo con 1 núcleo, doble núcleo, 200 y 500. Luego se itera sobre estas configuraciones, ejecutando el millón de dígitos hexadecimales de Pi. En cada configuración se mide el tiempo de ejecución con `System.nanoTime()`, convirtiéndolo a milisegundos y luego se imprimen los resultados.

   **Fragmento de código**:
   ![image](https://github.com/user-attachments/assets/1d24380a-6424-48d5-a49f-e634ff976839)

Al iniciar el programa, ejecute el monitor jVisualVM y anote el consumo de CPU y de memoria en cada caso.

- **Código modificado**:
  ![image](https://github.com/user-attachments/assets/41cb2356-d615-409f-b278-714ad58af3bb)

**Resultados de Ejecución**:

- **1 hilo**:
  
  ![image](https://github.com/user-attachments/assets/9149aa90-f116-4df1-a7bf-916fde6049d0)
  ![image](https://github.com/user-attachments/assets/20b1ccc7-f800-4470-a1a4-ebd388e83b93)

- **16 hilos (núcleos de procesamiento)**:
  
  ![image](https://github.com/user-attachments/assets/22aa7692-6a23-4eab-8ea7-644e05cb1ae9)
  ![image](https://github.com/user-attachments/assets/6181d088-7d24-4577-a80c-41297431abe4)

- **32 hilos**:
  
  ![image](https://github.com/user-attachments/assets/3e67cdc6-1b09-49ff-ab32-ce035bde70fd)
  ![image](https://github.com/user-attachments/assets/780a5ee7-86d9-4202-aab4-973162fa9ce8)

- **200 hilos**:
  
  ![image](https://github.com/user-attachments/assets/6cafd32a-0668-44c3-afb5-75138f353923)
  ![image](https://github.com/user-attachments/assets/92934f92-0068-42d0-826b-f80e206f0a5a)

- **500 hilos**:
  
  ![image](https://github.com/user-attachments/assets/f442dc8e-1aa5-49ae-8f32-30927ca968cf)
  ![image](https://github.com/user-attachments/assets/dce91da6-31b2-48c8-9901-7299ecd14c4d)

Con lo anterior y los tiempos de ejecución dados, se realiza una gráfica de tiempo de solución vs. número de hilos.

**Gráfica tiempo de solución vs. número de hilos**:
- ![image](https://github.com/user-attachments/assets/50093396-f49d-498f-9cca-338779f58e4b)

### Análisis de Resultados

1. **Ley de Amdahl**:
   - Según la [ley de Amdahl](https://www.pugetsystems.com/labs/articles/Estimating-CPU-Performance-using-Amdahls-Law-619/#WhatisAmdahlsLaw?), el mejor desempeño no se logra con 500 hilos debido a la sobrecarga en la gestión de hilos. Esto provoca cuellos de botella por competencia por recursos. El mejor desempeño se logra con 32 hilos (335 ms), y con 200 se degrada levemente (352 ms), mientras que con 500 hilos el tiempo aumenta (382 ms).

2. **Comparación de núcleos vs. hilos**:
   - Con 16 núcleos (tiempo de ejecución 411 ms) y el doble (32 hilos, 335 ms), se muestra ineficiencia con 16 hilos debido a la competencia por recursos.

3. **Uso de múltiples máquinas**:
   - Ejecutar 500 hilos en una máquina no es eficiente. Utilizar múltiples máquinas distribuyendo el trabajo puede mejorar el rendimiento.

#### Criterios de evaluación.

1. Funcionalidad:
	- El problema fue paralelizado (el tiempo de ejecución se reduce y el uso de los núcleos aumenta), y permite parametrizar el número de hilos usados simultáneamente.

2. Diseño:
	- La signatura del método original sólo fue modificada con el parámetro original, y en el mismo debe quedar encapsulado la paralelización e inicio de la solución, y la sincronización de la finalización de la misma.
	- Las nuevas pruebas con sólo UN hilo deben ser exactamente iguales a las originales, variando sólo el parámetro adicional. Se incluyeron pruebas con hilos adicionales, y las mismas pasan.
	- Se plantea un método eficiente para combinar los resultados en el orden correcto (iterar sobre cada resultado NO sera eficiente).

3. Análisis.
	- Se deja evidencia de la realización de los experimentos.
	- Los análisis realizados son consistentes.
