import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

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

  constructor(private authService: AuthService, private router: Router) {
    this.loginRequest = {
      usuario: '',
      senha: '',
    };
  }

  ngOnInit(): void {}

  public onSubmit(loginForm: NgForm) {
    this.loginRequest.usuario = loginForm.value.usuario;
    this.loginRequest.senha = loginForm.value.senha;
    this.authService.login(this.loginRequest).subscribe((data) => {
      if (data) {
        console.log('Sucesso ao realizar o login');
        this.router.navigateByUrl('gerencia/funcionarios/cadastro');
      } else {
        console.log('Falha ao realizar o login');
      }
    });
  }
}
