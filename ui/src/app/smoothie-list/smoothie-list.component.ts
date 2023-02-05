import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Smoothie} from "../../model/smoothie";

@Component({
  selector: 'app-smoothie-list',
  templateUrl: './smoothie-list.component.html',
  styleUrls: ['./smoothie-list.component.css']
})
export class SmoothieListComponent implements OnInit {

  public smoothies: Smoothie[] = [];

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get<Smoothie[]>("http://localhost:8080/api/")
      .subscribe((data) => {
        this.smoothies = data;
      });
  }

  addSmoothie() {
    this.smoothies.push({name: "", img: "", nutrition: ""})
  }

  onDelete(event: string){
    this.smoothies = this.smoothies.filter(smoothie => smoothie.id != event);
  }


}
