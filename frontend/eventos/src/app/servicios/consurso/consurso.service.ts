import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment.development';
import { RolService } from '../jwt/rol.service';

@Injectable({
  providedIn: 'root'
})
export class ConsursoService {
  private readonly _http = inject(HttpClient);
  private readonly _rolService = inject(RolService)
  private readonly _id = this._rolService.getUsuarioId()
  private readonly _URL = environment.HOST_URL;

  constructor() { }

  getConcurso(){
    return this._http.get(this._URL+'concurso');
  }
}
