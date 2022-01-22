import { Controller, Get, Res } from '@nestjs/common';
import { Response } from 'express';
import { register } from 'prom-client';
import { firstValueFrom } from 'rxjs';
import { SunExporterService } from './sun-exporter.service';

@Controller()
export class SunExporterController {
  constructor(private readonly sunExporterService: SunExporterService) {}

  @Get('/metrics')
  async index(@Res() response: Response) {
    const metrics = await firstValueFrom(
      this.sunExporterService.getSolarWindData(),
    );

    response.header('Content-Type', register.contentType);
    response.send(metrics);
  }
}
