export class Data {
  constructor(
    public days: number,
    public title: string,
    public summary: string,
    public url: string,
    public nrViews: number,
    public lng: number,
    public lat: number,
    public meterRadius: number,
    public oneTimeView: number
  ) {}

}

export class appAndCompany {
  constructor(
    public app: string,
    public company: string
  ) {}
}

export class companyName{
  constructor(
    public name: string
  ) {}
}
