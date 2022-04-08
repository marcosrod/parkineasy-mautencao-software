import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { VagaResponse } from './vaga-response';
import { VagasService } from './vagas.services';

@Component({
  selector: 'app-root',
  templateUrl: './vagas.component.html',
  styleUrls: ['./vagas.component.css'],
})
export class VagasComponent {
  title = 'parkineasy-front';
  url = './public/images/parkicon.png';

  constructor(private vagasService: VagasService, private router: Router) {}

  ngOnInit() {}

  public onAddVaga(addForm: NgForm): void {
    this.vagasService.addVaga(addForm.value).subscribe({
      next: (response: VagaResponse) => {
        let resposta = `
        Vaga criada com sucesso!
        Código da vaga: ${response.codigo}
        Descrição: ${response.descricao}`;
        alert(resposta);
        window.location.reload();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
    });
  }

  voltar(): void {
    this.router.navigateByUrl('/gerencia/funcionarios/cadastro');
  }
}
