import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TiposvagasComponent } from './tiposvagas.component';

describe('TiposvagasComponent', () => {
  let component: TiposvagasComponent;
  let fixture: ComponentFixture<TiposvagasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TiposvagasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TiposvagasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
