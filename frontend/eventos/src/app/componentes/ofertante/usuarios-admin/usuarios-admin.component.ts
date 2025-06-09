import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { UsuarioService } from '../../../servicios/usuario.service';
import { RouterModule, Router } from '@angular/router';
import { User } from '../../../modelos/user';

@Component({
  selector: 'app-usuarios-admin',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    RouterModule,
    CommonModule
  ],
  templateUrl: './usuarios-admin.component.html',
  styleUrl: './usuarios-admin.component.scss'
})
export class UsuariosAdminComponent {
  private readonly _usuarioService = inject(UsuarioService);
  private readonly router = inject(Router);
  usuarios: User [] = [
    {
      id: 1,
      nombre: "pepe",
      apellidos: "papa",
      userName:  "pp"
    },
    {
      id: 2,
      nombre: "ana",
      apellidos: "perez",
      userName:  "anpe"
    },
    {
      id: 3,
      nombre: "juan",
      apellidos: "suarez",
      userName:  "jjj"
    },
    {
      id: 4,
      nombre: "maria",
      apellidos: "montes",
      userName:  "mm"
    },
    {
      id: 5,
      nombre: "pepa",
      apellidos: "pig",
      userName:  "iii"
    },
  ];

  ngOnInit(): void {
    /*this._usuarioService.getAllUser().subscribe({
      next: (data) => this.usuarios = data,
      error: (err) => console.error('Error al obtener usuarios', err)
    });*/
  }

  editarUsuario(id: number) {
    this.router.navigate(['admin/perfil/User/',id])
    console.log('Editar usuario', id);
  }

  eliminarUsuario(id: number) {
    if (confirm('¿Estás seguro de eliminar este usuario?')) {
      this._usuarioService.deleteUsuarioDataAdmin(id).subscribe({
        next: () => {
          this.usuarios = this.usuarios.filter(u => u.id !== id);
          console.log('Usuario eliminado');
        },
        error: (err) => console.error('Error al eliminar usuario', err)
      });
    }
  }
}
