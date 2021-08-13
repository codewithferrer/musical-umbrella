# musical-umbrella

Comprobaciones de seguridad, entre las que se chequea lo siguiente:
- Si se puede hacer debug
- Si tenemos el debugger activado
- Se comprueba el nombre o packagename de la aplicación
- Se comprueba la firma de la aplicación
- Se comprueba si el instalador es la tienda de Google o Amazon
- Si se ejecuta desde un emulador
- Si se lanza desde un dispositivo rooteado

Uso
-----

1. En el `onCreate` de la clase `Application` crea una instancia de la clase `Security`. 
   Debes configurar el `packageName` de tu aplicación y la firma. 
   Además de activar/desactivar las comprobaciones que consideres necesarias.
2. Invoca el método `checkSecurity`, este lanzará una excepción especializada en caso de encontrar uno de los puntos anteriores.

```java

  val security = Security(application, object: SecurityConfiguration {
      override val expectedSignature: String
          get() = "YOUR_APP_SIGNATURE"
      override val packageName: String
          get() = "YOUR_PACKAGE_NAME"
  })
  
  security.checkSecurity()

```

Adicionalmente, se ha implementado un método para poder obtener la firma de la aplicación, con el fin de colocar en el ejemplo anterior

```java
  AppSignatureValidator.getAppSignature(context)
```

Instalación
-----

Disponible en [maven central](https://search.maven.org/search?q=a:musicalumbrella), para incluirla usando Gradle, simplemente añade:

```java
dependencies {
    implementation 'es.3pies:musicalumbrella:0.4.2'
}
```

Mención
-----

Para publicar esta librería en Sonatype he seguido este tutorial. Después de revisar bastantes, este ha sido el que me ha ayudado a subir la librería
https://s01.oss.sonatype.org/content/repositories/snapshots/

Para la comprobación del dispositivo rooteado, se utiliza Rootbeer. Podéis encontrar más info sobre ella aquí:
https://github.com/scottyab/rootbeer

# Licencia

Apache License, Version 2.0

    Copyright (C) 2021, Daniel Ferrer
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
         http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.