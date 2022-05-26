import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { MatSnackBarModule } from '@angular/material/snack-bar';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { FuncionariosComponent } from './funcionarios/funcionarios.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { HttpClientInterceptor } from './http-client-interceptor';
import { VagasComponent } from './vagas/vagas.component';
import { SelecionarvagaComponent } from './selecionarvaga/selecionarvaga.component';
import { TiposvagasComponent } from './tiposvagas/tiposvagas.component';
import { RelatoriovagasComponent } from './relatoriovagas/relatoriovagas.component';
import { CaixaComponent } from './caixa/caixa.component';


@NgModule({
  declarations: [
    AppComponent,
    VagasComponent,
    FuncionariosComponent,
    HeaderComponent,
    LoginComponent,
    HomeComponent,
    SelecionarvagaComponent,
    TiposvagasComponent,
    RelatoriovagasComponent,
    CaixaComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatCardModule,
    MatInputModule,
    HttpClientModule,
    MatSnackBarModule,
    FormsModule,
    MatIconModule,
    NgxWebstorageModule.forRoot(),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpClientInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
