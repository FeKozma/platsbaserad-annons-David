import { Component, OnInit } from '@angular/core';
import { Http, Response } from '@angular/http';
import { companyName } from '../data.module';


@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})
export class StatisticComponent implements OnInit {

  arr: any;
  model = new companyName("");
  private baseApiUrl = 'http://localhost:8080/';
  private apiUrl;

  constructor(private http: Http) {


  }


  ngOnInit() {
  }

  extendClass
  getSubmit() {

    console.log('contacting api');
    this.apiUrl = "getcurnrviews/" + this.model.name;
    this.sendData();


  }

  sendData() {
    this.getData().subscribe(data => {
        console.log(data);

        this.arr = JSON.parse(JSON.stringify(data));

    });
  }




  getData() {
    return this.http.get(this.baseApiUrl + this.apiUrl).map((res: Response) => res.json());
  }
}
