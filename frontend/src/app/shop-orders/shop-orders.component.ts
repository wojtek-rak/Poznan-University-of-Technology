import { Component, OnInit } from '@angular/core';
import {CustomerService} from '../Services/customer.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-shop-orders',
  templateUrl: './shop-orders.component.html',
  styleUrls: ['./shop-orders.component.css']
})
export class ShopOrdersComponent implements OnInit {

  private orders: Order[] = [];

  constructor(private customerService: CustomerService, private router: Router) { }

  ngOnInit() {
    console.log(this.customerService.token);
    if (!this.customerService.token) {
      this.router.navigate(['/customer-sign']);
    } else {
      this.getData();
    }

  }

  private getData() {
    this.customerService.getOrders().subscribe(
      res => {
        this.orders = res as Order[];
        console.log(res);
      },
      err => {
        console.log(err);
      }
    );
  }
}
