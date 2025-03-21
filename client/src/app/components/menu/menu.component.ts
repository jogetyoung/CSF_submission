import {Component, inject, OnInit} from '@angular/core';
import {RestaurantService} from '../../restaurant.service';
import {Subject, Subscription} from 'rxjs';
import {MenuItems} from '../../models';
import {Router} from '@angular/router';
import {OrderItem, OrderStore} from '../../order.store';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit{
  // TODO: Task 2

  private restaurantSvc = inject(RestaurantService)

  private router = inject(Router)

  private orderStore = inject(OrderStore)

  sub$! : Subscription

  menuList: MenuItems[] =[]

  quantities: { [id: string]: number } = {}; // Store selected quantities


  ngOnInit() {
    this.getMenuFromSvc()
  }

  getMenuFromSvc() {
    this.sub$ = this.restaurantSvc.getMenuItems().subscribe({
      next: result => {
        this.menuList = result
        this.quantities = {}
        console.info("MENU ITEMS: ", this.menuList)
      },

      error: err => {console.log(err)},
      complete: () => {this.sub$.unsubscribe()}

    })

  }

  increaseQty(id: string) {
    this.quantities[id] = (this.quantities[id] || 0) + 1;
  }

  decreaseQty(id: string) {
    if (this.quantities[id] && this.quantities[id] > 0) {
      this.quantities[id]--;
      if (this.quantities[id] === 0) {
        delete this.quantities[id]; // Remove item when quantity is 0
      }
    }
  }

  // Get total price of selected items
  getTotalPrice(): number {
    return Object.keys(this.quantities)
      .reduce((sum, id) => sum + (this.menuList.find(item => item.id === id)?.price || 0) * this.quantities[id], 0);
  }

  // Get total selected items count
  getTotalItems(): number {
    return Object.values(this.quantities).reduce((sum, qty) => sum + qty, 0);
  }

  placeOrder() {
    const selectedItems: OrderItem[] = this.menuList
      .filter(item => this.quantities[item.id])
      .map(item => ({
        ...item,
        quantity: this.quantities[item.id],
        total: this.quantities[item.id] * item.price
      }))

    this.orderStore.setOrder(selectedItems)

    this.router.navigate(['/view2']) //brings to next view with all the data
  }

}
