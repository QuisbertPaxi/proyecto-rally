import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core'; // Asegúrate de importar MatNativeDateModule
import { OfertanteService } from '../../../servicios/ofertante/ofertante.service';
import { ApiResponseActividad } from '../../../modelos/api-response-actividad';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { RolService } from '../../../servicios/jwt/rol.service';


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
    { provide: MAT_DATE_LOCALE, useValue: 'es-ES' } // Opcional: configurar el idioma
  ],
  templateUrl: './edit-actividad.component.html',
  styleUrl: './edit-actividad.component.scss'
})
export class EditActividadComponent implements OnInit{

  private fb = inject(FormBuilder)

  private readonly _ofertanteServicio = inject(OfertanteService)
  private readonly _rolService = inject(RolService)
  private readonly _routeAc = inject(ActivatedRoute)

  actividad: ApiResponseActividad = {}
  idAct = this._routeAc.snapshot.params["id"]

  editAct = this.fb.group({
    titulo: [this.actividad.titulo, Validators.required],
    ubicacion: [this.actividad.ubicacion, Validators.required],
    descripcion: [this.actividad.descripcion, Validators.required],
    precio: [this.actividad.precio,Validators.required],
    fecha: [this.actividad.fecha, Validators.required],
  });

  ngOnInit(): void 
  {
    this._ofertanteServicio.getActividadId(this.idAct).subscribe({
      next: (response) => 
        {
          this.actividad = response
          console.log(this.actividad);
          this.rellenarForm()
        },
      error:(error) => {console.log(error);},
    })
  }

  rellenarForm() {

    let fecha = this.convertStringToDate(this.actividad.fecha)
    this.editAct.patchValue({
      titulo: this.actividad.titulo,
      ubicacion: this.actividad.ubicacion,
      descripcion: this.actividad.descripcion,
      precio: this.actividad.precio,
      fecha: fecha
      ,
    });
  }

  convertStringToDate(dateString: any): Date {
    const [day, month, year] = dateString.split('/').map(Number);
    return new Date(year, month - 1, day);  
  }

  convertDateToString(date: any): string {
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Month is 0-based
    const year = date.getFullYear().toString();
    return `${day}/${month}/${year}`;
  }

  onSubmit(){
    console.log(this.editAct.value);
    console.log(this.convertDateToString(this.editAct.value.fecha));
    if (this.editAct.valid) 
    {
      console.log(this.editAct.value.fecha);
      this.actividad.titulo = this.editAct.value.titulo
      this.actividad.descripcion = this.editAct.value.descripcion
      this.actividad.ubicacion = this.editAct.value.ubicacion
      this.actividad.precio = this.editAct.value.precio
      this.actividad.fecha = this.convertDateToString(this.editAct.value.fecha)
      this.actividad.idOfertante = this._rolService.getUsuarioId()
      this.actividad.id = this.idAct

      console.log(this.actividad);

      this._ofertanteServicio.putEditActividad(this.actividad).subscribe({
        next: (response) => {console.log(response);},
        error: (error) => {console.error(error);},
        complete: () => { window.location.reload();}
      })
    }
  }
}
