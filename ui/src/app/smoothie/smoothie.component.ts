import {Component, Input, OnInit} from '@angular/core';
import {Smoothie} from "../../model/smoothie";
import {MatCardModule} from '@angular/material/card';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-smoothie',
  templateUrl: './smoothie.component.html',
  styleUrls: ['./smoothie.component.css']
})
export class SmoothieComponent implements OnInit {

  private readonly httpClient: HttpClient

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  @Input() public smoothie: any = null;

  public smoothieFormGroup!: FormGroup;

  ngOnInit(): void {
    this.smoothieFormGroup = new FormGroup({

      id: new FormControl(this.smoothie.id),
      name: new FormControl(this.smoothie.name),
      img: new FormControl(this.smoothie.img),
      nutrition: new FormControl(this.smoothie.nutritions)
    })
  }

  public onSubmit() {
    console.log(this.smoothieFormGroup.value)

    this.httpClient.put("http://localhost:8080/api", this.smoothieFormGroup.value)
      .subscribe(msg => console.log(msg));
  }

  public onDelete() {
    this.httpClient.delete("http://localhost:8080/api", {body: this.smoothieFormGroup.value})
      .subscribe(msg => console.log(msg))

  }


}
