import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

import { FuncionarioResponse } from './funcionario-response';
import { FuncionariosServices } from './funcionarios.services';

@Component({
  selector: 'app-funcionarios',
  templateUrl: './funcionarios.component.html',
  styleUrls: ['./funcionarios.component.css'],
})
export class FuncionariosComponent {
  constructor(private funcionariosServices: FuncionariosServices) {}

  public onAddFuncionario(addForm: NgForm): void {
    this.funcionariosServices.addFuncionario(addForm.value).subscribe({
      next: (response: FuncionarioResponse) => {
        let resposta = `
        Funcionário criado com sucesso!
        ID: ${response.id}
        Nome: ${response.nome}
        E-mail: ${response.email}
        Usuário: ${response.usuario}`;
        alert(resposta);
        window.location.reload();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
    });
  }
  title = 'parkineasy-front';
  url = './public/images/parkicon.png';
  hide: any;
}
