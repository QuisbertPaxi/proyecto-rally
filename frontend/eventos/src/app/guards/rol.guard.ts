import { inject } from '@angular/core';
import { CanActivateChildFn, Router } from '@angular/router';
import { TokenService } from '../servicios/jwt/token.service';
import { RolService } from '../servicios/jwt/rol.service';

export const rolGuard: CanActivateChildFn = (childRoute, state) => {
  
  const tokenService = inject(TokenService)
  const roleService = inject(RolService)
  const router = inject(Router)

  const expectedRole = childRoute.data['expectedRole'];

  //console.log("expectedRole: ", expectedRole);
  //console.log("childRoute.data: ", childRoute.data);

  if (tokenService.isTokenExpired()) 
  {
    router.navigateByUrl("/LogIn")
    return false
  }

  if (!roleService.getUsuarioRol().includes(expectedRole)) 
  {
   console.log("rol: ", roleService.getUsuarioRol());
   router.navigateByUrl("/")
   return false
  }
  
  //console.log("el rol esperado: ", expectedRole);
  //console.log("el rol en el token: ", roleService.getUsuarioRol());
  //console.log("id_user:", roleService.getUsuarioId());
  return true;
};
