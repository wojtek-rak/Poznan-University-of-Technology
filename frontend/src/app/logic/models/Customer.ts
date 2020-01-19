export interface Address {
  street: string;
  homeNumber: string;
  postcode: string;
  city: string;
  email: string;
}

export interface Customer {
  name: string;
  address: Address;
  phone: string;
  token: string;
}
