import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';

import { FuncionarioResponse } from './funcionario-response';
import { FuncionariosServices } from './funcionarios.services';

@Component({
  selector: 'app-funcionarios',
  templateUrl: './funcionarios.component.html',
  styleUrls: ['./funcionarios.component.css'],
})
export class FuncionariosComponent {
  constructor(private funcionariosServices: FuncionariosServices,private snackBar: MatSnackBar) {}

  public onAddFuncionario(addForm: NgForm): void {
    this.funcionariosServices.addFuncionario(addForm.value).subscribe({
      next: (response: FuncionarioResponse) => {
        let resposta = `
        Funcionário criado com sucesso!
        ID: ${response.id}
        Nome: ${response.nome}
        E-mail: ${response.email}
        Usuário: ${response.usuario}`;
        this.openSnackBar('Cadastro Concluido')
        setTimeout(() => {
          window.location.reload();
        }, 3500)
      },
      error: (error: HttpErrorResponse) => {
        this.openSnackBar(error.error)
      },
    });
  }
  title = 'parkineasy-front';
  url = './public/images/parkicon.png';
  hide: any;

  openSnackBar(message: string) {
    this.snackBar.open(message,'' ,{
      duration: 3000,
    });
  }
  
}
