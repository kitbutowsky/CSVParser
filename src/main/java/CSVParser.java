import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CSVParser {
    public static List<PassengersPojo> ParseTitanicCsv(String fileName) {
        try {
            var resource = ClassLoader.getSystemResource(fileName).toURI();
            //Загружаем строки из файла
            List<PassengersPojo> passengersPojos = new ArrayList<>();
            var fileLines = Files.readAllLines(Paths.get(resource));
            String[] headers = fileLines.remove(0).split(",");
            for (String fileLine : fileLines) {
                String[] values = fileLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", headers.length);
                Map<String, String> data = IntStream.range(0, headers.length).boxed().collect(
                        Collectors.toMap(i -> headers[i], i -> values[i]));
                passengersPojos.add(new PassengersPojo(data));
            }
            return passengersPojos;
        } catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Данные не были обработанны!");
            return new ArrayList<>();
        }

    }
}
