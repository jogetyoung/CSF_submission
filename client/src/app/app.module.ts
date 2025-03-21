import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { provideHttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { MenuComponent } from './components/menu/menu.component';
import { PlaceOrderComponent } from './components/place-order/place-order.component';

import { ConfirmationComponent } from './components/confirmation/confirmation.component';
import {RestaurantService} from './restaurant.service';
import {RouterModule, RouterOutlet, Routes} from '@angular/router';
import {OrderStore} from './order.store';


const routes: Routes = [
  {path: '', component:MenuComponent},
  {path: 'view2', component:PlaceOrderComponent},
  {path: 'view3', component:ConfirmationComponent},
  {path: '**', redirectTo:'/', pathMatch:'full'} //wildcard must lways be last
];

@NgModule({
  declarations: [
    AppComponent, MenuComponent, PlaceOrderComponent, ConfirmationComponent
  ],
  imports: [
    BrowserModule, ReactiveFormsModule, RouterOutlet, RouterModule.forRoot(routes)
  ],
  providers: [ provideHttpClient(), RestaurantService, OrderStore ],
  bootstrap: [AppComponent]
})
export class AppModule { }
