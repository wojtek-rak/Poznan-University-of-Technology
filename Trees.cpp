#include<stdio.h>
#include<stdlib.h>
#include <iostream>
#include <cstdlib>
#include <fstream>
#include <ctime>
#include <sstream>  

int tab[1000];

using namespace std; 

struct tree
{
	int key;
	struct tree *left, *right;
};

struct tree *new_tree(int item)
{
	struct tree *temp = (struct tree *)malloc(sizeof(struct tree));
	temp->key = item;
	temp->left = temp->right = NULL;
	return temp;
}


void inorder(struct tree *root)
{
	if (root != NULL)
	{
		inorder(root->left);
		printf("%d ", root->key);
		inorder(root->right);
	}
}



void preorder(struct tree *root)
{
	if (root != NULL)
	{
		printf("%d ", root->key);
		preorder(root->left);
		preorder(root->right);
	}
}

void postorder(struct tree *root)
{
	if (root != NULL)
	{
		postorder(root->left);
		postorder(root->right);
		printf("%d ", root->key);
	}
}

//dodawanie
tree* insert(struct tree* root, int key)
{

	if (root == NULL) return new_tree(key);

	if (key < root->key)
		root->left = insert(root->left, key);
	else
		root->right = insert(root->right, key);

	return root;
}

//leci w dó³
tree * minValueNode(struct tree* root)
{
	struct tree* current = root;

	while (current->left != NULL)
		current = current->left;

	return current;
}


tree* deleteNode(struct tree* root, int key)
{

	if (root == NULL) return root;


	//szukanie
	if (key < root->key)
		root->left = deleteNode(root->left, key);

	else if (key > root->key)
		root->right = deleteNode(root->right, key);

	else
	{
		// nie ma prawego albo lewego
		if (root->left == NULL)
		{
			struct tree *temp = root->right;
			free(root);
			return temp;
		}
		else if (root->right == NULL)
		{
			struct tree *temp = root->left;
			free(root);
			return temp;
		}

		// subtree z elementem z do³u
		struct tree* temp = minValueNode(root->right);

		// dó³ do usuniêtego
		root->key = temp->key;

		// usuwa najmniejszy z do³u
		root->right = deleteNode(root->right, temp->key);
	}
	return root;
}



void parting(int tab1[], int tab2[], int b, int e, int i) {
	int m = (b + e) / 2;
	tab2[i] = tab1[m];
	if (m>b)
		parting(tab1, tab2, b, m - 1, i + 1);
	if (m<e)
		parting(tab1, tab2, m + 1, e, i + m - b + 1);
}


void find_BST(struct tree *root, int szukane) { 

	while ((root->key != szukane)) {
		if (szukane < (root->key))
			root = root->left;
		else
			root = root->right;
	}

}

int max(int a, int b) {
	if (a > b) return a;
	else return b;
}

int index = 0;
int post_tab[15501];
void postorder_tab(struct tree *root)
{
	if (root != NULL)
	{
		postorder_tab(root->left);
		postorder_tab(root->right);
		post_tab[index++] = root->key;
	}
}

void inorder_avl(struct tree *root)
{
	if (root != NULL)
	{
		inorder_avl(root->left);
		tab[index++] = root->key;
		inorder_avl(root->right);
	}
}

int height(struct tree *root) {

	if (root == NULL)
		return 0;
	else
		return (1 + max(height(root->left), height(root->right)));
}



int main()
{


	struct tree *root[30] = { NULL, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, NULL, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, NULL, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL};
	float czas[30];
	
	int date;
	
	//for (int i=0; i<31; i++){}

	
    ifstream infile("data.txt");
    
	clock_t start = clock();
	
	ofstream out("output_czas_tworzenia.txt");
	
	for (int i = 0; i < 30; i++){
		
		start = clock();
		
		for (int j = 0; j < 500 + 500 * i; j++){
			infile>>date;
			root[i] = insert(root[i], date);
		}
		
		out << clock() - start<<endl;
		printf("\n czas tworzenia %lu \n", clock() - start);
	}
	
	infile.close();
    out.close();
    
    printf("inorder: \n");
	inorder(root[2]);
	
    ifstream infile_szukanie("data.txt");
    ofstream out_szukanie("output_czas_szukania.txt");
	
	for (int i = 0; i < 30; i++){
		
		start = clock();
		
		for (int j = 0; j < 500 + 500 * i; j++){
			infile_szukanie>>date;
			find_BST(root[i], date);
		}
		
		//printf("\n czas %lu \n", clock() - start);
		out_szukanie << clock() - start<<endl;
		printf("\n czas szukania %lu \n", clock() - start);
	}
	
	
    out_szukanie.close();
	infile_szukanie.close();
	
	
    ofstream out_usuwanie("output_czas_uwuwania.txt");
	
	for (int i = 0; i < 30; i++){
		index = 0;
		//int post_tab[15501];
		postorder_tab(root[i]); // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		
		start = clock();
		
		for (int j = 0; j < 500 + 500 * i; j++){
			root[i] = deleteNode(root[i], post_tab[j]);
		}
		
		//printf("\n czas %lu \n", clock() - start);
		out_usuwanie << clock() - start<<endl;
		printf("\n czas usuwania %lu \n", clock() - start);
	}
	
	
    out_usuwanie.close();
	
	printf("inorder: \n");
	inorder(root[2]);
	
	/*while(infile>>date){
		root = insert(root, date);
	}*/
	/*czas = clock() - start;
	printf("\n czas %f \n", czas);
	printf("\n czas %lu \n", clock() - start);*/
	//avl test
	/*root = insert(root, 10);
	root = insert(root, 8);
	root = insert(root, 7);
	root = insert(root, 6);
	root = insert(root, 5);
	root = insert(root, 4);
	root = insert(root, 3);*/
	
	
	/*
	printf("inorder: \n");
	inorder(root);

	printf("\npreorder: \n");
	preorder(root);

	printf("\nPostorder: \n");
	postorder(root);

	/*printf("\nDelete 10\n");
	root = deleteNode(root, 10);

	printf("Postorder: \n");
	postorder(root);

	printf("\nDelete 3\n");
	root = deleteNode(root, 3);

	printf("Postorder: \n");
	postorder(root);*/
/*
	inorder_avl(root);
	printf("\n %u \n", index);

	for (int i = 0; i < index; i++)
	{
		printf("%u, ", tab[i]);
	}

	int *avl_tab = new int[index];
	parting(tab, avl_tab, 0, index, 0);

	printf("\n");
	for (int i = 0; i < index; i++)
	{
		printf("%u, ", avl_tab[i]);
	}

	struct tree *root_avl = NULL;
	for (int i = 0; i < index; i++)
	{
		root_avl = insert(root_avl, avl_tab[i]);
	}

	printf("\n height of root: %u, ", height(root));
	printf("\n height of root_avl : %u, \n", height(root_avl));
	
	find_BST(root, 10);
*/
	system("PAUSE");
	return 0;
}
