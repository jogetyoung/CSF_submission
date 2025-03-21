import {Component, inject, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {OrderStore} from '../../order.store';
import {RestaurantService} from '../../restaurant.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {take} from 'rxjs';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent implements OnInit{

  private restaurantSvc = inject(RestaurantService)

  private router = inject(Router)

  private orderStore = inject(OrderStore)

  order$ = this.orderStore.order$

  totalPrice$ = this.orderStore.totalPrice$

  private fb = inject(FormBuilder)

  form!: FormGroup

  // TODO: Task 3

  ngOnInit() {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  confirmOrder() {
    if (this.form.invalid) {
      alert("Please enter a username and password.");
      return;
    }

    this.order$.pipe(take(1)).subscribe(order => {
      if (!order.length) {
        alert("No items in order!");
        return;
      }

      const orderData = {
        username: this.form.value.username,
        password: this.form.value.password,
        items: order
      };

      this.restaurantSvc.confirmOrder(orderData).subscribe({
        next: response => {
          alert("Order placed successfully!");
          console.info("response", response)
          this.router.navigate(['/view3'], {
            queryParams: {
              orderId: response.orderId,
              paymentId: response.paymentId,
              total: response.total.toFixed(2),
              timestamp: response.timestamp
            }
            }
          );
        },
        error: err => {
          alert("Failed to place order: " + err.error.message);
        }
      });
    });
  }
}
