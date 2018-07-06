import { Component } from '@angular/core';

import { Idioma } from './../../../../entidades/idioma';
import { IdiomasService } from './../../../../servicios/idiomas.service';

@Component( {
    selector: 'abm-idiomas',
    templateUrl: './abm-idiomas.component.html'
} )
export class ABMIdiomasComponent 
{
    idiomas: Idioma[] = [];
    idiomaSeleccionado: Idioma = new Idioma();

    inhabilitarBotonesOperaciones: boolean;
    inhabilitarSelectIdioma: boolean;
    inhabilitarInputAbreviatura: boolean;
    inhabilitarInputNombre: boolean;
    operacionSeleccionada: string;


    constructor(private idiomasService: IdiomasService) 
    {
        this.inicializarFormulario(true);
    }
    
    aceptarOperacion()
    {
        switch (this.operacionSeleccionada)
        {
            case "alta": 
            { 
                if (this.idiomaSeleccionado.abreviatura === "" || this.idiomaSeleccionado.nombre === "")
                {
                    window.alert("Llena bien los campos, gil");
                }
                else
                {
                    this.idiomasService.altaIdioma(this.idiomaSeleccionado).subscribe(resp => this.inicializarFormulario(resp));
                }
                
                break; 
            } 
            case "baja": 
            { 
                this.idiomasService.bajaIdioma(this.idiomaSeleccionado).subscribe(resp => this.inicializarFormulario(resp));

                break; 
            }
            case "modificacion": 
            { 
                this.idiomasService.modificarIdioma(this.idiomaSeleccionado).subscribe(resp => this.inicializarFormulario(resp));
                
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
                this.inhabilitarInputAbreviatura = false;
                this.inhabilitarInputNombre = false;
                this.idiomaSeleccionado.abreviatura = "";
                this.idiomaSeleccionado.nombre = "";
                
                break; 
            } 
            case "baja": 
            { 
                this.inhabilitarBotonesOperaciones = true;
                this.inhabilitarSelectIdioma = false;

                break; 
            }
            case "modificacion": 
            { 
                this.inhabilitarBotonesOperaciones = true;
                this.inhabilitarSelectIdioma = false;
                this.inhabilitarInputAbreviatura = false;
                this.inhabilitarInputNombre = false;
                
                break; 
            }
            case "cancelar": 
            { 
                this.inicializarFormulario(true);
                
                break; 
            }
        }
    }
    
    obtenerValorSelect(abreviatura: string)
    {
        for (let i = 0; i < this.idiomas.length; i++)
        {
            if (abreviatura === this.idiomas[i].abreviatura)
            {
                this.idiomaSeleccionado = this.idiomas[i];
                break;
            }
        }
    }
    
    private inicializarFormulario(respuesta: boolean)
    {
        if (respuesta)
        {
            this.idiomasService.getIdiomas().subscribe(idiomas => this.idiomas = idiomas);

            this.idiomaSeleccionado.abreviatura = "";
            this.idiomaSeleccionado.nombre = "";
            this.inhabilitarBotonesOperaciones = false;
            this.inhabilitarSelectIdioma = true;
            this.inhabilitarInputAbreviatura = true;
            this.inhabilitarInputNombre = true;
            
            this.operacionSeleccionada = null;
        }
        else
        {
            window.alert("Fallo la operacion");
        }
    }
}