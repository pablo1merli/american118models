import { DatosAuto } from './datos-auto';
import { Review } from './review';

export class Auto 
{
    id: string;
    datosAuto: DatosAuto;
    loTengo: boolean;
    fabricante: string;
    serie: string;
    cantidadProducida: number;
    edicionEspecial: string;
    anioFabricacion: number;
    urlRest: string;
    reviews: Review[];
    urlFotos: string[];  
    token: string;
}