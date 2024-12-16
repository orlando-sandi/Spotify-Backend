<div align="center">
  <img src="./.github/assets/svg/logo-small.svg" width="120"/>
  <h1>Code Challenge: Spotify Powered Backend</h1>
</div>

To look for the frontend go to: [Spotify Powered Frontend](https://github.com/orlando-sandi/Spotify-Frontend)
## Demo:

[![Spotify Backend Demo](https://img.youtube.com/vi/vTNaHgLjxj8/0.jpg)](https://www.youtube.com/watch?v=vTNaHgLjxj8&ab)


## Setup

1 - This project uses MySQL, so there is several ways to setup the database:

  - (Recommended) Create a docker container with a default database named `spotifydemo`, host it on port `3007` so it doesn't collide with any other MySQL instance you may have.
  - Go to your local MySQL instance and create a database named `spotifydemo`
  
2 - Clone the repository

3 - Download the `secrets.properties` you were provided with and store it inside the resources folder. This file contains the Spotify authentication string.

- You may also duplicate the `secrets.template.properties` file and enter your own values,

4 -  Run the following command to install the dependencies:
```bash
mvn install
```

5 - Run the following command to run the project:
```bash
mvn spring-boot:run
```

6 - Open http://localhost:8080/swagger-ui/index.html to see the API in action!
