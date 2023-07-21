# Inclement
## A REST API built using SpringBoot

---
## How to set up:
1. Create a mysql database with name `weather` on localhost or elsewhere.
Using a docker instance would be ideal.
2. Create an unprivileged user with permissions to modify the `weather` database
3. Insert this user's credentials into the application.properties file under `src/main/resources`
4. Start the application

## How to use:
The REST API contains the following endpoints:
- `/` fetches all data
- `/sensor/:id` fetches all data for that sensor
- `/since/:date` fetches all data since specified date began. Date should be formatted as `yyyy-MM-dd`.
- `/type/:unit` fetches all data of a particular type, either `TEMPERATURE` or `HUMIDITY`.
- `/search` allows the user to search for any full combination of type, date, and sensor ids. Parameters are `?ids`, `?after`, and `?type`
- `/sensor/<hum/temp>/:id` fetches all temperature or humidity data for a sensor
- `/<min/max/avg>/search` allows a user to perform a search and obtain either the min, max, or average of all measurements matching that search
- `/upload` for uploading individual sensor data, expects a valid JSON object containing `sensor`, `unit` (either TEMPERATURE or HUMIDITY), `measurement`, and `datetime` formatted as `yyyy-MM-dd`.
- `/uploadBulk` for uploading bulk sensor data, expects a json array of valid JSON objects like the ones required for `/upload`

### Not yet complete:
- defaulting queries to fetch data within 24hours as opposed to all data
- fetching multiple data types at once
- improved exception handling
- Input validation beyond type conflict
- Nullable search parameters