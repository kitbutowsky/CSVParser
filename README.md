# Итоговый проект JAVA

### Вариант 3 - Пассажиры Титаника

### Процесс работы:
* Класс объекта пользователя или PassengersPojo

Класс простой имеет конструктор и свойства которые даны в таблице CSV. 
Итоговый вид POJO представлен в файле `PassengersPojo.java`
Так как файл содержит довольно большое количество полей(пусть и не критично большое)
было решенно передавать в конструктор словарь.

* Класс парсинга данных из CSV файла или CSVParser

Класс парсера находится в файле `CSVParser.java` и содержит единственный статический
метод `ParseTitanicCsv(String fileName)` принимающий имя файла. Сам файл находится
в директории ресурсов. 

* Взаимодействие с базой данных или DBMethods

Класс отвечающий за работу с базой данных в файле `DBMethods.java`. 
Файл базы данных так же берется из директории ресурсов. 
Для того, что бы не открывать лишних соединений с базой данных, конструктор класса вызывается из статического метода `getInstance()`, который либо создат соединение, либо вернет уже существующее.
Таблица в базе данных полностью соответствует CSV, т.к. создание более сложной структуры не поможет избавится от повторяемости данных. 
В этом классе есть методы:
 - создания таблицы `public void createTable()`
 - добавления в нее данных `public void addData(List<PassengersPojo> passengers)` 
 - получения стоимости билета `public Map<String, Double> getTicketPrice()`
 - получения разницы стоимости билета `public double getDifference()`
 - получения списка билетов `public List<String> getTickets()`


#### Выпонение зданий
* Задание 1: Постройте график цены билетов у пассажиров объеденив их по полу и колонке Embarked
  !
График представляет из себя диаграмму которая показывает среднюю цену билета в зависимости от сочетаний пола и класса билета. Где видно
  <img src="P:\Docs\Lessons\UrFU\4 семестр\popa\Screens\Fare.jpg"/>
* Задание 2: Найдите разницу между максимальным и минимальным ценой билета у женщин от 15 до 30 лет.
  <img src="P:\Docs\Lessons\UrFU\4 семестр\popa\Screens\Tickets and Diference.jpg"/>
* Задание 3: Найдите список билетов, мужчин в возрасте от 45 до 60 и женщин от 20 до 25.

