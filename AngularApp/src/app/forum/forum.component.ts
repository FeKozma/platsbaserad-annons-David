import { Component, OnInit, ViewChild, ElementRef, NgZone } from '@angular/core';
import { FormControl }  from "@angular/forms";
import { MapsAPILoader } from "@agm/core";
import { Data } from '../data.module';
import { classResponce } from '../classResponce.module';
import { } from 'googlemaps';


import { Http, Response } from '@angular/http';

@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html',
  styleUrls: ['./forum.component.css']
})
export class ForumComponent implements OnInit {

//success: string;
// id: any;

getRadius($event){
   console.log($event);
   this.radius = $event;
   console.log(this.radius);

 }

 getCentet($event) {
   this.lng = $event.lng;
   this.lat = $event.lat;
 }

private baseApiUrl = 'http://localhost:8080/';
private apiUrl;

extendClass
getSubmit() {
  if (this.model.nrViews >= 1)
  {
    console.log('contacting api');
    this.apiUrl = "addad/" + this.lng + "/" + this.lat + "/" + this.radius + "/" + this.model.title /*company*/ + "/" + this.model.summary + "/" + this.model.days + "/" + this.model.nrViews + "/query?url=" + this.model.url;
    this.data = this.getContacts();


}
  else {

    alert("antal visningar måste vata över noll");
  }


}

get currentData(){
  return JSON.stringify(this.model);
}
get currentDays(){
  return this.model.days;
}

getData() {
  return this.http.get(this.baseApiUrl + this.apiUrl).map((res: Response) => res.json());
}



getContacts() {
  this.getData().subscribe(data => {
      console.log(data);

      this.data = JSON.parse(JSON.stringify(data));

  });
}


data: any = {};


  title: string = 'Google maps';
  public lat: number = 56.16156;
  public lng: number = 15.58661;
  public radius: number = 300;
  model = new Data(1, '', '', 'http://', 0, 0, 0, 0, 0);

  constructor(private http: Http,
    private mapsAPILoader: MapsAPILoader,
      private ngZone: NgZone)
      {

      }


  public searchControl: FormControl;

  @ViewChild("search")
public searchElementRef: ElementRef;

  ngOnInit() {
    //create search FormControl
   this.searchControl = new FormControl();

   //set current position
    this.setCurrentPosition();

    //load Places Autocomplete
    this.mapsAPILoader.load().then(() => {
      let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
        types: ["address"]
      });
      autocomplete.addListener("place_changed", () => {
        this.ngZone.run(() => {
          //get the place result
          let place: google.maps.places.PlaceResult = autocomplete.getPlace();

          //verify result
          if (place.geometry === undefined || place.geometry === null) {
            return;
          }

          //set latitude, longitude and zoom
          this.lat = place.geometry.location.lat();
          this.lng = place.geometry.location.lng();
          //this.zoom = 12;
        });
      });
    });
  }

  private setCurrentPosition() {
    if ("geolocation" in navigator) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.lat = position.coords.latitude;
        this.lng = position.coords.longitude;
        //this.zoom = 12;
      });
    }
  }

  checkbox() {
    if (this.model.oneTimeView == 0)
    {
      this.model.oneTimeView = 1;
    }
    else{
      this.model.oneTimeView = 0;
    }
  }
}
