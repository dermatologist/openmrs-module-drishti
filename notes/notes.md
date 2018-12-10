
Administrative service

```java

String getGlobalProperty(String propertyName,
                         String defaultValue)
                         
```

* org.openmrs.api.MissingRequiredIdentifierException: Patient is missing the following required identifier(s): OpenMRS ID
his is the error message. It means that you are trying to create a patient without a value for one or more of the required identifiers (in this case OpenMRS ID). Query the identifierType resource and note which identifiers have "required": true. There must be a value for all of those identifiers when creating a patient.

