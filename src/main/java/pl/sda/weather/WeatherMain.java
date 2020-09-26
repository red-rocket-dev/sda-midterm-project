package pl.sda.weather;

import pl.sda.weather.dao.UserDao;
import pl.sda.weather.dao.UserPreferencesDao;
import pl.sda.weather.dto.WeatherInformation;
import pl.sda.weather.provider.OpenWeatherMapHttpClient;
import pl.sda.weather.service.UserService;
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
    private static final String QUIT_ACTION = "quit";
    private static final String USER_1_LOGIN = "user1";
    private static final String USER_1_PASSWORD = "password1";
    private static final String USER_2_LOGIN = "user2";
    private static final String USER_2_PASSWORD = "password2";

    public static void main(String[] args) throws IOException, InterruptedException {
        UserService userService = new UserService(new UserDao(), new UserPreferencesDao());
        initData(userService);
        Scanner scanner = new Scanner(System.in);
        String login;
        String password;
        do {
            System.out.println("Podaj login");
            login = scanner.nextLine();
            System.out.println("Podaj haslo");
            password = scanner.nextLine();
        } while (!userService.login(login, password));
        System.out.println("Witaj uzytkowniku!");
        System.out.println(login);

        String defaultCity = getDefaultCityOfCurrentUser(userService);
        System.out.println("Witaj! Pogoda dla miasta " + defaultCity + " to:");
        OpenWeatherMapHttpClient openWeatherMapHttpClient = new OpenWeatherMapHttpClient();
        WeatherService weatherService = new WeatherService(openWeatherMapHttpClient);

        WeatherInformation weather = weatherService.getWeather(defaultCity);

        System.out.println("Temperatura " + weather.getTemperature());
        System.out.println("Wilgotnosc " + weather.getHumidity());
        System.out.println("Cisnienie " + weather.getPressure());
        String windMessage = "Wiatr " + weather.getWindInformation().getDegrees() + " " + weather.getWindInformation().getForce();
        System.out.println(windMessage);

        String choice;
        do {
            choice = scanner.nextLine();
            switch (choice) {
                case "temperature":
                    defaultCity = getDefaultCityOfCurrentUser(userService);
                    System.out.println(weatherService.getTemperature(defaultCity));
                    break;
                case "wind":
                    defaultCity = getDefaultCityOfCurrentUser(userService);
                    System.out.println(weatherService.getWind(defaultCity).getForce());
                    break;
                case "humidity":
                    defaultCity = getDefaultCityOfCurrentUser(userService);
                    System.out.println(weatherService.getHumidity(defaultCity));
                    break;
                case "pressure":
                    defaultCity = getDefaultCityOfCurrentUser(userService);
                    System.out.println(weatherService.getPressure(defaultCity));
                    break;
                case "change city":
                    String newDefaultCity = scanner.nextLine();
                    userService.changeDefaultCityOfCurrentUser(newDefaultCity);
                    break;
                case "change days":
                    String newAmountOfDays = scanner.nextLine();
                    userService.changeDefaultAmountOfDaysOfCurrentUser(Long.valueOf(newAmountOfDays));
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

    private static String getDefaultCityOfCurrentUser(UserService userService) {
        return userService.currentUser()
                .map(user -> user.getUserPreferences().getDefaultCity())
                .orElseThrow(() -> new RuntimeException("Niepoprawny stan aplikacji!"));
    }


    public static void initData(UserService userService) {
        createUserIfNotExists(userService, USER_1_LOGIN, USER_1_PASSWORD);
        createUserIfNotExists(userService, USER_2_LOGIN, USER_2_PASSWORD);
    }

    private static void createUserIfNotExists(UserService userService, String login, String password) {
        if (userService.findByLogin(login).isEmpty()) {
            userService.register(login, password);
        }
    }
}
