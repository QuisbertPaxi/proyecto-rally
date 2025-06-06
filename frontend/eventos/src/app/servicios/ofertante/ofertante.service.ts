import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { RolService } from '../jwt/rol.service';
import { ApiResponseOferta } from '../../modelos/api-response-oferta';
import { ApiResponseActividad } from '../../modelos/api-response-actividad';

@Injectable({
  providedIn: 'root'
})
export class OfertanteService {

  private readonly _http = inject(HttpClient);
  private readonly _rolService = inject(RolService)
  private readonly _id = this._rolService.getUsuarioId()
  private readonly _URL = environment.API_URL;

  constructor() { }

  getAllOfertas()
  {
    return this._http.get<ApiResponseOferta[]>(this._URL+'ofertas/Ofertantes/All/'+this._id)
   // return this._http.get<any>(this._URL+'ofertas/Ofertantes/All/'+this._id)
  }

  // GET http://localhost:19090/api/v1/actividades/Ofertantes/All/{{idOfertante}}
  getAllActividades()
  {
    return this._http.get<ApiResponseActividad[]>(this._URL+'actividades/Ofertantes/All/'+this._id)
  }

  //POST http://localhost:19090/api/v1/actividades/Ofertantes/Add

  postAddActividad(actividad : ApiResponseActividad)
  {
    return this._http.post<any>(this._URL+'actividades/Ofertantes/Add', actividad)
  }

  // GET http://localhost:19090/api/v1/actividades/{{idActividad}}

  getActividadId(id: number)
  {
    return this._http.get<any>(this._URL+'actividades/'+ id)
  }

  // PUT http://localhost:19090/api/v1/actividades/Ofertantes/Update

  putEditActividad(actividad : ApiResponseActividad) 
  {
    return this._http.put<any>(this._URL+'actividades/Ofertantes/Update', actividad)
  }

  // DELETE http://localhost:19090/api/v1/actividades/Ofertantes/Delete

  deleteActividad(actividad : ApiResponseActividad)
  {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this._http.delete<any>(this._URL+'actividades/Ofertantes/Delete',{ headers, body: actividad })
  }
}
