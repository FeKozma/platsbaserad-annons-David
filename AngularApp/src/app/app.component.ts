import { NgModule, Component } from '@angular/core';
import { AgmCoreModule } from '@agm/core';
import { BrowserModule } from '@angular/platform-browser';
/** import { AddAdComponent } from './add-ad/addAdImport'*/

import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';



@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
/**  directives: [AddAdComponent]*/
})
export class AppComponent {
  title: string = 'Platsbaserad reklam';



  constructor() {

  }






}
