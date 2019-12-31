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

}
