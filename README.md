# idea-hub

School project for module 183 by Simon Friedli ([@Simai00](https://github.com/Simai00)) and Kay Mattern ([@mtte](https://github.com/mtte)).

## Run the Application

Docker and docker-compose can be used to run the applicaiton:

```bash
docker-compose up -d
```

## Development

Here some information about the development process:

### Backend

The backend is written in Java. [Gradle](https://gradle.org/) is used as the build tool.

IntelliJ is used as the IDE to develop the backend application. The API is powered by the Spark web framework. To access the database we use jOOQ. The required java files are automatically created during the build process and not checked into version control.

**Build the project**: `gradlew build`

**Start the application**: `gradlew run`

**Create jOOQ files**: `graldew genereateDatabaseJooqSchemaSource`

### Frontend

The frontend uses [Vue.js](https://vuejs.org/). For more information have a look at [README](frontend/README.md).

**Setup**: `npm install`

**Compile** and serve with hot reload: `npm run serve`

### DB

PostgreSQL is used for the database. 

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.