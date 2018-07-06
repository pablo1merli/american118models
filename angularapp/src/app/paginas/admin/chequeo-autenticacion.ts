import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable()
export class ChequeoAutenticacion implements CanActivate 
{

    constructor( private router: Router ) 
    {
        
    }

    canActivate( route: ActivatedRouteSnapshot, state: RouterStateSnapshot ) 
    {
        if ( localStorage.getItem( 'administrador' ) ) 
        {
            // hay un usuario autenticado
            return true;
        }

        // no hay usuario autenticado, se manda a la homepage
        this.router.navigate( ['/inicio'] );
        return false;
    }
}