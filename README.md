# shopping








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
depandacies we need
 <artifactId>spring-boot-starter-actuator</artifactId> need to add in the pom.xml

 <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-rest-hal-browser</artifactId>
 </dependency>

 hal - hypertext appliation language

http://localhost:8080/actuator will give json repsonse.

add below property in application.properties and will get nice UI.
management.endpoint.web.exposure.include=*

http://localhost:8080/browser/index.html#/

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