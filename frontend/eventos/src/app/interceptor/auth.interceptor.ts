import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { TokenService } from '../servicios/jwt/token.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const tokenService = inject(TokenService)
  const token = tokenService.getToken()

  if (token) 
  {
    //console.log("hay un token el local Storage");
    const headers = req.headers.set('Authorization', `Bearer ${token}`)
    //console.log("me temos el token en los headers: ", headers);
    const reqClon = req.clone({ headers }) 

    return next(reqClon)
  } 

  return next(req);
};
