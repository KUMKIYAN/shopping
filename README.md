Class 1:
https://github.com/in28minutes/spring-microservices

Class 5:
W3 definition: software system desinged to support interoperatble machine to machine interaction over a network.


webservice basic concept is request and response.

Service Defintion -> request and response format
					 structure of request
					 structure of response
					 how to call the service ? end points


key terminalogies: 	1. request and response
					2. message exchange format -> xml and json


SOAP : data exchange format : request and reponse format will be xml
		transport can be http or mq
		service defintion is WSDL -> web service definition language.
		no caching

REST : representional state transfer

http  methods, get post, put and delete
http  status code, 200 or 404

no restrictions on Data exchage format : json is popular
transport : only HTTP
service defintion : WADL/swagger - > Web application defintion language.
caching

class 11:
start.spring. -> all all your depandacies and create project and import into your IDE.

Key abstractions are resources.
/users/kiyan/todos
/users/kiyan/todos/1
/users/kiyan

@SpringBootApplication and SpringApplication.run - > starting point of spring boot applicaiton will be placed on top of main method class.
@RestController at class level and @RequestMapping(method, path) at method level. method=RequestMethod.GET, path="/hello-world". <- URI/Resource.

a RequestMapping can return a simple string/bean back/ object. -> RestController (SpringbootAutoConfiguration) is responsible to present data in the json format.

add @PathVariable annotation in the method parameters.

DAO is reponsible for connect the db and get data and also has all possible methods and use these methods in RestCotroller class.
@component and @Autowired will have connection. we create object for component class without using new operator by placing autowired annotation on top of variable.

inorder to return created uri for post method implemetation use
URI location = ServiceUriCompnentBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(SavedUser.getId().toUri());
return ResponseEntity.created(location).build(); -> we get response code 201. -> in headers we can see uri in the form of get request for this post method operation.
apart from created (response code 201) we have many other methods at ResponseEntity dot.


@RequestBody will take payload assigne to variable in method definition. RequestBody method must be application/json format.

we need to create default constructor along witn user defined argument constructors for each bean.


Response status not found ->404 not found. Create runtime exception class and add @ResponseStatus(HttpStatus.NOT_FOUND) at class level.

For all exceptions maintain simple common structure to maintain standard.

for this step 1 is create exception response bean class.
next create a child class for a predefined spring class ResponseEntityExceptionHandler and @RestController and @ControllerAdvise and write a method with annotation @ExceptionHandler(Exception.class) and create object for exception response bean class creted in step1 and return response entity.

@Valid annonation we use at the method parameters. and we add valiation annotations like @Size(min=2, message="name should have atleast 2 charters") @Past annoations for Date at bean level.

@Size(min=2, message="name should have atleast 2 charters") -> this message will displayed as part of response. getBindingResult method has too many methods for customization.

we have many validtor in the "hibernet validator" and "validation api" jars come as part of starter-web dependacies.

as part request response, if want to see meaning full exception, override handleMethodArgumentNotValid method.


HATEOAS -> additonal info as part of response.
Hypermedia as the engine of application state.

here we need to perform 3 things.
Resource -> create Resource object with generics for which resource/controller we need to this.
ControllerLinkBuilder -> on which resource/controller method you want to give additional information
resource.add -> add that to resource object and return to that resource.



logging.level.org.springframework = debug
spring.jackson.serialization.write-dates-as-timestamps=false.


Class 30

Internationalization i18

configuration:
LocaleResolver
- Default Locale - Locale.US
ResourceBundleMessageSource

Usage
Autowire MessageSource
@RequestHeader(value="Accept-Language", required=false) Locale locale
-messageSource.getMessage("helloWorld.message", null, locale)

