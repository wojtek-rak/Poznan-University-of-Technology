#include "View.h"
#include "Customer.h"
#include<iostream>
#include <windows.h>

HANDLE console = GetStdHandle(STD_OUTPUT_HANDLE);
CONSOLE_SCREEN_BUFFER_INFO *ConsoleInfo = new CONSOLE_SCREEN_BUFFER_INFO();
COORD CursorPosition;

using namespace std;


const char hello[] =
"\n\
\n\
\n\
\n\
                              _______________________________\n\
                             |                               |\n\
                             |                               |\n\
                             |                               |\n\
                             |           H E L L O           |\n\
                             |                               |\n\
                             |                               |\n\
                             |_______________________________|\n\
\n\
\n\
\n\
\n\
";

int heightOfMenu = 26;

View::View()
{
}


View::~View()
{
}

void View::gotoXY(int x, int y)
{
	CursorPosition.X = x;
	CursorPosition.Y = y;
	SetConsoleCursorPosition(console, CursorPosition);
}

void View::PrintHello()
{
	cout << hello;
}

void View::PrintCustomerList(Customer cust[100], int numberOfCustomers)
{
	cout << "\n";
	GetConsoleScreenBufferInfo(console, ConsoleInfo);
	WORD OriginalColors = ConsoleInfo->wAttributes;

	for (int i = 0; i < numberOfCustomers + 1; i++)
	{
		SetConsoleTextAttribute(console, BACKGROUND_GREEN | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);

		cout <<"ID: "<<i<< " Customer name: " << cust[i].Name() << ", Customer budget: " << cust[i].Budget() << ", Customer Spots Length:" << cust[i].SpotsLength()<<", CheapPlan: "<< cust[i].CheapPlan << ", Event PriceList: " << cust[i].EventPriceList <<"\n";
		cout << "Days: ";
		for (auto f : cust[i].days) {
			cout << f<< ", ";
		}
		cout << "Hours: ";
		for (auto f : cust[i].hours) {
			cout << f << ", ";
		}
		cout << "\n";
		//for (int j = 0; j < 7; j++)
		//{
		//	if (priceList.advert[i].free[j] > 330) SetConsoleTextAttribute(console, BACKGROUND_RED | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
		//	if (priceList.advert[i].price[j] > 9999) cout << priceList.advert[i].price[j] << "\t\t";
		//	else  if (priceList.advert[i].price[j] > 999) cout << priceList.advert[i].price[j] << " " << "\t\t";
		//	else  if (priceList.advert[i].price[j] > 99) cout << priceList.advert[i].price[j] << "  " << "\t\t";
		//	else  if (priceList.advert[i].price[j] > 9) cout << priceList.advert[i].price[j] << "   " << "\t\t";
		//	else cout << priceList.advert[i].price[j] << "    " << "\t\t";
		//	SetConsoleTextAttribute(console, BACKGROUND_GREEN | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
		//}
		//SetConsoleTextAttribute(console, OriginalColors);
		//cout << endl;
	}
	SetConsoleTextAttribute(console, OriginalColors);

}


