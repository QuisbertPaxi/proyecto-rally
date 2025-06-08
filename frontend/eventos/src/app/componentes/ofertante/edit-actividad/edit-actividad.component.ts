import { User } from './../../../modelos/user';
import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core'; // Asegúrate de importar MatNativeDateModule
import { UsuarioService } from '../../../servicios/usuario.service';
import { ApiResponseConsurso } from '../../../modelos/api.response-concurso';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ConsursoService } from '../../../servicios/consurso/consurso.service';
import { DatePipe } from '@angular/common';



@Component({
  selector: 'app-edit-actividad',
  standalone: true,
  imports: [
    MatCardModule,
    ReactiveFormsModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    RouterLink
  ],
  providers: [
    [DatePipe],
    { provide: MAT_DATE_LOCALE, useValue: 'es-ES' }
  ],
  templateUrl: './edit-actividad.component.html',
  styleUrl: './edit-actividad.component.scss'
})
export class EditActividadComponent implements OnInit{

  private fb = inject(FormBuilder)

  private readonly _concursoService = inject(ConsursoService)
  private readonly _routeAc = inject(ActivatedRoute)
  private readonly _usuarioService = inject(UsuarioService)

  concurso: ApiResponseConsurso = {}
  id = this._routeAc.snapshot.params["id"]
  datosUsuario: User = {};

  editCon = this.fb.group({
    descripcion: ['', Validators.required],
    fechaVinicio: [this.concurso.fechaInicioVotacion, Validators.required],
    fechaVfin: [this.concurso.fechaFinVotacion, Validators.required],
    fechaEinicio: [this.concurso.fechaInicioEnvio, Validators.required],
    fechaEfin: [this.concurso.fechaFinEnvio, Validators.required],
    fechaAnuncio: [this.concurso.fechaAnuncio, Validators.required],
  });

  datosConcurso: ApiResponseConsurso = {};
  fEnvioIni: any;
  fEnvioFin: any;
  fVotoIni: any;
  fVotoFin: any;
  fGanadores: any;

  constructor(private datePipe:DatePipe){}

ngOnInit(): void {
  this._usuarioService.getUserData().subscribe({
    next: (us) => {
      this.datosUsuario = us;
    }
  })
  this._concursoService.getConcurso().subscribe({
    next: (data) => {
      this.datosConcurso = data;
      // Actualizar formulario
      this.editCon.patchValue({
        descripcion: this.datosConcurso.descripcion,
        fechaEinicio: this.datosConcurso.fechaInicioEnvio,
        fechaEfin:  this.datosConcurso.fechaFinEnvio,
        fechaVinicio:  this.datosConcurso.fechaInicioVotacion,
        fechaVfin:  this.datosConcurso.fechaFinVotacion,
        fechaAnuncio:  this.datosConcurso.fechaAnuncio
      });
    },
    error: (err) => {
      console.error("Error al obtener el concurso:", err);
    }
  });
}

  onSubmit(){
    console.log(this.editCon.value);
    this.concurso.descripcion = this.editCon.value.descripcion;
    this.concurso.fechaInicioEnvio = this.datePipe.transform(this.editCon.value.fechaEinicio, 'yyyy-MM-dd');
    this.concurso.fechaFinEnvio = this.datePipe.transform(this.editCon.value.fechaEfin, 'yyyy-MM-dd');
    this.concurso.fechaInicioVotacion = this.datePipe.transform(this.editCon.value.fechaVinicio, 'yyyy-MM-dd');
    this.concurso.fechaFinVotacion = this.datePipe.transform(this.editCon.value.fechaVfin, 'yyyy-MM-dd');
    this.concurso.fechaAnuncio = this.datePipe.transform(this.editCon.value.fechaAnuncio, 'yyyy-MM-dd');
    this._concursoService.updateConcurso(this.datosUsuario.id!, this.concurso).subscribe({
      next: (res) => {
        console.log("Actualización exitosa", res);
      },
      error: (err) => {
        console.error("Error al actualizar", err);
      }
  });

  }
}
