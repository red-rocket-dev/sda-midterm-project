Manual:
1. Zintegruj aplikację z serwisem do pobierania pogody, tak aby możliwe było odczytanie temperatury, wilgotności powietrza, ciśnienia, prędkości i kierunku wiatru
    API do pogody - omówimy na zajęciach jak się zabrać za połączenie z nimi
    * https://openweathermap.org/api
    * https://developer.accuweather.com/apis
    * https://weatherstack.com/
    Przy dodawaniu nowego API pamiętaj o utrzymaniu odpowiedniego pakietowania i nazewnictwa (tak jak w przykładzie)
2. Utwórz klasę `pl.sda.weather.dao.WeatherInformation` zawierającą pola z informacją o pogodzie - temperatura, wilgotność, ciśnienie i osobny obiekt na prędkość i kierunek wiatru, np. WindInformation
3. Utwórz klasę `pl.sda.weather.service.WeatherService`, która będzie odpowiedzialna za pośrednictwo w pobieraniu danych z naszych API pogodowych
    1. W klasie powinny być metody: 
       1. `WindInformation getWind(String city, int days)`
       2. `double getTemperature(String city, int days)`
       3. `double getHumidity(String city, int days)`
       4. `double getPressure(String city, int days)`
       5. `WeatherInformation getWeather(String city, int days)`
      W każdej z nich powinno zostać uruchomione odpowiednie API (być może to będzie po prostu zawsze to samo, bo będzie miało wszystkie informacje) do pobrania danej informacji
4. W  WeatherMain wywołaj WeatherService, żeby pobrać pogodę i wyświetlić na początku działania aplikacji, umożliw użytkownikowi podanie danych (miasto i na za ile dni)
5. Podepnij wymienione wyżej metody do akcji menu `wind`, `temperature`, `humidity`, `pressure`
6. Logowanie:
   1. Utwórz klasę `pl.sda.weather.service.UserService` - będzie odpowiedzialna za dodawanie użytkowników, ich odnajdywanie, sprawdzanie hasła itd.
       1. Żadna z metod w serwisie **nie** powinna bezpośrednio używać `EntityManager`, a UserDDao
       2. Utwórz metody:
           1. `UserEntity findById(Long id)` -> znajduje użytkownika o zadanym id
           2. `UserEntity findByLogin(String login)` -> znajduje użytkownika o zadanym loginie
           3. `List<UserEntity> all()` -> zwraca wszystkich użytkowników
           4. `UserEntity register(String login, String password)` -> tworzy nowego użytkownika o zadanym haśle, w przyszłości będzie też nadawało domyślne ustawienia, co więcej będzie się tutaj działo na zajęciach :)
           5. `boolean login(String login, String password)` -> jeśli zadany użytkownik istnieje w bazie danych i ma ustawione takie właśnie hasło zwraca w Optionalu tego użytkownika, w przeciwnym razie zwraca pustego Optionala
           6. `Optional<UserEntity> currentUser()` -> zwraca aktualnie zalogowanego użytkownika (w klasie powinno być pole typu `UserEntity`, które będzie go przechowywało)
           7. `void logout()` -> ustawia wyżej wspomniane pole na null, wylogowując tym samym użytkownika
   2. W `WeatherMain`:
      1. utwórz metodę `void initData()` - utwórz w niej 2 użytkowników testowych za pomocą `register` (wybierz jakie mają mieć loginy i hasła), wcześniej sprawdzając czy istnieją za pomocą `findByLogin`, żeby nie mieć duplikatów.
      2. wywołaj metodę `initData()` na samym początku maina
   3. Przed główną pętlą z menu poproś użytkownika o wprowadzenie loginu i hasła, a następnie użyj metody `login` z `UserService`, jeśli użytkownikowi uda się zalogować wypisz jego login
7. Ustawienia użytkownika:
   1. Utwórz encję `pl.sda.weather.model.UserPreferences` i złącz ją odpowiednią relacją z użytkownikiem, powinna mieć pola
       * id
       * defaultCity -> domyślne miasto, którego pogoda będzie wyświetlana po zalogowaniu
       * defaultAmountOfDaysAhead -> to będzie mówiło czy po zalogowaniu wyświetlamy dzisiejszą prognozę (jeśli pole = 0) czy za x dni
       * pozostałe pola wymienione w ogólnym opisie zadania zostawimy na później
   2. Utwórz klasę typu `Dao` do `UserPreferences` pamiętaj o odpowiednim umiejscowieniu w pakietach i nazwie!
   3. Nie zapomnij zmodyfikować metody `pl.sda.weather.service.UserService.register` - poza zapisywaniem użytkownika powinna teraz też zapisywać przypisane do niego ustawienia. Ustaw je domyślnie na 0 dni i miasto Katowice (żeby była jakakolwiek wartość startowa).   
   4. Po zalogowaniu pobierz aktualnego użytkownika (pewnie już to robisz, żeby pobrać login) i wyciągnij z niego dodatkowo ustawienia, w oparciu o nie zwróć prognozę pogody dla danego miasta i za x dni
   5. Wyczyść bazę danych (np. za pomocą create-drop w hibernate), żeby użytkownicy testowi utworzyli się na nowo
   6. Przetestuj działanie
8. Umożliw użytkownikowi zmianę miasta i liczby dni po zalogowaniu
   1. Dodaj metodę `pl.sda.weather.service.UserService.changeDefaultCity(String city)` -> pobierze aktualnego użytkownika (np. pomagając sobie `currentUser()`), i zmieni domyślne miasto na przekazane w argumencie
   2. Dodaj metodę `pl.sda.weather.service.UserService.changeDefaultDays(Long days)` -> pobierze aktualnego użytkownika, i zmieni domyślną ilosć dni na przekazaną w argumencie
   3. Dodaj obsługę zmiany ustawień w Mainie, wystarczy, że użyjesz nowych metod po wpisaniu przez użytkownika `settings` (dokładniej jak ma to działać w ogólnym opisie aplikacji). Pamiętaj, żeby `Scanner` używać tylko w main!
   4. Przetestuj działanie
9. Historia logowań
   1. Dodaj encję `pl.sda.weather.model.LoginEventEntity`, ustaw ją w odpowiedniej relacji z `UserEntity`, a w niej pola:
      * `shownMessage` -> zawiera wiadomość pokazaną użytkownikowi po zalogowaniu
      * `time` -> zawiera dokładny czas w którym użytkownik się zalogował
   2. Dodaj klasę `Dao` dla tej encji w odpowiednim pakiecie
   3. Dodaj klasę `pl.sda.weather.LoginEventService`, mającą metody:
      1. `List<LoginEventEntity> findByUserId(long id)` -> zwróci wszystkie wydarzenia logowania użytkownika o zadanym id
      2. `LoginEventEntity findLast(long id)` -> zwróci ostatnie wydarzenie logowania dla użytkownika o zadanym id
      3. `void registerEvent(UserEntity user, String message)` -> zapisze do bazy danych nowy wpis podpięty do użytkownika `user` o aktualnym czasie i `shownMessage` z argumentu
   4. Po zalogowaniu się użytkownika w main wyświetl czas ostatniego wcześniejszego poprawnego logowania korzystając z metody `findLast`
   5. Następnie wywołaj `registerEvent`
10. Zintegruj aplikację z conajmniej jeszcze jednym API, tak jak to wcześniej sobie pokazaliśmy (pewnie to zrobimy trochę razem znowu)