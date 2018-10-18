#include "SaveManager.h"
#include <fstream>
using namespace std;

SaveManager::SaveManager()
{
}


SaveManager::~SaveManager()
{
}

void SaveManager::SaveCustomers(int numberOfCustomers, Customer cust[100])
{
	fstream outFileEvent("customer.txt", ios::out);
	for (int i = 0; i < numberOfCustomers + 1; i++)
	{
		if (outFileEvent.good())
		{
			outFileEvent << "n " << cust[i].Name() << " b " << cust[i].Budget() << " l " << cust[i].SpotsLength() << " p " << cust[i].CheapPlan;
			for (auto f : cust[i].days) {
				outFileEvent << " d " << f;
			}
			for (auto f : cust[i].hours) {
				outFileEvent << " h " << f;
			}
			outFileEvent << "\n";
			outFileEvent.flush();

		}
	}
	outFileEvent.close();
}


void SaveManager::SaveCustomers(int numberOfCustomers, Customer cust[100], bool deadLetter, int id)
{
	fstream outFileEvent("customer.txt", ios::out);
	for (int i = 0; i < numberOfCustomers + 1; i++)
	{
		if (outFileEvent.good())
		{
			if (deadLetter && id == i) continue;
			outFileEvent << "n " << cust[i].Name() << " b " << cust[i].Budget() << " l " << cust[i].SpotsLength() << " p " << cust[i].CheapPlan;
			for (auto f : cust[i].days) {
				outFileEvent << " d " << f;
			}
			for (auto f : cust[i].hours) {
				outFileEvent << " h " << f;
			}
			outFileEvent << "\n";
			outFileEvent.flush();

		}
	}
	outFileEvent.close();
}

void SaveManager::SavePriceList(bool priceListOneActive, PriceList priceList)
{
	if (priceListOneActive)
	{
		fstream outFileEvent("data.txt", ios::out);
		if (outFileEvent.good())
		{
			for (int i = 0; i < 24; i++)
			{
				for (int j = 0; j < 7; j++)
				{
					outFileEvent << priceList.advert[i].price[j] << " ";
					outFileEvent.flush();
				}
				outFileEvent << "\n";
				outFileEvent.flush();

			}
			outFileEvent.close();
		}
		fstream outFileEventTime("dataTime.txt", ios::out);
		if (outFileEventTime.good())
		{
			for (int i = 0; i < 24; i++)
			{
				for (int j = 0; j < 7; j++)
				{
					outFileEventTime << priceList.advert[i].free[j] << " ";
					outFileEventTime.flush();
				}
				outFileEventTime << "\n";
				outFileEventTime.flush();

			}
			outFileEventTime.close();
		}
	}
	else
	{
		fstream outFileEvent("dataEvent.txt", ios::out);
		if (outFileEvent.good())
		{
			for (int i = 0; i < 24; i++)
			{
				for (int j = 0; j < 7; j++)
				{
					outFileEvent << priceList.advert[i].price[j] << " ";
					outFileEvent.flush();
				}
				outFileEvent << "\n";
				outFileEvent.flush();

			}
			outFileEvent.close();
		}
		fstream outFileEventTime("dataEventTime.txt", ios::out);
		if (outFileEventTime.good())
		{
			for (int i = 0; i < 24; i++)
			{
				for (int j = 0; j < 7; j++)
				{
					outFileEventTime << priceList.advert[i].free[j] << " ";
					outFileEventTime.flush();
				}
				outFileEventTime << "\n";
				outFileEventTime.flush();

			}
			outFileEventTime.close();
		}
	}
}