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
import { MenuConsuComponent } from './componentes/consumidor/menu/menu.component';
import { PrincipalConsuComponent } from './componentes/consumidor/principal-consu/principal-consu.component';
import { ListOfertaComponent } from './componentes/consumidor/list-oferta/list-oferta.component';
import { AddOfertaComponent } from './componentes/consumidor/add-oferta/add-oferta.component';
import { MenuToolbarComponent } from './componentes/anonimo/menu-toolbar/menu-toolbar.component';
import { FormDatosPersonalesComponent } from './componentes/participante/form-datos-personales/form-datos-personales.component';

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
            { path: 'listaAct', component: ListActividadComponent, data: { expectedRole: 'admin' } },
            { path: 'addActividad', component: AddActividadComponent, data: { expectedRole: 'admin' }},
            { path: 'editActividad/:id', component: EditActividadComponent, data: { expectedRole: 'admin' }},
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
            { path: 'listaOferta', component: ListOfertaComponent, data: { expectedRole: 'participante' } },
            { path: 'addOferta', component: AddOfertaComponent, data: { expectedRole: 'participante' }},
            //{ path: 'editOferta/:id', component: EditActividadComponent, data: { expectedRole: 'participante' }},
            { path: 'addOferta/:id', component: AddOfertaComponent, data: { expectedRole: 'participante' }},
            { path: '', redirectTo: 'inicio', pathMatch: 'full' },
            { path: '**', redirectTo: 'inicio' }
          ]
    },
    {
        path: '**',
        redirectTo: ''
    },


];
