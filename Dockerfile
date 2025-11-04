# Dockerfile para la aplicación Spring Boot
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Instalar Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copiar todo y compilar directamente sin cache de dependencias
COPY . .

# Intentar compilar y mostrar errores claros
RUN mvn clean compile || (echo "COMPILATION FAILED - SHOWING DETAILED ERROR" && exit 1)

# Si compila bien, hacer el package
RUN mvn package -DskipTests

# Exponer puerto
EXPOSE 8080

# Ejecutar aplicación
CMD ["java", "-jar", "target/tecno_proyect-0.0.1-SNAPSHOT.jar"]