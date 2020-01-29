import { Component, OnInit } from '@angular/core';
import {CustomerService} from '../Services/customer.service';
import {Router} from '@angular/router';
import {ManagerService} from '../Services/manager.service';
import {Address, Customer} from '../logic/models/Customer';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css']
})
export class ManagerComponent implements OnInit {

  signupForm;
  private fail = false;
  private managerOrder: ManagerOrder[] = [];

  constructor(private managerService: ManagerService, private router: Router, private formBuilder: FormBuilder) {
    this.signupForm = this.formBuilder.group({
      name: '',
      username: '',
      password: ''
    });
  }

  ngOnInit() {
    console.log(this.managerService.managerToken);
    if (!this.managerService.managerToken) {
      this.router.navigate(['/manager-sign']);
    } else {
      this.getData();
    }

  }

  getData() {
    this.managerService.getShopOrders().subscribe(
      res => {
        this.managerOrder = res as ManagerOrder[];
        console.log(res);
      },
      err => {
        console.log('ERROR DURING REQUEST');
      }
    );
  }

  onSubmitSignUp(value: any) {
    console.warn('Your order has been submitted', value);
    console.log(value);
    this.managerService.postSignUp(value).subscribe(
      res => {
        console.log(res);
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
