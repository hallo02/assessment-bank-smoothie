import {Component} from '@angular/core';
import {OrderServiceService} from "../order-service.service";
import {ShoppingCartItem} from "../../model/shopping-cart-item";
import {MatSnackBar} from "@angular/material/snack-bar";
import {environment} from "../../environment";

@Component({
  selector: 'app-shopping-cart-overview',
  templateUrl: './shopping-cart-overview.component.html',
  styleUrls: ['./shopping-cart-overview.component.css']
})
export class ShoppingCartOverviewComponent {

  /**************************************************************
   **** Member variables
   **************************************************************/
  public shoppingCartItems$ = this._orderService.shoppingCartItems;


  /**************************************************************
   **** Constructor
   **************************************************************/
  constructor(
    private readonly _snackBar: MatSnackBar,
    private readonly _orderService: OrderServiceService
  ) {
  }

  /**************************************************************
   **** API
   **************************************************************/
  public onShoppingCardItemDelete(shoppingCartItem: ShoppingCartItem) {
    this._orderService.removeFromShoppingCart(shoppingCartItem);
    this._snackBar.open("Item removed from shopping cart", "", {
      duration: environment.snackBarDuration
    });
  }
}
