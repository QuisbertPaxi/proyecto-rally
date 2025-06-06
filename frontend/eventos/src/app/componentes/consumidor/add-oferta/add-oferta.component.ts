import { Component, inject } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { AsyncPipe } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { RouterLink, Router } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core'; // Asegúrate de importar MatNativeDateModule
import { FotografiaService } from '../../../servicios/fotografia/fotografia.service';
import { HttpClient } from '@angular/common/http';
import { ApiResponseFotografia } from '../../../modelos/api-response-fotografia';
import { ActivatedRoute } from '@angular/router';
import { UsuarioService } from '../../../servicios/usuario.service';
import { User } from './../../../modelos/user';

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
  participanteDatos: User = {} as User;
  private readonly _usuarioService = inject(UsuarioService)
  private readonly _fb = inject(FormBuilder);
  private readonly _http = inject(HttpClient);
  private readonly _fotoService = inject(FotografiaService);
  private breakpointObserver = inject(BreakpointObserver);
  fotografia: ApiResponseFotografia = {} as ApiResponseFotografia;
  isEdit:boolean = false;

  fotoForm: FormGroup = this._fb.group({
    titulo: ['', Validators.required],
    descripcion: ['']
  });

  selectedFile: File | null = null;

  constructor(private router: Router, private route: ActivatedRoute,){}

  ngOnInit(): void {
    this._usuarioService.getUserData().subscribe({
      next: (us: User) => {
        this.participanteDatos = us;
      },
      error: err => {
        console.error('Error al obtener participante:', err);
        alert('No se pudo obtener el participante.');
      }
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this._fotoService.getFotografiaId(+id).subscribe((foto) => {
        this.fotoForm.patchValue({
          titulo: foto.titulo,
          descripcion: foto.descripcion
        });
      this.fotografia = foto;
      });
    }
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onSubmit() {
    let id = this.route.snapshot.paramMap.get('id');
    if (this.fotoForm.valid && (this.selectedFile || this.isEdit)) {
      if (this.isEdit) {
        this.fotografia.id = +id!;
        this.fotografia.titulo = this.fotoForm.value.titulo;
        this.fotografia.descripcion = this.fotoForm.value.descripcion;
        this.fotografia.usuMod = this.participanteDatos.userName!;
        this.fotografia.idParticipante = this.participanteDatos.id;
        this.fotografia.link = "https://picsum.photos/499/300?random=5";
        if (confirm(`¿Guardar los cambios en la fotografia "${this.fotografia.titulo}"?`)) {
          this._fotoService.updateFotografia(this.fotografia).subscribe({
            next: () => {
              alert('¡Fotografía editada con éxito!');
            },
            error: err => {
              console.error('Error al enviar al backend:', err);
              alert('Hubo un problema al guardar la fotografía');
            }
          });
        }
      } else {
        if (this.fotoForm.valid && this.selectedFile) {
          // 1. Subir imagen a Cloudinary (puede ser otro host)
          const formData = new FormData();
          formData.append('file', this.selectedFile);
          formData.append('upload_preset', 'proyecto_rally');

          /*this._http.post<any>('https://api.cloudinary.com/v1_1/dottcqrrd/image/upload', formData)
            .subscribe({
              next: (res) => {
                const imageUrl = res;*/

                this.fotografia.descripcion = this.fotoForm.value.descripcion;
                this.fotografia.idParticipante = this.participanteDatos.id;
                //this.fotografia.link = imageUrl;
                this.fotografia.link = "https://picsum.photos/499/300?random=4"
                this.fotografia.titulo = this.fotoForm.value.titulo;
                this.fotografia.usuCre = this.participanteDatos.userName!;

                console.log("enviando: ",this,this.fotografia);

                this._fotoService.postFotografiaParticipante(this.fotografia).subscribe({
                  next: () => alert('¡Fotografía subida con éxito!'),
                  error: err => {
                    console.error('Error al enviar al backend:', err);
                    alert('Hubo un problema al guardar la fotografía');
                  }
                });
              /*},
              error: err => {
                console.error('Error al subir imagen:', err);
                alert('No se pudo subir la imagen');
              }
            });*/
        }
      }
    }
    this.router.navigate(['/consumidor/inicio']);
  }
}
