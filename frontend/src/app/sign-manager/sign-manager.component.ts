import { Component, OnInit } from '@angular/core';
import {CustomerService} from '../Services/customer.service';
import {FormBuilder} from '@angular/forms';
import {Router} from '@angular/router';
import {Customer} from '../logic/models/Customer';
import {ManagerService} from '../Services/manager.service';

@Component({
  selector: 'app-sign-manager',
  templateUrl: './sign-manager.component.html',
  styleUrls: ['./sign-manager.component.css']
})
export class SignManagerComponent implements OnInit {

  signinForm;

  constructor(private managerService: ManagerService,
              private formBuilder: FormBuilder,
              private router: Router) {
    this.signinForm = this.formBuilder.group({
      username: '',
      password: ''
    });
  }

  ngOnInit() {
  }

  onSubmit(value: any) {


    this.managerService.postLogin(value).subscribe(
      res => {
        this.managerService.managerToken = res;
        this.router.navigate(['/manager']);
      },
      err => {
        console.log('ERROR DURING REQUEST');
      }
    );
  }
}