void View::PrintPriceList(PriceList priceList)
{
	cout << "\n";
	GetConsoleScreenBufferInfo(console, ConsoleInfo);
	WORD OriginalColors = ConsoleInfo->wAttributes;
	
	for (int i = 0; i < 24; i++)
	{
		cout << "  ";
		SetConsoleTextAttribute(console, BACKGROUND_GREEN | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
		for (int j = 0; j < 7; j++)
		{
			if (priceList.advert[i].free[j] > 0) SetConsoleTextAttribute(console, BACKGROUND_RED | BACKGROUND_GREEN | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
			if(priceList.advert[i].free[j] > 330) SetConsoleTextAttribute(console, BACKGROUND_RED | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
			if (priceList.advert[i].price[j] > 9999) cout << priceList.advert[i].price[j] << "\t\t";
			else  if (priceList.advert[i].price[j] > 999) cout << priceList.advert[i].price[j] << " " << "\t\t";
			else  if (priceList.advert[i].price[j] > 99) cout << priceList.advert[i].price[j] << "  " << "\t\t";
			else  if (priceList.advert[i].price[j] > 9) cout << priceList.advert[i].price[j] << "   " << "\t\t";
			else cout << priceList.advert[i].price[j] << "    " << "\t\t";
			SetConsoleTextAttribute(console, BACKGROUND_GREEN | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
		}
		SetConsoleTextAttribute(console, OriginalColors);
		cout << endl;
	}
	SetConsoleTextAttribute(console, OriginalColors);
}

void View::PrintPriceListTime(PriceList priceList)
{
	cout << "\n";
	GetConsoleScreenBufferInfo(console, ConsoleInfo);
	WORD OriginalColors = ConsoleInfo->wAttributes;

	for (int i = 0; i < 24; i++)
	{
		cout << "  ";
		SetConsoleTextAttribute(console, BACKGROUND_GREEN | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
		for (int j = 0; j < 7; j++)
		{
			if (priceList.advert[i].free[j] > 0) SetConsoleTextAttribute(console, BACKGROUND_RED | BACKGROUND_GREEN | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
			if (priceList.advert[i].free[j] > 330) SetConsoleTextAttribute(console, BACKGROUND_RED | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
			if (priceList.advert[i].free[j] > 9999) cout << priceList.advert[i].free[j] << "\t\t";
			else  if (priceList.advert[i].free[j] > 999) cout << priceList.advert[i].free[j] << " " << "\t\t";
			else  if (priceList.advert[i].free[j] > 99) cout << priceList.advert[i].free[j] << "  " << "\t\t";
			else  if (priceList.advert[i].free[j] > 9) cout << priceList.advert[i].free[j] << "   " << "\t\t";
			else cout << priceList.advert[i].free[j] << "    " << "\t\t";
			SetConsoleTextAttribute(console, BACKGROUND_GREEN | FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
		}
		SetConsoleTextAttribute(console, OriginalColors);
		cout << endl;
	}
	SetConsoleTextAttribute(console, OriginalColors);
}

void View::MenuStart(int menu_item)
{
	GetConsoleScreenBufferInfo(console, ConsoleInfo);
	WORD OriginalColors = ConsoleInfo->wAttributes;
	View::gotoXY(2, heightOfMenu);  cout << "PRICE LISTS";
	View::gotoXY(32, heightOfMenu);  cout << "ADVERTISMENT PLAN";
	View::gotoXY(62, heightOfMenu);  cout << "CUSTOMER";
	//View::gotoXY(32, heightOfMenu); cout << "4) ...";
	//View::gotoXY(42, heightOfMenu); cout << "Quit Program";
	switch (menu_item) {
	case 0: {
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "PRICE LISTS";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	}
	case 1: {
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "ADVERTISMENT PLAN";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	}
	case 2: {
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "CUSTOMER";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	}
	//case 3: {
	//	SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
	//	View::gotoXY(32, heightOfMenu); cout << "4) ...";
	//	SetConsoleTextAttribute(console, OriginalColors);
	//	break;
	//}
	//case 4: {
	//	SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
	//	View::gotoXY(42, heightOfMenu); cout << "Quit Program";
	//	SetConsoleTextAttribute(console, OriginalColors);
	//	break;
	//}
	
	}
}
void View::MenuPriceList(int menu_item)
{
	GetConsoleScreenBufferInfo(console, ConsoleInfo);
	WORD OriginalColors = ConsoleInfo->wAttributes;
	View::gotoXY(2, heightOfMenu);  cout << "USUAL PRICE LISTS";
	View::gotoXY(32, heightOfMenu);  cout << "CHRISTMAS PRICE LISTS";
	View::gotoXY(62, heightOfMenu);  cout << "EDIT PRICE LIST";
	View::gotoXY(92, heightOfMenu);  cout << "RETURN";
	//View::gotoXY(32, heightOfMenu); cout << "4) ...";
	//View::gotoXY(42, heightOfMenu); cout << "Quit Program";
	switch (menu_item) {
	case 0:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "USUAL PRICE LISTS";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	case 1: 
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "CHRISTMAS PRICE LISTS";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	case 2: 
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "EDIT PRICE LIST";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	case 3:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "RETURN";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	}
}

void View::MenuAdvertismentPlan(int menu_item)
{
	GetConsoleScreenBufferInfo(console, ConsoleInfo);
	WORD OriginalColors = ConsoleInfo->wAttributes;
	View::gotoXY(2, heightOfMenu);  cout << "USUAL PRICE LISTS";
	View::gotoXY(32, heightOfMenu);  cout << "CHRISTMAS PRICE LISTS";
	View::gotoXY(62, heightOfMenu);  cout << "SHOW OCCUPANCY";
	View::gotoXY(92, heightOfMenu);  cout << "RETURN";
	//View::gotoXY(32, heightOfMenu); cout << "4) ...";
	//View::gotoXY(42, heightOfMenu); cout << "Quit Program";
	switch (menu_item) {
	case 0:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "USUAL PRICE LISTS";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	case 1:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "CHRISTMAS PRICE LISTS";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	case 2:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "SHOW OCCUPANCY";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	case 3:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 30, heightOfMenu);  cout << "RETURN";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	}
}

void View::MenuCustomers(int menu_item)
{
	GetConsoleScreenBufferInfo(console, ConsoleInfo);
	WORD OriginalColors = ConsoleInfo->wAttributes;
	View::gotoXY(2, heightOfMenu);  cout << "ADD CUSTOMER";
	View::gotoXY(22, heightOfMenu);  cout << "REMOVE CUSTOMER";
	View::gotoXY(42, heightOfMenu);  cout << "EDIT CUSTOMER";
	View::gotoXY(62, heightOfMenu);  cout << "SHOW CUSTOMER";
	View::gotoXY(82, heightOfMenu);  cout << "RETURN";
	//View::gotoXY(32, heightOfMenu); cout << "4) ...";
	//View::gotoXY(42, heightOfMenu); cout << "Quit Program";
	switch (menu_item) {
	case 0:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 20, heightOfMenu);  cout << "ADD CUSTOMER";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	case 1:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 20, heightOfMenu);  cout << "REMOVE CUSTOMER";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	case 2:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 20, heightOfMenu);  cout << "EDIT CUSTOMER";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	case 3:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 20, heightOfMenu);  cout << "SHOW CUSTOMER";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	case 4:
		SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
		View::gotoXY(2 + menu_item * 20, heightOfMenu);  cout << "RETURN";
		SetConsoleTextAttribute(console, OriginalColors);
		break;
	}
}

void View::EditPriceList(int menu_item, int menu_up_item, PriceList priceList, bool editingList, bool wrongPrice)
{
	GetConsoleScreenBufferInfo(console, ConsoleInfo);
	WORD OriginalColors = ConsoleInfo->wAttributes;
	if (menu_item == 1 || menu_item == 0)  View::gotoXY(menu_item * 14 + 2, menu_up_item + 1);
	else  View::gotoXY(menu_item * 16, menu_up_item + 1);
	SetConsoleTextAttribute(console, BACKGROUND_BLUE | BACKGROUND_GREEN | BACKGROUND_RED);
	int j = menu_item;
	int i = menu_up_item;
	if (priceList.advert[i].price[j] > 9999) cout << priceList.advert[i].price[j];
	else  if (priceList.advert[i].price[j] > 999) cout << priceList.advert[i].price[j] << " ";
	else  if (priceList.advert[i].price[j] > 99) cout << priceList.advert[i].price[j] << "  ";
	else  if (priceList.advert[i].price[j] > 9) cout << priceList.advert[i].price[j] << "   ";
	else cout << priceList.advert[i].price[j] << "    ";
	SetConsoleTextAttribute(console, OriginalColors);
	if (!wrongPrice) { View::gotoXY(2, heightOfMenu);  cout << "Press enter to edit, escape to return and save"; }
	if (wrongPrice) { View::gotoXY(2, heightOfMenu);  cout << "Set max 5 numbers price"; }
	if (editingList)
	{
		if (menu_item == 1 || menu_item == 0)  View::gotoXY(menu_item * 14 + 2, menu_up_item + 1);
		else  View::gotoXY(menu_item * 16, menu_up_item + 1);
		cout << "     ";
		if (menu_item == 1 || menu_item == 0)  View::gotoXY(menu_item * 14 + 2, menu_up_item + 1);
		else  View::gotoXY(menu_item * 16, menu_up_item + 1);
	}
	
	//View::gotoXY(22, heightOfMenu);  cout << "REMOVE CUSTOMER";
	//View::gotoXY(42, heightOfMenu);  cout << "EDIT CUSTOMER";
	//View::gotoXY(62, heightOfMenu);  cout << "SHOW CUSTOMER";
	//View::gotoXY(82, heightOfMenu);  cout << "RETURN";
	//View::gotoXY(32, heightOfMenu); cout << "4) ...";
	//View::gotoXY(42, heightOfMenu); cout << "Quit Program";
}