#ifndef SAVEMANAGER_H
#define SAVEMANAGER_H

#include "PriceList.h"

class SaveManager
{
public:
	SaveManager();
	~SaveManager();

	static void SavePriceList(bool priceListOneActive, PriceList priceList);
	static void SaveCustomers(int numberOfCustomers, Customer cust[100]);
	static void SaveCustomers(int numberOfCustomers, Customer cust[100], bool deadLetter, int id);
};


#endif