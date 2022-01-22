import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { Gauge, Registry } from 'prom-client';
import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

@Injectable()
export class SunExporterService {
  private reg = new Registry();

  private b_gauge = new Gauge({
    name: 'solar_wind_mag_gsm_tesla',
    help: 'Magnetfeldkomponenten des Sonnendwinds relativ zum Erdmagnetfeld [tesla]',
    labelNames: ['component'],
    registers: [this.reg],
  });
  private coord_gauge = new Gauge({
    name: 'solar_wind_coord_gsm',
    help: 'coordiantes in GSM [deg]',
    labelNames: ['component'],
    registers: [this.reg],
  });

  constructor(private httpService: HttpService) {}

  getSolarWindData(): Observable<string> {
    return this.httpService
      .get<string[][]>(
        'https://services.swpc.noaa.gov/products/solar-wind/mag-5-minute.json',
      )
      .pipe(
        map((res) => res.data),
        switchMap((data) => {
          this.b_gauge.set({component: 'bx'},+data[1][1] * 1e-9);
          this.b_gauge.set({component: 'by'},+data[1][2] * 1e-9);
          this.b_gauge.set({component: 'bz'},+data[1][3] * 1e-9);
          this.b_gauge.set({component: 'bt'},+data[1][6] * 1e-9);
          this.coord_gauge.set({component: 'lon'}, +data[1][4]);
          this.coord_gauge.set({component: 'lat'}, +data[1][5]);
          return this.reg.metrics();
        }),
      );
  }
}
