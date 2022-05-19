import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatoriovagasComponent } from './relatoriovagas.component';

describe('RelatoriovagasComponent', () => {
  let component: RelatoriovagasComponent;
  let fixture: ComponentFixture<RelatoriovagasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RelatoriovagasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatoriovagasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
