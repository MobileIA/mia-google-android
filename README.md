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

