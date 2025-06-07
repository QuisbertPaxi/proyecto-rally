import { ApiResponseFotografia } from './../../../modelos/api-response-fotografia';
import { FotografiaService } from './../../../servicios/fotografia/fotografia.service';
import { User } from './../../../modelos/user';
import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { ApiResponseActividad } from '../../../modelos/api-response-actividad';
import { UsuarioService } from '../../../servicios/usuario.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-principal-consu',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, CommonModule],

  templateUrl: './principal-consu.component.html',
  styleUrls: ['./principal-consu.component.scss']
})
export class PrincipalConsuComponent implements OnInit{
  participanteDatos: User = {} as User;
  fotografias: ApiResponseFotografia [] = [];
  private readonly _usuarioService = inject(UsuarioService)
  private readonly _fotografiaService = inject(FotografiaService)
  actividades: ApiResponseActividad[] = [];

  constructor(private router: Router){}

  ngOnInit(): void {
    this._usuarioService.getUserData().subscribe({
      next: (us: User) => {
        this.participanteDatos = us;

        this._fotografiaService.getFotografiaParticipante(this.participanteDatos.id!).subscribe({
          next: data => {
            this.fotografias = data.filter((f:ApiResponseFotografia) => f.estado !== 'ELIMINADO');
            //console.log('Fotografías:', this.fotografias);
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

  editarFotografia(foto:ApiResponseFotografia){
    this.router.navigate(['/participante/addOferta/',foto.id]);
  }

  eliminarFoto(foto: ApiResponseFotografia) {
    if (confirm(`¿Estás seguro de eliminar la foto "${foto.titulo}"?`)) {
      this._fotografiaService.deleteFotografia(foto.id!, this.participanteDatos.userName!).subscribe({
        next: () => {
          this.fotografias = this.fotografias.filter(f => f.id !== foto.id);
          alert('Foto eliminada correctamente');
        },
        error: err => {
          console.error('Error al eliminar la foto:', err);
          alert('No se pudo eliminar la foto');
        }
      });
    }
  }

  irAddFotografia(){
    this.router.navigate(["/participante/addOferta"]);
  }
}
