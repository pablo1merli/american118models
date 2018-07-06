import { Injectable }    from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Idioma } from './../entidades/idioma';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class IdiomasService
{
    private idiomasApi = 'http://localhost:8080/american118models/servicios/rest/idioma';
    
    constructor(private http: HttpClient)
    { 
        
    }

    getIdiomas(): Observable<Idioma[]>
    {
        return this.http.post<Idioma[]>(this.idiomasApi + "/getIdiomas", JSON.stringify({}), httpOptions).pipe(
            catchError(this.manejoDeErrores<Idioma[]>('getIdiomas')));    
    }

    altaIdioma(idioma: Idioma): Observable<boolean>
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        idioma.token = token;
        
        return this.http.post<boolean>(this.idiomasApi + "/altaIdioma", JSON.stringify(idioma), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('altaIdioma')));    
        
    }

    bajaIdioma(idioma: Idioma): Observable<boolean> 
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        idioma.token = token;
        
        return this.http.post<boolean>(this.idiomasApi + "/bajaIdioma", JSON.stringify(idioma), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('bajaIdioma')));   
    }

    modificarIdioma(idioma: Idioma): Observable<boolean>
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        idioma.token = token;
        
        return this.http.post<boolean>(this.idiomasApi + "/modificarIdioma", JSON.stringify(idioma), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('modificarIdioma')));   
    }

    private manejoDeErrores<T> (operation = 'operation', result?: T) 
    {
        return (error: any): Observable<T> => {
           return of(result as T);
        };
    }    
}
