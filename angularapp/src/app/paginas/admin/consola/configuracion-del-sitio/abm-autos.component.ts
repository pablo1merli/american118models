import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Auto } from './../../../../entidades/auto';
import { AutosService } from './../../../../servicios/autos.service';
import { DatosAuto } from './../../../../entidades/datos-auto';
import { Review } from './../../../../entidades/review';

@Component( {
    selector: 'abm-autos',
    templateUrl: './abm-autos.component.html'
} )
export class ABMAutosComponent 
{
    autos: Auto[] = [];
    autoSeleccionado: Auto = new Auto();

    inhabilitarBotonesOperaciones: boolean;
    inhabilitarSelectAuto: boolean;
    inhabilitarInputAuto: boolean;
    operacionSeleccionada: string;
    urlRest: string;

    constructor(private autosService: AutosService, private route: ActivatedRoute) 
    {
        this.urlRest = this.route.snapshot.paramMap.get('urlRest');
        this.inicializarFormulario(true);
    }
    
    aceptarOperacion()
    {
        switch (this.operacionSeleccionada)
        {
            case "alta": 
            {
                let cantidadDatosDinamicosVacios = 0;
                for (let i = 0; i < this.autoSeleccionado.urlFotos.length && cantidadDatosDinamicosVacios == 0; i++)
                {
                    if (this.autoSeleccionado.urlFotos[i] === undefined || this.autoSeleccionado.urlFotos[i].trim() === "")
                    {
                        cantidadDatosDinamicosVacios++;
                    }
                }
                
                if ((this.autoSeleccionado.reviews != null) && (this.autoSeleccionado.reviews.length > 0))
                {
                    for (let i = 0; i < this.autoSeleccionado.reviews.length && cantidadDatosDinamicosVacios == 0; i++)
                    {
                        if (this.autoSeleccionado.reviews[i].idioma === undefined || this.autoSeleccionado.reviews[i].idioma.trim() === "" ||
                            this.autoSeleccionado.reviews[i].review === undefined || this.autoSeleccionado.reviews[i].review.trim() === "")
                        {
                            cantidadDatosDinamicosVacios++;
                        }
                    }
                }
                
                if (this.autoSeleccionado.anioFabricacion === 0 || this.autoSeleccionado.datosAuto.anio === 0 || this.autoSeleccionado.datosAuto.color.trim() === "" || this.autoSeleccionado.datosAuto.marca.trim() === "" || 
                    this.autoSeleccionado.datosAuto.modelo.trim() === "" || this.autoSeleccionado.datosAuto.motor.trim() === "" || this.autoSeleccionado.datosAuto.ordenamiento < 0 || this.autoSeleccionado.datosAuto.potencia === 0 ||
                    this.autoSeleccionado.fabricante.trim() === "" || this.autoSeleccionado.serie.trim() === "" || this.autoSeleccionado.cantidadProducida === 0 || this.autoSeleccionado.urlRest.trim() === "" || cantidadDatosDinamicosVacios > 0)
                {
                    window.alert("Llena bien los campos, gil");
                }
                else 
                {
                    // Lo asigno en null para que en la BD no se guarde el campo sin valores
                    if ((this.autoSeleccionado.reviews != null) && (this.autoSeleccionado.reviews.length == 0))
                    {
                        this.autoSeleccionado.reviews = null;
                    }
                    
                    this.autosService.altaAuto(this.autoSeleccionado).subscribe(resp => this.inicializarFormulario(resp));
                }

                break; 
            } 
            case "baja": 
            { 
                if ((this.autoSeleccionado.reviews != null) && (this.autoSeleccionado.reviews.length > 0))
                {
                    for (let i = 0; i < this.autoSeleccionado.reviews.length; i++)
                    {
                        this.autoSeleccionado.reviews[i].fecha = null;
                    }
                }

                this.autosService.bajaAuto(this.autoSeleccionado).subscribe(resp => this.inicializarFormulario(resp));

                break; 
            }
            case "modificacion": 
            { 
                let cantidadDatosDinamicosVacios = 0;
                for (let i = 0; i < this.autoSeleccionado.urlFotos.length && cantidadDatosDinamicosVacios == 0; i++)
                {
                    if (this.autoSeleccionado.urlFotos[i] === undefined || this.autoSeleccionado.urlFotos[i].trim() === "")
                    {
                        cantidadDatosDinamicosVacios++;
                    }
                }
                
                if ((this.autoSeleccionado.reviews != null) && (this.autoSeleccionado.reviews.length > 0))
                {
                    for (let i = 0; i < this.autoSeleccionado.reviews.length && cantidadDatosDinamicosVacios == 0; i++)
                    {
                        this.autoSeleccionado.reviews[i].fecha = null;
                        if (this.autoSeleccionado.reviews[i].idioma === undefined || this.autoSeleccionado.reviews[i].idioma.trim() === "" ||
                            this.autoSeleccionado.reviews[i].review === undefined || this.autoSeleccionado.reviews[i].review.trim() === "")
                        {
                            cantidadDatosDinamicosVacios++;
                        }
                    }
                }
                
                if (this.autoSeleccionado.anioFabricacion === 0 || this.autoSeleccionado.datosAuto.anio === 0 || this.autoSeleccionado.datosAuto.color.trim() === "" || this.autoSeleccionado.datosAuto.marca.trim() === "" || 
                    this.autoSeleccionado.datosAuto.modelo.trim() === "" || this.autoSeleccionado.datosAuto.motor.trim() === "" || this.autoSeleccionado.datosAuto.ordenamiento < 0 || this.autoSeleccionado.datosAuto.potencia === 0 ||
                    this.autoSeleccionado.fabricante.trim() === "" || this.autoSeleccionado.serie.trim() === "" || this.autoSeleccionado.cantidadProducida === 0 || this.autoSeleccionado.urlRest.trim() === "" || cantidadDatosDinamicosVacios > 0)
                {
                    window.alert("Llena bien los campos, gil");
                }
                else 
                {
                    // Lo asigno en null para que en la BD no se guarde el campo sin valores
                    if ((this.autoSeleccionado.reviews != null) && (this.autoSeleccionado.reviews.length == 0))
                    {
                        this.autoSeleccionado.reviews = null;
                    }
                    
                    this.autosService.modificarAuto(this.autoSeleccionado).subscribe(resp => this.inicializarFormulario(resp));
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
                this.inhabilitarInputAuto = false;

                this.blanquearAutoSeleccionado();
                this.agregarFoto();

                break; 
            } 
            case "baja": 
            { 
                this.inhabilitarBotonesOperaciones = true;
                this.inhabilitarSelectAuto = false;

                break; 
            }
            case "modificacion": 
            { 
                this.inhabilitarBotonesOperaciones = true;
                this.inhabilitarSelectAuto = false;

                this.inhabilitarInputAuto = false;
                
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
        for (let i = 0; i < this.autos.length; i++)
        {
            if (tag === (this.autos[i].datosAuto.anio + this.autos[i].datosAuto.marca + this.autos[i].datosAuto.modelo + this.autos[i].datosAuto.motor))
            {
                this.autoSeleccionado = this.autos[i];
                break;
            }
        }
    }
  
    agregarFoto()
    {
        this.autoSeleccionado.urlFotos.push("");
    }
    
    agregarReview()
    {
        this.autoSeleccionado.reviews.push(new Review());
    }
    
    private inicializarFormulario(respuesta: boolean)
    {
        if (respuesta)
        {
            this.autosService.getAutos().subscribe(autos => this.autos = autos);
            
            if (this.urlRest !== "default")
            {
                this.seleccionarAutoPorUrlRest();
            }

            if (this.autoSeleccionado.datosAuto == null)
            {
                this.autoSeleccionado.datosAuto = new DatosAuto();  
            }

            if (this.autoSeleccionado.reviews == null)
            {
                this.autoSeleccionado.reviews = [];  
            }
            else
            {
                while (this.autoSeleccionado.reviews.length > 0)
                {
                    this.autoSeleccionado.reviews.pop();
                }
            }

            if (this.autoSeleccionado.urlFotos == null)
            {
                this.autoSeleccionado.urlFotos = [];  
            }
            else
            {
                while (this.autoSeleccionado.urlFotos.length > 0)
                {
                    this.autoSeleccionado.urlFotos.pop();
                }
            }
          
            this.inhabilitarSelectAuto = true;
            this.inhabilitarBotonesOperaciones = false;
            this.inhabilitarInputAuto = true;

            this.blanquearAutoSeleccionado();
            
            this.operacionSeleccionada = null;
        }
        else
        {
            window.alert("Fallo la operacion");
        }
    }
    
    private seleccionarAutoPorUrlRest()
    {
        for (let i = 0; i < this.autos.length; i++)
        {
            if (this.autos[i].urlRest === this.urlRest)
            {
                this.autoSeleccionado = this.autos[i];
                break;
            }
        }
    }
    
    private blanquearAutoSeleccionado()
    {
        this.autoSeleccionado.anioFabricacion = 0;
        this.autoSeleccionado.datosAuto.anio = 0;
        this.autoSeleccionado.datosAuto.color = "";
        this.autoSeleccionado.datosAuto.marca = "";
        this.autoSeleccionado.datosAuto.modelo = "";
        this.autoSeleccionado.datosAuto.motor = "";
        this.autoSeleccionado.datosAuto.ordenamiento = 0;
        this.autoSeleccionado.datosAuto.potencia = 0;
        this.autoSeleccionado.fabricante = "";
        this.autoSeleccionado.serie = "";
        this.autoSeleccionado.cantidadProducida = 0;
        this.autoSeleccionado.loTengo = false;
        this.autoSeleccionado.edicionEspecial = "";
        this.autoSeleccionado.urlRest = "";
    }
}