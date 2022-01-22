import { HttpModule } from '@nestjs/axios';
import { Module } from '@nestjs/common';
import { SunExporterController } from './sun-exporter.controller';
import { SunExporterService } from './sun-exporter.service';

@Module({
  imports: [HttpModule],
  controllers: [SunExporterController],
  providers: [SunExporterService],
})
export class SunExporterModule {}
