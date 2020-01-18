#  System współdzielonego kalendarza sieciowego (1 osoba)

Aplikacja servera: cpp

Aplikacja klienta: ASP.NET Core 2.1 MVC

OPIS PROTOKOŁU KOMUNIKACYJNEGO
=====
Protokół komunikacyjny opiera się na 4 wiadomościach i odpowiedziach na nie. 
Wszystkie wysyłane i odbierane wiadomości zawsze są wielkości 256, ale cała istotna treść
z regóły jest krótsza i zawsze kończy się znakiem <XD>:

w {} umieszczone są wartości nadawane dynamicznie podczas działania aplikacji

GET_CALENDAR
-------

Wiadomość od klienta o treści:
GET_CALENDAR

Pierwsza wiadomość zwrotna od servera:
{liczba kolejnych wiadomości}

Kolejne wiadomości od servera:
`{idEventu},{data w postaci yyMMdd},{tytuł}.{idEventu},{data w postaci yyMMdd},{tytuł}. ... (7 eventów)`

W przypadku 11 eventów zostaną łącznie wysłane 3 wiadomości, pierwsza z ifndormacją o liczbie eventów i kolejne dwie z podstawowymi informacjami o eventach.

