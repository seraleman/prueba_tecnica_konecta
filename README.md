# Prueba Técnica Konecta

## Tabla de contenido

1. [Descripción](#descripción)
2. [Diagrama](#diagrama)
3. [Tecnologías](#tecnologías)
4. [Pruebas](#pruebas)  
&nbsp;4.1 [Proyecto](#proyecto)  
&nbsp;4.2 [Base de Datos](#base-de-datos)  
&nbsp;4.3 [Endpoints](#endpoints)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.1 [Catagoría](#categorías-category)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.1 [Producto](#productos-product)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.1 [Venta](#ventas-sale)
6. [Autor](#autor)

## Descripción

Módulo backend API REST creado en Spring Boot encargado de administrar los productos, sus stocks y las ventas de una cafetería.

Cuenta con tres entidades para lograr la solución:
- Categoría
- Producto
- Venta

Producto contiene a Catagoría en una relación de Muchas Categorías para Un Producto.  
Venta contiene a Producto en una relación de Un Producto para Una Venta.

## Diagrama

```
├── src
│   └── main
│       └── java
│           ├── ...
│               └── prueba_tecnica_java
│                   ├── controllers
│                       ├── CategoryRestController.java
│                       ├── ProductRestController.java
│                       └── SaleRestController.java
│                   ├── helpers
│                       ├── IResponse.java
│                       └── ResponseImpl.java
│                   ├── models
│                       ├── Category.java
│                       ├── Product.java
│                       └──Sale.java
│                   ├── repositories
│                       ├── ICategoryDao.java
│                       ├── IProductDao.java
│                       └── ISaleDao.java
│                   ├── services
│                       ├── CategoryServiceImpl.java
│                       ├── ICategoryService.java
│                       ├── IProductService.java
│                       ├── ISaleService.java
│                       ├── ProductServiceImpl.java
│                       └── SaleServiceImpl.java
|                   └── PruebaTecnicaJavaApplication.java
│           └── resources
|               ├── application.properties
|               └── import.sql
├── ...
├── pom.xml
└── README.md
```

## Tecnologías

- Java
- SpringBoot
- MySQL

## Pruebas

### Proyecto
Clonar el repositorio y abrirlo en el IDE favorito. Para "correrlo", si se está usando VSC es necesario tener los pluggins de Java y Spring Boot. También se puede ejecutar desde Intellij Idea o Eclipse a su respectiva manera.
Tener en cuenta revisar la conexión a la base de datos en local en el archivo _application.properties_, la cual debe de tener el usuario `...username=` y contraseña `...password=` propios del MySQL de cada computador. Las otras líneas del archivo pueden permanecer igual.

Al ser API REST es necesario un software como Postman o Thunder Client(pluggin de VSC) para probar cada uno de los endpoints y con ello la funcionalidad del módulo. Más adelante se describen los endpoints.

### Base de Datos

Base de datos en local.

- Nombre del host / IP `localhost`
- Usuario `<usuario personal de la BD>`
- Contraseña `<contraseña personal de la BD>`
- Base de datos `prueba_tecnica_konecta_Sergio_Manrique`
- Puerto `3306` o preferido

Se adjunta **[carpeta](https://drive.google.com/drive/folders/1RhLNDdTGisKDK5JkKrp2BPjluPAyHDVg?usp=sharing)** con cinco archivos:
- **script_creacion_base_de_datos_con_registros** (Aunque el proyecto está en `ddl-auto=create-drop` lo cual creará la base de datos cuando se "corra" el proyecto). También, en la inicialización del mismo se poblará la base de datos con los datos provenientes del archivo _import.sql_, los cuales servirán para la prueba.

- **consulta_producto_con_mas_stock** solicitada en la prueba.
- **consulta_producto_mas_vendido** solicitada en la prueba.

Consultas para facilitar la visualización del comportamiento de los datos en la base de datos cuando se crean, actualizan o eliminan ventas. Cada una de estas acciones repercute en el stock de los productos. **Sírvase verificarlo :)**
- **consulta_inventario**
- **consulta_ventas**

### **Endpoints**
A continuación enuncio los endpoints disponibles en el componente, dando por hecho que el puerto en el que se "levanta" el servidor de Spring Boot es el 8080 estarían listos para usar. De lo contrario, favor modificar el endpoint al respectivo puerto.

#### **Categorías (category)**

> Listar todas las categorías
>>Verbo Get  
`localhost:8080/category/` 

> Listar categoría por id
>>Verbo Get  
`localhost:8080/category/1`

> Crear categoría
>>Verbo Post  
`localhost:8080/category/`  
_Se debe pasar el objeto 'category'_
`{
    "name": "Frutos"
}`

> Actualizar categoría por id
>>Verbo Put  
`localhost:8080/category/1`  
_Se debe pasar el objeto 'category'_ `{
    "name": "Frutos secos"
}`

> Eliminar categoría por id  
>>Verbo Delete  
`localhost:8080/category/1`

#### **Productos (Product)**

>Listar todos los productos  
>>Verbo Get  
`localhost:8080/product/`


>Listar producto por id  
>>Verbo Get  
`localhost:8080/product/1`

>Crear producto  
>>Verbo Post  
`localhost:8080/product/`  
_Se debe pasar el objeto 'product'_
```
{
  "price": 800,
  "name": "Gancito",
  "created": "2022-06-15",
  "reference": "Bimbo",
  "weight": 80,
  "stock": 3,
  "category": {
    "id": 5
  }
}
```

>Actualizar producto por id  
>>Verbo Put  
`localhost:8080/product/10`  
_Se debe pasar el objeto 'product'_
```
{
  "price": 800,
  "name": "Gancito",
  "created": "2022-06-15",
  "reference": "Bimbo",
  "weight": 80,
  "stock": 3,
  "category": {
    "id": 5
  }
}
```

> Eliminar producto por id  
>>Verbo Delete  
`localhost:8080/product/10`  


### **Ventas (Sale)**

>Listar todas las ventas
>>Verbo Get  
`localhost:8080/sale/`

>Listar venta por id  
>>Verbo Get  
`localhost:8080/sale/1`

>Crear venta  
>>Verbo Post  
`localhost:8080/sale/`  
_Se debe pasar el objeto 'sale'_
```
{
  "created": "2015-06-14",
  "quantity": 2,
  "product": {
    "id": 3
  }
}
```

>Actualizar venta por id  
>>Verbo Put  
`localhost:8080/sale/5`  
_Se debe pasar el objeto 'sale'_
```
{
  "created": "2015-06-14",
  "quantity": 3,
  "product": {
    "id": 3
  }
}
```

> Eliminar venta por id
>>Verbo Delete  
`localhost:8080/sale/5`


## Autor

**[Sergio Manrique](https://www.linkedin.com/in/seraleman/)**