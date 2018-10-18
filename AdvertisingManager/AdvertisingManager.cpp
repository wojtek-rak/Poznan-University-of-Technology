// AdvertisementsManager.cpp : Defines the entry point for the console application.
//

#include "Customer.h"
#include "AdvertEntity.h"
#include "PriceList.h"
#include "View.h"
#include "SaveManager.h"

#include <conio.h>
#include <windows.h>
#include <iostream>
#include <set>
#include <list>
#include <fstream>
#include <cstdlib>


using namespace std;

template<class T>
void operator+= (set<T>& set, T obj)
{
	set.insert(obj);
}

Customer cust[100];

int LoadCustomer()
{
	int numberOfCustomers = -1;
	char selector;
	int tempX;
	string name;
	bool tempPlan;
	ifstream infileCustomers;
	infileCustomers.open("customer.txt");
	while (infileCustomers >> selector)
	{
		switch (selector)
		{
		case 'n':
			numberOfCustomers++;
			infileCustomers >> name;
			cust[numberOfCustomers].Name(name);
			break;
		case 'b':
			infileCustomers >> tempX;
			cust[numberOfCustomers].Budget(tempX);
			break;
		case 'l':
			infileCustomers >> tempX;
			cust[numberOfCustomers].SpotsLength(tempX);
			break;
		case 'p':
			infileCustomers >> tempPlan;
			cust[numberOfCustomers].CheapPlan = tempPlan;
			break;
		case 'd':
			infileCustomers >> tempX;
			cust[numberOfCustomers].days += (DayNames)tempX;
			break;
		case 'h':
			infileCustomers >> tempX;
			cust[numberOfCustomers].hours += (HoursEnums)tempX;
			break;
		default:
			break;
		}
	}
	infileCustomers.close();
	return numberOfCustomers;
}



