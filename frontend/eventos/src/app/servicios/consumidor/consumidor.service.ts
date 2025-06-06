import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { RolService } from '../jwt/rol.service';
import { ApiResponseActividad } from '../../modelos/api-response-actividad';

@Injectable({
  providedIn: 'root'
})
export class ConsumidorService {

  private readonly _http = inject(HttpClient);
  private readonly _rolService = inject(RolService)
  private readonly _id = this._rolService.getUsuarioId()
  private readonly _URL = environment.API_URL;

  // GET http://localhost:19090/api/v1/actividades/Consumidores/All/{{idConsumidor}}
  getAllActividades(){
    return this._http.get<ApiResponseActividad[]>(this._URL+'actividades/Consumidores/All/'+this._id)
  }

  // GET http://localhost:19090/api/v1/ofertas/Consumidores/All/{{idConsumidor}}

  getAllOfertas(){
    return this._http.get<any>(this._URL+"ofertas/Consumidores/All/"+this._id)
  }

}
