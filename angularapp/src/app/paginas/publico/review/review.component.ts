import { Component, DoCheck, KeyValueDiffers, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

import { NgxGalleryOptions, NgxGalleryImage, NgxGalleryAnimation } from 'ngx-gallery';

import { Auto } from './../../../entidades/auto';
import { AutosService } from './../../../servicios/autos.service';
import { Idioma } from './../../../entidades/idioma';
import { ManejoCookieIdiomaService } from './../../../servicios/manejo-cookie-idioma.service';
import { Review } from './../../../entidades/review';
import { TextosPaginaService } from './../../../servicios/textos-pagina.service';

@Component( {
    selector: 'review',
    templateUrl: './review.component.html'
} )
export class ReviewComponent implements DoCheck, OnDestroy
{
    private pagina = 'review.component.html';
    idiomaSeleccionado: Idioma = {abreviatura: "", nombre: "", token: ""};
    textosPagina: Object;
    subscripcion: Subscription;
    diferenciaIdioma: any;
    tituloPagina: string;
    urlRest: string;
    galleryOptions: NgxGalleryOptions[];
    galleryImages: NgxGalleryImage[];
    autoSeleccionado: Auto;
    seLlenoGaleria: boolean;
    reviewSegunIdioma: Review;

    constructor(private manejoCookieIdiomaService: ManejoCookieIdiomaService, private autosService: AutosService, private textosPaginaService: TextosPaginaService, private differs: KeyValueDiffers, private route: ActivatedRoute) 
    {
        this.seLlenoGaleria = false;
        this.diferenciaIdioma = differs.find({}).create();
        this.idiomaSeleccionado.abreviatura = manejoCookieIdiomaService.getValorCookieIdioma();
        this.subscripcion = this.manejoCookieIdiomaService.obtenerValorPropagadoDeCookieIdioma().subscribe(abreviatura => this.idiomaSeleccionado.abreviatura = abreviatura);
        this.urlRest = this.route.snapshot.paramMap.get('urlRest');
        this.autosService.getAutoPorUrlRest(this.urlRest).subscribe(auto => this.autoSeleccionado = auto);

        this.galleryOptions = 
        [
             {
                 width: '100%',
                 //height: '400px',
                 thumbnailsColumns: 6,
                 previewCloseOnClick: true, 
                 previewCloseOnEsc: true, 
                 imageAnimation: NgxGalleryAnimation.Slide
             },
             // max-width 800
             {
                 breakpoint: 800,
                 width: '100%',
                 height: '600px',
                 imagePercent: 80,
                 thumbnailsPercent: 20,
                 thumbnailsMargin: 20,
                 thumbnailMargin: 20
             },
             // max-width 400
             {
                 breakpoint: 400,
                 preview: false
             }
        ];
    }
    
    ngDoCheck()
    {
        let huboCambiosIdioma = this.diferenciaIdioma.diff(this.idiomaSeleccionado);
        
        if (this.autoSeleccionado != undefined)
        {
            if (!this.seLlenoGaleria)
            {
                this.llenarGaleriaImagenes();
                this.seLlenoGaleria = true;
            }
            
            if (this.autoSeleccionado.reviews != null)
            {
                for (let review of this.autoSeleccionado.reviews)
                {
                    if (review.idioma == this.idiomaSeleccionado.abreviatura)
                    {
                        this.reviewSegunIdioma = review;
                        break;
                    }
                }
            }
        }
    
        if (huboCambiosIdioma)
        {
            this.textosPaginaService.getTextosDeLaPaginaSegunIdioma(this.pagina, this.idiomaSeleccionado.abreviatura).subscribe(textos => this.textosPagina = textos);
        }

        if (this.textosPagina != undefined)
        {
            this.tituloPagina = this.textosPagina['review-titulo'];
        }
    }

    ngOnDestroy() 
    {
        this.subscripcion.unsubscribe();
    }
    
    private llenarGaleriaImagenes()
    {
        
        if (this.galleryImages == undefined)
        {
            this.galleryImages = [];
        }
        else
        {
            while (this.galleryImages.length > 0)
            {
                this.galleryImages.pop();
            }
        }
        
        for (let i = 0; i < this.autoSeleccionado.urlFotos.length; i++)
        {
            let foto =  {
                            small: this.autoSeleccionado.urlFotos[i],
                            medium: this.autoSeleccionado.urlFotos[i],
                            big: this.autoSeleccionado.urlFotos[i]
                        };
            
            this.galleryImages.push(foto);
        }
    }
}