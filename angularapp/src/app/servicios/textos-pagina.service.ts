import { Injectable }    from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { TextosPagina } from './../entidades/textos-pagina';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class TextosPaginaService
{
    private textosPaginaApi = 'http://${angular.url.ambiente}/american118models/servicios/rest/textosPagina';
    
    constructor(private http: HttpClient) { }

    getTextosPagina(): Observable<TextosPagina[]> 
    {
        return this.http.post<TextosPagina[]>(this.textosPaginaApi + "/getTextosPagina", JSON.stringify({}), httpOptions).pipe(
            catchError(this.manejoDeErrores<TextosPagina[]>('getTextosPagina')));    
    }
    
    getTextosDeLaPaginaSegunIdioma(pagina: string, idioma: string): Observable<Object> 
    {
        return this.http.post<TextosPagina>(this.textosPaginaApi + "/getTextosDePaginaSegunIdioma", JSON.stringify({pagina: pagina, idioma: idioma}), httpOptions).pipe(
            map(respuesta => this.convertirArrayEnObject(respuesta)),
            catchError(this.manejoDeErrores<TextosPagina>('getTextosDeLaPaginaSegunIdioma')));    
    }
    
    altaTextoPagina(textoPagina: TextosPagina): Observable<boolean>
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        textoPagina.token = token;
        
        return this.http.post<boolean>(this.textosPaginaApi + "/altaTextosPagina", JSON.stringify(textoPagina), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('altaTextosPagina')));    
        
    }

    bajaTextoPagina(textoPagina: TextosPagina): Observable<boolean> 
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        textoPagina.token = token;
        
        return this.http.post<boolean>(this.textosPaginaApi + "/bajaTextosPagina", JSON.stringify(textoPagina), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('bajaTextosPagina')));   
    }

    modificarTextoPagina(textoPagina: TextosPagina): Observable<boolean>
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        textoPagina.token = token;
        
        return this.http.post<boolean>(this.textosPaginaApi + "/modificarTextosPagina", JSON.stringify(textoPagina), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('modificarTextosPagina')));   
    }
    
    private convertirArrayEnObject(textosPagina: TextosPagina): Object
    {
        let textosPaginaObject = {};
        
        for (let textoPagina of textosPagina.textosPagina)
        {
            textosPaginaObject[textoPagina.tag] = textoPagina.texto;
        }

        return textosPaginaObject;
    }

    private manejoDeErrores<T> (operation = 'operation', result?: T) 
    {
        return (error: any): Observable<T> => {
           return of(result as T);
        };
    }    
}
