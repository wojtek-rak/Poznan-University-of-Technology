#include <iostream>
#include <cstdio>
#include <ctime>
int tab[1000];
using namespace std;
struct lista {
	int key;
	lista *next;
};
void tolist(lista *&x, int key) {
	if (x == NULL) {
		x = new lista;
		x->key = key;
		x->next = NULL;

	}
	else {
		if (key >= x->key) tolist(x->next, key);
		else {
			tolist(x->next, x->key);
			x->key = key;
		}
	}
}
void usun_pierwszy(lista* &x)
{
	lista *tmp; // NIe trzeba tworzyc !! po co ? = new element;
	while(x != NULL)
	{
		//cout << "Lista jest pusta :( ";
		tmp = x->next;
		delete x;
		x = tmp;
	}
	printf("usuniete");
}
struct drzewo {
	int klucz;
	drzewo *lewy, *prawy;
};
void readlist(lista *x) {
	if (x != NULL) {
		cout << x->key << " ";
		readlist(x->next);
	}
}
//drzewo *x = NULL;
lista *x = NULL;
void Insert(drzewo *&x, int klucz) {
	if (x == NULL) {
		x = new drzewo;
		x->klucz = klucz;
		x->lewy = NULL;
		x->prawy = NULL;
	}
	else {
		if (klucz >= x->klucz) Insert(x->prawy, klucz);
		if (klucz<x->klucz) Insert(x->lewy, klucz);
	}
}

void inorder(drzewo *x) {
	if (x != NULL) {
		inorder(x->lewy);
		cout << x->klucz << " ";
		inorder(x->prawy);
	}
}

void preorder(drzewo *x) {
	if (x != NULL) {
		cout << x->klucz << " ";
		preorder(x->lewy);
		preorder(x->prawy);
	}
}

void postorder(drzewo *x) {
	if (x != NULL) {
		postorder(x->lewy);
		postorder(x->prawy);
		cout << x->klucz << " ";
	}
}

int main()
{
	/*int a;
	cin >> a;
	while (a != 5) {
		Insert(x, a);
		cin >> a;
	}
	//inorder(x);
	preorder(x);
	//postorder(x);*/
	int i = 0,n;
	while (i < 5)
	{
		cin >> n;
		tolist(x, n);
		i++;
	}
	clock_t start = clock();
	readlist(x);
	usun_pierwszy(x);
	readlist(x);
	printf("Czas wykonywania: %lu ms\n", clock() - start);
	system("pause");
	return 0;
}
