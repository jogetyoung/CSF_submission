import { Injectable } from '@angular/core';
import { ComponentStore } from '@ngrx/component-store';

export interface OrderItem {
  id: string;
  name: string;
  price: number;
  quantity: number;
  total: number;
}

export interface OrderState {
  items: OrderItem[];
  totalPrice: number;
}

@Injectable({ providedIn: 'root' })
export class OrderStore extends ComponentStore<OrderState> {
  constructor() {
    super({ items: [], totalPrice: 0 });
  }

  // ✅ Setter: Update Order Data
  setOrder = this.updater((state, order: OrderItem[]) => ({
    ...state,
    items: order,
    totalPrice: order.reduce((sum, item) => sum + item.total, 0)
  }));

  // ✅ Getter: Select Order Items
  readonly order$ = this.select(state => state.items);

  // ✅ Getter: Select Total Price
  readonly totalPrice$ = this.select(state => state.totalPrice);
}
