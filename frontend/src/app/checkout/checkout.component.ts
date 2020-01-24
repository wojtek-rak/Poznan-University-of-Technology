import { Component, OnInit } from '@angular/core';
import {CustomerService} from '../Services/customer.service';
import {Router} from '@angular/router';
import {Address, Customer} from '../logic/models/Customer';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  private fail = false;
  checkoutForm;
  private cart: Cart;
  private order: Order;

  constructor(private customerService: CustomerService,
              private formBuilder: FormBuilder,
              private router: Router) {
    this.checkoutForm = this.formBuilder.group({
      street: '',
      homeNumber: '',
      postcode: '',
      city: '',
      email: '',
      phone: ''
    });
  }

  ngOnInit() {
    console.log(this.customerService.token);
    if (!this.customerService.token) {
      this.router.navigate(['/customer-sign']);
    } else {
      this.getData();
    }
  }

  getData() {

    this.customerService.getCartProducts().subscribe(
      res => {
        this.cart = res as Cart;
        console.log(res);
      },
      err => {
        console.log(err);
      }
    );
  }

  checkout(value: any) {
    const address: Address = {
      street: value.street,
      homeNumber: value.homeNumber,
      postcode: value.postcode,
      city: value.city,
      email: value.email
    };

    this.customerService.putCheckoutCart(address).subscribe(
      res => {
        this.order = res as Order;
        console.log(res);
        this.router.navigate(['/shop-orders']);
      },
      err => {
        console.log('ERROR DURING REQUEST');
        if(err.status === 409)
        {
          this.showErrorDup();
        }
      }
    );

  }

  showErrorDup() {
    this.fail = true;
  }
}
