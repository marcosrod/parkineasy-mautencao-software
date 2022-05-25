import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

import { TicketRequest } from './ticket-request';
import { TicketResponse } from './ticket-response';
import { VagaRequest } from './vaga-request';
import { VagaResponse } from './vaga-response';
import { PagamentoRequest } from './pagamento-request';
import { PagamentoResponse } from './pagamento-response';

export interface listaVagas {
  codigo: string;
  ocupada: boolean;
  descricao: string
}

export interface editVagaProps {
  cod: string;
  tipo: string
}

@Injectable({ providedIn: 'root' })
export class VagasService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  public addVaga(vagaRequest: VagaRequest): Observable<VagaResponse> {
    return this.http.post<VagaResponse>(
      `${this.apiServerUrl}/api/v1/gerencia/vagas`,
      vagaRequest
    );
  }

  public editVaga({cod, tipo}: editVagaProps): Observable<VagaResponse> {
    return this.http.put<any>(
      `${this.apiServerUrl}/api/v1/gerencia/vagas/${cod}/alterar`,
      {tipo}
    );
  }

  public delVaga(cod: string) {
    return this.http.delete(
      `${this.apiServerUrl}/api/v1/gerencia/vagas/${cod}/excluir`);
  }

  public listarVagasPorTipo(id: number) {
    return this.http.get<Array<string>>(
      `${this.apiServerUrl}/api/v1/gerencia/vagas/tipovaga/${id}`
    );
  }
  public listarVagasOcupadas() {
    return this.http.get<Array<listaVagas>>(
      `${this.apiServerUrl}/api/v1/gerencia/vagas/ocupadas?ocupada=true`
    );
  }
  public listarVagasOrdenadas() {
    return this.http.get<Array<listaVagas>>(
      `${this.apiServerUrl}/api/v1/gerencia/vagas/ordenadas`
    );
  }

  public calcularValor(tickedId: string) {
    return this.http.get<number>(
      `${this.apiServerUrl}/api/v1/tickets/${tickedId}/calcular-valor`
    );
  }

  public confirmarTicket(
    ticketRequest: TicketRequest
  ): Observable<TicketResponse> {
    return this.http.post<TicketResponse>(
      `${this.apiServerUrl}/api/v1/tickets`,
      ticketRequest
    );
  }

  public pagarTicket(
    pagamentoRequest: PagamentoRequest
  ): Observable<PagamentoResponse> {
    return this.http.post<PagamentoResponse>(
      `${this.apiServerUrl}/api/v1/tickets/pagar`,
      pagamentoRequest
    );
  }
}
