import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Smoothie} from "../../model/smoothie";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../auth.service";
import {environment} from "../../environment";
import {OrderServiceService} from "../order-service.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {DeleteDialogComponent} from "./delete-dialog/delete-dialog.component";

@Component({
  selector: 'app-smoothie',
  templateUrl: './smoothie.component.html',
  styleUrls: ['./smoothie.component.css']
})
export class SmoothieComponent implements OnInit {

  /**************************************************************
   **** Member variables
   **************************************************************/
  @Input()
  public smoothie: any = null;

  @Output()
  public delete: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  public update: EventEmitter<string> = new EventEmitter<string>();

  public smoothieFormGroup!: FormGroup;

  public isAuth$ = this._authService.login.asObservable();


  /**************************************************************
   **** Constructor
   **************************************************************/

  constructor(
    private readonly _httpClient: HttpClient,
    private readonly _router: Router,
    private readonly _authService: AuthService,
    private readonly _orderService: OrderServiceService,
    private readonly _snackBar: MatSnackBar,
    private readonly _dialog: MatDialog
  ) {
  }

  /**************************************************************
   **** Lifecycle
   **************************************************************/
  ngOnInit(): void {
    this.smoothieFormGroup = new FormGroup({
      id: new FormControl(this.smoothie.id),
      name: new FormControl(this.smoothie.name,
        [Validators.required, Validators.maxLength(50)])
      ,
      slogan: new FormControl(this.smoothie.slogan,
        [Validators.required, Validators.maxLength(500)]
      ),
      carbohydrates: new FormControl(this.smoothie.carbohydrates,
        [Validators.required, Validators.maxLength(50)]
      ),
      fat: new FormControl(this.smoothie.fat,
        [Validators.required, Validators.maxLength(50)]
      ),
      protein: new FormControl(this.smoothie.protein,
        [Validators.required, Validators.maxLength(50)]
      )
    })
  }

  /**************************************************************
   **** API
   **************************************************************/
  public onSubmit() {

    this._httpClient.put(environment.backendUrl + "/api/admin/", this.smoothieFormGroup.value, {withCredentials: true})
      .subscribe(
        msg => this.update.emit(this.smoothieFormGroup.value),

        msg => window.location.href = environment.backendUrl + "/login",
        () => console.log("complete")
      )
  }

  public addToBag(smoothie: Smoothie) {
    this._orderService.addToShoppingCard(smoothie);
    this._snackBar.open("Item added to shopping cart", "", {
      duration: environment.snackBarDuration
    });
  }

  public onDelete() {
    this._dialog.open(DeleteDialogComponent, {
      width: '250px'
    }).afterClosed().subscribe(deleteDecision => {
      if (deleteDecision != "true") {
        return
      }
      this._httpClient.delete(environment.backendUrl + "/api/admin/", {
        body: this.smoothieFormGroup.value,
        withCredentials: true
      })
        .subscribe(msg => {
          console.log(msg);
          this.delete.emit(this.smoothieFormGroup.value.id)
        })

    })


  }
}
