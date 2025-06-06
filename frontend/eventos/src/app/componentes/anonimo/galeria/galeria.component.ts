import { Component, OnInit } from '@angular/core';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { FotografiaService } from '../../../servicios/fotografia/fotografia.service';
import { ApiResponseFotografia } from '../../../modelos/api-response-fotografia';

@Component({
  selector: 'app-galeria',
  standalone: true,
  imports: [
    MatGridListModule,
    MatCardModule,
    MatButtonModule
  ],
  templateUrl: './galeria.component.html',
  styleUrls: ['./galeria.component.scss']
})
export class GaleriaComponent {
  fotografias: ApiResponseFotografia [] = [];
  constructor(private fotografiaService: FotografiaService){}

  ngOnInit(): void {
    this.fotografiaService.getAllFotografia().subscribe(
    (response) =>
      {//esta funcioanando
        if (response != null) {
            this.fotografias = response;
            console.log(response);
        }
      });
  }

  votar(imagen: any) {
    //imagen.votos++;
  }

  buscarFotografias(){

  }
}
