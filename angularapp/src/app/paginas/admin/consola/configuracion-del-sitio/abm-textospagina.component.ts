import { Component } from '@angular/core';

import { TextoPagina } from './../../../../entidades/texto-pagina';
import { TextosPagina } from './../../../../entidades/textos-pagina';
import { TextosPaginaService } from './../../../../servicios/textos-pagina.service';

@Component( {
    selector: 'abm-textospagina',
    templateUrl: './abm-textospagina.component.html'
} )
export class ABMTextosPaginaComponent 
{
    textosPagina: TextosPagina[] = [];
    textosPaginaSeleccionado: TextosPagina = new TextosPagina();

    inhabilitarBotonesOperaciones: boolean;
    inhabilitarSelectTextosPagina: boolean;
    inhabilitarInputIdioma: boolean;
    inhabilitarInputPagina: boolean;
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
                let cantidadTagTextoVacios = 0;
                for (let i = 0; i < this.textosPaginaSeleccionado.textosPagina.length && cantidadTagTextoVacios == 0; i++)
                {
                    if (this.textosPaginaSeleccionado.textosPagina[i].tag === undefined || this.textosPaginaSeleccionado.textosPagina[i].tag.trim() === "" || 
                        this.textosPaginaSeleccionado.textosPagina[i].texto === undefined || this.textosPaginaSeleccionado.textosPagina[i].texto.trim() === "")
                    {
                        cantidadTagTextoVacios++;
                    }
                }
                    
                if (this.textosPaginaSeleccionado.idioma.trim() === "" || this.textosPaginaSeleccionado.pagina.trim() === "" || cantidadTagTextoVacios > 0)
                {
                    window.alert("Llena bien los campos, gil");
                }
                else
                {
                    this.textosPaginaService.altaTextoPagina(this.textosPaginaSeleccionado).subscribe(resp => this.inicializarFormulario(resp));
                }
                
                break; 
            } 
            case "baja": 
            { 
                this.textosPaginaService.bajaTextoPagina(this.textosPaginaSeleccionado).subscribe(resp => this.inicializarFormulario(resp));

                break; 
            }
            case "modificacion": 
            { 
                let cantidadTagTextoVacios = 0;
                for (let i = 0; i < this.textosPaginaSeleccionado.textosPagina.length && cantidadTagTextoVacios == 0; i++)
                {
                    if (this.textosPaginaSeleccionado.textosPagina[i].tag === undefined || this.textosPaginaSeleccionado.textosPagina[i].tag.trim() === "" || 
                        this.textosPaginaSeleccionado.textosPagina[i].texto === undefined || this.textosPaginaSeleccionado.textosPagina[i].texto.trim() === "")
                    {
                        cantidadTagTextoVacios++;
                    }
                }
                    
                if (this.textosPaginaSeleccionado.idioma.trim() === "" || this.textosPaginaSeleccionado.pagina.trim() === "" || cantidadTagTextoVacios > 0)
                {
                    window.alert("Llena bien los campos, gil");
                }
                else 
                {
                    this.textosPaginaService.modificarTextoPagina(this.textosPaginaSeleccionado).subscribe(resp => this.inicializarFormulario(resp));
                }
                
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
                this.inhabilitarInputPagina = false;
                this.inhabilitarInputTag = false;
                this.inhabilitarInputTexto = false;
                this.textosPaginaSeleccionado.idioma = "";
                this.textosPaginaSeleccionado.pagina = "";
                this.agregarTag();
                
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
            if (tag === (this.textosPagina[i].pagina + this.textosPagina[i].idioma))
            {
                this.textosPaginaSeleccionado = this.textosPagina[i];
                break;
            }
        }
    }
  
    agregarTag()
    {
        this.textosPaginaSeleccionado.textosPagina.push(new TextoPagina());
    }
    
    private inicializarFormulario(respuesta: boolean)
    {
        if (respuesta)
        {
            this.textosPaginaService.getTextosPagina().subscribe(textosPagina => this.textosPagina = textosPagina);

            if (this.textosPaginaSeleccionado.textosPagina == null)
            {
                  this.textosPaginaSeleccionado.textosPagina = [];  
            }
            else
            {
                while (this.textosPaginaSeleccionado.textosPagina.length > 0)
                {
                    this.textosPaginaSeleccionado.textosPagina.pop();
                }
            }
            this.textosPaginaSeleccionado.idioma = "";
            this.textosPaginaSeleccionado.pagina = "";
            this.inhabilitarBotonesOperaciones = false;
            this.inhabilitarSelectTextosPagina = true;
            this.inhabilitarInputIdioma = true;
            this.inhabilitarInputPagina = true;
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