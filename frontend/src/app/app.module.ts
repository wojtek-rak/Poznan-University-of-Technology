import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ManagerComponent } from './manager/manager.component';
import { CustomerComponent } from './customer/customer.component';
import { AppRoutingModule } from './app-routing.module';
import {RouterModule} from '@angular/router';
import { SelectRoleComponent } from './select-role/select-role.component';

@NgModule({
  declarations: [
    AppComponent,
    ManagerComponent,
    CustomerComponent,
    SelectRoleComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
