#ifndef VIEW_H
#define VIEW_H

#include "PriceList.h"

class View
{
public:
	View();
	~View();

	static void PrintPriceList(PriceList priceList);
};

#endif