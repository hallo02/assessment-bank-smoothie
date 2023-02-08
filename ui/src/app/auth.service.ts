import {Injectable} from '@angular/core';
import {BehaviorSubject, catchError, interval, mergeMap, of} from "rxjs";
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
    interval(1000).pipe(
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
        } else {
          this.login.next(false)
        }
      },
      (error) => console.log("nok")
    )

  }

  public login: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);


}
