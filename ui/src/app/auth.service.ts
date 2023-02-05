import {Injectable} from '@angular/core';
import {BehaviorSubject, catchError, EMPTY, empty, flatMap, interval, mergeMap, of, throwError} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private httpClient: HttpClient
  ) {
    console.log("init");
    interval(5000).pipe(
      mergeMap(
        (msg) => this.httpClient.get(environment.backendUrl + "/api/admin", {withCredentials: true})
          .pipe(
            catchError((error) => {
              return of("nok")
            }))
      ),
    ).subscribe(
      (msg) => {
        if (msg != "nok") {
          this.login.next(true)
        }
      },
      (error) => console.log("nok")
    )

  }

  public login: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);


}
