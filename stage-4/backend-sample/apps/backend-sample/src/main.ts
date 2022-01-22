import { NestFactory } from '@nestjs/core';
import * as promBundle from 'express-prom-bundle';
import { AppModule } from './app.module';

async function bootstrap() {
  const metricsMiddleware = promBundle({includeMethod: true, includePath: true});

  const app = await NestFactory.create(AppModule);
  app.use(metricsMiddleware);
  await app.listen(3000);
}
bootstrap();
