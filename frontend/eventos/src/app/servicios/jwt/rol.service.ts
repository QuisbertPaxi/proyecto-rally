import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class RolService {

  constructor(private tokenService: TokenService) { }

  getUsuarioRol(): string 
  {
    const token = this.tokenService.getToken()

    if (!token) { return "" }

    const tokenPayload = environment.decodeToken(token);
    return tokenPayload.roles || "";

  }

  getUsuarioId(): number
  {
    const token = this.tokenService.getToken()
    
    if (!token) {return 0 }

    const tokenPayload = environment.decodeToken(token);
    //console.log("token decode", tokenPayload);
    return tokenPayload.id || 0
  }
}

