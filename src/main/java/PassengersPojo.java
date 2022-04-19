import java.util.Map;

public class PassengersPojo {
    private final int passengerId;
    private final int survived;
    private final int pclass;
    private final String name;
    private final String sex;
    private final double age;
    private final int sibsp;
    private final int parch;
    private final String ticket;
    private final double fare;
    private final String cabin;
    private final String embarked;

    public PassengersPojo(Map<String, String> elements) {
        this.passengerId = Integer.parseInt(elements.get("PassengerId"));
        this.survived = Integer.parseInt(elements.get("Survived"));
        this.pclass = Integer.parseInt(elements.get("Pclass"));
        this.name = elements.get("Name");
        this.sex = elements.get("Sex");
        this.age = Double.parseDouble(elements.get("Age").equals("") ? "0": elements.get("Age"));
        this.sibsp = Integer.parseInt(elements.get("SibSp"));
        this.parch = Integer.parseInt(elements.get("Parch"));
        this.ticket = elements.get("Ticket");
        this.fare = Double.parseDouble(elements.get("Fare"));
        this.cabin = elements.get("Cabin");
        this.embarked = elements.get("Embarked");
    }

    public String getEmbarked() {
        return embarked;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public int getSurvived() {
        return survived;
    }

    public String getName() {
        return name;
    }

    public int getPclass() {
        return pclass;
    }

    public String getSex() {
        return sex;
    }

    public double getAge() {
        return age;
    }

    public int getSibsp() {
        return sibsp;
    }

    public int getParch() {
        return parch;
    }

    public String getTicket() {
        return ticket;
    }

    public double getFare() {
        return fare;
    }

    public String getCabin() {
        return cabin;
    }
}
