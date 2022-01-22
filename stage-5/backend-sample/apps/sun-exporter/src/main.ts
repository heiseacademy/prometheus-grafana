import { NestFactory } from '@nestjs/core';
import { SunExporterModule } from './sun-exporter.module';

async function bootstrap() {
  const app = await NestFactory.create(SunExporterModule);
  await app.listen(3000);
}
bootstrap();
