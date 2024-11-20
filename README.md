
# Documentación del Proyecto E-commerce

## Especificaciones Técnicas del Proyecto

- **Java**: 17  
- **Spring Boot**: 3.3.0  
- **Maven**: 3.0  
- **Base de Datos**: MySQL 8.0  

## Prerrequisitos

- **MongoDB**  
- **Maven** 3.0  
- **JDK** 17  
- **Editor de Texto**: Visual Studio Code o IDE preferido  
- **Docker**  

## Configuración de Docker y Maven

### Clonación del Repositorio

```bash
git clone https://github.com/sebassuaza98/backend_ecommers.git
```

Una vez clonado el repositorio, abre tu IDE de preferencia, carga el proyecto y en la raíz del proyecto ejecuta el siguiente comando para construirlo:

### Construcción del Proyecto

```bash
mvn clean install
```

### Ejecutar Pruebas Unitarias

Para ejecutar las pruebas unitarias, ubícate en la raíz del proyecto y ejecuta el siguiente comando:

```bash
mvn test
```

## Documentación del Proyecto E-commerce

### 1. Descripción del Proyecto

Este proyecto es una plataforma de e-commerce desarrollada en Java, que permite a los usuarios gestionar inventario de productos, realizar órdenes, modificar stocks, y editar productos. La aplicación incluye funcionalidades de autenticación y registro de usuario.

**Características principales**:
- Registro e inicio de sesión de usuarios.
- Visualización de catálogo de productos.
- Creación de órdenes.
- Auditorías y reportes.
- Administración de inventario y gestión de productos.

### 2. Tecnologías Usadas

- **Backend**: Java 17, Spring Boot (Spring Data JPA, Spring Security, Spring Web)
- **Base de Datos**: MySQL para el almacenamiento de datos persistentes.
- **Frontend**: Angular para la interfaz de usuario

### 3. Arquitectura del Sistema

Este proyecto sigue el patrón de arquitectura **MVC** (Modelo-Vista-Controlador). La comunicación entre las capas de la aplicación se realiza de la siguiente manera:

- **Controladores**: Manejan las solicitudes HTTP y responden a las solicitudes del usuario.
- **Servicios**: Contienen la lógica de negocio.
- **Repositorios**: Interactúan con la base de datos utilizando JPA.

### 4. Modelo de Base de Datos

**Diagrama Entidad-Relación (ER)**:  
El diagrama de base de datos incluye las tablas y sus relaciones: `audit_log`, `customer`, `discount`, `orders`, `order_item`, `product`, `session`, y `user`.

### 5. Manual de Instalación y Configuración

#### Requisitos previos

- **Java**: 17  
- **Maven**: Para la construcción del proyecto.
- **MySQL**: Base de datos utilizada para el almacenamiento.

#### Configuración del entorno

1. Clona el repositorio del proyecto:

   ```bash
   git clone <URL-del-repositorio>
   ```

2. Instala las dependencias con Maven:

   ```bash
   mvn clean install
   ```

3. Configura la base de datos en el archivo `application.properties`:

   ```properties
      spring.application.name=ecommerce
      spring.datasource.url=jdbc:mysql://34.55.55.115:3306/ecommers
      spring.datasource.username=root
      spring.datasource.password=12345
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
      spring.profiles.active=dev

   ```

4. Ejecución de la aplicación:

   ```bash
   mvn spring-boot:run
   ```

   
# Documentación de los Endpoints

## 1. Registro de Usuario

- **URL**: http://localhost:8080/register
- **Método**: POST
- **Descripción**: Registra un nuevo usuario en el sistema.
- **Body** (Request):
  ```json
  {
    "userId": "string",
    "password": "string",
    "email": "string",
    "fullName": "string"
  }
  ```
- **Respuesta**:
  - **Código 201** (Creado): Si el registro es exitoso.
  - **Código 400** (Solicitud Incorrecta): Si los datos proporcionados son inválidos.
  - **Código 500** (Error Interno): Si ocurre un error en el servidor.

## 2. Login de Usuario

- **URL**: http://localhost:8080/auth/login
- **Método**: POST
- **Descripción**: Inicia sesión en el sistema con las credenciales del usuario.
- **Body** (Request):
  ```json
  {
    "userId": "string",
    "password": "string"
  }
  ```
- **Respuesta**:
  - **Código 200** (OK): Si el login es exitoso, se devuelve un token de autenticación.
  - **Código 400** (Solicitud Incorrecta): Si las credenciales son incorrectas.

## 3. Creación de Orden

- **URL**: http://localhost:8080/api/create
- **Método**: POST
- **Descripción**: Crea una nueva orden en el sistema.
- **Body** (Request):
  ```json
  {
    "customer": "string",
    "orderItemDTOs": [
      {
        "productId": "string",
        "quantity": 1
      }
    ],
    "randomOrder": true
  }
  ```
- **Respuesta**:
  - **Código 201** (Creado): Si la orden se crea exitosamente.
  - **Código 500** (Error Interno): Si ocurre un error en el servidor.

## 4. Creación de Producto

- **URL**: http://localhost:8080/api/products
- **Método**: POST
- **Descripción**: Crea un nuevo producto en el sistema.
- **Body** (Request):
  ```json
  {
    "name": "string",
    "price": 100.0,
    "stock": 50
  }
  ```
- **Respuesta**:
  - **Código 201** (Creado): Si el producto es creado exitosamente.
  - **Código 500** (Error Interno): Si ocurre un error en el servidor.

## 5. Obtener Todos los Productos

- **URL**: http://localhost:8080/api/getProducts
- **Método**: GET
- **Descripción**: Obtiene todos los productos disponibles en el sistema.
- **Respuesta**:
  - **Código 200** (OK): Si los productos se obtienen exitosamente.
  - **Código 500** (Error Interno): Si ocurre un error en el servidor.

## 6. Actualización de Producto

- **URL**: http://localhost:8080/api/update
- **Método**: PUT
- **Descripción**: Actualiza un producto existente en el sistema.
- **Body** (Request):
  ```json
  {
    "id": "string",
    "name": "string",
    "price": 150.0,
    "stock": 40
  }
  ```
- **Respuesta**:
  - **Código 200** (OK): Si el producto se actualiza exitosamente.
  - **Código 404** (No Encontrado): Si no se encuentra el producto a actualizar.
  - **Código 500** (Error Interno): Si ocurre un error en el servidor.
