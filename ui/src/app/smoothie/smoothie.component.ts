import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Smoothie} from "../../model/smoothie";
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../auth.service";
import {environment} from "../../environment";
import {OrderServiceService} from "../order-service.service";

@Component({
  selector: 'app-smoothie',
  templateUrl: './smoothie.component.html',
  styleUrls: ['./smoothie.component.css']
})
export class SmoothieComponent implements OnInit {

  private readonly httpClient: HttpClient;
  private readonly router: Router;

  @Input() public smoothie: any = null;

  @Output()
  public delete: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  public update: EventEmitter<string> = new EventEmitter<string>();

  public smoothieFormGroup!: FormGroup;

  public isAuth$ = this.authService.login.asObservable();

  constructor(
    httpClient: HttpClient,
    router: Router,
    private authService: AuthService,
    private orderService: OrderServiceService
  ) {
    this.httpClient = httpClient;
    this.router = router;
  }


  ngOnInit(): void {
    this.smoothieFormGroup = new FormGroup({

      id: new FormControl(this.smoothie.id),
      name: new FormControl(this.smoothie.name),
      img: new FormControl(this.smoothie.img),
      carbohydrates: new FormControl(this.smoothie.carbohydrates),
      fat: new FormControl(this.smoothie.fat),
      protein: new FormControl(this.smoothie.protein)
    })
  }

  public onSubmit() {
    console.log(this.smoothieFormGroup.value)

    this.httpClient.put(environment.backendUrl + "/api/admin/", this.smoothieFormGroup.value, {withCredentials: true})
      .subscribe(
        msg => this.update.emit(this.smoothieFormGroup.value),

        msg => window.location.href = environment.backendUrl + "/login",
        () => console.log("complete")
      )
  }

  addToBag(smoothie: Smoothie) {
    this.orderService.addToShoppingCard(smoothie);
  }

  public onDelete() {
    this.httpClient.delete(environment.backendUrl + "/api/admin/", {
      body: this.smoothieFormGroup.value,
      withCredentials: true
    })
      .subscribe(msg => {
        console.log(msg);
        this.delete.emit(this.smoothieFormGroup.value.id)
      })


  }


}
