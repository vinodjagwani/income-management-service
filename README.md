# Getting Started

### System requirements

1.  Windows 10 or Mac or Linux or Ubuntu, minimum memory requirement is 8 gb

### Software requirements
1. Java SDK v17 or higher
2. Gradle (3.7+)
3. git
4. Intellij Idea (Optional)

### Installation Instructions

1. Download Java Java SDK v17 or higher from https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
2. Install Java by following link https://data-flair.training/blogs/install-java/
3. Set Java home (JAVA_HOME) by using this link https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux
4. Verify by typing on console java -version
5. Download gradle and placed somewhere https://gradle.org/install/
6. Set Gradle home (GRADLE_HOME)
8. Download intelij idea and run project by following this link https://vaadin.com/docs/v14/flow/tutorials/in-depth-course/project-setup

### Running locally instructions

There are different ways to run it locally:

1. Import project into intelij idea and run from idea it self by right click on Application.java
2. Using gradle command as follows
   ./gradlew clean build and then ./gradlew bootRun
or
   ./gradlew clean build and then java -jar build/libs/income-management-service-1.0.jar
or  docker-compose up --build

in order to check application whether application is up or not you can hit the following url.

from local
http://localhost:9089/income-management-service/actuator/health

from docker
http://localhost:9099/income-management-service/actuator/health

### Swagger url on local:

from local
http://localhost:8089/income-management-service/swagger-ui/index.html

from docker
http://localhost:8099/income-management-service/swagger-ui/index.html

in order to access the api you can use follow curl:

curl --location --request POST 'http://localhost:8089/income-management-service/v1/payslips/employees/monthly-payslips' \
--header 'Content-Type: application/json' \
--data-raw '[
{
"annualSalary": 87500,
"firstName": "Vinod",
"lastName": "Jagwani",
"paymentStartDate": "2021-09-13",
"supperRate": 19
},
{
"annualSalary": 789,
"firstName": "Test",
"lastName": "Test",
"paymentStartDate": "2021-09-17",
"supperRate": 90
}
]'

Note: docker is running on port 8099 and local port is 8089


### Taxable income mapping is done in config file like below based on salary range

0-18200: 0,0,0
18201-37000: 0,19,18200
37001-87000: 3572,32.5,37000
87001-180000: 19822,37,87000
180001-99999999999: 54232,45,180000

ex: 3572,32.5,37000 = $3,572 plus 32.5c for each $1 over $37,000

### Deployment Instructions on azure cloud

Application is deployable on docker container of azure cloud with following services of azure cloud

### Azure cloud resources
resource-group: jagwani-azure-resource-group
location: centralus
docker-registry: jagwaniazuredockerregistry
docker-container: incomemanagementcontainer

### github workflow is attached with source code at .github/workflows/cicd-config.yml
so you when you push code on github it will automatically deploy on azure cloud which configured with above resources.
after pushing the code on github you can check following url.


curl --location --request POST 'http://azure-demo.centralus.azurecontainer.io:8099/income-management-service/v1/payslips/employees/monthly-payslips' \
--header 'Content-Type: application/json' \
--data-raw '[
{
"annualSalary": 87500,
"firstName": "Vinod",
"lastName": "Jagwani",
"paymentStartDate": "2021-09-13",
"supperRate": 19
},
{
"annualSalary": 789,
"firstName": "Test",
"lastName": "Test",
"paymentStartDate": "2021-09-17",
"supperRate": 90
}
]'

### Service health check url on azure cloud:
http://azure-demo.centralus.azurecontainer.io:9099/income-management-service/actuator/health

### Swagger url on azure cloud:
http://azure-demo.centralus.azurecontainer.io:8099/income-management-service/swagger-ui/index.html


### Setup Azure cloud from locally (Optional)

1. install azure cli using command: 'brew install azure-cli'

az login: 'login azure from command line'

create resource group by using following command 
'az group create --name=jagwani-azure-resource-group --location=centralus'

create docker registry by using following command 
'az acr create --resource-group jagwani-azure-resource-group --location centralus --name jagwaniazuredockerregistry --sku Basic'

create docker container on azure by using following command
az container create --resource-group jagwani-azure-resource-group --name incomemanagementcontainer --image jagwaniazuredockerregistry.azurecr.io/income-management-service --dns-name-label azure-demo --ports 8099, 9099

Get azure credentials by using following command
az ad sp create-for-rbac --name "myApp" --role contributor \
--scopes /subscriptions/1cefad64-45a1-431d-af83-1478eb03a754/resourceGroups/jagwani-azure-resource-group \
--sdk-auth

You can use above credentials to setup GitHub action or any pipeline where applicable.

