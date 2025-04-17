# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.example.springboot-camel-play' is invalid and this project uses 'com.example.springboot_camel_play' instead.

# Getting Started

Run Spring boot

* Create new hello word logger
```
curl --location 'http://localhost:8080/routes' \
--header 'Content-Type: application/json' \
--data '{
  "id": "myDynamicRoute",
  "message": "Hello from dynamically created route!"
}'
```
* Upldate and reload
```
curl --location --request PUT 'http://localhost:8080/routes/myDynamicRoute/reload' \
--header 'Content-Type: application/json' \
--data '{
  "message": "Updated message for reloaded route!"
}'
```
*URL Fetcher
```
curl --location 'http://localhost:8080/routes/http' \
--header 'Content-Type: application/json' \
--data '{
  "id": "fetchFromGoogle",
  "url": "https://www.google.com"
}
'
```
* List all routes
```
curl --location --request GET 'http://localhost:8080/routes' \
--header 'Content-Type: application/json' \
--data '{
  "id": "myDynamicRoute",
  "message": "Hello from dynamically created route!"
}
'
```

*Load YAML route dynamically
```
curl --location 'http://localhost:8080/routes/yaml' \
--header 'Content-Type: text/plain' \
--data '- route:
    id: myYamlRoute
    from:
      uri: "timer:yaml?period=3000"
      steps:
        - log:
            message: "🚀 Hello from dynamic YAML!"
'
```
* Generic for compiled class files
```
curl --location 'http://localhost:8080/routes/java' \
--header 'Content-Type: application/json' \
--data '{
  "className": "com.example.camelplay.routes.HttpLogRouteBuilder",
  "params": {
    "routeId": "googleFetcher",
    "url": "https://www.google.com"
  }
}
'

```