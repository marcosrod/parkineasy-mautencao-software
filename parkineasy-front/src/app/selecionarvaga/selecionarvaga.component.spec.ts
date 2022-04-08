import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelecionarvagaComponent } from './selecionarvaga.component';

describe('SelecionarvagaComponent', () => {
  let component: SelecionarvagaComponent;
  let fixture: ComponentFixture<SelecionarvagaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelecionarvagaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelecionarvagaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
