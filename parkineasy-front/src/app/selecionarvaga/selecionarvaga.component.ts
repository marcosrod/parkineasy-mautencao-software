import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { TicketResponse } from '../vagas/ticket-response';
import { VagasService } from '../vagas/vagas.services';

@Component({
  selector: 'app-selecionarvaga',
  templateUrl: './selecionarvaga.component.html',
  styleUrls: ['./selecionarvaga.component.css'],
})
export class SelecionarvagaComponent implements OnInit {
  public listaVagas: Array<string>;
  public sub: any;
  public id: number;
  constructor(
    private vagasService: VagasService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.id = 0;
    this.listaVagas = [];
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe((params) => {
      this.id = +params['id'];
    });
    this.vagasService.listarVagasPorTipo(this.id).subscribe((params) => {
      this.listaVagas = params;
    });
  }

  voltar(): void {
    this.router.navigateByUrl('/home');
  }

  confirmar(addForm: NgForm): boolean {
    this.vagasService.confirmarTicket(addForm.value).subscribe({
      next: (response: TicketResponse) => {
        let dataHora = new Date(response.dataHora);
        let resposta = `
        Ticket criado com sucesso!
        ID: ${response.id}
        Código da vaga: ${response.codigoVaga}
        Data: ${
          dataHora.getDate() +
          '/' +
          (dataHora.getMonth() + 1) +
          '/' +
          dataHora.getFullYear()
        }
        Hora: ${
          dataHora.getHours() +
          ':' +
          dataHora.getMinutes() +
          ':' +
          dataHora.getSeconds()
        }
        `;
        alert(resposta);
        this.router.navigateByUrl('/home');
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
        return false;
      },
    });
    return true;
  }
}
