import {Injectable} from '@angular/core';
import {BehaviorSubject, map} from "rxjs";
import {Smoothie} from "../model/smoothie";
import {ShoppingCartItem} from "../model/shopping-cart-item";
//@ts-ignore
import * as uuid from "uuid";

@Injectable({
  providedIn: 'root'
})
export class OrderServiceService {

  private _shoppingCartItems$ = new BehaviorSubject<ShoppingCartItem[]>([])
  public shoppingCartItemsNumber$ = this._shoppingCartItems$.pipe(map(arr => arr.length));
  public shoppingCartItems = this._shoppingCartItems$.asObservable();

  constructor() {
  }

  public addToShoppingCard(smoothie: Smoothie) {
    let oldValue = this._shoppingCartItems$.value;
    oldValue.push({uuid: uuid.v4(), smoothie: smoothie});
    this._shoppingCartItems$.next(
      oldValue
    );
  }

  public removeFromShoppingCart(shoppingCartItem: ShoppingCartItem) {
    let reducedShoppingCartItems = this._shoppingCartItems$.value.filter(s => s.uuid != shoppingCartItem.uuid);
    this._shoppingCartItems$.next(reducedShoppingCartItems);
  }
}
