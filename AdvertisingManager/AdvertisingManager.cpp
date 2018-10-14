// AdvertisementsManager.cpp : Defines the entry point for the console application.
//

#include "Customer.h"
#include "AdvertEntity.h"
#include "PriceList.h"
#include "View.h"

#include <conio.h>
#include <windows.h>
#include <iostream>
#include <set>
#include <list>
#include <fstream>
#include <cstdlib>

//double


using namespace std;

int main()
{
	int heightOfMenu = 26;
	PriceList priceListOne;
	PriceList priceListEvent;
	PriceList priceListToShow;

	Customer cust;
	cust.Budget(10);
	cust.SpotsLength(20);
	cust.days.insert(Monday);
	cust.days.insert(Monday);
	cust.days.insert(Tuesday);
	
	ifstream infile;
	infile.open("data.txt");
	
	int tempX;
	int iteratorDays = 0;
	int iteratorHours = 0;
	//cout << "Reading from the file" << endl;
	while (infile >> tempX)
	{
		//cout << x <<" ";
		priceListOne.advert[iteratorHours].price[iteratorDays] = tempX;
		priceListOne.advert[iteratorHours].free[iteratorDays] = true;
		iteratorDays++;
		if (iteratorDays > 6)
		{
			iteratorDays = 0;
			iteratorHours++;
			//cout << "\n";
		}
	}
	iteratorDays = 0;
	iteratorHours = 0;
	ifstream infileEvent;
	infileEvent.open("dataEvent.txt");
	while (infileEvent >> tempX)
	{
		//cout << x <<" ";
		priceListEvent.advert[iteratorHours].price[iteratorDays] = tempX;
		priceListEvent.advert[iteratorHours].free[iteratorDays] = true;
		iteratorDays++;
		if (iteratorDays > 6)
		{
			iteratorDays = 0;
			iteratorHours++;
			//cout << "\n";
		}
	}
	priceListOne.advert[5].free[5] = false;
	priceListEvent.advert[3].free[3] = false;
	priceListEvent.advert[3].free[5] = false;
	

	//cout << endl << endl << endl << endl;
	//if (cust.days.find(Monday) != cust.days.end()) cout << "JEST MOnday \n";
	//if (cust.days.find(Tuesday) != cust.days.end()) cout << "JEST TUESDAY \n";
	//if (cust.days.find(Sunday) != cust.days.end()) cout << "JEST kurwa niedziela\n";
	//else cout << "nie ma na szczesciu\n";
	//cout << "Hello World!\n";

	int appState = 0; 
	//0 default
	//1 Price List
	//2 ADVERTISMENT PLAN
	//3 CUSTOMER
	
	int menu_item = 0, run, x = 0;
	bool running = true;

	View::gotoXY(18, 5); cout << "Main Menu";
	View::gotoXY(18, 7); cout << "->";

	priceListToShow = priceListOne;

	while (running)
	{
		switch (appState)
		{
		case 0:
			View::gotoXY(0, 0);
			View::PrintHello();
			View::MenuStart(menu_item);
			break;
		case 1:
			View::gotoXY(0, 0);
			View::PrintPriceList(priceListToShow);
			View::MenuPriceList(menu_item);
			break;
		case 2:
			View::gotoXY(0, 0);
			View::PrintPriceList(priceListToShow);
			View::MenuAdvertismentPlan(menu_item);
			break;
		case 3:
			View::gotoXY(0, 0);
			View::PrintPriceList(priceListToShow);
			View::MenuCustomers(menu_item);
			break;
		}
		system("pause>nul"); // the >nul bit causes it the print no message

		// >>>>>>>>>>>>>>>>>>>>>>>>>>> START MENU <<<<<<<<<<<<<<<<<<<<<<<<<<<
		if (appState == 0)
		{
			if (GetAsyncKeyState(VK_RIGHT) && x != 60) 
			{
				x += 30;
				menu_item++;
				continue;
			}

			if (GetAsyncKeyState(VK_LEFT) && x != 0) 
			{
				x -= 30;
				menu_item--;
				continue;
			}

			if (GetAsyncKeyState(VK_RETURN)) {

				switch (menu_item) {

				case 0:
					appState = 1;
					x = 0;
					menu_item = 0;
					break;

				case 1:
					appState = 2;
					x = 0;
					menu_item = 0;
					break;

				case 2:
					appState = 3;
					x = 0;
					menu_item = 0;
					break;
				}
				system("cls");
				continue;
			}
		}
		// >>>>>>>>>>>>>>>>>>>>>>>>>>> PRICE LIST MENU <<<<<<<<<<<<<<<<<<<<<<<<<<<
		if (appState == 1) 
		{
			if (GetAsyncKeyState(VK_RIGHT) && x != 90) 
			{
				x += 30;
				menu_item++;
				continue;
			}

			if (GetAsyncKeyState(VK_LEFT) && x != 0) 
			{
				x -= 30;
				menu_item--;
				continue;
			}

			if (GetAsyncKeyState(VK_RETURN)) 
			{ 

				switch (menu_item) {
				case 0:
					priceListToShow = priceListOne;
					break;

				case 1:
					priceListToShow = priceListEvent;
					break;

				case 2: // IMPLEMENT EDIT ACTUAL PRICELIST
					break;
				case 3:
					appState = 0;
					x = 0;
					menu_item = 0;
					break;
				}
				system("cls");
				continue;

			}
		}
		// >>>>>>>>>>>>>>>>>>>>>>>>>>> ADVERTISMENT PLAN <<<<<<<<<<<<<<<<<<<<<<<<<<<
		if (appState == 2)
		{
			if (GetAsyncKeyState(VK_RIGHT) && x != 60)
			{
				x += 30;
				menu_item++;
				continue;
			}

			if (GetAsyncKeyState(VK_LEFT) && x != 0)
			{
				x -= 30;
				menu_item--;
				continue;
			}

			if (GetAsyncKeyState(VK_RETURN))
			{

				switch (menu_item) {
				case 0:
					priceListToShow = priceListOne;
					break;
				case 1:
					priceListToShow = priceListEvent;
					break;
				case 2:
					appState = 0;
					x = 0;
					menu_item = 0;
					break;
				}
				system("cls");
				continue;

			}
		}
		// >>>>>>>>>>>>>>>>>>>>>>>>>>> CUSTOMERS <<<<<<<<<<<<<<<<<<<<<<<<<<<
		if (appState == 3)
		{
			if (GetAsyncKeyState(VK_RIGHT) && x != 120)
			{
				x += 30;
				menu_item++;
				continue;
			}

			if (GetAsyncKeyState(VK_LEFT) && x != 0)
			{
				x -= 30;
				menu_item--;
				continue;
			}

			if (GetAsyncKeyState(VK_RETURN))
			{

				switch (menu_item) {
				case 0:
					//ADD CUSTOMER
					break;
				case 1:
					//RM CUSTOMER
					break;
				case 2:
					//EDIT CUSTOMER
					break;
				case 3:
					//SHOW CUSTOMER
					break;
				case 4:
					appState = 0;
					x = 0;
					menu_item = 0;
					break;
				}
				system("cls");
				continue;

			}
		}

	}
	return 0;
}