int main()
{
	int posIterator = 66;
	int heightOfMenu = 26;
	bool priceListOneActive = true;
	PriceList priceListOne;
	PriceList priceListEvent;
	PriceList priceListToShow;

	int numberOfCustomers = LoadCustomer();
	
	//cust[0].Budget(10);
	//cust[0].Name("XD");
	//cust[0].CheapPlan = true;
	//cust[0].SpotsLength(30);
	//cust[0].days += Monday;
	//cust[0].days += Monday;
	//cust[0].days += Tuesday;
	char selector;
	int tempX;
	bool tempPlan;
	bool removeCustomer = false;
	string name;


	bool menuCustomer = true;
	bool wrongPrice = false;
	
	bool editingList = false;
	int iteratorDays = 0;
	int iteratorHours = 0;
	//cout << "Reading from the file" << endl;
	ifstream infile;
	infile.open("data.txt");
	while (infile >> tempX)
	{
		//cout << x <<" ";
		priceListOne.advert[iteratorHours].price[iteratorDays] = tempX;
		iteratorDays++;
		if (iteratorDays > 6)
		{
			iteratorDays = 0;
			iteratorHours++;
			//cout << "\n";
		}
	}
	infile.close();

	iteratorDays = 0;
	iteratorHours = 0;
	ifstream infileTime;
	infileTime.open("dataTime.txt");
	while (infileTime >> tempX)
	{
		//cout << x <<" ";
		priceListOne.advert[iteratorHours].free[iteratorDays] = tempX;
		iteratorDays++;
		if (iteratorDays > 6)
		{
			iteratorDays = 0;
			iteratorHours++;
			//cout << "\n";
		}
	}
	infileTime.close();

	iteratorDays = 0;
	iteratorHours = 0;
	ifstream infileEvent;
	infileEvent.open("dataEvent.txt");
	while (infileEvent >> tempX)
	{
		//cout << x <<" ";
		priceListEvent.advert[iteratorHours].price[iteratorDays] = tempX;
		iteratorDays++;
		if (iteratorDays > 6)
		{
			iteratorDays = 0;
			iteratorHours++;
			//cout << "\n";
		}
	}
	infileEvent.close();

	iteratorDays = 0;
	iteratorHours = 0;
	ifstream infileEventTime;
	infileEventTime.open("dataEventTime.txt");
	while (infileEventTime >> tempX)
	{
		//cout << x <<" ";
		priceListEvent.advert[iteratorHours].free[iteratorDays] = tempX;
		iteratorDays++;
		if (iteratorDays > 6)
		{
			iteratorDays = 0;
			iteratorHours++;
			//cout << "\n";
		}
	}
	infileEventTime.close();
	

	priceListOne.advert[5].free[5] = 600;
	priceListEvent.advert[3].free[3] = 300;
	priceListEvent.advert[3].free[5] = 200;
	

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
	int y = 0;
	int menu_up_item = 0;
	bool running = true;

	priceListToShow = priceListOne;

	while (running)
	{
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> VIEW HANDLER <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
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
			View::PrintCustomerList(cust, numberOfCustomers); 
			if(menuCustomer) View::MenuCustomers(menu_item);
			break;
		case 4:
			//system("cls");
			View::gotoXY(0, 0);
			View::PrintPriceList(priceListToShow);
			View::EditPriceList(x, y, priceListToShow, editingList, wrongPrice);
			break;
		}
		if(!editingList) system("pause>nul");

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
			if (GetAsyncKeyState(VK_ESCAPE))
			{
				exit(0);
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
					priceListOneActive = true;
					break;

				case 1:
					priceListToShow = priceListEvent;
					priceListOneActive = false;
					break;

				case 2: 
					appState = 4;
					x = 0;
					y = 0;
					menu_item = 0;
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
			if (removeCustomer)
			{
				View::gotoXY(2, heightOfMenu);  cout << "Type Customer's Id to REMOVE: ";
				cin >> tempX;
				SaveManager::SaveCustomers(numberOfCustomers, cust, true, tempX);
				numberOfCustomers -= 1;
				menuCustomer = true;
				removeCustomer = false;
				editingList = false;
				numberOfCustomers = LoadCustomer();
				system("cls");
				continue;
			}
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
					system("cls");
					numberOfCustomers++;
					View::gotoXY(2, 2);  cout << "Name: "; cin >> name;
					cust[numberOfCustomers].Name(name);
					View::gotoXY(2, 4);  cout << "Budget: "; cin >> tempX;
					cust[numberOfCustomers].Budget(tempX);
					View::gotoXY(2, 6);  cout << "SpotsLength: "; cin >> tempX;
					cust[numberOfCustomers].SpotsLength(tempX);
					View::gotoXY(2, 8);  cout << "Plan (type 0 for expensive or 1 for cheap): "; cin >> tempPlan;
					cust[numberOfCustomers].CheapPlan = tempPlan;
					View::gotoXY(2, 10);  cout << "Type Favourite days (0 Monday, 1 Tuesday ... -1 for go next): ";
					posIterator = 66;
					while (cin >> tempX)
					{
						if (tempX == -1) break;
						View::gotoXY(posIterator, 10);
						if(tempX > -1 && tempX < 7) cust[numberOfCustomers].days += (DayNames)tempX;
						posIterator += 2;
					}
					View::gotoXY(2, 12);  cout << "Favourite hours (0 for 0:01-1:00, 1 for 1:01-2:00... -1 for go next): ";
					posIterator = 74;
					while (cin >> tempX)
					{
						if (tempX == -1) break;
						View::gotoXY(posIterator, 12);
						if (tempX > -1 && tempX < 24) cust[numberOfCustomers].hours += (HoursEnums)tempX;
						if (tempX > 9) posIterator += 3;
						else posIterator += 2;
					}
					SaveManager::SaveCustomers(numberOfCustomers, cust);
					break;
				case 1:
					menuCustomer = false;
					removeCustomer = true;
					editingList = true;
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
		// >>>>>>>>>>>>>>>>>>>>>>>>>>> EDIT PRICELSIT <<<<<<<<<<<<<<<<<<<<<<<<<<<
		if (appState == 4)
		{
			if (editingList)
			{
				int tempV;

				cin >> tempV;
				if (tempV < 0 || tempV > 99999) wrongPrice = true;
				else
				{
					wrongPrice = false; editingList = false;

					priceListToShow.advert[y].price[x] = tempV;


					if (priceListOneActive)
					{
						priceListOne.advert[y].price[x] = tempV;
						SaveManager::SavePriceList(priceListOneActive, priceListToShow);
					}
					else
					{
						priceListEvent.advert[y].price[x] = tempV;
						SaveManager::SavePriceList(priceListOneActive, priceListToShow);
					}
					appState = 1;
					x = 0;
					menu_item = 0;
				}

				system("cls");
				continue;
			}


			if (GetAsyncKeyState(VK_RIGHT) && x != 6)
			{
				x += 1;
				menu_item++;
				continue;
			}

			if (GetAsyncKeyState(VK_LEFT) && x != 0)
			{
				x -= 1;
				menu_item--;
				continue;
			}
			if (GetAsyncKeyState(VK_UP) && y != 0)
			{
				y -= 1;
				menu_up_item++;
				continue;
			}

			if (GetAsyncKeyState(VK_DOWN) && y != 23)
			{
				y += 1;
				menu_up_item--;
				continue;
			}

			if (GetAsyncKeyState(VK_ESCAPE))
			{
				appState = 1;
				x = 0;
				menu_item = 0;
			}

			
			if (GetAsyncKeyState(VK_RETURN))
			{
				editingList = true;
			}
			system("cls");

		}

	}
	return 0;
}

