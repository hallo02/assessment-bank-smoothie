import {Component} from '@angular/core';
import {ShoppingCartOverviewComponent} from "../shopping-cart-overview/shopping-cart-overview.component";
import {MatDialog} from "@angular/material/dialog";
import {environment} from "../../environment";
import {AuthService} from "../auth.service";
import {map} from "rxjs";
import {OrderServiceService} from "../order-service.service";

@Component({
  selector: 'app-smoothie-toolbar',
  templateUrl: './smoothie-toolbar.component.html',
  styleUrls: ['./smoothie-toolbar.component.css']
})
export class SmoothieToolbarComponent {

  /**************************************************************
   **** Member variables
   **************************************************************/

  public itemCounter = this.orderService.shoppingCartItemsNumber$;

  public user$ = this.authService.login.pipe(map(value => value ? "[Business Owner]" : "[End User]"))
  public userAction$ = this.authService.login.pipe(map(value => value ? "Logout" : "Login"))

  /**************************************************************
   **** Constructor
   **************************************************************/

  constructor(
    private readonly dialog: MatDialog,
    private readonly authService: AuthService,
    private readonly orderService: OrderServiceService
  ) {
  }

  /**************************************************************
   **** API
   **************************************************************/

  public openShippingCart() {
    this.dialog.open(ShoppingCartOverviewComponent, {
      data: {},
    });
  }

  public onLogin() {
    if (this.isAdmin()) {
      window.location.href = environment.backendUrl + "/logout";
    } else {
      window.location.href = environment.backendUrl + "/login";
    }
  }

  /**************************************************************
   **** Member variables
   **************************************************************/

  private isAdmin(): boolean {
    return this.authService.login.value
  }
}
