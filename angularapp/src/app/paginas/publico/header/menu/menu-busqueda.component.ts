import { Component, DoCheck, KeyValueDiffers, OnDestroy} from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';


import { Idioma } from './../../../../entidades/idioma';
import { ManejoCookieIdiomaService } from './../../../../servicios/manejo-cookie-idioma.service';
import { TextoPagina } from './../../../../entidades/texto-pagina';
import { TextosPaginaService } from './../../../../servicios/textos-pagina.service';


@Component( {
    selector: 'menu-busqueda',
    templateUrl: './menu-busqueda.component.html',
} )
export class MenuBusquedaComponent implements  DoCheck, OnDestroy
{
    private pagina = 'menu-busqueda.component.html';
    idiomaSeleccionado: Idioma = {abreviatura: "", nombre: "", token: ""};
    textosPagina: Object;
    subscripcion: Subscription;
    diferencia: any;

    constructor(private manejoCookieIdiomaService: ManejoCookieIdiomaService, private textosPaginaService: TextosPaginaService, private differs: KeyValueDiffers, private router: Router) 
    {
        this.diferencia = differs.find({}).create();
        this.idiomaSeleccionado.abreviatura = manejoCookieIdiomaService.getValorCookieIdioma();
        this.subscripcion = this.manejoCookieIdiomaService.obtenerValorPropagadoDeCookieIdioma().subscribe(abreviatura => this.idiomaSeleccionado.abreviatura = abreviatura);
    }
    
    irAListado()
    {
        this.router.navigate( ['/listado'] );
    }

    ngDoCheck()
    {
        let huboCambios = this.diferencia.diff(this.idiomaSeleccionado);
        
        if (huboCambios)
        {
            this.textosPaginaService.getTextosDeLaPaginaSegunIdioma(this.pagina, this.idiomaSeleccionado.abreviatura).subscribe(textos => this.textosPagina = textos);
        }
    }

    ngOnDestroy() 
    {
        this.subscripcion.unsubscribe();
    }
}