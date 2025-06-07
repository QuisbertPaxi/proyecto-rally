import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { TokenService } from '../jwt/token.service';
import { ApiResponseFotografia } from '../../modelos/api-response-fotografia';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class FotografiaService {

  private readonly _http = inject(HttpClient);
  private readonly _tokenService = inject(TokenService);
  private readonly _URL = environment.API_URL;

  getAllFotografia(): Observable<any> {
    return this._http.get(`${this._URL}fotografias/All`).pipe(
      catchError(error => {
        console.error('Error al obtener las fotografías', error);
        throw error;
      })
    );
  }

  getFotografiaId(idFotografia: number): Observable<any> {
    const token = this._tokenService.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this._http.get(`${this._URL}fotografias/${idFotografia}`, { headers }).pipe(
      catchError(error => {
        console.error('Error al obtener la fotografía', error);
        throw error;
      })
    );
  }

  getFotografiaParticipante(idParticipante: number): Observable<any> {
    const token = this._tokenService.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this._http.get(`${this._URL}fotografias/Participantes/All/${idParticipante}`, { headers }).pipe(
      catchError(error => {
        console.error('Error al obtener la fotografía del participante', error);
        throw error;
      })
    );
  }

  getFotografiaEstado(estado: string): Observable<any> {
    const token = this._tokenService.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this._http.get(`${this._URL}fotografias/State/11/${estado}`, { headers }).pipe(
      catchError(error => {
        console.error('Error al obtener la fotografía', error);
        throw error;
      })
    );
  }

  getFotografiaPalabra(palabra: string): Observable<any> {
    return this._http.get(`${this._URL}fotografias/All/${palabra}`).pipe(
      catchError(error => {
        console.error('Error al obtener la fotografía', error);
        throw error;
      })
    );
  }

  postFotografiaParticipante(fotografia: ApiResponseFotografia): Observable<any> {
    const token = this._tokenService.getToken();
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    const url = `${this._URL}fotografias/Participantes/Add`;
    return this._http.post(url, fotografia, { headers }).pipe(
      catchError(error => {
        console.error('Error al agregar fotografía:', error);
        throw error;
      })
    );
  }

  updateFotografia(fotografia: ApiResponseFotografia):Observable<any>{
    const token = this._tokenService.getToken();
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    const url = `${this._URL}fotografias/Participantes/Update`;

    return this._http.put(url,fotografia, { headers }).pipe(
      catchError(error => {
        console.error('Error al actualizar fotografia: ', error);
        throw error;
      })
    );
  }

  deleteFotografia(idFotografia:number, usumod: string):Observable<string>{
    const token = this._tokenService.getToken();
    const data = {
      id: idFotografia,
      usuMod: usumod
    }
    //console.log("data delete: ",data);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    const url = `${this._URL}fotografias/Participantes/Delete`;

    return this._http.request('delete', url, {
      headers,
      body: data,
      responseType: 'text'
      }).pipe(
        catchError(error => {
        console.error('Error al eliminar fotografía:', error);
        throw error;
        })
      );
  }
}
