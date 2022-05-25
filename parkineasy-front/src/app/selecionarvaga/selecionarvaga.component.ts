import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PagamentoResponse } from '../vagas/pagamento-response';

import { TicketResponse } from '../vagas/ticket-response';
import { VagasService } from '../vagas/vagas.services';

interface ticketObj {
  id: string;
  codigo: string;
  date: string;
  time: string
}

@Component({
  selector: 'app-selecionarvaga',
  templateUrl: './selecionarvaga.component.html',
  styleUrls: ['./selecionarvaga.component.css'],
})
export class SelecionarvagaComponent implements OnInit {
  public listaVagas: Array<string>;
  public sub: any;
  public id: number;
  public pageContent = 'initial'
  public ticket: ticketObj = {
    id: '',
    codigo: '',
    date: '',
    time: ''
  }; 
  public showModal = false
  public mtdPag = ''
  public comprovante: PagamentoResponse = {
    codigoVaga: '',
    dataPagamento: new Date(),
    valor: 0,
    metodoPagamento: ''
  }
  public ticketValue = 0;
  public vagasOcupadas: string[] = [];
  public vagasGerais: any[] = [];

  constructor(
    private vagasService: VagasService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.id = 0;
    this.listaVagas = [];
  }
  public vagaType = window.history.state.vagaType;
  public chosenVaga = '';

  ngOnInit(): void {
    this.sub = this.route.params.subscribe((params) => {
      this.id = +params['id'];
    });
    this.vagasService.listarVagasPorTipo(this.id).subscribe((params) => {
      this.listaVagas = params;
    });
    this.vagasService.listarVagasOcupadas().subscribe((values) => {
      this.vagasOcupadas = values.map(item => item.codigo)
    });
    this.vagasService.listarVagasOrdenadasPrefixo().subscribe((values) => {
      this.vagasGerais = [...values]
    });
  }

  escolherVaga(value: any): void{
    this.chosenVaga = value;
  }

  voltar(): void {
    this.router.navigateByUrl('/home');
  }

  abrirModal(): void {
    this.calcular()
  }

  fecharModal(): void {
    this.showModal = false
  }

  pagDinheiro(): void {
    this.mtdPag = 'DINHEIRO';
    this.showModal = false;
    this.pagar()
  }

  pagCartao(): void {
    this.mtdPag = 'CARTAO';
    this.showModal = false;
    this.pagar()
  }

  pagPix(): void {
    this.mtdPag = 'PIX';
    this.showModal = false;
    this.pagar()
  }

  calcular(): boolean {
    this.vagasService.calcularValor(this.ticket.id).subscribe({
      next: (value: number) => {
        this.ticketValue = value
        this.showModal = true
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
        return false;
      },
    });
    return true;
  }

  confirmar(addForm: NgForm): boolean {
    console.log(addForm)
    this.vagasService.confirmarTicket(addForm.value).subscribe({
      next: (response: TicketResponse) => {
        let dataHora = new Date(response.dataHora);
        let month = (dataHora.getMonth() + 1) < 10 ? '0'+(dataHora.getMonth() + 1) : (dataHora.getMonth() + 1)
        const ticketData = {
          id: response.id.toString(),
          codigo: response.codigoVaga,
          date: dataHora.getDate() +
          '/' +
          month +
          '/' +
          dataHora.getFullYear(),
          time: dataHora.getHours() +
          ':' +
          dataHora.getMinutes() +
          ':' +
          dataHora.getSeconds()
        }
        this.ticket = {...ticketData}
        this.pageContent = 'ticket'
        // this.router.navigateByUrl('/home');
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
        return false;
      },
    });
    return true;
  }

  pagar(): boolean {
    this.vagasService.pagarTicket({ticketId: this.ticket.id, vagaId: this.ticket.codigo, metodoPagamento: this.mtdPag, valor: this.ticketValue}).subscribe({
      next: (response: PagamentoResponse) => {
        const stringPagamento = response.dataPagamento.toString()
        const arrData = stringPagamento.split(',')
        const dataPagamento = `${arrData[2]}/${parseInt(arrData[1]) < 10 ? '0'+arrData[1] : arrData[1]}/${arrData[0]} - ${arrData[3]}:${parseInt(arrData[4]) < 10 ? '0'+arrData[4] : arrData[4]}:${parseInt(arrData[5]) < 10 ? '0'+arrData[5] : arrData[5]}`
        this.comprovante = {...response}
        this.comprovante.dataPagamento = dataPagamento
        this.pageContent = 'comprovante'
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
        return false;
      },
    });
    return true;
  }

}
