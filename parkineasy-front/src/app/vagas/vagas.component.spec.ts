import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { VagasComponent } from './vagas.component';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [VagasComponent],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(VagasComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'parkineasy-front'`, () => {
    const fixture = TestBed.createComponent(VagasComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('parkineasy-front');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(VagasComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.content span')?.textContent).toContain(
      'parkineasy-front app is running!'
    );
  });
});
