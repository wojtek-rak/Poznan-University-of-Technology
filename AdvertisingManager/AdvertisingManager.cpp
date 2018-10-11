// AdvertisementsManager.cpp : Defines the entry point for the console application.
//

#include "Customer.h"
#include "AdvertEntity.h"
#include "PriceList.h"
#include "View.h"

#include <iostream>
#include <set>
#include <list>
#include <fstream>

using namespace std;


int main()
{
	PriceList priceListOne;
	Customer cust;
	cust.Budget(10);
	cust.SpotsLength(20);
	cust.days.insert(Monday);
	cust.days.insert(Monday);
	cust.days.insert(Tuesday);
	
	ifstream infile;
	infile.open("data.txt");
	int x;
	int iteratorDays = 0;
	int iteratorHours = 0;
	cout << "Reading from the file" << endl;
	while (infile >> x)
	{
		//cout << x <<" ";
		priceListOne.advert[iteratorHours].price[iteratorDays] = x;
		priceListOne.advert[iteratorHours].free[iteratorDays] = true;
		iteratorDays++;
		if (iteratorDays > 6)
		{
			iteratorDays = 0;
			iteratorHours++;
			//cout << "\n";
		}
	}
	priceListOne.advert[5].free[5] = false;
	View::PrintPriceList(priceListOne);

	cout << endl << endl << endl << endl;
	if (cust.days.find(Monday) != cust.days.end()) cout << "JEST MOnday \n";
	if (cust.days.find(Tuesday) != cust.days.end()) cout << "JEST TUESDAY \n";
	if (cust.days.find(Sunday) != cust.days.end()) cout << "JEST kurwa niedziela\n";
	else cout << "nie ma na szczesciu\n";
	cout << "Hello World!\n";
	system("PAUSE");
	return 0;
}

