import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ChequeoAutenticacion } from './paginas/admin/chequeo-autenticacion';

import { ABMIdiomasComponent } from './paginas/admin/consola/configuracion-del-sitio/abm-idiomas.component';
import { ABMTextosPaginaComponent } from './paginas/admin/consola/configuracion-del-sitio/abm-textospagina.component';
import { ConsolaComponent }  from './paginas/admin/consola/consola.component';
import { HomePageComponent }  from './paginas/publico/homepage/homepage.component';
import { LoginComponent }  from './paginas/admin/login/login.component';

const routes: Routes = [
    { path: 'abmIdiomas', component: ABMIdiomasComponent, canActivate: [ChequeoAutenticacion] },
    { path: 'abmTextosPagina', component: ABMTextosPaginaComponent, canActivate: [ChequeoAutenticacion] },
    { path: 'consola', component: ConsolaComponent, canActivate: [ChequeoAutenticacion] },
    { path: 'inicio',  component: HomePageComponent },
    { path: 'login',  component: LoginComponent },
    { path: '**',  redirectTo: '/inicio' }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class American118ModelsRoutingModule {}