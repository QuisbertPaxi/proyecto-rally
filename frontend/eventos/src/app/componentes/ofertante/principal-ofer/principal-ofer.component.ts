import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { OfertanteService } from '../../../servicios/ofertante/ofertante.service';
import { ApiResponseOferta } from '../../../modelos/api-response-oferta';

@Component({
  selector: 'app-principal-ofer',
  standalone: true,
  imports: [MatCardModule, MatButtonModule],
  templateUrl: './principal-ofer.component.html',
  styleUrl: './principal-ofer.component.scss'
})
export class PrincipalOferComponent implements OnInit{

  private readonly _ofertanteService = inject(OfertanteService)
  ofertas: ApiResponseOferta[] = []

  ngOnInit(): void {
    this._ofertanteService.getAllOfertas().subscribe(
      (response) => 
      {
        if (response != null) {
          this.ofertas = response
        }
      }
    )
  }

  hacerOferta(flag: boolean){
    // el ofertante se encargara de cambiar el 
  }

}
