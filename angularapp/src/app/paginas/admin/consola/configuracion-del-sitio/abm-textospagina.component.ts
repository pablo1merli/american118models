import { Component } from '@angular/core';

import { TextoPagina } from './../../../../entidades/texto-pagina';
import { TextosPaginaService } from './../../../../servicios/textos-pagina.service';

@Component( {
    selector: 'abm-textospagina',
    templateUrl: './abm-textospagina.component.html'
} )
export class ABMTextosPaginaComponent 
{
    textosPagina: TextoPagina[] = [];
    textoPaginaSeleccionado: TextoPagina = new TextoPagina();

    inhabilitarBotonesOperaciones: boolean;
    inhabilitarSelectTextosPagina: boolean;
    inhabilitarInputIdioma: boolean;
    inhabilitarInputTag: boolean;
    inhabilitarInputTexto: boolean;
    operacionSeleccionada: string;


    constructor(private textosPaginaService: TextosPaginaService) 
    {
        this.inicializarFormulario(true);
    }
    
    aceptarOperacion()
    {
        switch (this.operacionSeleccionada)
        {
            case "alta": 
            { 
                if (this.textoPaginaSeleccionado.tag === "" || this.textoPaginaSeleccionado.texto === "")
                {
                    window.alert("Llena bien los campos, gil");
                }
                else
                {
                    this.textosPaginaService.altaTextoPagina(this.textoPaginaSeleccionado).subscribe(resp => this.inicializarFormulario(resp));
                }
                
                break; 
            } 
            case "baja": 
            { 
                this.textosPaginaService.bajaTextoPagina(this.textoPaginaSeleccionado).subscribe(resp => this.inicializarFormulario(resp));

                break; 
            }
            case "modificacion": 
            { 
                this.textosPaginaService.modificarTextoPagina(this.textoPaginaSeleccionado).subscribe(resp => this.inicializarFormulario(resp));
                
                break; 
            }
            case "cancelar": 
            { 
                this.inicializarFormulario(true);
                
                break; 
            }
        }
    }

    bloquearDesbloquearControles(operacion: string)
    {
        this.operacionSeleccionada = operacion;
        
        switch (operacion)
        {
            case "alta": 
            { 
                this.inhabilitarBotonesOperaciones = true;
                this.inhabilitarInputIdioma = false;
                this.inhabilitarInputTag = false;
                this.inhabilitarInputTexto = false;
                this.textoPaginaSeleccionado.idioma = "";
                this.textoPaginaSeleccionado.tag = "";
                this.textoPaginaSeleccionado.texto = "";
                
                break; 
            } 
            case "baja": 
            { 
                this.inhabilitarBotonesOperaciones = true;
                this.inhabilitarSelectTextosPagina = false;

                break; 
            }
            case "modificacion": 
            { 
                this.inhabilitarBotonesOperaciones = true;
                this.inhabilitarSelectTextosPagina = false;
                this.inhabilitarInputIdioma = false;
                this.inhabilitarInputTag = false;
                this.inhabilitarInputTexto = false;
                
                break; 
            }
            case "cancelar": 
            { 
                this.inicializarFormulario(true);
                
                break; 
            }
        }
    }
    
    obtenerValorSelect(tag: string)
    {
        for (let i = 0; i < this.textosPagina.length; i++)
        {
            if (tag === (this.textosPagina[i].idioma + this.textosPagina[i].tag))
            {
                this.textoPaginaSeleccionado = this.textosPagina[i];
                break;
            }
        }
    }
    
    private inicializarFormulario(respuesta: boolean)
    {
        if (respuesta)
        {
            this.textosPaginaService.getTextosPagina().subscribe(textosPagina => this.textosPagina = textosPagina);

            this.textoPaginaSeleccionado.idioma = "";
            this.textoPaginaSeleccionado.tag = "";
            this.textoPaginaSeleccionado.texto = "";
            this.inhabilitarBotonesOperaciones = false;
            this.inhabilitarSelectTextosPagina = true;
            this.inhabilitarInputIdioma = true;
            this.inhabilitarInputTag = true;
            this.inhabilitarInputTexto = true;
            
            this.operacionSeleccionada = null;
        }
        else
        {
            window.alert("Fallo la operacion");
        }
    }
}