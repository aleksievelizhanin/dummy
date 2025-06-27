# Use plain Ubuntu then add Node.js LTS
FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# 1) Install curl + Node (18.x LTS) + tini for clean PID 1
RUN apt-get update -qq \
 && apt-get install -y curl ca-certificates tini --no-install-recommends \
 && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
 && apt-get install -y nodejs \
 && apt-get clean && rm -rf /var/lib/apt/lists/*

# 2) Copy service code
WORKDIR /app
COPY package*.json ./
RUN npm ci --omit=dev
COPY server.js .

# 3) Expose and run
EXPOSE 8080
ENTRYPOINT ["tini", "--"]
CMD ["npm", "start"]
