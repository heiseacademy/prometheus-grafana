FROM node:lts-slim as builder

ARG VARIANT=backend-sample
ARG PORT=3000

RUN apt update && apt-get install -y --no-install-recommends build-essential python3 node-gyp dumb-init

#dependency layer
COPY package*json ./
RUN npm ci

#application layer
COPY . .
RUN npm run build -- ${VARIANT}

#runtime image
FROM node:lts-slim as runtime
WORKDIR /app

EXPOSE 3000

#provide simple init process for better container management (stop/restart)
ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["node", "dist/main"]

COPY --from=builder /usr/bin/dumb-init /usr/bin/dumb-init
COPY --from=builder node_modules/ node_modules/
COPY --from=builder dist/ dist/

#this is quite slow..
#RUN chown -R node /app
RUN chown -R node /app/dist


ARG PRODUCTION=true
ENV PRODUCTION=${PRODUCTION}
#RUN echo production is ${PRODUCTION}

USER node
