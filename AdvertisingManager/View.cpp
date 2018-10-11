#include "View.h"
#include <windows.h>

HANDLE console = GetStdHandle(STD_OUTPUT_HANDLE);
CONSOLE_SCREEN_BUFFER_INFO *ConsoleInfo = new CONSOLE_SCREEN_BUFFER_INFO();

using namespace std;

View::View()
{
}


View::~View()
{
}

void View::PrintPriceList(PriceList priceList)
{
	GetConsoleScreenBufferInfo(console, ConsoleInfo);
	WORD OriginalColors = ConsoleInfo->wAttributes;
	SetConsoleTextAttribute(console, BACKGROUND_GREEN | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	for (int i = 0; i < 24; i++)
	{
		for (int j = 0; j < 7; j++)
		{
			if(!priceList.advert[i].free[j]) SetConsoleTextAttribute(console, BACKGROUND_RED | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
			cout << priceList.advert[i].price[j] << "\t";
			SetConsoleTextAttribute(console, BACKGROUND_GREEN | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
		}
		cout << endl;
	}
	SetConsoleTextAttribute(console, OriginalColors);
}
