import { Injectable }    from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Administrador } from './../../entidades/administrador';
import { Token } from './../../entidades/token';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class AdministradorService
{
    private administradorApi = 'http://localhost:8080/american118models/servicios/rest/administrador';
    
    constructor(private http: HttpClient) { }

    isExisteAdministrador(): Observable<boolean> 
    {
        return this.http.post<boolean>(this.administradorApi + "/existeAdministrador", JSON.stringify({}), httpOptions).pipe(
            catchError(this.manejoDeErrores<boolean>('isExisteAdministrador')));    
    }
    
    loginAdministrador(administrador: Administrador) : Observable<boolean> 
    {
        return this.http.post<Token>(this.administradorApi + "/loginAdministrador", JSON.stringify({usuario: administrador.usuario, password: administrador.password}), httpOptions).pipe(
            map((response : Token) => this.almacenarSesion(response.token)),
            catchError(this.manejoDeErrores<boolean>('loginAdministrador')));    
    }
    
    logoutAdministrador(): Observable<boolean> 
    {
        let token = JSON.parse(localStorage.getItem('administrador'));
        
        return this.http.post<boolean>(this.administradorApi + "/logoutAdministrador", token, httpOptions).pipe(
            map(response => this.removerSesion(response as boolean)),
            catchError(this.manejoDeErrores<boolean>('logoutAdministrador')));    
    }
    
    private almacenarSesion(token: string): boolean
    {
        if (token !== "")
        {
            localStorage.setItem('administrador', JSON.stringify(token));
            return true;
        }

        return false;
    }
    
    private removerSesion(respuesta: boolean): boolean
    {
        if (respuesta)
        {
            localStorage.removeItem('administrador');
        }
        
        return respuesta;
    }

    private manejoDeErrores<T> (operation = 'operation', result?: T) 
    {
        return (error: any): Observable<T> => {
           return of(result as T);
        };
    }    
}