@Bean
	public LocaleResolver localeResolver(){
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

@Bean
	public ResourceBundleMessageSource messageSource(){
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("message");
		return messageSource;
	}

 @Autowired
    private MessageSource messageSource;

 @GetMapping(path="/hello-world")
    public String helloWorld(@RequestHeader(name="Accept-Language", required = false) Locale locale){
        return messageSource.getMessage("good.morning.message", null, locale);
    }

Class 31:

Simplication of Class 30

SessionLocaleResolver replaced with AcceptHeaderLocaleResolver

@Bean
	public LocaleResolver localeResolver1(){
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

messageSource() is simplified with "spring.messages.basename=message" in properties file

resource/endpoint can be without annotation.

Class 32 - Content Negociation:
If we want xml instead of json as part of response.
Accept application/json
Accept application/xml - > not acceptable

for this we need to add below depandancy in the pom.xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.8.3</version>
</dependency>

use "Accept:application/xml" to see the response in the xml format.

for post method use below headers
Accept:application/xml
Content-Type:application/xml

Class 33 - Swagger Documentation configuration

add dependencies

<dependency>
<groupId>io.springfox</groupId>
<artifactId>springfox-swagger2</artifactId>
<version>2.6.1</version>
</dependency>

<dependency>
<groupId>io.springfox</groupId>
<artifactId>springfox-swagger-ui</artifactId>
<version>2.6.1</version>
</dependency>

configure swagger using configuation annotation
enable swagger using enableswagger2 annoation
define bean that return Docket (DocumentationType.Swagger2)
//swagger 2
// all the paths - http://localhost:8080/v2/api-docs
//all the apis  -  http://localhost:8080/swagger-ui.html


Class 34 - API Contract/Documentation
info - > basic information
paths - > contains all resources - > Schema like of array of users
definition -> definition of API
Swagger annotation help to improvise API Contract/Documentation
(http://localhost:8080/v2/api-docs / http://localhost:8080/swagger-ui.html)

Add this one in SwaggerConfig class to get more details in api contract

public static final Contact DEFAULT_CONTACT
public static final ApiInfo DEFAULT_API_INFO
public static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES

To add additional notes like birth date can't be future date and name should be more than 4 characters
use

@ApiModel(description = "User Bean Documentation")
@ApiModelProperty(notes = "DOB can't be Future Date")

To give robust info in api contract

class 35: Actuator

this helps which micro service is down.
the below depandacies we need to add in the pom.xml
```
 <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
 </dependency>
 
 <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-rest-hal-browser</artifactId>
 </dependency>
```
 hal - hypertext appliation language

http://localhost:8080/actuator or http://localhost:8080/application or http://localhost:8080 will give json repsonse.

add below property in application.properties and will get nice UI.
management.endpoint.web.exposure.include=*

HAL UI: http://localhost:8080/browser/index.html#/

explore "/actuator"


class 36 :filtering (static filtering)

ignoreing some parts as part of json reponses

attributes like password and DOB which are important.

we need to add jackson annotation like  @JsonIgnore at particular property level
or JsonIgnoreProperties(value={"field1","field2"})


class 37 : Dynamic filtering
in some request we send some combination of attributes
in some other request we send some other combination of attributes

@JsonFilter("MyFilterName") need to apply at bean level

in controller we have to apply
public MappingJacksonValue getUsers(){
        Credentials mybean =  new Credentials("kiyandoor","xyz123");
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userName");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("MyFilterName",filter);
        MappingJacksonValue mapping = new MappingJacksonValue(mybean);
        mapping.setFilters(filters);
        return mapping;
}

Class 38 & 39 - versioning

URI versioning
request parameter versioning.
header versioning
minetype versioning

different company follows different versing.


versioning facts:
uri pollution - if we use URI versioning / request parameter versioning
Misuse of HTTP Headers - http headers are never intended for versioning
Caching - problem with header and minetype versioning becuase url same only headers changes

No perfect solution we have versioning. - depend/choose best fit versioning.

springtool plugin property in eclipse will give autohelp
inorder to see json response send "Accept" header with "applicaiton/json"

class 41: Security
add "spring-boot-starter-secuirty" depandancy in security server.
basic auth in postman

username is "user"
get password from log when starting server.

or add the below properties in application.properties
security.user.name=username
security.user.password=xyz123


class 43 - entity

add below two depandacies in pom.xml
                      <artifactId>spring-boot-starter-data-jpa</artifactId>

                      <groupId>com.h2database</groupId>
                      <artifactId>h2</artifactId>
                      <scope>runtime</scope>


the below annotations should be from javax.persistence only which use the depandency spring-boot-starter-data-jpa
@Entity annotation at th bean level
@Id
@GeneratedValue <- database will generate id value for us

disable secuirty in pom.xml

spring.jpa.show-sql=true
spring.h2.console.enabled=true

in console we can see table is created in memeory database . we can see create table statement in console
in resources folder create data.sql - that automatically insert the data into table. use single quotes for string data.

localhost:8080/h2-console
jdbc url field should be present. jdbc:h2:mem:testdb

*********

@ManyToOne -> many order have one relationship with User
and
@OneToMany -> one user have many order relationship

annotations add dables automatically in DB

@ManyToMany -> Many Orders have many Product relationship.

component and Autowired annotions should be in sync

Richardson maturity model
how restful you are
three levels
level - 0 -> soap services to rest style
level - 1 -> exposing resources with proper uri
level - 2 -> ( level 1 + proper use of http methods)
level - 3 -> (Level 2 + HATEOAS) -> DATA + NEXT POSSIBLE ACTIONS

Best practices:
consumer first - > web application / mew application -> what they want
naming of resoures should be understood by your client
documents should be understood by your client
right request methods get post, delete put
response status should be appropriate
200 - success
404 - resource not found
400 - bad request
201 - created
401 - not authorized
500 - server error

no secure info go in url.
resources name should be noun and plurals
exceptions search - on the resources apply verb/actions

https://github.com/in28minutes/spring-microservices/blob/master/02.restful-web-services/pom.xml
https://github.com/in28minutes/spring-microservices/tree/master/02.restful-web-services/src/main/java/com/in28minutes/rest/webservices/restfulwebservices

@JsonIgnoreProperties(ignoreUnknown = true)

@JsonIgnoreProperties(ignoreUnknown = true)

____________________________________________________________________________________________

Spring boot by jetbrains Koushik.

production ready application in matter of mins. - > Restful application.

in pom.xml add -> parent tag with artifact spring-boot-starter-parent contains opionated maven set of configurations for each version.
our project is child project of this parent.

depandacy block -> spring-boot-starter-web

properties -> java version - 1.8

create a class with main method and add annotation @SpringBootApplication - this is starting point of application
in main method call static method run to run the application SpringApplication.run(mainmethod.class, args)

 convention over configuration - > 80% of work will be done by spring
 sets up default configuration
 starts spring application context
 performs class path scan -> each class contain annotation / marker -> depanding on marker treat them differently. controller vs service.
 Tomcat server come in built

controller classes help to handle request  method and response @RestController.

the resource /hello will trigger the method and method executes

@RequestMapping at method level -> path and method (HttpMethod.GET)


the @RestController will automatically covert return of a method to json format.

 Parent section of POM -> spring-boot-starter-parent and its version tag tells what version of jars of depandacies section has to be download. -> bill of materials

 embedded tomcat server.

spring mvc controller will map resource and its method  to appropriate request.
