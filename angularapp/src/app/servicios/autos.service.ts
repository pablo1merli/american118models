import { Injectable }    from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Auto } from './../entidades/auto';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class AutosService
{
    private autoApi = 'http://${angular.url.ambiente}/american118models/servicios/rest/auto';
    
    constructor(private http: HttpClient) { }

    getAutoPorUrlRest(urlRest: string): Observable<Auto> 
    {
        return this.http.post<Auto>(this.autoApi + "/getAutoPorUrlRest", urlRest, httpOptions).pipe(
            catchError(this.manejoDeErrores<Auto>('getAutoPorUrlRest')));    
    }

    getAutos(): Observable<Auto[]> 
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        let auto = new Auto();
        auto.token = token;

        return this.http.post<Auto[]>(this.autoApi + "/getAutos", JSON.stringify(auto), httpOptions).pipe(
            catchError(this.manejoDeErrores<Auto[]>('getAutos')));    
    }
 
    altaAuto(auto: Auto): Observable<boolean>
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        auto.token = token;
        
        return this.http.post<boolean>(this.autoApi + "/altaAuto", JSON.stringify(auto), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('altaAuto')));    
        
    }

    bajaAuto(auto: Auto): Observable<boolean> 
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        auto.token = token;
        
        return this.http.post<boolean>(this.autoApi + "/bajaAuto", JSON.stringify(auto), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('bajaAuto')));   
    }

    modificarAuto(auto: Auto): Observable<boolean>
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        auto.token = token;
        
        return this.http.post<boolean>(this.autoApi + "/modificarAuto", JSON.stringify(auto), httpOptions).pipe(
                catchError(this.manejoDeErrores<boolean>('modificarAuto')));   
    }

    private manejoDeErrores<T> (operation = 'operation', result?: T) 
    {
        return (error: any): Observable<T> => {
           return of(result as T);
        };
    }    
}
