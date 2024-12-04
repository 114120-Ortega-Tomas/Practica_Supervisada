import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Login, RegisterRequest, User } from '../../Models/Models';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {this.loggedInSubject = new BehaviorSubject<boolean>(false);
    this.loggedIn$ = this.loggedInSubject.asObservable(); }

  urlLogin="http://localhost:8080/Usuarios/Login"
  urlRegister="http://localhost:8080/Usuarios/PostUsuario"
  private loggedInSubject: BehaviorSubject<boolean>;
  public loggedIn$: Observable<boolean>;
  user : User = new User();
  register(register: RegisterRequest)
  {
    return this.http.post(this.urlRegister, register);
  }
 
  login(login: Login): Observable<User> {
    return this.http.post<User>(this.urlLogin, login);
  }

  setLoggedIn(value: boolean) {
    this.loggedInSubject.next(value);
  }

  getAllUsers(){
    return this.http.get("http://localhost:8080/Usuarios/GetAll")
  }
  getUserbyUsername(username:string , id:number)
  {
    return this.http.get("http://localhost:8080/Usuarios/getUsersByUsername?username="+username+"&id="+id);
  }
  UpdateUser(id:number , request:RegisterRequest)
  {
    return this.http.put("http://localhost:8080/Usuarios/UpdateUsuario?id="+id , request);
  }

  get isLoggedIn(): boolean {
    return this.loggedInSubject.value;
  }
}
