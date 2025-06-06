import { Component, inject} from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { AsyncPipe } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { TokenService } from '../../../servicios/jwt/token.service';


@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [
    MatToolbarModule, 
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    RouterLink,
    AsyncPipe,
    RouterModule
  ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuConsuComponent {
  private breakpointObserver = inject(BreakpointObserver);
  private readonly _tokenService = inject(TokenService)
  private readonly route = inject(Router)

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

    logout():void
    {
      this._tokenService.removeToken()
      console.log("se removio el token (null)", this._tokenService.getToken());
      this.route.navigateByUrl("/")
    }
}
