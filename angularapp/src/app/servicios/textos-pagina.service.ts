import { Injectable }    from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { TextoPagina } from './../entidades/texto-pagina';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class TextosPaginaService
{
    private textosPaginaApi = 'http://localhost:8080/american118models/servicios/rest/textosPagina';
    
    constructor(private http: HttpClient) { }

    getTextosPagina(): Observable<TextoPagina[]> 
    {
        return this.http.post<TextoPagina[]>(this.textosPaginaApi + "/getTextosPagina", JSON.stringify({}), httpOptions).pipe(
            catchError(this.manejoDeErrores<TextoPagina[]>('getTextosPagina')));    
    }
    
    getTextosDeLaPaginaSegunIdioma(pagina: string, idioma: string): Observable<Object> 
    {
        return this.http.post<TextoPagina[]>(this.textosPaginaApi + "/getTextosDePaginaSegunIdioma", JSON.stringify({pagina: pagina, idioma: idioma}), httpOptions).pipe(
            map(respuesta => this.convertirArrayEnObject(respuesta)),
            catchError(this.manejoDeErrores<TextoPagina[]>('getTextosDeLaPaginaSegunIdioma')));    
    }
    
    altaTextoPagina(textoPagina: TextoPagina): Observable<boolean>
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        textoPagina.token = token;
        
        return this.http.post<boolean>(this.textosPaginaApi + "/altaTextoPagina", JSON.stringify(textoPagina), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('altaTextoPagina')));    
        
    }

    bajaTextoPagina(textoPagina: TextoPagina): Observable<boolean> 
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        textoPagina.token = token;
        
        return this.http.post<boolean>(this.textosPaginaApi + "/bajaTextoPagina", JSON.stringify(textoPagina), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('bajaTextoPagina')));   
    }

    modificarTextoPagina(textoPagina: TextoPagina): Observable<boolean>
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        textoPagina.token = token;
        
        return this.http.post<boolean>(this.textosPaginaApi + "/modificarTextoPagina", JSON.stringify(textoPagina), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('modificarTextoPagina')));   
    }
    
    private convertirArrayEnObject(textosPagina: TextoPagina[]): Object
    {
        let textosPaginaObject = {};
        
        for (let textoPagina of textosPagina)
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
