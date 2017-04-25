# BubbleBattStore
This application was generated using JHipster 4.3.0, you can find documentation and help at [https://jhipster.github.io/documentation-archive/v4.3.0](https://jhipster.github.io/documentation-archive/v4.3.0).

**Live Version: [http://bubble-batt-store.herokuapp.com](bubble-batt-store.herokuapp.com)**

# Running the Project

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.
2. [Yarn][]: We use Yarn to manage Node dependencies.
   Depending on your system, you can install Yarn either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    yarn install

We use [Gulp][] as our build system. Install the Gulp command-line tool globally with:

    yarn global add gulp-cli


[Bower][] is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [bower.json](bower.json). You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.

### Setting up database
*This System ues PostgreSQL Database, [You can download PostgresQL Here](https://www.postgresql.org/download/)*

Create your database in PostgreSQL then enter the credentails in ```application-dev.yml```
 which can be found at ```main/resources/config/```
 
 Set your database to like the following section
    
    datasource:
            type: com.zaxxer.hikari.HikariDataSource
            url: jdbc:postgresql://localhost:5432/dbtwo
            username: basky                             
            password: basky
            
The system will then automatically create the tables for you

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./gradlew
    gulp

  

[Node.js]: https://nodejs.org/
[Yarn]: https://yarnpkg.org/
[Bower]: http://bower.io/
[Gulp]: http://gulpjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
[Leaflet]: http://leafletjs.com/
[DefinitelyTyped]: http://definitelytyped.org/
