import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError, of } from 'rxjs';
import { environment } from '../../../../environnement';
import {
  AuthResponse,
  GenericResponse,
  LoginRequest,
  RegisterRequest,
  UserDto
} from '../models/souli.model';
import { SnackbarService } from './snackbar.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly snackbarService = inject(SnackbarService);
  private readonly authUrl = `${environment.apiUrl}/auth`;

  register(request: RegisterRequest): Observable<GenericResponse<UserDto>> {
    return this.http.post<GenericResponse<UserDto>>(`${this.authUrl}/register`, request).pipe(
      tap(response => this.snackbarService.handleSuccess(response.message)),
      catchError(error => {
        this.snackbarService.handleError(error.message);
        return of()
      })
    );
  }

  login(request: LoginRequest): Observable<GenericResponse<AuthResponse>> {
    return this.http.post<GenericResponse<AuthResponse>>(`${this.authUrl}/login`, request).pipe(
      tap(response => this.snackbarService.handleSuccess(response.message)),
      catchError(error => {
        this.snackbarService.handleError(error.message);
        return of();
      })
    );
  }
}
