import { Component, OnInit } from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {CustomerService} from '../Services/customer.service';
import {Customer} from '../logic/models/Customer';
import {GlobalVariables} from '../logic/global-variables';
import {Router} from '@angular/router';

@Component({
  selector: 'app-sign-customer',
  templateUrl: './sign-customer.component.html',
  styleUrls: ['./sign-customer.component.css']
})
export class SignCustomerComponent implements OnInit {
  signinForm;
  signupForm;
  private customer: Customer;
  constructor(private customerService: CustomerService,
              private formBuilder: FormBuilder,
              private router: Router) {
    this.signinForm = this.formBuilder.group({
      name: ''
    });
    this.signupForm = this.formBuilder.group({
      name: '',
      street: '',
      homeNumber: '',
      postcode: '',
      city: '',
      email: '',
      phone: ''
    });
  }

  ngOnInit() {
  }

  onSubmit(value: any) {
    console.warn('Your order has been submitted', value);

    this.customerService.postLogin(value).subscribe(
      res => {
        console.log(res);
        this.customerService.token = res;
        this.router.navigate(['/customer']);
      },
      err => {
        console.log('ERROR DURING REQUEST');
      }
    );
  }

  onSubmitSignUp(value: any) {
    console.warn('Your order has been submitted', value);

    this.customerService.postSignUp(value).subscribe(
      res => {
        this.customer = res as Customer;
        console.log(res);
        this.customerService.token = this.customer.token;
        this.router.navigate(['/customer']);
      },
      err => {
        console.log('ERROR DURING REQUEST');
      }
    );
  }
}
