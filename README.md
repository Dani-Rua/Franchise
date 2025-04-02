## Crear imagen de docker
docker build -t franquicia-sucursal:1.0.0 .

## Correr contenedor de docker
docker run --name  franquicia -p 8091:8091 franquicia-sucursal:1.0.0
