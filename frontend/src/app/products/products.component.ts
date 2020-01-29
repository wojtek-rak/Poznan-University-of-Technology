import { Component, OnInit } from '@angular/core';
import {ManagerService} from '../Services/manager.service';
import {Router} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {Address, Customer} from '../logic/models/Customer';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  private wareHouseCodes: WareHouseCode[] = [];

  fillForm;
  constructor(private managerService: ManagerService, private router: Router, private formBuilder: FormBuilder) {
    this.fillForm = this.formBuilder.group({
      amount: 0
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
    this.managerService.getWareHouseProducts().subscribe(
      res => {
        this.wareHouseCodes = res as WareHouseCode[];
        console.log(res);
      },
      err => {
        console.log('ERROR DURING REQUEST');
      }
    );
  }


  onSubmitFillForm(value: any) {
    console.warn('Your order has been submitted', value);



    this.managerService.getFillPorducts(value).subscribe(
      res => {
        this.wareHouseCodes = res as WareHouseCode[];
        console.log(res);

      },
      err => {
        console.log(err);
      }
    );
  }
}
