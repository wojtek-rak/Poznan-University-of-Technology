import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {SelectRoleComponent} from './select-role/select-role.component';
import {CustomerComponent} from './customer/customer.component';
import {ManagerComponent} from './manager/manager.component';

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
    path: 'manager',
    component: ManagerComponent,
    data: { depth: 2},
  },
];


@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})

export class AppRoutingModule { }
