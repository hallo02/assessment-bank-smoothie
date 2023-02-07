import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Smoothie} from "../../model/smoothie";
import {AuthService} from "../auth.service";
import {environment} from "../../environment";
import {OrderServiceService} from "../order-service.service";

@Component({
  selector: 'app-smoothie-list',
  templateUrl: './smoothie-list.component.html',
  styleUrls: ['./smoothie-list.component.css']
})
export class SmoothieListComponent implements OnInit {

  /**************************************************************
   **** Member variables
   **************************************************************/
  public smoothies: Smoothie[] = [];

  public isAuth$ = this.authService.login.asObservable();

  /**************************************************************
   **** Constructor
   **************************************************************/
  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {
  }

  /**************************************************************
   **** Lifecycle
   **************************************************************/
  ngOnInit(): void {
    this.http.get<Smoothie[]>(environment.backendUrl + "/api/")
      .subscribe((data) => {
        this.smoothies = data;
      });
  }

  /**************************************************************
   **** API
   **************************************************************/

  public addSmoothie() {
    this.smoothies.push({name: "", slogan: "", carbohydrates: "", fat: "", protein: ""})
  }

  public onDelete(event: string) {
    this.smoothies = this.smoothies.filter(smoothie => smoothie.id != event);
  }

  public onUpdate(event: string) {
    this.http.get<Smoothie[]>(environment.backendUrl + "/api/")
      .subscribe((data) => {
        this.smoothies = data;
      });
  }
}
