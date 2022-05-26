import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

import { AuthService } from '../auth.service';
import { LoginRequest } from './login-request';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  private loginRequest: LoginRequest;
  public mostrarCampoSenha = true;

  constructor(private authService: AuthService, private router: Router, private snackBar: MatSnackBar) {
    this.loginRequest = {
      usuario: '',
      senha: '',
    };
  }

  ngOnInit(): void {}

  openSnackBar(message: string) {
    this.snackBar.open(message,'' ,{
      duration: 3000,
    });
  }

  public onSubmit(loginForm: NgForm) {
    this.loginRequest.usuario = loginForm.value.usuario;
    this.loginRequest.senha = loginForm.value.senha;
    this.authService.login(this.loginRequest).subscribe({
      next: () => {
          this.router.navigateByUrl('gerencia/funcionarios/mapa');
      },
      error: (error: any) => {
        this.openSnackBar('Usuário ou Senha Incorretos')
      },
    });
  }
}


