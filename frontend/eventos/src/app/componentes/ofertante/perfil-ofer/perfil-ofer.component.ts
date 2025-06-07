import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { User } from '../../../modelos/user';
import { UsuarioService } from '../../../servicios/usuario.service';
import { TokenService } from '../../../servicios/jwt/token.service';
import { Router } from '@angular/router';


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
  private fb = inject(FormBuilder);
  ofertante: User = {}
  signInForm = this.fb.group({
    nombre: [this.ofertante.nombre, Validators.required],
    apellidos: [this.ofertante.apellidos, Validators.required], 
    userName: [this.ofertante.userName, Validators.required],
    email: [this.ofertante.email, [Validators.required, Validators.email]],
    password: [null, Validators.compose([
      Validators.required, Validators.minLength(5), Validators.maxLength(10)])
    ],
  });

  constructor(private router: Router) {}


  ngOnInit(): void 
  {
    this._usuarioService.getUserData().subscribe({

      next: (response) => 
      {
        this.ofertante = response; 
        console.log(this.ofertante);
        this.rellenarForm()
      },
      error: (errorData) =>{alert(errorData)},
      complete: () => {console.log("user data ok")}
    })

    console.log(this.ofertante);
  }
  

  rellenarForm() {
    this.signInForm.patchValue({
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
        //console.log(this.signInForm.value as User);
        this._usuarioService.putUpdateData(this.signInForm.value as User).subscribe({
          next: (response) => {
            console.log(response)
            resp = response
          },
          error: (error) => {alert(error)},
          complete: () => 
            { 
              alert(resp.mensaje)
              this.logout()
            }
        })
  
      }
    }

    logout():void
  {
    this._tokenService.removeToken()
    console.log("se removio el token (null)", this._tokenService.getToken());
    window.location.reload();
  }

  irAInicio() {
    this.router.navigateByUrl('/participante/inicio');
  }

}
