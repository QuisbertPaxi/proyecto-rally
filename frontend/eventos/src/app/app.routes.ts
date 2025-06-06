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
      children: [
      { path: '', component: InicioComponent },
      { path: 'Galeria', component: GaleriaComponent },
      { path: 'Stats', component: EstadisticasComponent },
      ]
    },
    {
        path: "LogIn",
        component: LogInComponent
    },
    {
        path: "SignUp",
        component: SignUpComponent
    },
    {
        path: "participante",
        component: FormDatosPersonalesComponent
    },
    {
        path: "ofertante",
        component: MenuComponent,
        canActivateChild:[rolGuard],
        data: {expectedRole: 'ofertante'},
        children: [
            { path: 'inicio', component: PrincipalOferComponent, data: { expectedRole: 'ofertante' } },
            { path: 'perfil', component: PerfilOferComponent, data: { expectedRole: 'ofertante' } },
            { path: 'listaAct', component: ListActividadComponent, data: { expectedRole: 'ofertante' } },
            { path: 'addActividad', component: AddActividadComponent, data: { expectedRole: 'ofertante' }},
            { path: 'editActividad/:id', component: EditActividadComponent, data: { expectedRole: 'ofertante' }},
            { path: '', redirectTo: 'inicio', pathMatch: 'full' },
            { path: '**', redirectTo: 'inicio' }
          ]
    },
    {
        path: "consumidor",
        component: MenuConsuComponent,
        canActivateChild:[rolGuard],
        data: {expectedRole: 'consumidor'},
        children: [
            { path: 'inicio', component: PrincipalConsuComponent, data: { expectedRole: 'consumidor' } },
            { path: 'perfil', component: PerfilOferComponent, data: { expectedRole: 'consumidor' } },
            { path: 'listaOferta', component: ListOfertaComponent, data: { expectedRole: 'consumidor' } },
            { path: 'addOferta', component: AddOfertaComponent, data: { expectedRole: 'consumidor' }},
            { path: 'editOferta/:id', component: EditActividadComponent, data: { expectedRole: 'consumidor' }},
            { path: '', redirectTo: 'inicio', pathMatch: 'full' },
            { path: '**', redirectTo: 'inicio' }
          ]
    },
    {
        path: '**',
        redirectTo: ''
    },


];
