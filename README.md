# <u>*Meal Orders*</u>

Este programa gestiona las órdenes típicas de un restaurante como son menus diarios, menus semanales y artículos individuales como bebidas o comidas .

# <u>El dominio del programa está compuesto por las clases **Menu (WeeklyMenu,DailyMenu)** y una clase **Item**.</u>
## Menús

Existen los menús diarios y los semanales. El programa no controla que no se puedan pedir menus diarios del día anterior ni menús semanales de la semana anterior. Simplemente no se ha hecho por falta de tiempo.
Un menú está compuesto por una lista de ítems que no tiene por qué estar declarada en la base de datos de ítems
Los menús tienen un ID único que los distingue del resto de menús de sus mismo tipo con el mismo nombre.

## MenuFactory

Factoria de menús para generar nuevos menús daoly o weekly y realizar copias de existentes.

## Items

Los ítems son artículos individuales que se pueden ordenar de forma individual y en bloque dentro de un menú.
Cada ítem tiene un ID único que lo diferencia del resto de sus iguales con la misma descripción.

# <u>Hay dos interfaces de uso Stock y Order y dos implementaciones StockManager y OrderManager</u>
## Stock
Indica lo que se puede hacer en el stock:
* Rellenar stock en caso de ser necesario
* Ver si hay un item o menú en el stock 
* Consumir un item o menú una vez se ha ordenado.
## Order
Indica qué y cómo funcionan las órdenes en el restaurante
* Se pueden ordenar ítems o menús individualmente o en conjunto
* Se pueden pagar ítems o menús
* Se pueden generar facturas de ítems o menús individualmente o en conjunto
## StockManager
Es una implementación de Stock que mantiene una base de datos para menús y otra para ítems
El stock se rellena para un item o menú del mismo tipo si quedan menos de tres y se informa al usuario.Chekea si un item o menú con un Id determinado está en la base de datos. Consume un item o menú e informa al usuario del stock restante

## OrderManager
Mantiene una base de datos para pedidos de artículos y otra para menús , asimismo tiene una referencia a los stocks para poder manegarlo y consultarlo a medida que se van pidiendo comandas.
Lanza ordenes comprobando que no se haya pedido ya ese menú o item y si el articulo no existe por ID en el stock lo añade. Acto seguido se añade a la base de datos el pedido y se consume del stock. Finalmente se rellena el stock en caso de ser necesario. Esta función va informando al usuario en cada fase.
Pago de ítems o menús. Cualquier articulo o menú se puede pagar siempre y cuando se haya pedido con anterioridad. Se informa al usuario de que se ha procedido al pago.
Generación de facturas Se pueden generar facturas de ítems o menús siempre y cuando el articulo o menús se haya pedido con anterioridad y se haya pagado.En caso de generación de factura conjunta de ítems o menus se suma el precio dando como resultado el sumatorio.

# Main
El main principal de la aplicación lanza una serie de pedidos ,pagos y generación de facturas. Contiene tres funciones estáticas para llevar a cabo la generación de ítems y menus.




