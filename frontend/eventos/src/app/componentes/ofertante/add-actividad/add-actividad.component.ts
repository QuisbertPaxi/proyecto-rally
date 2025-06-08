import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core'; // Asegúrate de importar MatNativeDateModule
import { ApiResponseActividad } from '../../../modelos/api-response-actividad';
import { OfertanteService } from '../../../servicios/ofertante/ofertante.service';
import { RolService } from '../../../servicios/jwt/rol.service';


@Component({
  selector: 'app-add-actividad',
  standalone: true,
  imports:  [
    MatCardModule, 
    ReactiveFormsModule, 
    MatInputModule, 
    MatIconModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'es-ES' } // Opcional: configurar el idioma
  ],
  templateUrl: './add-actividad.component.html',
  styleUrl: './add-actividad.component.scss'
})
export class AddActividadComponent {

  private fb = inject(FormBuilder);
  private readonly _ofertanteServicio = inject(OfertanteService)
  private readonly _rolService = inject(RolService)
  actividad: ApiResponseActividad = {}
  addAct = this.fb.group({
    titulo: [null, Validators.required],
    ubicacion: [null, Validators.required],
    descripcion: [null, Validators.required],
    precio: [null,Validators.required],
    fecha: [null, Validators.required],
  });
  
    /**{   "titulo":"Fotografía Urbana",
    "descripcion": 
    "Caminata fotográfica: Una caminata guiada por la ciudad centrada en técnicas fotográficas. Los participantes deben traer su propia cámara y usar zapatos cómodos. Duración: 2 horas. Se recomienda conocimiento básico de fotografía.",
    "fecha":"12/06/2024",
    "ubicacion": "Casco Antiguo",
    "precio": "15.00",
    "idOfertante": "2"
} */
  
  hide = true;
  clickEvent(event: MouseEvent) {
    this.hide = !this.hide;
    event.stopPropagation();
  }
  
  
  onSubmit() {
  if (this.addAct.valid) 
    {

      this.actividad.titulo = this.addAct.value.titulo
      this.actividad.descripcion = this.addAct.value.descripcion
      this.actividad.fecha = this.convertDateToString(this.addAct.value.fecha)
      this.actividad.ubicacion = this.addAct.value.ubicacion
      this.actividad.precio = this.addAct.value.precio
      this.actividad.idOfertante = this._rolService.getUsuarioId()

      this._ofertanteServicio.postAddActividad(this.actividad).subscribe({
        next: (response) => {console.log(response);},
        error: (error) => {console.error(error);},
        complete: () => {this.addAct.reset(); alert("se ha actualizado correctamente")}
      })
      
    }
  }

  convertDateToString(date: any): string {
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Month is 0-based
    const year = date.getFullYear().toString();
    return `${day}/${month}/${year}`;
  }

  delete()
  {
    
  }

}
