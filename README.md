# FlexiDorms

## Microservices:
1. **Config service:** 
This service is responsible for providing the configuration to other services. It is a centralized configuration service.
    - URL: `http://localhost:8888/`

2. **Registry service:** 
This service is responsible for service discovery and registration of services. It is a Eureka server.
    - URL: `http://localhost:8761/`

3. **Gateway service:**
This service is responsible for routing the requests to the appropriate service.

4. **User service:**
This service is responsible for user management. Also, it provides the APIs for user registration, login, logout, etc.
    - URL: `http://localhost:8090/`

## Util commands:
- **Search with process use a specific port:** 
    - Windows: `netstat -aon | findstr [port]`
- **Kill a process by PID:**
    - Windows: `taskkill /F /PID [pid]`