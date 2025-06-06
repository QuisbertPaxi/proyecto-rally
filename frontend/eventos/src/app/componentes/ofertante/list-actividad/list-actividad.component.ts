import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { OfertanteService } from '../../../servicios/ofertante/ofertante.service';
import { ApiResponseActividad } from '../../../modelos/api-response-actividad';
import { UsuarioService } from '../../../servicios/usuario.service';
import { RolService } from '../../../servicios/jwt/rol.service';


@Component({
  selector: 'app-list-actividad',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule, RouterLink],
  templateUrl: './list-actividad.component.html',
  styleUrl: './list-actividad.component.scss'
})
export class ListActividadComponent implements OnInit{

  private readonly _ofertanteService = inject(OfertanteService)
  private readonly _usuarioService = inject(UsuarioService)
  private readonly _rolService = inject(RolService)

  actividades: ApiResponseActividad[] = []
  email: string | undefined = ""

  ngOnInit(): void {
    this._ofertanteService.getAllActividades().subscribe(
    (response) =>
    {
      if (response != null) {
       
        this._usuarioService.getUserData().subscribe( (resp) =>
        
          {
            this.email = resp.email
            this.actividades = response

            console.log(response);
          }
        )

      }
    }
  )}

  eliminar(id: any){
    console.log(id);

    this._ofertanteService.deleteActividad({id: id, idOfertante: this._rolService.getUsuarioId()}).subscribe({
      next: (value) => {console.log(value);} , 
      error: (error) => {console.log(error);} , 
      complete: () => {window.location.reload();}
    })
  }
}
