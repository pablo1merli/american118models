// Angular 2
import * as $ from 'jquery';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Fin Angular 2

// Otras bibliotecas

// CookieService
import { CookieService } from 'ng2-cookies';
// Materialize
import { MzButtonModule, MzCardModule, MzDropdownModule, MzIconModule, MzIconMdiModule, MzInputModule, MzNavbarModule, MzSelectModule } from 'ngx-materialize';

// Fin Otras bibliotecas

// PRINCIPAL - American118Models
import { American118ModelsRoutingModule } from './american118models-routing.module';
import { American118ModelsComponent } from './american118models.component';
// Fin PRINCIPAL - American118Models

// SUBCOMPONENTES - American118Models
import { ABMIdiomasComponent } from './paginas/admin/consola/configuracion-del-sitio/abm-idiomas.component';
import { ABMTextosPaginaComponent } from './paginas/admin/consola/configuracion-del-sitio/abm-textospagina.component';
import { AdministradorService } from './paginas/admin/administrador.service';
import { ChequeoAutenticacion } from './paginas/admin/chequeo-autenticacion';
import { HeaderComponent } from './paginas/publico/header/header.component';
import { HomePageComponent } from './paginas/publico/homepage/homepage.component';
import { LoginComponent } from './paginas/admin/login/login.component';
import { LogoIdiomasComponent } from './paginas/publico/header/logo-idiomas/logo-idiomas.component';
import { IdiomasService } from './servicios/idiomas.service';
import { ManejoCookieIdiomaService } from './servicios/manejo-cookie-idioma.service';
import { ConsolaComponent } from './paginas/admin/consola/consola.component';
import { MenuAdminComponent } from './paginas/admin/header/menu-admin/menu-admin.component';
import { MenuBusquedaComponent } from './paginas/publico/header/menu/menu-busqueda.component';
import { TextosPaginaService } from './servicios/textos-pagina.service';
// Fin SUBCOMPONENTES - American118Models


@NgModule( {
    exports: [
        // Materialize
        MzButtonModule, MzCardModule, MzDropdownModule, MzIconModule, MzIconMdiModule, MzInputModule, MzNavbarModule, MzSelectModule
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpClientModule,
        American118ModelsRoutingModule,
        // Materialize
        MzButtonModule, MzCardModule, MzDropdownModule, MzIconModule, MzIconMdiModule, MzInputModule, MzNavbarModule, MzSelectModule
    ],
    declarations: [
        // PRINCIPAL
        American118ModelsComponent,
        // SUBCOMPONENTES
        ABMIdiomasComponent,
        ABMTextosPaginaComponent,
        ConsolaComponent,
        HeaderComponent,
        HomePageComponent,
        LoginComponent,
        LogoIdiomasComponent,
        MenuAdminComponent,
        MenuBusquedaComponent
    ],
    providers: [
        AdministradorService,
        ChequeoAutenticacion,
        CookieService, IdiomasService,
        ManejoCookieIdiomaService,
        TextosPaginaService
    ],
    bootstrap: [American118ModelsComponent]
} )
export class American118ModelsModule { }
