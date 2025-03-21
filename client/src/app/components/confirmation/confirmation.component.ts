import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-confirmation',
  standalone: false,
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements OnInit{

  private router = inject(ActivatedRoute)

  orderId!: string
  paymentId!: string
  timestamp!: number
  total!: number


  // TODO: Task 5

  ngOnInit() {
    this.router.queryParams.subscribe(params => {
      this.orderId = params['orderId'];
      this.paymentId = params['paymentId'];
      this.timestamp = params['timestamp'];
      this.total = params['total'];
    });

  }

}
