import { Component, inject } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { AsyncPipe } from '@angular/common';

import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core'; // Asegúrate de importar MatNativeDateModule

@Component({
  selector: 'app-add-oferta',
  standalone: true,
  imports: [
    MatDatepickerModule,
    MatInputModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    AsyncPipe,
    RouterLink,
    MatCardModule,
    ReactiveFormsModule,
    MatNativeDateModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'es-ES' } // Opcional: configurar el idioma
  ],
  templateUrl: './add-oferta.component.html',
  styleUrl: './add-oferta.component.scss'
})
export class AddOfertaComponent {
  
  private breakpointObserver = inject(BreakpointObserver);

  private fb = inject(FormBuilder);
  addOfert = this.fb.group({
      titulo: [null, Validators.required],
      ubicacion: [null, Validators.required],
      descripcion: [null, Validators.required],
      fechaSolicitada: [null, Validators.required],
  });

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
  .pipe(
    map(result => result.matches),
    shareReplay()
  );

  hide = true;
    clickEvent(event: MouseEvent) {
      this.hide = !this.hide;
      event.stopPropagation();
    }
  
  
    onSubmit() {
      if (this.addOfert.valid) {
        console.log(this.addOfert.value);
      }
      alert('Actualizamos tus datos con existo');
    }
    
}
