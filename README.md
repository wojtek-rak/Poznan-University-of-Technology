#  System współdzielonego kalendarza sieciowego (1 osoba)

Aplikacja servera: cpp
Aplikacja klienta: ASP.NET Core 2.1 MVC

Protokół komunikacyjny opiera się na 4 wiadomościach i odpowiedziach na nie:

w {} umieszczone są wartości nadawane dynamicznie podczas działania aplikacji

Wiadomość od klienta o treści:
GET_CALENDAR
Wiadomość zwrotna od servera:
