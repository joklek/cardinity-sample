# Intro
This is an application made for a job application and fun.
This is a mock store with a CLI storefront, using Cardinity as the payment processor.
Implemented the regular and 3d payment workflow.

## How to run
Enter your cardinity details in the `application.properties` file and then run the these maven commands

```
mvn install
mvn exec:java -Dexec.mainClass="com.joklek.cardintitysample.CardinitySample"
```

That's it, you're in the shop. Follow the instructions and try enjoy your stay
