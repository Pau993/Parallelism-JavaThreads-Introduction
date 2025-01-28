## Escuela Colombiana de Ingeniería
### Arquitecturas de Software
### Introducción al paralelismo - hilos
## Integrantes
#### Paula Natalia Paez Vega
#### Manuel Felipe Barrera Barrera

## Introducción
En el presente repositorio se realiza un analisis de los hilos en Java, estos son unidades ligeras de ejecucion que permiten realizar multiples de manera concurrente dentro de un mismo programa, estos comparten el mismo espacio de memoria del proceso, a la vez que requieren mecanismos de sincronización para evitar problemas en su desarrollo.

**Parte I Hilos Java**

1. De acuerdo con lo revisado en las lecturas, complete las clases CountThread, para que las mismas definan el ciclo de vida de un hilo que imprima por pantalla los números entre A y B.
Explicación:
- En el constructor se reciben los limites A y B (A inferior del rango, B superior del rango) y se permite crear un hilo con un rango de numeros específico.
- En el método 'run()' se sobrescribe el método de la clase 'Thread', si A es mayor que B, intercambia los valores, imprime los números entre A y B (excluyendo estos mismos).
Fragmento de código:
![image](https://github.com/user-attachments/assets/1827a4e6-d447-44c4-8075-98d18778c8b8)

2. Complete el método __main__ de la clase CountMainThreads para que:
	1. Cree 3 hilos de tipo CountThread, asignándole al primero el intervalo [0..99], al segundo [99..199], y al tercero [200..299].
- Se genera el hilo con los valores y se predefinen con los rangos específicos.
	3. Inicie los tres hilos con 'start()'.
- Se inicializan los hilos respectivamente.
	4. Ejecute y revise la salida por pantalla.
- Salida:
![image](https://github.com/user-attachments/assets/488d2f13-61aa-44d4-be4a-fa5da5a90481)
	5. Cambie el incio con 'start()' por 'run()'. Cómo cambia la salida?, por qué?.
Fragmento de código:
![image](https://github.com/user-attachments/assets/f0d5f0af-2642-47b5-b42a-92460d66dd89)
- No existe paralelismo ya que se el código se ejecuta de forma secuencial, el tiempo de ejecución es similar a un solo hilo y se pierde el beneficio de concurrencia.

**Parte II Hilos Java**

Para este ejercicio se quiere calcular, en el menor tiempo posible, y en una sola máquina (aprovechando las características multi-core de la mismas) al menos el primer millón de dígitos de PI (en base 16). Para esto

1. Cree una clase de tipo Thread que represente el ciclo de vida de un hilo que calcule una parte de los dígitos requeridos.
Fragmento de código:
![image](https://github.com/user-attachments/assets/ce056140-3534-4511-8c83-6a2f9e886814)
- Se generan los atributos donde tendra su punto de inicio, la cantidad de dígitos a calcular y el array.
- El metodo run() se recalcula la suma cada DigitsPerSum iteraciones y se realiza el calculo del dígito hexadecimal.
3. Haga que la función PiDigits.getDigits() reciba como parámetro adicional un valor N, correspondiente al número de hilos entre los que se va a paralelizar la solución. Haga que dicha función espere hasta que los N hilos terminen de resolver el problema para combinar las respuestas y entonces retornar el resultado. Para esto, revise el método [join](https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html) del API de concurrencia de Java.
- Se realiza la modificación del metodo principal para soportar multiples hilos, calculando tambien el tamaño de cada chunk, con un arreglo para almacenar los hilos, creando e iniciando estos mismos.
Fragmento de código:
![image](https://github.com/user-attachments/assets/85ef3466-7ba6-4e64-9568-2b7ab7647742)
4. Ajuste las pruebas de JUnit, considerando los casos de usar 1, 2 o 3 hilos (este último para considerar un número impar de hilos!)
- En el ajuste de las pruebas unicamente se adecua para los diferentes hilos respectivamente adicionalmente se incluye una salida para ver el proceso de cada uno de los hilos.
Salida de las pruebas:
![image](https://github.com/user-attachments/assets/0a535c56-a71b-4df6-86d8-2dc718008798)

**Parte III Evaluación de Desempeño**

A partir de lo anterior, implemente la siguiente secuencia de experimentos para calcular el millon de dígitos (hex) de PI, tomando los tiempos de ejecución de los mismos (asegúrese de hacerlos en la misma máquina):

1. Un solo hilo.
2. Tantos hilos como núcleos de procesamiento (haga que el programa determine esto haciendo uso del [API Runtime](https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html)).
3. Tantos hilos como el doble de núcleos de procesamiento.
4. 200 hilos.
5. 500 hilos.
- Para la implementación se implementó 'PiDigitsPerformanceTests' donde se verifica el rendimiento con configuraciones variadas de hilos, primero se determina el numero de nucleos del procesador y se define un arreglo con 1 nucleo, doble nucleo, 200 y 500, luego se itera sobre estas configuraciones ejecutando el millón de gígitos hexadecimales de Pi. En cada configuración se mide el tiempo de ejecución con 'System.nanoTime()', convirtiendolo a milisegundos luego se imprimen los resultados con el tiempo total, los primeros y ultimos 10 dígitos hexadecimales, con el metodo 'bytesToHex()' se convierten los bytes a hexadecimal, para la visualización.
- Fragmento de código:
![image](https://github.com/user-attachments/assets/1d24380a-6424-48d5-a49f-e634ff976839)

Al iniciar el programa ejecute el monitor jVisualVM, y a medida que corran las pruebas, revise y anote el consumo de CPU y de memoria en cada caso.
- Para realizar el analisis, se modifca el codigo para poder evidenciar en diferentes momentos la cantidad de hilos con la que opera, se redujo a 10000 para poder obtener resultados, ya que con 1.000.000 no procesaba todo.
- Código modificado:
- ![image](https://github.com/user-attachments/assets/41cb2356-d615-409f-b278-714ad58af3bb)

- 1 hilo:
- ![image](https://github.com/user-attachments/assets/9149aa90-f116-4df1-a7bf-916fde6049d0)
- ![image](https://github.com/user-attachments/assets/20b1ccc7-f800-4470-a1a4-ebd388e83b93)

- 16 hilos (nucleos de procesamiento)
- ![image](https://github.com/user-attachments/assets/22aa7692-6a23-4eab-8ea7-644e05cb1ae9)
- ![image](https://github.com/user-attachments/assets/6181d088-7d24-4577-a80c-41297431abe4)

- 32 hilos
- ![image](https://github.com/user-attachments/assets/3e67cdc6-1b09-49ff-ab32-ce035bde70fd)
- ![image](https://github.com/user-attachments/assets/780a5ee7-86d9-4202-aab4-973162fa9ce8)

- 200 hilos
- ![image](https://github.com/user-attachments/assets/6cafd32a-0668-44c3-afb5-75138f353923)
- ![image](https://github.com/user-attachments/assets/92934f92-0068-42d0-826b-f80e206f0a5a)

- 500 hilos
- ![image](https://github.com/user-attachments/assets/f442dc8e-1aa5-49ae-8f32-30927ca968cf)
- ![image](https://github.com/user-attachments/assets/dce91da6-31b2-48c8-9901-7299ecd14c4d)

Con lo anterior, y con los tiempos de ejecución dados, haga una gráfica de tiempo de solución vs. número de hilos. Analice y plantee hipótesis con su compañero para las siguientes preguntas (puede tener en cuenta lo reportado por jVisualVM):



1. Según la [ley de Amdahls](https://www.pugetsystems.com/labs/articles/Estimating-CPU-Performance-using-Amdahls-Law-619/#WhatisAmdahlsLaw?):

	![](img/ahmdahls.png), donde _S(n)_ es el mejoramiento teórico del desempeño, _P_ la fracción paralelizable del algoritmo, y _n_ el número de hilos, a mayor _n_, mayor debería ser dicha mejora. Por qué el mejor desempeño no se logra con los 500 hilos?, cómo se compara este desempeño cuando se usan 200?. 

2. Cómo se comporta la solución usando tantos hilos de procesamiento como núcleos comparado con el resultado de usar el doble de éste?.

3. De acuerdo con lo anterior, si para este problema en lugar de 500 hilos en una sola CPU se pudiera usar 1 hilo en cada una de 500 máquinas hipotéticas, la ley de Amdahls se aplicaría mejor?. Si en lugar de esto se usaran c hilos en 500/c máquinas distribuidas (siendo c es el número de núcleos de dichas máquinas), se mejoraría?. Explique su respuesta.



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
