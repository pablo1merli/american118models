import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AdministradorService } from './../../administrador.service';

@Component( {
    selector: 'menu-admin',
    templateUrl: './menu-admin.component.html',
} )
export class MenuAdminComponent 
{
    constructor(private administradorService: AdministradorService, private router: Router) 
    {
    }
    
    cerrarSesion()
    {
        this.administradorService.logoutAdministrador().subscribe(respuesta => this.analizarLogout(respuesta));
    }

    irAABMIdiomas()
    {
        this.router.navigate( ['/abmIdiomas'] );
    }
    
    irAABMTextosPagina()
    {
        this.router.navigate( ['/abmTextosPagina'] );
    }
    
    private analizarLogout(logoutExitoso: boolean)
    {
        if (logoutExitoso)
        {
            this.router.navigate( ['/login'] );
        }
        else
        {
            window.alert("Datos Incorrectos");
        }
    }
}