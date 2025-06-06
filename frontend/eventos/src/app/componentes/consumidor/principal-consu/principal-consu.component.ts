import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { ApiResponseActividad } from '../../../modelos/api-response-actividad';
import { ConsumidorService } from '../../../servicios/consumidor/consumidor.service';
import { UsuarioService } from '../../../servicios/usuario.service';

@Component({
  selector: 'app-principal-consu',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule],

  templateUrl: './principal-consu.component.html',
  styleUrl: './principal-consu.component.scss'
})
export class PrincipalConsuComponent implements OnInit{

    imagenes = [
    {
      src: 'https://picsum.photos/1280/720?random=1',
      titulo: 'Atardecer Urbano',
      descripcion: 'Captura de la ciudad durante la puesta de sol.',
      votos: 0
    },
    {
      src: 'https://picsum.photos/400/300?random=2',
      titulo: 'Arquitectura Moderna',
      descripcion: 'Diseño contemporáneo en un entorno urbano srty egtyhu dfghy sfdhj sdfghj sfdg dddddddddddddddddddddddddddddddddd dddddddddddddddddd ddddddddddddddd.',
      votos: 0
    },
    {
      src: 'https://picsum.photos/400/300?random=3',
      titulo: 'Vida Callejera',
      descripcion: 'La energía cotidiana de la ciudad.',
      votos: 0
    }
  ];
  private readonly _consumidorService = inject(ConsumidorService)
  private readonly _usuarioService = inject(UsuarioService)
  actividades: ApiResponseActividad[] = []

  ngOnInit():void {
    this._consumidorService.getAllActividades().subscribe(
      (response) =>
      {
        if (response != null) {
          response.forEach((act: ApiResponseActividad) =>{
            this._usuarioService.getUsuarioData(act.idOfertante).subscribe((resp) =>
              {
                act.email = resp.email
              }
            )
          })

          this.actividades = response
        }
      }
    )
  }
}
