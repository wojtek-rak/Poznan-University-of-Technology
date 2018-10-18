#ifndef VIEW_H
#define VIEW_H

#include "PriceList.h"

class View
{
public:
	View();
	~View();

	static void PrintPriceList(PriceList priceList);
	static void gotoXY(int x, int y);
	static void MenuStart(int menu_item);
	static void MenuPriceList(int menu_item);
	static void PrintHello();
	static void MenuAdvertismentPlan(int menu_item);
	static void MenuCustomers(int menu_item);
	static void EditPriceList(int menu_item, int menu_up_item, PriceList priceList, bool editingList, bool wrongPrice);
	static void PrintCustomerList(Customer cust[100], int numberOfCustomers);

};

#endif