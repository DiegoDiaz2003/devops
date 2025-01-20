# Etapa 1: Construcción de la aplicación con Node.js
FROM node:18 AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar los archivos de dependencias
COPY package*.json ./

# Instalar las dependencias
RUN npm install

# Copiar el resto del código fuente al contenedor
COPY . .

# Construir la aplicación
RUN npm run build

# Etapa 2: Servir los archivos construidos con Nginx
FROM nginx:alpine

# Configurar Nginx con un archivo personalizado
ADD ./config/nginx.conf /etc/nginx/conf.d/default.conf

# Copiar los archivos construidos en la etapa anterior al directorio de Nginx
COPY --from=build /app/dist /var/www/app/

# Exponer el puerto 5174
EXPOSE 5174

# Iniciar Nginx en modo "foreground"
CMD ["nginx", "-g", "daemon off;"]