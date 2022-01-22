import { Injectable } from '@nestjs/common';

@Injectable()
export class AppService {
    getHello(): Promise<string> {
        return new Promise<string>(resolve => {
            const rnd = Math.random() * 800;
            setTimeout(() => resolve(`Hello World, wait for: ${rnd}ms`), rnd);
        });
    }

    getDemo() {
        return new Promise<string>(resolve => {
            const rnd = Math.random() * 100;
            setTimeout(() => resolve(`Demo is faster, wait for: ${rnd}ms`), rnd);
        });
    }
}
