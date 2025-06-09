import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { User } from '../../../modelos/user';
import { UsuarioService } from '../../../servicios/usuario.service';
import { TokenService } from '../../../servicios/jwt/token.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AlertService } from '../../../servicios/alert.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-perfil-ofer',
  standalone: true,
  imports: [
    MatCardModule,
    ReactiveFormsModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './perfil-ofer.component.html',
  styleUrl: './perfil-ofer.component.scss'
})
export class PerfilOferComponent implements OnInit{

  private readonly _usuarioService = inject(UsuarioService);
  private readonly _tokenService = inject(TokenService)
  private readonly _alertService = inject(AlertService);
  private fb = inject(FormBuilder);
  ofertante: User = {}
  signInForm = this.fb.group({
    id: [this.ofertante.id],
    nombre: [this.ofertante.nombre, Validators.required],
    apellidos: [this.ofertante.apellidos, Validators.required],
    userName: [this.ofertante.userName, Validators.required],
    email: [this.ofertante.email, [Validators.required, Validators.email]],
    password: [null, Validators.compose([
      Validators.required, Validators.minLength(5), Validators.maxLength(10)])
    ],
  });

  constructor(private router: Router, private location: Location, private activatedRoute: ActivatedRoute) {}


ngOnInit(): void {
  const id = this.activatedRoute.snapshot.paramMap.get('id');

  if (id) {
    this._usuarioService.getUsuarioData(Number(id)).subscribe({
      next: (usuario) => {
        this.ofertante = usuario;
        this.rellenarForm();
      },
      error: (error) => {
        alert("Error al obtener usuario por ID");
        console.error(error);
      }
    });
  } else {
    this._usuarioService.getUserData().subscribe({
      next: (response) => {
        this.ofertante = response;
        this.rellenarForm();
      },
      error: (errorData) => { alert(errorData) },
      complete: () => { console.log("user data ok") }
    });
  }
}



  rellenarForm() {
    this.signInForm.patchValue({
      id: this.ofertante.id,
      nombre: this.ofertante.nombre,
      apellidos: this.ofertante.apellidos,
      userName: this.ofertante.userName,
      email: this.ofertante.email
    });
  }

    /**{
      "apellidos": "Hamilton",
      "email": "alex@example.com",
      "nombre": "Alexander",
      "password": "password2",
      "role": "x",
      "userName": "AlexH."
  } */

    hide = true;
    clickEvent(event: MouseEvent) {
      this.hide = !this.hide;
      event.stopPropagation();
    }

    onSubmit() {
      let resp: {mensaje: string};
      if (this.signInForm.valid) {
        this._alertService
          .confirmBox("Editar datos", "¿Está seguro de editar su datos personales?")
          .then((result) => {
            if (result.value) {
              this._usuarioService.putUpdateDataAdmin(this.signInForm.value as User).subscribe({
                next: (response) => {
                  resp = response
                },
                error: (error) => {alert(error)},
                complete: () =>
                  {
                    this._alertService.alertWithSuccess(resp.mensaje);
                    this.location.back();
                  }
              })
            }
          })
      }
    }

    logout():void
  {
    this._tokenService.removeToken()
    window.location.reload();
  }

  irAInicio() {
    this.router.navigateByUrl('/participante/inicio');
  }

}
