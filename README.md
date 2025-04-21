# Price Checker

**Price Checker** es una API REST desarrollada con **Spring Boot** que permite consultar el precio aplicable a un producto y una marca en una fecha determinada, basado en una lista de precios con diferentes niveles de prioridad.

## Tecnologías
- **Java 17**
- **Spring Boot 3.4.4**

### Dependencias/Librerías
- **H2 Database** – Base de datos relacional en memoria utilizada para desarrollo y pruebas.
- **JUnit 5** – Librería para pruebas unitarias y de integración.
- **Mockito** – Librería para mockeo de pruebas.
- **Rest-Assured** – Librería para pruebas de integración y validación de respuestas HTTP de servicios REST.
- **MapStruct** – Generador automático de mapeadores entre clases.
- **Maven** – Herramienta de gestión de dependencias y automatización del build.
- **Lombok** – Librería para reducir código repetitivo a través de anotaciones.


## Arquitectura
La aplicación implementa una **arquitectura Hexagonal (Ports and Adapters)** para mantener una separación clara entre el dominio y las capas externas, facilitando el mantenimiento, las pruebas y la escalabilidad.

## Uso
A través de un único endpoint, la aplicación permite filtrar los precios, recuperando aquellos que estén vigentes según los criterios de búsqueda proporcionados.

La búsqueda está diseñada para que la marca, el producto y la fecha sean parámetros opcionales.

En caso de no especificar la marca o el producto, se devolverán todos los precios aplicables para cada combinación marca/producto en la fecha indicada.

La fecha debe proporcionarse en el formato estándar ISO 8601. Si no se especifica, se tomará la fecha y hora del servidor por defecto.

También admite la paginación de resultados, lo que permite limitar la cantidad de datos devueltos en cada solicitud. Esto optimiza el rendimiento y mejora la experiencia del usuario al reducir los tiempos de carga y facilitar la navegación por grandes conjuntos de datos.

## Endpoint

### `GET /api/v1/price-checker/prices`

Permite consultar los precios aplicables según los parámetros proporcionados.

#### Parámetros opcionales

- `brand_id` (String): Identificador de la marca.
- `product_id` (String): Identificador del producto.
- `date` (LocalDateTime): Fecha en formato ISO 8601 (por ejemplo, `2020-06-14T10:00:00`).
- `size` (Integer): Tamaño de la página (10 por defecto).
- `page` (Integer): Número de página (0 por defecto)

#### Ejemplo de solicitud

```bash
curl -X GET "http://localhost:8080/api/v1/price-checker/prices?date=2020-06-14T16:00:00&brand_id=1&product_id=35455&size=1&page=0"
```

#### Ejemplo de respuesta

```json
[
  {
    "product_id": "35455",
    "brand_id": "1",
    "price_list": "2",
    "start_date": "2020-06-14T15:00:00",
    "end_date": "2020-06-14T18:30:00",
    "price": 25.45
  }
]
```
​