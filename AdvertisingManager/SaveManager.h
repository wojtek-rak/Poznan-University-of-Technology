#ifndef SAVEMANAGER_H
#define SAVEMANAGER_H

#include "PriceList.h"

class SaveManager
{
public:
	SaveManager();
	~SaveManager();

	static void SavePriceList(bool priceListOneActive, PriceList priceList);
};


#endif