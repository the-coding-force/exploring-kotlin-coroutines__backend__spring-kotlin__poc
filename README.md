# PoC: Performance Comparison with and without Coroutines in Spring Kotlin

This PoC aims to stress test a **[Spring](https://spring.io/)** application using **[Kotlin](https://kotlinlang.org/)** to compare performance with and without coroutines. We are testing high-load scenarios to evaluate the impact of coroutines on performance, CPU usage, and thread management efficiency.

## 💡 What we explore:

- **💻 Performance comparison** under stress scenarios, with and without coroutines
- **📊 Measuring execution time**, CPU, and memory usage
- **🔧 Simple and effective validation** of the results

## 🛠️ Technologies:

- 🟢 **[Kotlin](https://kotlinlang.org/)** (focused on coroutines)
- 🌐 **[Spring Framework](https://spring.io/)**
- 📊 **[JMeter](https://jmeter.apache.org/)** or **[Gatling](https://gatling.io/)** for load testing
- 📉 **[Prometheus](https://prometheus.io/)** and **[Grafana](https://grafana.com/)** for performance monitoring
- 🐳 **[Docker](https://www.docker.com/)** for containerization

## ⚙️ Workflow:

We follow **GitFlow** and manage changes through **Pull Requests (PRs)**.

## 🚀 Running Locally

To run the application locally, use Docker Compose. Execute the following command:

```sh
docker compose -f docker/docker-compose.yml up -d
```

After the Docker containers are up and running, start the Spring Boot application with:
```sh
./gradlew bootRun
```

## 📊 Acessing Grafana locally:

To access Grafana, after application are up and running, open your browser and go to `http://localhost:3000`. 
Use the following credentials:
- **Username:** admin
- **Password:** grafana

## 📊 Acessing Grafana locally:

To access Prometheus, after application are up and running, open your browser and go to `http://localhost:9090`.
