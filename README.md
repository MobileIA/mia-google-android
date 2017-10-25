#MobileIA Google

## Como usar

1. Configurar app en Firebase o Google Developer para obtener las Keys o el archivo de configuración requerido.
2. Agregar en build.gradle del proyecto: 
```gradle
classpath 'com.google.gms:google-services:3.0.0'
```
3. Agregar en el build.grade del app:
```gradle
apply plugin: 'com.google.gms.google-services'
```



* No olvidar: Configurar la huella digital de la firma con la que se compila.

1. Ingresar Consola de Google
2. Ir al proyecto
3. Ir a API y Servicios
4. Ir a Credenciales
5. En ID de clientes Oauth 2.0
6. Abrir la de Android
7. Configurar SHA1

