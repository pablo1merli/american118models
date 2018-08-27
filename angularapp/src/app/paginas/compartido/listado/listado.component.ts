import { Component, DoCheck, KeyValueDiffers, OnDestroy} from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { Auto } from './../../../entidades/auto';
import { AutosService } from './../../../servicios/autos.service';
import { Idioma } from './../../../entidades/idioma';
import { ManejoCookieIdiomaService } from './../../../servicios/manejo-cookie-idioma.service';
import { TextosPaginaService } from './../../../servicios/textos-pagina.service';


@Component( {
    selector: 'listado',
    templateUrl: './listado.component.html'
} )
export class ListadoComponent implements DoCheck, OnDestroy
{
    private pagina = 'listado.component.html';
    idiomaSeleccionado: Idioma = {abreviatura: "", nombre: "", token: ""};
    textosPagina: Object;
    subscripcion: Subscription;
    diferencia: any;
    autos: Auto[];
    tituloPagina: string;
    isAdministrador: boolean;

    constructor(private manejoCookieIdiomaService: ManejoCookieIdiomaService, private autosService: AutosService, private textosPaginaService: TextosPaginaService, private differs: KeyValueDiffers, private router: Router) 
    {
        this.diferencia = differs.find({}).create();
        this.idiomaSeleccionado.abreviatura = manejoCookieIdiomaService.getValorCookieIdioma();
        this.subscripcion = this.manejoCookieIdiomaService.obtenerValorPropagadoDeCookieIdioma().subscribe(abreviatura => this.idiomaSeleccionado.abreviatura = abreviatura);
        this.autosService.getAutos().subscribe(autos => this.autos = autos);
        this.isAdministrador = (localStorage.getItem('administrador') != null) ? true: false;
    }
    
    irAReview(urlRest: string)
    {
        if (this.isAdministrador)
        {
            this.router.navigate( ['/abmAutos/' + urlRest] );
        }
        else
        {
            this.router.navigate( ['/review/' + urlRest] );
        }
    }

    ngDoCheck()
    {
        let huboCambios = this.diferencia.diff(this.idiomaSeleccionado);
        
        if (huboCambios)
        {
            this.textosPaginaService.getTextosDeLaPaginaSegunIdioma(this.pagina, this.idiomaSeleccionado.abreviatura).subscribe(textos => this.textosPagina = textos);
        }

        if (this.textosPagina != undefined)
        {
            this.tituloPagina = this.textosPagina['listado-titulo'];
        }
    }

    ngOnDestroy() 
    {
        this.subscripcion.unsubscribe();
    }
}