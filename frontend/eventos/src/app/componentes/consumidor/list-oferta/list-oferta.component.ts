import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ConsumidorService } from '../../../servicios/consumidor/consumidor.service';
import { RolService } from '../../../servicios/jwt/rol.service';
import { UsuarioService } from '../../../servicios/usuario.service';
import { ApiResponseOferta } from '../../../modelos/api-response-oferta';

@Component({
  selector: 'app-list-oferta',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule, RouterLink],
  templateUrl: './list-oferta.component.html',
  styleUrl: './list-oferta.component.scss'
})
export class ListOfertaComponent implements OnInit{

  /* private readonly _usuarioService = inject(UsuarioService)
  private readonly _rolService = inject(RolService) */
  private readonly _consumidorService = inject(ConsumidorService)
  ofertas: ApiResponseOferta[] = []

  ngOnInit(): void
  {
    this._consumidorService.getAllOfertas().subscribe(
      (response) => {
        if (response != null) {
          console.log(response);
          this.ofertas = response
        }
      }
    )
  }
}
