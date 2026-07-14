# Sistema de Venta de Entradas - Copa Mundial FIFA 2026 ⚽🏆

Este proyecto consiste en el desarrollo de un sistema de consola interactivo para la gestión y adquisición de boletos (entradas individuales y paquetes combinados de Kits) para la Copa Mundial de la FIFA 2026. Ha sido desarrollado bajo el paradigma de **Programación Orientada a Objetos (POO)**.

## 👥 Integrantes
* **Jorge Andrés Martínez Gutiérrez**

* **Asignatura:** Programación Orientada a Objetos (POO)

## 🛠️ Tecnologías Aplicadas
* **Lenguaje:** Java SE 8+
* **Interfaz:** Consola Interactiva con Saneamiento de Buffers de Entrada (`Scanner`).
* **Persistencia:** Carga, consulta e hidratación de objetos dinámica a través de ficheros de texto plano (`usuarios.txt`, `partidos.txt`, `kits.txt`, `compras.txt`).
* **Comunicación:** Integración con la API nativa de escritorio (`java.awt.Desktop`) y protocolo asíncrono `mailto:` para el despacho de correos electrónicos informativos.

## 📐 Pilares de Programación Orientada a Objetos Implementados
1. **Abstracción:** Definición conceptual de la clase padre abstracta `Usuario`.
2. **Encapsulamiento:** Ocultamiento riguroso de estados internos mediante atributos `private`/`protected` y manipulación externa exclusiva a través de métodos accesores públicos (`get` y `set`).
3. **Herencia:** Extensión de funcionalidades y reutilización de código en las subclases `Aficionado` y `Organizador`.
4. **Polimorfismo:** Gestión homogénea de nóminas de cuentas de accesos dinámicos en colecciones dinámicas polimórficas (`ArrayList<Usuario>`).
5. **Sobrescritura (@Override):** Comportamiento dinámico alternativo del método `consultarEntradas()` según el rol autenticado.
6. **Sobrecarga:** Polimorfismo estático aplicado en múltiples firmas de procesamiento lógico para `comprar()` y `notificar()`.
7. **Interfaz Comparable:** Implementación del ordenamiento de colecciones cronológicas dinámicas en la clase `Partido`.