
Administrative service

```java

String getGlobalProperty(String propertyName,
                         String defaultValue)
                         
```

* org.openmrs.api.MissingRequiredIdentifierException: Patient is missing the following required identifier(s): OpenMRS ID
his is the error message. It means that you are trying to create a patient without a value for one or more of the required identifiers (in this case OpenMRS ID). Query the identifierType resource and note which identifiers have "required": true. There must be a value for all of those identifiers when creating a patient.

org.hibernate.NonUniqueObjectException: A different object with the same identifier value was already associated with the session 

https://talk.openmrs.org/t/person-to-patient/13224/6

https://github.com/openmrs/openmrs-core/blob/master/api/src/test/java/org/openmrs/api/PatientServiceTest.java#L713-L735

Context.clearSession();
```
<property
    name="hibernate.enable_lazy_load_no_trans"
    value="true"/>
 ```   
    https://javarevisited.blogspot.com/2014/04/orghibernatelazyinitializationException-Could-not-initialize-proxy-no-session-hibernate-java.html
