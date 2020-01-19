import { Component, OnInit } from '@angular/core';
import {CustomerService} from '../Services/customer.service';
import {FormBuilder} from '@angular/forms';
import {Router} from '@angular/router';
import {ManagerService} from '../Services/manager.service';
import {Address, Customer} from '../logic/models/Customer';

@Component({
  selector: 'app-add-prduct',
  templateUrl: './add-prduct.component.html',
  styleUrls: ['./add-prduct.component.css']
})
export class AddPrductComponent implements OnInit {
  addProductForm;

  constructor(private managerService: ManagerService,
              private formBuilder: FormBuilder,
              private router: Router) {

    this.addProductForm = this.formBuilder.group({
      name: '',
      ean: 0,
      price: 0,
      vat: 0,
      percentDiscount: 0,
      categoryName: '',
      supplierName: '',
      count: 0,
      warehouseCode: 0
    });
  }
  ngOnInit() {
  }

  onSubmitSignUp(value: any) {
    console.warn('Your order has been submitted', value);

    const supplier: any = {
      name: value.supplierName
    };

    const category: any = {
      name: value.categoryName,
      supplier
    };
    const sale: any = {
      percentDiscount: value.percentDiscount
    };
    const  sales: Sales[] = [];
    sales.push(sale);

    const product: any = {
      name: value.name,
      ean: value.ean,
      price: value.price,
      vat: value.vat,
      category,
      sales
    };

    const warehousePRoduct: WarehouseProduct = {
      product,
      count: value.count,
      warehouseCode: value.warehouseCode
      }
    ;
    console.log(warehousePRoduct);
    this.managerService.postAddProduct(warehousePRoduct).subscribe(
      res => {
        console.log(res);
        this.router.navigate(['/products']);
      },
      err => {
        console.log('ERROR DURING REQUEST');
      }
    );
  }
}
