import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {GlobalVariables} from '../logic/global-variables';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient,
              private cookieService: CookieService) {

  }
  private host = GlobalVariables.host;

  private HttpHeaders;

  public get token() {
    return this.cookieService.get('Token');
  }
  public set token(value: string) {
    this.cookieService.set( 'Token', value );
  }

  public getStoreProducts() {
    return this.http.get(this.host + 'store/products' );
  }

  public getStoreProductsByCategoryId(id: number) {
    return this.http.get(this.host + 'store/products?categoryId=' + id);
  }

  public getCartProducts() {
    const token = this.token;
    const headers = this.getHeader(token);

    return this.http.get(this.host + 'my-profile/cart', { headers });
  }

  public postSignUp(body: any) {
    let headers = new HttpHeaders();
    headers = this.addContentTypeToHeader(headers);
    return this.http.post(this.host + 'sign-up', body, {headers});
  }

  public postLogin(body: any) {
    let headers = new HttpHeaders();
    headers = this.addContentTypeToHeader(headers);
    return this.http.post(this.host + 'login', body, {headers, responseType: 'text'});
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
    headers = headers.append("Content-Type", "application/json");
    return headers;
  }
}
