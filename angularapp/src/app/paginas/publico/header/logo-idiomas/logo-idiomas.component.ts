import { Component, OnInit } from '@angular/core';

import { Idioma } from './../../../../entidades/idioma';
import { IdiomasService } from './../../../../servicios/idiomas.service';
import { ManejoCookieIdiomaService } from './../../../../servicios/manejo-cookie-idioma.service';
import { TextoPagina } from './../../../../entidades/texto-pagina';
import { TextosPaginaService } from './../../../../servicios/textos-pagina.service';

@Component( {
    selector: 'logo-idiomas',
    templateUrl: './logo-idiomas.component.html'
} )
export class LogoIdiomasComponent implements OnInit 
{
    private pagina = 'logo-idiomas.component.html';
    idiomaSeleccionado: string;
    idiomas: Idioma[] = [];
    textosPagina: Object;

    constructor(private manejoCookieIdiomaService: ManejoCookieIdiomaService, private idiomasService: IdiomasService, private textosPaginaService: TextosPaginaService) 
    {
    }

    ngOnInit(): void 
    {
        this.idiomaSeleccionado = this.manejoCookieIdiomaService.getValorCookieIdioma();
        this.idiomasService.getIdiomas().subscribe(idiomas => this.idiomas = idiomas);
        this.textosPaginaService.getTextosDeLaPaginaSegunIdioma(this.pagina, this.idiomaSeleccionado).subscribe(textos => this.textosPagina = textos);
    }

    cambiarIdioma(idioma: Idioma) 
    {
        this.manejoCookieIdiomaService.cambiarCookieIdioma(idioma.abreviatura);
        this.idiomaSeleccionado = this.manejoCookieIdiomaService.getValorCookieIdioma();
        this.textosPaginaService.getTextosDeLaPaginaSegunIdioma(this.pagina, this.idiomaSeleccionado).subscribe(textos => this.textosPagina = textos);
    }
    
    getIdiomaSeleccionado() : string
    {
        return this.idiomaSeleccionado;
    }
}