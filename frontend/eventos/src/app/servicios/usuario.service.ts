import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { RolService } from './jwt/rol.service';
import { User } from '../modelos/user';
import { catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private readonly _http = inject(HttpClient);
  private readonly _rolService = inject(RolService)
  private readonly _id = this._rolService.getUsuarioId()
  private readonly _URL = environment.API_URL;

  getUserData()
  {
    // http://localhost:19090/api/v1/usuarios/{{id}}
    return this._http.get<User>(this._URL+'usuarios/'+this._id)
  }

  getUsuarioData(idOf: number| null | undefined)
  {
    // http://localhost:19090/api/v1/usuarios/{{id}}
    return this._http.get<User>(this._URL+'usuarios/'+idOf)
  }

  // PUT http://localhost:19090/api/v1/usuarios/{{id}}

  putUpdateData(usurio: User)
  {
    return this._http.put<{mensaje: string}>(this._URL+'usuarios/'+this._id, usurio).pipe(
      catchError(this.handleError)
    )
  }

  private handleError(error:HttpErrorResponse){
    if(error.status===0){
      console.error('Se ha producio un error ', error.error);
    }
    else{
      console.error('Backend retornó el código de estado ', error);
    }
    return throwError(()=> new Error('Algo falló. Por favor intente nuevamente.'));
  }

}
