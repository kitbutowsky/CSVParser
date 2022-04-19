import java.awt.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        List<PassengersPojo> dataCSV = CSVParser.ParseTitanicCsv("passengers.csv");
        DBMethods dbInstance = DBMethods.getInstance();
        dbInstance.createTable();
        dbInstance.addData(dataCSV);
        int c = 0;
        System.out.println("Cписок билетов, мужчин в возрасте от 45 до 60 и женщин от 20 до 25: ");
        for (String ticket : dbInstance.getTickets()){
            System.out.print(ticket + " | ");
            c++;
            if (c % 10 == 0)
                System.out.println();
        }
        System.out.println("\n" + "Разница между максимальной и минимальной ценой билета у женщин от 15 до 30 лет: " + dbInstance.getDifference());

        Map<String, Double> ticketPrice = dbInstance.getTicketPrice();

        EventQueue.invokeLater(() -> {
            TicketPriceChart ex = new TicketPriceChart(ticketPrice);
            ex.setVisible(true);
        });
    }
}

