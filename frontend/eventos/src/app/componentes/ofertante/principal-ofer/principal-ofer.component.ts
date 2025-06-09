import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FotografiaService } from '../../../servicios/fotografia/fotografia.service';
import { ApiResponseFotografia } from '../../../modelos/api-response-fotografia';
import { UsuarioService } from '../../../servicios/usuario.service';
import { User } from '../../../modelos/user';
import { AlertService } from '../../../servicios/alert.service';

@Component({
  selector: 'app-principal-ofer',
  standalone: true,
  imports: [MatCardModule,
            MatFormFieldModule,
            MatSelectModule,
            MatInputModule,
            FormsModule,
            ReactiveFormsModule,],
  templateUrl: './principal-ofer.component.html',
  styleUrl: './principal-ofer.component.scss'
})
export class PrincipalOferComponent implements OnInit{
  estados = ['APROBAR', 'RECHAZAR'];
  adminDatos: User = {};

  fotografias: ApiResponseFotografia [] = [];

  private readonly _fotografiaService = inject(FotografiaService)
  private readonly _usuarioService = inject(UsuarioService)
  private readonly _alertService = inject(AlertService);

  ngOnInit(): void {
    this._usuarioService.getUserData().subscribe({
          next: (us: User) => {
            this.adminDatos = us;

            this._fotografiaService.getAllFotografia().subscribe({
              next: data => {
                this.fotografias = data;
              },
              error: err => {
                console.error('Error al obtener las fotografías:', err);
                alert('No se pudo obtener las fotografías.');
              }
            });
          },
          error: err => {
            console.error('Error al obtener participante:', err);
            alert('No se pudo obtener el participante.');
          }
        });
  }

  cambiarEstadoFoto(foto: any, nuevoEstado: string) {
    const estadoAnterior = foto.estado;

    this._alertService
      .confirmBox("Cambiar Estado", `¿Está seguro de "${nuevoEstado}" la fotografia ${foto.descripcion}?`)
      .then((result) => {
        if (result.value) {
          const aprobado = nuevoEstado === 'APROBAR';
          foto.estado = nuevoEstado;

          this._fotografiaService.aprobarFotografia(foto.id, this.adminDatos.id!, aprobado).subscribe({
            next: (resp) => console.log('Cambio de estado exitoso', resp),
            error: (err) => console.error('Error al cambiar estado', err)
          });

          this.ngOnInit();
        } else {
          foto.estado = estadoAnterior;
        }
    });
  }

}
