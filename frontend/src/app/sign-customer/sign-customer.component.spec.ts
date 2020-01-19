import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignCustomerComponent } from './sign-customer.component';

describe('SignCustomerComponent', () => {
  let component: SignCustomerComponent;
  let fixture: ComponentFixture<SignCustomerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignCustomerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
