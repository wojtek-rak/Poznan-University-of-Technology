
#ifndef CUSTOMER_H
#define CUSTOMER_H

#include <string>
#include <set>

enum DayNames {
	Monday,
	Tuesday,
	Wednesday,
	Thursday,
	Friday,
	Saturday,
	Sunday
};
enum HoursEnums {
	H0001_0100,
	H0101_0200,
	H0201_0300,
	H0301_0400,
	H0401_0500,
	H0501_0600,
	H0601_0700,
	H0701_0800,
	H0801_0900,
	H0901_1000,
	H1001_1100,
	H1101_1200,
	H1201_1300,
	H1301_1400,
	H1401_1500,
	H1501_1600,
	H1601_1700,
	H1701_1800,
	H1801_1900,
	H1901_2000,
	H2001_2100,
	H2101_2200,
	H2201_2300,
	H2301_2400,
};

class Customer
{
public:
	Customer();
	Customer(int budget, int spotslength, std::string name);
	~Customer();
	
	void Budget(int value);
	int Budget();
	void SpotsLength(int value);
	int SpotsLength();
	void Name(std::string value);
	std::string Name();
	std::set<DayNames> days;
	std::set<HoursEnums> hours;
private:
	
	int budget;
	int spotsLength;
	std::string name;
};



#endif