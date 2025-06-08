import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { TokenService } from '../jwt/token.service';
import { environment } from '../../../environments/environment.development';

export interface VotoDTO {
  ip: string;
  idFotografia: number;
}

@Injectable({
  providedIn: 'root'
})
export class VotoService {

  private readonly _http = inject(HttpClient);
  private readonly _tokenService = inject(TokenService);
  private readonly _URL = environment.HOST_URL;

  postVotar(voto: VotoDTO): Observable<any> {
    const url = `${this._URL}votos`;
    return this._http.post(url, voto).pipe(
      catchError(error => {
        console.error('Error al agregar fotografía:', error);
        throw error;
      })
    );
  }

}
