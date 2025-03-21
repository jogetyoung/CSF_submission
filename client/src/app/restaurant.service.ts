import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {MenuItems} from './models';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  private http = inject(HttpClient)

  // TODO: Task 2.2
  // You change the method's signature but not the name
  getMenuItems(): Observable<any> {

    return this.http.get<MenuItems[]>(`/api/menu`)
    //return this.http.get<MenuItems[]>(`http://localhost:3000/api/menu`)

  }

  // TODO: Task 3.2
  confirmOrder(orderData:any): Observable<any> {
    return this.http.post<any>('/api/food_order', orderData)
    // return this.http.post<any>('http://localhost:3000/api/food_order', orderData)
  }

}
