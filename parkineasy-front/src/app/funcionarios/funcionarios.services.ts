import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

import { FuncionarioRequest } from './funcionario-request';
import { FuncionarioResponse } from './funcionario-response';

@Injectable({ providedIn: 'root' })
export class FuncionariosServices {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  public addFuncionario(
    funcionarioRequest: FuncionarioRequest
  ): Observable<FuncionarioResponse> {
    return this.http.post<FuncionarioResponse>(
      `${this.apiServerUrl}/api/v1/gerencia/funcionarios`,
      funcionarioRequest
    );
  }
}
