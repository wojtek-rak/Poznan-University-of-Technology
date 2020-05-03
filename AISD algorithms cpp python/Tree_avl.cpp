#include<stdio.h>
#include<stdlib.h>
#include <iostream>
#include <cstdlib>
#include <fstream>
#include <ctime>
#include <sstream>  



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

int tab[15501];
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
	ofstream out("output_wysokoœæ drzewa bst.txt");
	
	for (int i = 0; i < 30; i++){
		
		start = clock();
		
		for (int j = 0; j < 500 + 500 * i; j++){
			infile>>date;
			root[i] = insert(root[i], date);
		}
		out << height(root[i])<<endl;
		printf("\n wysokoœæ %u \n", height(root[i]));
	}
	
	infile.close();
    out.close();
	
	///AVL
	
	
	struct tree *root_avl[30] = { NULL, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, NULL, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, NULL, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL};

    
	ofstream out_avl("output_wysokoœæ drzewa avl.txt");
	
	for (int i = 0; i < 30; i++){
		index = 0;
		inorder_avl(root[i]);
		start = clock();
		int *avl_tab = new int[index];
		parting(tab, avl_tab, 0, index, 0);
		
		for (int j = 0; j < 500 + 500 * i; j++){
			root_avl[i] = insert(root_avl[i], avl_tab[j]);
		}
		out_avl << height(root_avl[i])<<endl;
		printf("\n wysokoœæ %u \n", height(root_avl[i]));
	}
	

    out_avl.close();
	
	
	printf("inorder: \n");
	inorder(root[0]);
	
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
