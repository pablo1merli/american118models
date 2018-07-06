import { Component, DoCheck, KeyValueDiffers, OnDestroy} from '@angular/core';
import { Subscription } from 'rxjs';

import { Idioma } from './../../../entidades/idioma';
import { ManejoCookieIdiomaService } from './../../../servicios/manejo-cookie-idioma.service';
import { TextoPagina } from './../../../entidades/texto-pagina';
import { TextosPaginaService } from './../../../servicios/textos-pagina.service';


@Component( {
    selector: 'homepage',
    templateUrl: './homepage.component.html'
} )
export class HomePageComponent implements DoCheck, OnDestroy
{
    private pagina = 'homepage.component.html';
    idiomaSeleccionado: Idioma = {abreviatura: "", nombre: "", token: ""};
    textosPagina: Object;
    subscripcion: Subscription;
    diferencia: any;

    constructor(private manejoCookieIdiomaService: ManejoCookieIdiomaService, private textosPaginaService: TextosPaginaService, private differs: KeyValueDiffers) 
    {
        this.diferencia = differs.find({}).create();
        this.idiomaSeleccionado.abreviatura = manejoCookieIdiomaService.getValorCookieIdioma();
        this.subscripcion = this.manejoCookieIdiomaService.obtenerValorPropagadoDeCookieIdioma().subscribe(abreviatura => this.idiomaSeleccionado.abreviatura = abreviatura);
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