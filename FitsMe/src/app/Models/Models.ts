export class Login {
    email: string="";
    constraseña: string="";
}
export class RegisterRequest{
    nombre: string="";
    apellido:string="";
    email: string="";
    constraseña: string="";
    fecNac: string="";
    username: string="";
    pais: string="";
}
export class User{
  id: number=0;
  nombre: string="";
  apellido: string="";
  email: string="";
  constraseña: string="";
  fecNac: string="";
  username: string="";
  pais: string="";
  amigos:boolean=false;
  role:number=0;
}
export class WeekDay {
  date: Date = new Date();
  dayOfWeek: number = 0; // 0: Domingo, 1: Lunes, ..., 6: Sábado
}

export class Evento {
  eventoId:number=0;
  descripcion: string = '';
  fecha: string = '';
  horario: string = '';
  usuario: number = 0;
  outfitResponse:Outfit = new Outfit();
}

export class Prenda{
  prendaId: number=0;
  descripcion: string="";
  base64: string="";
  usos:number=0;
  tipoPrenda: TipoPrenda=new TipoPrenda();
  estilos: estilos[]=[];
  climas: clima[]=[];
  colores: color[]=[];
  favorito:boolean=false;
  prestada:boolean=false;
  prestamo:boolean=false;
  prestadabyUserId:number=0;
  genteUsando:number=0;
}
export class estilos{
  id: number=0;
  descripcion: string="";
}
export class clima{
  id: number=0;
  descripcion: string="";
}
export class color{
  id: number=0;
  descripcion: string="";
}

export class PrendaRequest{
  base64: string="";
  descripcion:string="";
  contentType:string="";
  color: number[]=[];
  tipoPrenda: number=0;
  estilo: number[]=[];
  clima: number[]=[];
  privado: boolean=false;
  userId: number=0;
}
export class Color {
  colorId: number = 0;
  descripcion: string = '';
}
export class Estilo {
  estiloId: number = 0;
  descripcion: string = '';
}
export class Clima {
  climaId: number = 0;
  descripcion: string = '';
}
export class TipoPrenda{
  id: number = 0;
  descripcion: string = '';
}
export class share {
  shareId: number=0;
  descripcion: string="";
}

export class PrendaToUserRequest{
  userId: number=0;
  prendaId: number=0;
}

export class Outfit{
  id: number=0;
  nombre:string="";
  usuario:string="";
  prendas:Prenda[]=[]
  favorito:boolean=false;
}

export class OutfitRequest{
  nombre:string="";
  descripcion:string="";
  usuarioId:number=0;
  prendas:number[]=[];
  favorito:boolean=false;
}

export class Filter{
  prendas:number[]=[];
  coloresId:number[]=[];
  climasId:number[]=[];
  estilosId:number[]=[];
  favorite:boolean=false;
  userId:number=0;
}
export class EventRequest{
  descripcion:string="";
  fecha:string="";
  horario:string="";
  usuarioId:number=0;
  outfitId:number=0;
}

export class solicitudRequest{
  userIdFrom:number=0;
  userIdTo:number=0;
}
export class solicitudResponse{
  solicitudId:number=0;
  userIdFrom:number=0;
  usernameFrom:string="";
  userIdTo:number=0;
}

export class solicitudPrendaRequest{
  userIdFrom:number=0;
  userIdTo:number=0;
  prendaId:number=0;
}
export class solicitudPrendaResponse{
  solicitudId:number=0;
  userIdFrom:number=0;
  usernameFrom:string="";
  userIdTo:number=0;
  prenda:Prenda=new Prenda();
}

export class UsuariosPorMes{
  usuarios:User[]=[];
  amount:number=0;
  mes:string="";
  ano:string="";
}
