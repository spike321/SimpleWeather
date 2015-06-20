# SimpleWeather

The task is to build a simple 2 screen app that will show weekly forecasts for a fixed set of cities using a free API.

##Screen 1 (Cities):

This screen displays a list of cities. To keep things simple, use the following 5 cities:
*New York
*London
*Los Angeles
*Paris
*Tokyo

##Screen 2 (City Forecast):

This screen displays the weather over the next week for the chosen city from screen 1.
Display the following data for the chosen city:

*Current temperature
*Summary for the week, e.g. "No precipitation throughout the week, with temperatures peaking at 70Â° on Saturday."
*For each of the next 7 days, the min and max temperature, and summary (e.g. "Mostly cloudy throughout the day.")

##API Usage

Use the free API from https://developer.forecast.io/

Need register and get an API key for access. Once registered, test out the below call which the app will use: https://api.forecast.io/forecast/<APIKEY>/<LATITUDE>,<LONGITUDE>

###Notes

Should only use the Android platform libraries. Do not use any external, 3rd party libraries, including any wrappers for forecast.io calls.
Make sensible decisions about what to display in terms of usability. Include comments in code to indicate where decisions were made to improve the user experience.

## Final Product 
### Screen 1
![Screenshot](http://imgur.com/Vg375si)
###Screen 2
![Screenshot](http://imgur.com/ijEPH36)
