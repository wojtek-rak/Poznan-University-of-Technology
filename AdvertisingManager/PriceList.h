#ifndef PRICELIST_H
#define PRICELIST_H
#include "Customer.h"
#include "AdvertEntity.h"
#include <iostream>
#include <set>
#include <list>

class PriceList
{
public:
	PriceList();
	~PriceList();

	AdvertEntity advert[24];
};

#endif