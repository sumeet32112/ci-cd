# ---- Stage 1: Build Angular ----
FROM node:20-alpine AS build

WORKDIR /app
COPY package*.json ./

# Install exact deps from lockfile (best for CI/CD)
RUN npm ci --legacy-peer-deps

# Copy source and build Angular
COPY . .
RUN npm run build --prod

# ---- Stage 2: Nginx for serving Angular ----
FROM nginx:alpine

# Remove default nginx static content
RUN rm -rf /usr/share/nginx/html/*

# Copy Angular dist files
COPY --from=build /app/dist/recipes-ui /usr/share/nginx/html

# Copy custom nginx config (if needed)
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
