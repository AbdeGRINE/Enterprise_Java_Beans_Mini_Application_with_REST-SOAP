# HISSAB - EJB / REST / SOAP Calculator

HISSAB is a Jakarta EE application built around a reusable calculator component named `CALC`. The application allows arithmetic expressions to be evaluated manually, through uploaded files(.png, .pdf, .txt) , and through web services. Each calculation can be traced and stored in a Derby database.
The Goal was not building the calculator but get experience using EJB components and Component based architecture alongside with including web services like REST and SOAP.

## Technical Stack

- Java / Jakarta EE
- EJB 3.x
- JSP / Servlet
- JAX-RS REST service
- SOAP-style XML web service
- JPA with EclipseLink
- Apache Derby database
- GlassFish 7 application server
- Apache PDFBox for PDF text extraction
- Tess4J / Tesseract OCR for image text extraction

## Project Structure

Typical Eclipse workspace structure:

```text
HissabEAR/
HissabEJB/
HissabWeb/
```

`HissabEJB` contains the business components, entities, and persistence configuration.  
`HissabWeb` contains the web interface, servlets, REST endpoint, and SOAP endpoint.  
`HissabEAR` packages the EJB and Web modules for deployment on GlassFish.

## Main Components

### CalcEJB

`CalcEJB` is the reusable calculator component. It evaluates arithmetic expressions such as:

```text
5 + 2 * 6 - 3
```

It supports common operators:

```text
+  -  *  /  ( )
```

It also normalizes some mathematical symbols:

```text
× -> *
÷ -> /
− -> -
```

### TraceEJB

`TraceEJB` stores calculation traces in the database. Each trace contains:

```text
id
expression
resultat
dateTraitement
```

The corresponding JPA entity is usually named `Trace`, and the table is:

```text
TRACE_CALCUL
```

### ExtractionEJB

`ExtractionEJB` extracts arithmetic expressions from input files:

- `.txt` files are read directly.
- `.pdf` files are processed with Apache PDFBox.
- image files such as `.png`, `.jpg`, `.jpeg` are processed with Tess4J OCR.

### HissabEJB

`HissabEJB` is the orchestration component. It coordinates the workflow:

```text
extract expression -> calculate result -> save trace -> return result
```

REST and SOAP services should call `HissabEJB` instead of calling `CalcEJB` directly, so that traces are stored in the database.

## Database Configuration

The application uses Apache Derby through the default GlassFish datasource:

```xml
<jta-data-source>jdbc/__default</jta-data-source>
```

The persistence unit is typically named:

```text
HissabPU
```

Example `persistence.xml` configuration:

```xml
<persistence-unit name="HissabPU" transaction-type="JTA">
    <jta-data-source>jdbc/__default</jta-data-source>
    <properties>
        <property name="jakarta.persistence.schema-generation.database.action" value="create"/>
    </properties>
</persistence-unit>
```

GlassFish uses Derby with the default datasource. The database can usually be accessed through:

```text
jdbc:derby://localhost:1527/sun-appserv-samples
```

Common Derby credentials:

```text
User: APP
Password: APP
```

## REST Web Service

The REST endpoint exposes the calculator through HTTP.

Example URL:

```text
http://localhost:8080/HissabWeb/api/calc?expression=5%2B2*6-3
```

Expected response:

```text
14.0
```

The `+` character must be URL-encoded as `%2B`.

## SOAP Web Service

The SOAP endpoint receives a SOAP XML request and returns a SOAP XML response.

Endpoint:

```text
http://localhost:8080/HissabWeb/soap/calc
```

Example SOAP request body:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
    <soapenv:Header/>
    <soapenv:Body>
        <calculer xmlns="http://soap.hissab.com/">
            <expression>5 + 2 * 6 - 3</expression>
        </calculer>
    </soapenv:Body>
</soapenv:Envelope>
```

Example SOAP response:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
    <soapenv:Body>
        <calculerResponse xmlns="http://soap.hissab.com/">
            <return>14.0</return>
        </calculerResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

## Web Client

The graphical client is available at:

```text
http://localhost:8080/HissabWeb/client.jsp
```

It allows the user to:

- enter an arithmetic expression;
- choose REST or SOAP;
- send the request;
- display the returned result.

## Trace History

Calculation history can be viewed at:

```text
http://localhost:8080/HissabWeb/traces
```

This page displays values stored in the Derby database through `TraceEJB`.

## File Upload

The main web interface supports expression extraction from uploaded files:

```text
http://localhost:8080/HissabWeb/
```

Supported inputs:

```text
.txt
.pdf
.png
.jpg
.jpeg
```

For OCR, the test image should be clear, with black text on a white background.

Recommended test expression:

```text
5 + 2 * 6 - 3
```

## External Libraries

Some libraries must be available to the application and to GlassFish at runtime.

Recommended GlassFish domain library folder:

```text
C:\Users\lenovo\Downloads\glassfish-7.0.25\glassfish7\glassfish\domains\domain1\lib
```

Typical required libraries:

```text
pdfbox-app-3.0.7.jar
tess4j.jar
lept4j.jar
jna.jar
jai-imageio-core.jar
slf4j-api.jar
slf4j-simple.jar
```

For JNA native loading, the following VM arguments may be required in the GlassFish launch configuration:

```text
-Djna.boot.library.path=C:\jna
-Djava.library.path=C:\jna
```

The folder should contain:

```text
C:\jna\jnidispatch.dll
```

For OCR language data, Tess4J expects a `tessdata` directory, for example:

```text
C:\tessdata\eng.traineddata
```

## Deployment

1. Start GlassFish 7.
2. In Eclipse, clean all projects.
3. Add `HissabEAR` to the GlassFish server.
4. Run the application on server.
5. Check the console for successful deployment.

Expected deployment message:

```text
HissabEAR was successfully deployed
```

## Test URLs

REST:

```text
http://localhost:8080/HissabWeb/api/calc?expression=5%2B2*6-3
```

SOAP:

```text
http://localhost:8080/HissabWeb/soap/calc
```

Graphical client:

```text
http://localhost:8080/HissabWeb/client.jsp
```

Trace history:

```text
http://localhost:8080/HissabWeb/traces
```

Main web interface:

```text
http://localhost:8080/HissabWeb/
```

## Notes

- REST and SOAP services should use `HissabEJB.traiterExpression(...)` to ensure that each calculation is saved in the trace database.
- PDFBox extracts text only from text-based PDFs. Scanned PDFs require OCR.
- OCR accuracy depends on your image quality.
- If deployment fails after repeated redeployments, clean the GlassFish server and remove old temporary deployments from the domain.
