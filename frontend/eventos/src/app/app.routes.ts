import { Routes } from '@angular/router';
import { InicioComponent } from './componentes/anonimo/inicio/inicio.component';
import { LogInComponent } from './componentes/anonimo/log-in/log-in.component';
import { SignUpComponent } from './componentes/anonimo/sign-up/sign-up.component';
import { GaleriaComponent } from './componentes/anonimo/galeria/galeria.component';
import { EstadisticasComponent } from './componentes/anonimo/estadisticas/estadisticas.component';
import { MenuComponent } from './componentes/ofertante/menu/menu.component';
import { PrincipalOferComponent } from './componentes/ofertante/principal-ofer/principal-ofer.component';
import { PerfilOferComponent } from './componentes/ofertante/perfil-ofer/perfil-ofer.component';
import { ListActividadComponent } from './componentes/ofertante/list-actividad/list-actividad.component';
import { AddActividadComponent } from './componentes/ofertante/add-actividad/add-actividad.component';
import { rolGuard } from './guards/rol.guard';
import { redirectIfAuthenticatedGuard } from './guards/redirect.guard';
import { EditActividadComponent } from './componentes/ofertante/edit-actividad/edit-actividad.component';
import { MenuConsuComponent } from './componentes/participante/menu/menu.component';
import { PrincipalConsuComponent } from './componentes/participante/principal-consu/principal-consu.component';
import { AddFotografiaComponent } from './componentes/participante/add-fotografia/add-fotografia.component';
import { MenuToolbarComponent } from './componentes/anonimo/menu-toolbar/menu-toolbar.component';
import {  UsuariosAdminComponent } from './componentes/ofertante/usuarios-admin/usuarios-admin.component';

export const routes: Routes = [

    {
      path: '',
      component: MenuToolbarComponent,
      canActivate: [redirectIfAuthenticatedGuard],
      children: [
      { path: '', component: InicioComponent },
      { path: 'Galeria', component: GaleriaComponent },
      { path: 'Stats', component: EstadisticasComponent },
      ]
    },
    {
        path: "LogIn",
        canActivate: [redirectIfAuthenticatedGuard],
        component: LogInComponent
    },
    {
        path: "SignUp",
        canActivate: [redirectIfAuthenticatedGuard],
        component: SignUpComponent
    },
    {
        path: "admin",
        component: MenuComponent,
        canActivateChild:[rolGuard],
        data: {expectedRole: 'admin'},
        children: [
            { path: 'inicio', component: PrincipalOferComponent, data: { expectedRole: 'admin' } },
            { path: 'perfil', component: PerfilOferComponent, data: { expectedRole: 'admin' } },
            { path: 'perfil/User/:id', component: PerfilOferComponent, data: { expectedRole: 'admin' }},
            { path: 'listaAct', component: ListActividadComponent, data: { expectedRole: 'admin' } },
            { path: 'addActividad', component: AddActividadComponent, data: { expectedRole: 'admin' }},
            { path: 'editActividad/:id', component: EditActividadComponent, data: { expectedRole: 'admin' }},
            { path: 'usersAdmin', component: UsuariosAdminComponent, data: { expectedRole: 'admin' }},
            { path: '', redirectTo: 'inicio', pathMatch: 'full' },
            { path: '**', redirectTo: 'inicio' }
          ]
    },
    {
        path: "participante",
        component: MenuConsuComponent,
        canActivateChild:[rolGuard],
        data: {expectedRole: 'participante'},
        children: [
            { path: 'inicio', component: PrincipalConsuComponent, data: { expectedRole: 'participante' } },
            { path: 'perfil', component: PerfilOferComponent, data: { expectedRole: 'participante' } },
            { path: 'addFotografia', component: AddFotografiaComponent, data: { expectedRole: 'participante' }},
            { path: 'addFotografia/:id', component: AddFotografiaComponent, data: { expectedRole: 'participante' }},
            { path: '', redirectTo: 'inicio', pathMatch: 'full' },
            { path: '**', redirectTo: 'inicio' }
          ]
    },
    {
        path: '**',
        redirectTo: ''
    },


];
