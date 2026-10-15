# Examen
Examen de progrmación de huertos urbanos

## Microservicios

El proyecto incluye dos microservicios Spring Boot 3 / Java 17, cada uno con su propia base de datos MongoDB:

- **Parcelas** (`http://localhost:8081`): registra y consulta parcelas.
- **Mediciones** (`http://localhost:8082`): registra y consulta mediciones de sensores, opcionalmente filtradas por parcela.
- **MongoDB** (`localhost:27017`): persistencia local levantada junto con los servicios.

Cada API valida las solicitudes con Bean Validation, elimina etiquetas HTML y caracteres de control de los campos de texto antes de persistirlos, y devuelve errores JSON consistentes para validaciones, recursos inexistentes y solicitudes mal formadas.

### Ejecutar con Docker Compose

Requiere Docker Desktop con Docker Compose. Desde la raíz del repositorio:

```powershell
docker compose up --build
```

Para detener los servicios:

```powershell
docker compose down
```

Los datos de MongoDB se conservan en el volumen `mongodb-data`. Para eliminar también esos datos, usa `docker compose down -v`.

### API de parcelas

`POST /api/v1/parcels`

```json
{
  "name": "Huerto central",
  "location": { "latitude": 19.4326, "longitude": -99.1332 },
  "soilType": "LIMOSO",
  "ownerOrLead": "Comunidad del centro"
}
```

`soilType` acepta `ARCILLOSO`, `LIMOSO` o `ARENOSO`. Latitud y longitud deben estar dentro de sus rangos GPS.

- `GET /api/v1/parcels`: lista parcelas.
- `GET /api/v1/parcels/{id}`: consulta una parcela por su ObjectId.

### API de mediciones

`POST /api/v1/sensor-readings`

```json
{
  "parcelId": "0123456789abcdef01234567",
  "soilMoisture": 42.5,
  "temperature": 22.0,
  "soilPh": 6.8,
  "nitrogen": 40,
  "phosphorus": 25,
  "potassium": 35,
  "airQuality": 28,
  "notes": "Medición de la mañana"
}
```

- `GET /api/v1/sensor-readings`: lista todas las mediciones.
- `GET /api/v1/sensor-readings?parcelId={id}`: filtra mediciones de una parcela.
- `GET /api/v1/sensor-readings/{id}`: consulta una medición por su ObjectId.

Las validaciones limitan humedad a 0–100%, temperatura a -80–80 °C, pH a 0–14, nutrientes a 0–10000 y calidad del aire a 0–500. Los errores incluyen fecha, código HTTP, mensaje en español, ruta y errores por campo cuando aplique.
