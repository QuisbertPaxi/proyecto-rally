import { Component, inject } from '@angular/core';

import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatCardModule } from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import { Router, RouterLink } from '@angular/router';
import { LoginRequest } from '../../../modelos/loginRequest';
import { LoginService } from '../../../servicios/auth/login.service';
import { TokenService } from '../../../servicios/jwt/token.service';
import { RolService } from '../../../servicios/jwt/rol.service';

@Component({
  selector: 'app-log-in',
  standalone: true,
  imports: [
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatRadioModule,
    MatCardModule,
    ReactiveFormsModule,
    MatIconModule,
    RouterLink
  ],
  templateUrl: './log-in.component.html',
  styleUrl: './log-in.component.scss'
})
export class LogInComponent {

  private fb = inject(FormBuilder);
  private readonly _router = inject(Router)
  private readonly _loginService = inject(LoginService)
  private readonly _tokenService = inject(TokenService)
  private readonly _roleUser = inject(RolService)
  hide = true;

  clickEvent(event: MouseEvent) {
    this.hide = !this.hide;
    event.stopPropagation();
  }


  logInForm = this.fb.group({
    userName: [null, Validators.required],
    password: [null, Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(10)])
    ]
  });


   /*
  {

    "email": "alex@example.com",
    "password": "password2",
    "userName": "AlexH"
  }
   */

  onSubmit() {
    if (this.logInForm.valid) {
      this._loginService.login(this.logInForm.value as LoginRequest).subscribe({
        next:(response) =>
        {
          if (!this._tokenService.getToken())
          {
            this._tokenService.setToken(response.token)
          } else
          {
            this._tokenService.removeToken()
            this._tokenService.setToken(response.token)
          }
        },
        error:(respError) => {console.log(respError); alert(respError); this.logInForm.reset()},
        complete: () =>
        {
          alert('¡Nos alegramos de verte de nuevo!')
          this.logInForm.reset()

          if (this._roleUser.getUsuarioRol() === "participante")
          {
            this._router.navigateByUrl("/participante/inicio")
            //this._router.navigateByUrl("/participante")
          } else if (this._roleUser.getUsuarioRol() === "administrador")
          {
            console.log("rol: ", this._roleUser.getUsuarioRol());
            this._router.navigateByUrl("/admin/inicio")
          } else{
            this._router.navigateByUrl("/")
          }
        }
      })
    } else {
      alert('Parece que hubo un problema :C');
      this.logInForm.reset()
    }

  }
}
