import {Component, OnInit, ViewChild} from '@angular/core';
import {CustomerService} from '../Services/customer.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  private products: Product[] = [];
  private loading = false;
  private fail = false;

  constructor(private customerService: CustomerService) { }

  ngOnInit() {
    this.getData();
  }

  getData() {

    this.customerService.getStoreProducts().subscribe(
      res => {
        this.products = res as Product[];
        console.log(res);
      },
      err => {
        this.showErrorMsg();
      }
    );

  }

  showErrorMsg() {
    this.loading = false;
    this.fail = true;
  }

}
