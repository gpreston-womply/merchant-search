# merchant-search
This is a RESTful service for reading (and eventually editing) information about Merchant and associated objects.
It is built using the womply-common-java libraries.

To build this project, execute the following in your command line:
```bash
mvn clean install
```

To run the main server, execute the following in your command line:
```bash
cd server
mvn exec:java
```


Currently working endpoints:


A java client to this service can be found at com.womply.client.MerchantServiceClient.  See main() for example usage.


