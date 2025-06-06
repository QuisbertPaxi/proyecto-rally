import { Component } from '@angular/core';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

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
    },
    {
      src: 'https://picsum.photos/400/300?random=4',
      titulo: 'Transporte',
      descripcion: 'El caos del transport en la ciudad.',
      votos: 0
    }
  ];

  votar(imagen: any) {
    imagen.votos++;
  }
}
