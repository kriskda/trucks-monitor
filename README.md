# Trucks monitor
[Screen_recording_20240307_141357.webm](https://github.com/kriskda/trucks-monitor/assets/2589087/be3255c0-c35d-4245-b969-baea04ee1fd0)

- Displays a list of active vehicles.
- Tapping on an item in the list reveals the details of the vehicle.
- Screens are automatically refreshed every 5 seconds.

## AUTH KEY
The project is currently lacking an authentication key. Please set it in the following file: `data/network/src/main/java/io/github/network/vehicle/VehicleFeedService.kt`

## Architecture
The architecture follows the MVVM pattern with a repository pattern for the data layer:
- `data/network` is responsible for fetching data from the backend.
- `data/repository` acts as an intermediary, converting fetched data to the domain model. It has the potential to be extended to store data in a local database for offline access.
- `domain` contains the business logic.
- `app` represents the presentation layer.

## Testing
Tests have not been written for every class. However there should be at least one test for each layer.
