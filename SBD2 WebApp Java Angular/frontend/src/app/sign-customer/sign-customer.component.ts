import { Component, OnInit } from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {CustomerService} from '../Services/customer.service';
import {Address, Customer} from '../logic/models/Customer';
import {GlobalVariables} from '../logic/global-variables';
import {Router} from '@angular/router';

@Component({
  selector: 'app-sign-customer',
  templateUrl: './sign-customer.component.html',
  styleUrls: ['./sign-customer.component.css']
})
export class SignCustomerComponent implements OnInit {
  private fail = false;
  private nameFail = false;
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
        if(err.status === 403)
        {
          this.showErrorName();
        }
      }
    );
  }

  onSubmitSignUp(value: any) {
    console.warn('Your order has been submitted', value);

    const address: Address = {
      street: value.street,
      homeNumber: value.homeNumber,
      postcode: value.postcode,
      city: value.city,
      email: value.email
    };

    const customer: Customer = {
      name: value.name,
      address,
      phone: value.phone,
      token: ''
    };

    this.customerService.postSignUp(customer).subscribe(
      res => {
        this.customer = res as Customer;
        console.log(res);
        this.customerService.token = this.customer.token;
        this.router.navigate(['/customer']);
      },
      err => {
        console.log(err);
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
  showErrorName() {
    this.nameFail = true;
  }
}
