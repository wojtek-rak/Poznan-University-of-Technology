#include<iostream>
#include "Customer.h"
#include<set>
#include<string>

using namespace std;


Customer::Customer() : budget(0), spotsLength(0) {
}
Customer::Customer(int budget, int spotslength, string name)
{
	this->budget = budget;
	this->spotsLength = spotslength;
	Name(name);
}
Customer::~Customer() {}
//set<DayNames> days;
//set<HoursEnums> hours;
void Customer::Budget(int value)
{
	budget = value;
}
int Customer::Budget()
{
	return budget;
}
void Customer::SpotsLength(int value)
{
	spotsLength = value;
}
int Customer::SpotsLength()
{
	return spotsLength;
}
void Customer::Name(string value)
{
	name = value;
}
string Customer::Name()
{
	return name;
}
