import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {SelectRoleComponent} from './select-role/select-role.component';
import {CustomerComponent} from './customer/customer.component';
import {ManagerComponent} from './manager/manager.component';
import {SignCustomerComponent} from './sign-customer/sign-customer.component';
import {CheckoutComponent} from './checkout/checkout.component';
import {ShopOrdersComponent} from './shop-orders/shop-orders.component';

const routes: Routes = [
  {
    path: '',
    component: SelectRoleComponent,
  },
  {
    path: 'customer',
    component: CustomerComponent,
    data: { depth: 1},
  },
  {
    path: 'customer-sign',
    component: SignCustomerComponent,
    data: { depth: 2},
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
    data: { depth: 3},
  },
  {
    path: 'shop-orders',
    component: ShopOrdersComponent,
    data: { depth: 4},
  },
  {
    path: 'manager',
    component: ManagerComponent,
    data: { depth: 6},
  },
];


@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})

export class AppRoutingModule { }
