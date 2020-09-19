package pl.sda.weather;

import pl.sda.weather.dto.WeatherInformation;
import pl.sda.weather.service.WeatherService;

import java.io.IOException;
import java.util.Scanner;

public class WeatherMain {
    /**
     * Twoim zadaniem jest przygotowanie aplikacji pogodynki wyświetlającej pogodę w zadanym mieście. Informacje o pogodzie będą pobierane z API zewnętrznych (patrz README.md).
     * Aplikacja powinna obsługiwać wielu użytkowników - tak więc pierwszym elementem, który pokaże się użytkownikowi będzie ekran logowania (rejestracje pominiemy)
     * Po zalogowaniu użytkownikowi prezentowana jest aktualna pogoda w jego domyślnym mieście (sam ustawia) w domyślnym czasie (na dzisiaj lub jutro, tak jak w ustawieniach) w zadany sposób (też pobierane z ustawień)
     * Na przykład dla użytkownika user1 to może być "Katowice, prognoza na jutro 22C"
     * a dla użytkownika xyz44 może to być "New York, prognoza na dzisiaj 22C, wilgotność 95%, wiatr silny NE"
     * <p>
     * Następnie użytkownik wybiera co chce zrobić:
     * kolejne punkty przedstawiam w formie dane które pobieramy od użytkownika -> rezultat
     * 1. temperature:
     * miasto, za ile dni -> wyświetlana jest temperatura w "miasto" za "ile dni" np. w Katowicach za 5 dni
     * analogicznie dla wilgotności, ciśnienia i wiatru
     * 2. settings: - tutaj dajemu możliwość użytkownikowi spersonalizowania swoich ustawień
     * wybór spośród [default-city, default-time, default-apis, default-format] -> pytamy użytkownika jaką wartość chce ustawić i taką ustawiamy.
     * Na przykład
     * Wybierz jakie ustawienie chcesz zmienić, dostępne opcje miasto, dni, apis, format:
     * Użytkownik wpisuje miasto
     * Czekamy aż wpisze miasto
     * Katowice
     * Zapisujemy do bazy danych zmianę preferencji i wracamy do pętli głównej
     * 3. logging history - wyświetlamy historię logowań użytkownika wraz z pogodą jaka została mu wtedy przedstawiona (w tamtym formacie), najnowsze na początku
     * np.
     * Pogoda                           | Czas
     * ------------------------------------------------------
     * Katowice, prognoza na jutro 22C  | 2020-06-01 12:30:00
     * Katowice, prognoza na jutro 12C  | 2020-04-01 12:30:00
     * Katowice, prognoza na dzisiaj 0C | 2020-03-01 12:30:00
     * Katowice, prognoza na jutro 22C  | 2020-02-01 12:30:00
     * 4. report
     * Wyświetlamy użytkownikowi następujące informacje:
     * * ile razy odpytywał poszczególne serwisy pogodowe (docelowo będą trzy)
     * * średnią temperaturę ze wszystkich wierszy
     * * od kiedy istnieje konto
     * 5. others
     * Wyświetlamy tabelkę wszystkich użytkowników składającą się z nicku i kiedy założono konto
     * 5. logout
     * Wylogowanie
     * <p>
     * Dodatki:
     * * wyświetlaj po zalogowaniu żart dnia ze strony https://api.jokes.one/jod
     * * zamiast trzymać w bazie domyślną lokalizację użytkownika ustalaj ją za pomocą adresu IP
     * * role użytkowników - administrator będzie miał dodatkową opcję w menu, dzięki której może zmienić użytkownika zwykłego na premium - użytkownik premium jest wyświetlany na liście użytkowników z gwiazdką obok loginu
     * * UI w JavaFX
     * Tematy do poruszenia przed startem:
     * * omówienie startera (w tym jak się zintegrować z api do pogody i co to ObjectMapper)
     * * model bazy danych stopniowo
     * * zewnętrzna baza danych na heroku
     */
    public static final String QUIT_ACTION = "quit";

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Witaj! Podaj miasto do pobrania pogody");
        WeatherService weatherService = new WeatherService();
        String city = scanner.nextLine();
        WeatherInformation weather = weatherService.getWeather(city);

        System.out.println("Temperatura " + weather.getTemperature());
        System.out.println("Wilgotnosc " + weather.getHumidity());
        System.out.println("Cisnienie " + weather.getPressure());
        String windMessage = "Wiatr " + weather.getWindInformation().getDegrees() + " " + weather.getWindInformation().getForce();
        System.out.println(windMessage);

        String choice = null;
        do {
            choice = scanner.nextLine();
            switch (choice) {
                case "temperature":
                    break;
                case "wind":
                    break;
                case "humidity":
                    break;
                case "pressure":
                    break;
                case "settings":
                    //strategia formatowania danych
                    //strategia pobierania danych (z jakich serwisow)
                    //domyslne miasto
                    //domyslnie czy wyswietlamy date dla dzisiaj czy jutra
                    break;
                case "logging history":
                    break;
                case "report":
                    //wyświetl historię żądań
                case "logout":
                    //wylogowujemy użytkownika
                default:
                    System.out.println("Type: weather, wind, humidity, pressure or settings");
            }
        } while (!QUIT_ACTION.equals(choice));


    }
}
