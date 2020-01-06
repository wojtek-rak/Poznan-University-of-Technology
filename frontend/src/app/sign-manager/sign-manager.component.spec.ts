import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignManagerComponent } from './sign-manager.component';

describe('SignManagerComponent', () => {
  let component: SignManagerComponent;
  let fixture: ComponentFixture<SignManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
