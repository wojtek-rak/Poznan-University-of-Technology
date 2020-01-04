import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {GlobalVariables} from '../logic/global-variables';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private host = GlobalVariables.host;

  constructor(private http: HttpClient) {}

  public getStoreProducts() {
    return this.http.get(this.host + 'store/products' );
  }

  public getStoreProductsByCategoryId(id: number) {
    return this.http.get(this.host + 'store/products?categoryId=' + id);
  }

  public getCartProducts() {
    // let headers: Headers;
    // headers = new Headers({
    //   'Authorization': ''//GlobalVariables.token
    // });

    let headers = new HttpHeaders();
    headers = headers.append(
      "Authorization",
      "Bearer " + "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJQYXRyY2phIiwicm9sZXMiOiJDVVNUT01FUiIsImlhdCI6MTU3ODE0Njg5M30.pTO6cytRb20LhgfoUF85adhNXHczwGykPx7ryu-oYS06tMoVBLVb5U9jL1fGQBVB"
    );
    headers = headers.append("Content-Type", "application/json");

    return this.http.get(this.host + 'my-profile/cart', { headers: headers });
  }

}
