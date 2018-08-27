// Angular
import * as $ from 'jquery';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Fin Angular

// Otras bibliotecas

// CookieService
import { CookieService } from 'ng2-cookies';
// Joda-Tme
import { LocalDate } from 'js-joda';
// Materialize
import { MzButtonModule, MzCardModule, MzCheckboxModule, MzDropdownModule, MzIconModule, MzIconMdiModule, MzInputModule, MzNavbarModule, MzSelectModule } from 'ngx-materialize';
// NGX-Gallery
import { NgxGalleryModule } from 'ngx-gallery';

// Fin Otras bibliotecas

// PRINCIPAL - American118Models
import { American118ModelsRoutingModule } from './american118models-routing.module';
import { American118ModelsComponent } from './american118models.component';
// Fin PRINCIPAL - American118Models

// SUBCOMPONENTES - American118Models
import { ABMAutosComponent } from './paginas/admin/consola/configuracion-del-sitio/abm-autos.component';
import { ABMIdiomasComponent } from './paginas/admin/consola/configuracion-del-sitio/abm-idiomas.component';
import { ABMTextosPaginaComponent } from './paginas/admin/consola/configuracion-del-sitio/abm-textospagina.component';
import { AdministradorService } from './paginas/admin/administrador.service';
import { AutosService } from './servicios/autos.service';
import { ChequeoAutenticacion } from './paginas/admin/chequeo-autenticacion';
import { ConsolaComponent } from './paginas/admin/consola/consola.component';
import { HeaderComponent } from './paginas/publico/header/header.component';
import { HomePageComponent } from './paginas/publico/homepage/homepage.component';
import { ListadoComponent } from './paginas/compartido/listado/listado.component';
import { LoginComponent } from './paginas/admin/login/login.component';
import { LogoIdiomasComponent } from './paginas/publico/header/logo-idiomas/logo-idiomas.component';
import { IdiomasService } from './servicios/idiomas.service';
import { ManejoCookieIdiomaService } from './servicios/manejo-cookie-idioma.service';
import { MenuAdminComponent } from './paginas/admin/header/menu-admin/menu-admin.component';
import { MenuBusquedaComponent } from './paginas/publico/header/menu/menu-busqueda.component';
import { ReviewComponent } from './paginas/publico/review/review.component';
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
        MzButtonModule, MzCardModule, MzCheckboxModule, MzDropdownModule, MzIconModule, MzIconMdiModule, MzInputModule, MzNavbarModule, MzSelectModule,
        // NGX-Gallery
        NgxGalleryModule
    ],
    declarations: [
        // PRINCIPAL
        American118ModelsComponent,
        // SUBCOMPONENTES
        ABMAutosComponent,
        ABMIdiomasComponent,
        ABMTextosPaginaComponent,
        ConsolaComponent,
        HeaderComponent,
        HomePageComponent,
        ListadoComponent,
        LoginComponent,
        LogoIdiomasComponent,
        MenuAdminComponent,
        MenuBusquedaComponent,
        ReviewComponent
    ],
    providers: [
        AdministradorService,
        AutosService,
        ChequeoAutenticacion,
        CookieService, IdiomasService,
        ManejoCookieIdiomaService,
        TextosPaginaService
    ],
    bootstrap: [American118ModelsComponent]
} )
export class American118ModelsModule { }
