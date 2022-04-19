import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBMethods {
    private static DBMethods instance = null;

    private Connection connection;

    public static DBMethods getInstance(){
        if (instance == null)
            instance = new DBMethods();
        return instance;
    }

    private DBMethods(){
        try {
            URI resource = ClassLoader.getSystemResource("database.sqlite3").toURI();
            // Выполняем подключение к базе данных
            this.connection = DriverManager.getConnection("jdbc:sqlite::resource:database.sqlite3");
            System.out.println("База Подключена!");
        } catch (SQLException e) {
            System.out.println("Драйвер базы данных не был подключен!");
            e.printStackTrace();
        } catch (URISyntaxException e){
            System.out.println("Невозможно найти указанный файл!");
            e.printStackTrace();
        }
    }

    public void createTable() {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS 'passengers' " +
                    "('passengerId' INTEGER PRIMARY KEY, " +
                    "'survived' INTEGER," +
                    "'pclass' INTEGER," +
                    "'name' VARCHAR(100)," +
                    "'sex' VARCHAR(50)," +
                    "'age' DOUBLE," +
                    "'sibsp' INTEGER," +
                    "'parch' INTEGER," +
                    "'ticket' VARCHAR(50)," +
                    "'fare' DOUBLE," +
                    "'cabin' VARCHAR(10)," +
                    "'embarked' VARCHAR(10));");
            System.out.println("Таблица создана или уже существует.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Double> getTicketPrice(){
        try (Statement statement = this.connection.createStatement()) {
            Map<String, Tuple<Integer, Double>> dataset = new HashMap<>();
            ResultSet dataFromDb = statement.executeQuery("SELECT sex, embarked, fare FROM passengers");
            while (dataFromDb.next()) {
                if (dataFromDb.getString("embarked").equals(""))
                    continue;
                String key = dataFromDb.getString("sex") + "-" + dataFromDb.getString("embarked");
                if (dataset.containsKey(key)) {
                    Tuple<Integer, Double> tuple = dataset.get(key);
                    tuple.setFirst(tuple.getFirst() + 1);
                    tuple.setSecond(tuple.getSecond() + dataFromDb.getDouble("fare"));
                    dataset.put(key, tuple);
                } else
                    dataset.put(key, new Tuple<>(1, dataFromDb.getDouble("fare")));
            }
            Map<String, Double> result = new HashMap<>();
            dataset.forEach((x, y) -> result.put(x, y.getSecond() / y.getFirst()));
            return result;
        } catch (SQLException e){
            System.out.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
            return null;
        }
    }

    public double getDifference(){
        try (Statement statement = this.connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT (MAX(fare) - MIN(fare)) AS result FROM passengers WHERE sex='female' AND age >= 15 AND age <= 30");
            return result.getDouble("result");
        } catch (SQLException e){
            System.out.println("Невозможно получить данные о цене билета");
            e.printStackTrace();
            return -1.0;
        }
    }

    public List<String> getTickets(){
        try (Statement statement = this.connection.createStatement()) {
            ResultSet tickets = statement.executeQuery(
                    "SELECT ticket FROM passengers WHERE (sex = 'male' AND age >= 45 AND age <= 60) " +
                            "OR (sex = 'female' AND age >= 20 AND age <= 25)");
            List<String> result = new ArrayList<>();
            while(tickets.next()){
                result.add(tickets.getString("ticket"));
            }
            return result;
        } catch (SQLException e){
            System.out.println("Невозможно получить данные о билетах");
            e.printStackTrace();
            return null;
        }
    }

    public void addData(List<PassengersPojo> passengers) {
        if (passengers.size() < 1)
            return;
        String sqlQuery = "INSERT INTO passengers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement = this.connection.prepareStatement(sqlQuery)) {
            connection.setAutoCommit(false);
            for (PassengersPojo passenger : passengers) {
                statement.setObject(1, passenger.getPassengerId());
                statement.setObject(2, passenger.getSurvived());
                statement.setObject(3, passenger.getPclass());
                statement.setObject(4, passenger.getName());
                statement.setObject(5, passenger.getSex());
                statement.setObject(6, passenger.getAge());
                statement.setObject(7, passenger.getSibsp());
                statement.setObject(8, passenger.getParch());
                statement.setObject(9, passenger.getTicket());
                statement.setObject(10, passenger.getFare());
                statement.setObject(11, passenger.getCabin());
                statement.setObject(12, passenger.getEmbarked());

                statement.addBatch();
            }
            statement.executeBatch();
            statement.clearBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
