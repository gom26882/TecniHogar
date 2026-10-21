# TecniHogar
## Descripción

TecniHogar es un programa desarrollado en Java que permite administrar órdenes de servicios técnicos a domicilio.

El programa utiliza herencia y polimorfismo para poder manejar diferentes tipos de servicios y calcular sus costos

## Funcionalidades

- Cotizar y registrar órdenes de servicio.
- Registrar cuadrillas de trabajo.
- Crear jornadas y asignar órdenes.
- Retirar órdenes de una jornada.
- Registrar visitas completadas o fallidas.
- Calcular costos y cargos por visitas adicionales.
- Consultar órdenes, cuadrillas y reportes.

## Tipos de servicio

- Diagnóstico.
- Instalación.
- Instalación eléctrica certificada.
- Mantenimiento de aire acondicionado.

## Estructura del proyecto

El programa está organizado en los siguientes paquetes:

- `model`: contiene las clases de las órdenes, cuadrillas, jornadas e ingresos.
- `controller`: administra las operaciones del sistema.
- `view`: contiene el menú de consola.
- `constants`: contiene las enumeraciones utilizadas.
- `Main.java`: inicia el programa.

## Cómo ejecutar el programa

1. Descargar o clonar el repositorio.
2. Abrir la carpeta del proyecto en Visual Studio Code.
3. Tener instalado Java JDK.
4. Ejecutar el archivo `Main.java`.
5. Utilizar el menú de consola para interactuar con el programa.


## Datos iniciales

El programa inicia con 8 órdenes de servicio y 3 cuadrillas para realizar pruebas.

Estos datos no generan ingresos al iniciar el sistema.

