import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { Administrador } from './../../../entidades/administrador';
import { AdministradorService } from './../administrador.service';

@Component( {
    selector: 'login',
    templateUrl: './login.component.html'
} )
export class LoginComponent 
{
    isExisteAdministrador: boolean;
    
    constructor(private administradorService: AdministradorService, private router: Router) 
    {
        this.administradorService.isExisteAdministrador().subscribe(isExisteAdministrador => this.isExisteAdministrador = isExisteAdministrador);
    }
    
    loginAdministrador(usuario: string, password: string)
    {
        if ((usuario.trim() === "") || (password.trim() === ""))
        {
            window.alert("Datos incorrectos");
        }
        else
        {
            let administrador = new Administrador();
            administrador.usuario = usuario;
            administrador.password = password;
            
            this.administradorService.loginAdministrador(administrador).subscribe(respuesta => this.analizarLogin(respuesta));
        }
    }
    
    private analizarLogin(loginExitoso: boolean)
    {
        if (loginExitoso)
        {
            this.router.navigate( ['/consola'] );
        }
        else
        {
            window.alert("Datos Incorrectos");
        }
    }
}