import { Component, OnInit } from '@angular/core';
import { Http, Response } from '@angular/http';
import { appAndCompany } from '../data.module';


@Component({
  selector: 'app-approveApp',
  templateUrl: './approveApp.component.html',
  styleUrls: ['./approveApp.component.css']
})
export class ApproveAppComponent implements OnInit {

  arr: any;
  gotData: any;
  model = new appAndCompany("","");
  private baseApiUrl = 'http://localhost:8080/';
  private apiUrl;

  constructor(private http: Http) {

    console.log('contacting api');
    this.apiUrl = "getapps";
    this.getContacts();

  }


  ngOnInit() {
  }

  extendClass
  getSubmit() {

    console.log('contacting api');
    this.apiUrl = "addcompany/" + this.model.app + "/" + this.model.company;
    this.sendData();


  }

  sendData() {
    this.getData().subscribe(data => {
        console.log(data);

        this.gotData = JSON.parse(JSON.stringify(data));

    });
  }

  getContacts() {
    this.getData().subscribe(data => {
        console.log(data);

        this.arr = JSON.parse(JSON.stringify(data));

    });
  }

  getData() {
    return this.http.get(this.baseApiUrl + this.apiUrl).map((res: Response) => res.json());
  }

}
