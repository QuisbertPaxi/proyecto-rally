import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { OfertanteService } from '../../../servicios/ofertante/ofertante.service';
import { ApiResponseConsurso } from '../../../modelos/api.response-concurso';
import { ConsursoService } from '../../../servicios/consurso/consurso.service';


@Component({
  selector: 'app-list-actividad',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule, RouterLink],
  templateUrl: './list-actividad.component.html',
  styleUrl: './list-actividad.component.scss'
})
export class ListActividadComponent implements OnInit{

  private readonly _ofertanteService = inject(OfertanteService)
  private readonly _conscursoService = inject(ConsursoService)

  datosConcurso: ApiResponseConsurso = {};
  fEnvioIni: any;
  fEnvioFin: any;
  fVotoIni: any;
  fVotoFin: any;
  fGanadores: any;


  ngOnInit(): void {
    this._conscursoService.getConcurso().subscribe({
      next: (data) => {
        this.datosConcurso = data;
        this.fEnvioIni = this.formatearFecha(this.datosConcurso.fechaInicioEnvio);
        this.fEnvioFin = this.formatearFecha(this.datosConcurso.fechaFinEnvio);
        this.fVotoIni = this.formatearFecha(this.datosConcurso.fechaInicioVotacion);
        this.fVotoFin = this.formatearFecha(this.datosConcurso.fechaFinVotacion);
        this.fGanadores = this.formatearFecha(this.datosConcurso.fechaAnuncio);
        //console.log("datos conc: ", this.datosConcurso);
      },
      error: (err) => {
        console.error("Error al obtener el concurso:", err);
      }
    });
  }

  formatearFecha(fecha: any): string {
    const [año, mes, dia] = fecha.split('-');
    return `${dia}/${mes}/${año}`;
  }

  eliminar(id: any){
    console.log(id);
  }
}
