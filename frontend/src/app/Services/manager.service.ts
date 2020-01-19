import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CookieService} from 'ngx-cookie-service';
import {GlobalVariables} from '../logic/global-variables';

@Injectable({
  providedIn: 'root'
})
export class ManagerService {

  constructor(private http: HttpClient,
              private cookieService: CookieService) { }

  private host = GlobalVariables.host;

  public get managerToken() {
    return this.cookieService.get('ManagerToken');
  }
  public set managerToken(value: string) {
    this.cookieService.set( 'ManagerToken', value );
  }

  public postSignUp(body: any) {
    let headers = this.getHeader(this.managerToken);
    headers = this.addContentTypeToHeader(headers);
    return this.http.post(this.host + 'manager/secure/sign-up', body, {headers});
  }

  public postLogin(body: any) {
    let headers = new HttpHeaders();
    headers = this.addContentTypeToHeader(headers);
    return this.http.post(this.host + 'manager/login', body, {headers, responseType: 'text'});
  }

  public getShopOrders() {
    const headers = this.getHeader(this.managerToken);
    return this.http.get(this.host + 'manager/secure/shop-orders', {headers});
  }

  public getWareHouseProducts() {
    const headers = this.getHeader(this.managerToken);
    return this.http.get(this.host + 'manager/secure/warehouse/products', {headers});
  }

  public postAddProduct(body: any) {
    let headers = this.getHeader(this.managerToken);
    headers = this.addContentTypeToHeader(headers);
    return this.http.post(this.host + 'manager/secure/warehouse/products/add', body, {headers});
  }

  public getFillPorducts() {
    let headers = this.getHeader(this.managerToken);
    //headers = this.addContentTypeToHeader(headers);
    return this.http.post(this.host + '/manager/secure/warehouse/products/fill', {headers});
  }

  private getHeader(token: string): HttpHeaders {
    let headers = new HttpHeaders();
    headers = headers.append(
      'Authorization',
      'Bearer ' + token
    );
    return headers;
  }


  private addContentTypeToHeader(headers: HttpHeaders): HttpHeaders {
    headers = headers.append('Content-Type', 'application/json');
    return headers;
  }
}
