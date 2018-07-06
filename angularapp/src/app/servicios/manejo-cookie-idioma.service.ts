import { Injectable }    from '@angular/core';
import { CookieService } from 'ng2-cookies';
import { Observable, Subject } from 'rxjs';

@Injectable()
export class ManejoCookieIdiomaService 
{
    private subject = new Subject<string>();
    cookies: Object;
    claves: Array<string>;
    private nombreCookieIdioma = "idioma";

    constructor(public cookieService: CookieService) 
    {
        if (!this.cookieService.get(this.nombreCookieIdioma))
        {
            this.cambiarCookieIdioma( "esp");
        }
    
        this.actualizarCookies();
        this.propagarValorDeCookieIdioma(this.getValorCookieIdioma());
    }
    
    cambiarCookieIdioma(cookieValor: string) 
    {
        this.cookieService.set(this.nombreCookieIdioma, cookieValor, 365);
        this.actualizarCookies();
        this.propagarValorDeCookieIdioma(cookieValor);
    }

    getValorCookieIdioma() : string
    {
        return this.cookieService.get(this.nombreCookieIdioma);
    }

    propagarValorDeCookieIdioma(abreviatura: string) 
    {
        this.subject.next(abreviatura);
    }

    obtenerValorPropagadoDeCookieIdioma(): Observable<string> 
    {
        return this.subject.asObservable();
    }

    private actualizarCookies() 
    {
        this.cookies = this.cookieService.getAll();
        this.claves = Object.keys( this.cookies );
    }
}
