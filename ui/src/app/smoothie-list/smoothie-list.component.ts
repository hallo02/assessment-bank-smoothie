import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Smoothie} from "../../model/smoothie";
import {MatExpansionModule} from '@angular/material/expansion';
import {AuthService} from "../auth.service";
import {environment} from "../../environment";

@Component({
  selector: 'app-smoothie-list',
  templateUrl: './smoothie-list.component.html',
  styleUrls: ['./smoothie-list.component.css']
})
export class SmoothieListComponent implements OnInit {

  public smoothies: Smoothie[] = [];

  public isAuth$ = this.authService.login.asObservable();

  constructor(
    private http: HttpClient,
    private authService: AuthService) {
  }

  ngOnInit(): void {
    this.http.get<Smoothie[]>(environment.backendUrl + "/api/")
      .subscribe((data) => {
        this.smoothies = data;
      });
  }

  addSmoothie() {
    this.smoothies.push({name: "", img: "", carbohydrates: "", fat: "", protein: ""})
  }

  onDelete(event: string) {
    this.smoothies = this.smoothies.filter(smoothie => smoothie.id != event);
  }

  onUpdate(event: string) {
    this.http.get<Smoothie[]>(environment.backendUrl + "/api/")
      .subscribe((data) => {
        this.smoothies = data;
      });

  }


}
