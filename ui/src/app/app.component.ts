import {Component} from '@angular/core';
import {OrderServiceService} from "./order-service.service";
import {AuthService} from "./auth.service";
import {map} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {ShoppingCartOverviewComponent} from "./shopping-cart-overview/shopping-cart-overview.component";
import {environment} from "../environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  public itemCounter = this.orderService.shoppingCartItemsNumber$;

  public user$ = this.authService.login.pipe(map(value => value ? "[Admin]" : "[User]"))

  constructor(
    private orderService: OrderServiceService,
    private authService: AuthService,
    public dialog: MatDialog
  ) {
    console.log("the cookie", document.cookie);

  }

  public openShippingCart() {
    const dialogRef = this.dialog.open(ShoppingCartOverviewComponent, {
      data: {},
    });
  }

  public onLogin() {
    if (!this.authService.login.value) {
      window.location.href = environment.backendUrl + "/login";
    } else {
      window.location.href = environment.backendUrl + "/logout";
    }
  }
}
