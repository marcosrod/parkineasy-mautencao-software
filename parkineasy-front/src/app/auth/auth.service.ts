import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { map, Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { LoginRequest } from './login/login-request';
import { LoginResponse } from './login/login-response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private BASE_URL = environment.apiBaseUrl;
  private LOGIN_URI = '/auth/login';
  private authenticationTokenKey = 'authenticationToken';
  private usuarioKey = 'usuario';

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) {}

  public login(loginRequest: LoginRequest): Observable<boolean> {
    return this.httpClient
      .post<LoginResponse>(this.BASE_URL + this.LOGIN_URI, loginRequest)
      .pipe(
        map((loginResponse: LoginResponse) => {
          this.localStorageService.store(
            this.authenticationTokenKey,
            loginResponse.authenticationToken
          );
          this.localStorageService.store(
            this.usuarioKey,
            loginResponse.usuario
          );
          return true;
        })
      );
  }

  public logout() {
    this.localStorageService.clear(this.authenticationTokenKey);
    this.localStorageService.clear(this.usuarioKey);
  }

  public isAuthenticated(): boolean {
    return (
      this.localStorageService.retrieve(this.authenticationTokenKey) !== null &&
      this.localStorageService.retrieve(this.usuarioKey) !== null
    );
  }

  public getUsername(): string {
    return this.localStorageService.retrieve(this.usuarioKey);
  }
}
