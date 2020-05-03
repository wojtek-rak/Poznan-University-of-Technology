#  System współdzielonego kalendarza sieciowego (1 osoba)

OPIS PROTOKOŁU KOMUNIKACYJNEGO
=====
Protokół komunikacyjny opiera się na 4 wiadomościach i odpowiedziach na nie. 
Wszystkie wysyłane i odbierane wiadomości zawsze są wielkości 256, ale cała istotna treść
z reguły jest krótsza w przypadku wiadomości z serwera i zawsze kończy się znakiem <XD>.
Wiadomości z Klienta do servera zawsze mają stałą, niezmienną długość zalężną od wiadomości:

w {} umieszczone są wartości nadawane dynamicznie podczas działania aplikacji

GET_CALENDAR
-------

Wiadomość od klienta o treści:

`GET_CALENDAR`

Pierwsza wiadomość zwrotna od servera:

`{liczba kolejnych wiadomości}`

Kolejne wiadomości od servera:

`{idEventu},{data w postaci yyMMdd},{tytuł}.{idEventu},{data w postaci yyMMdd},{tytuł}. ... (7 eventów)`

W przypadku 11 eventów zostaną łącznie wysłane 3 wiadomości, pierwsza z ifndormacją o liczbie eventów i kolejne dwie z podstawowymi informacjami o eventach.

GET_SINGLE_EVENT
-------

Wiadomość od klienta o treści:

`GET_SINGLE_EVENT.{idEventu 3 znaki}`

Wiadomość zwrotna od servera:

`{tytuł}.{właściciel}.{data w postaci yyMMdd}.{opis wydarzenia}`

ADD_EVENT
-------

Wiadomość od klienta o treści:

`ADD_EVENT.{tytuł 20 znaków}.{właściciel 10 znaków}.{data w postaci yyMMdd}.{opis wydarzenia 200 znaków}`

Wiadomość zwrotna od servera:

`DONE`

REMOVE_EVENT
-------

Wiadomość od klienta o treści:

`REMOVE_EVENT.{idEventu 3 znaki}`

Wiadomość zwrotna od servera:

`DONE`

Opis implementacji
=======

Aplikacja servera: cpp
---

Główna część kodu znajduje się w `server.cpp`. Dodatkowo jest też `saveManager.cpp` który odpowiada za zapis eventów do pliku txt (taka lokalna db). A także `event.cpp` gdzie znajduje się główna klasa biznesowa.

W klasie `server.cpp` wszystko zaczyna się w klasie main tam inicjalizujemy eventy z pliku do pamięci i wchodzimy do głównej pętli while która ma za zadanie akceptować połączenia. Po przyjściu pierwszej wiadomości od klienta w metodzie processRequest tworzony jest nowy wątek dla tego klienta i wywoływana jest metoda readMessage. Tam znajduje się druga pentla while dla danego klienta, tam też przetwarzane, czytane i wysyłane są wszystkie wiadomości. Po zerwaniu połączenia wątek jest uśmiercany.


Aplikacja klienta: ASP.NET Core 2.1 MVC
---

Aplikacja napisana w ASP.NET Core 2.1 MVC. Znajduje się tam Jeden kontroler i dwa główne widoki, pierwszy odpowiada za wyświetlanie kalendarza, dugi za wyświetlanie i tworzenie nowego eventu.


Sposób kompilacji
===


Aplikacja servera: cpp
---

Kompilacja: 
`g++ -Wall server.cpp event.cpp saveManager.cpp -o main`

Odpalenie: 
`./main`

Aplikacja klienta: ASP.NET Core 2.1 MVC
---

W appsettings.json w sekcji `IpSettings` należy podać odpowiednią wartość IP:

`"IpSettings": {
    "Ip": "192.168.0.15",
    "Port": 1234
  }`

Opublikowaną w Visual Studio aplikację możana włączyć za pomocą komendy (w przypadku odpalenia większej liczby istnacji należy zmienić port 5001 na inny):

`dotnet .\NetworkCalendar.dll  --urls="http://localhost:5001"`

