#include "SaveManager.h"
#include <fstream>
using namespace std;

SaveManager::SaveManager()
{
}


SaveManager::~SaveManager()
{
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