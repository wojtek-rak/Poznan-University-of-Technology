interface Product {
  id: number;
  name: string;
  ean: number;
  price: number;
  vat: number;
  category: Category;
  sales: Sales[];
  calculatedPrice: number;
  count: number;
}
