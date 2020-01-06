import { Component, OnInit } from '@angular/core';
import {ManagerService} from '../Services/manager.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  private wareHouseCodes: WareHouseCode[] = [];

  constructor(private managerService: ManagerService, private router: Router) { }

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

  addToWarehouses() {

  }
}
