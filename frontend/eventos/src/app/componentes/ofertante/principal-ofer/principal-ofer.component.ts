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
  estados = ['APROBADO','RECHAZADO'];
  adminDatos: User = {};

  fotografias: ApiResponseFotografia [] = [];

  private readonly _fotografiaService = inject(FotografiaService)
  private readonly _usuarioService = inject(UsuarioService)

  ngOnInit(): void {
    this._usuarioService.getUserData().subscribe({
          next: (us: User) => {
            this.adminDatos = us;

            this._fotografiaService.getFotografiaEstado(this.adminDatos.id!,'PENDIENTE').subscribe({
              next: data => {
                this.fotografias = data.filter((f:ApiResponseFotografia) => f.estado !== 'ELIMINADO');
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

  hacerOferta(flag: boolean){
    // el ofertante se encargara de cambiar el
  }

}
