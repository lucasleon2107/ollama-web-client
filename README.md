# Ollama Web Client

This project is a client for Ollama built with Vaadin and Kotlin.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Docker installed. Download it [here](https://www.docker.com/products/docker-desktop/).

## Getting Started

To get started with this project, follow these steps:

1. Clone the repository:

    ```bash
    git clone https://github.com/lucasleon2107/ollama-web-client.git
    ```
    
2. Navigate to the project directory:

    ```bash
    cd ollama-web-client
    ```

3. Build and start the services using Docker Compose:

    ```bash
    docker-compose up -d
    ```
4. Pull the `tinyllama` model for the `ollama` service:

    ```bash
    docker-compose exec ollama ollama pull tinyllama
    ```
   
5. Once the project runs, access it through your web browser at [http://localhost:8080](http://localhost:8080).

## Usage

- Upon accessing the web application, you should see the interface for interacting with the Ollama Web Client.
- Type your prompt in the message input. The message input will be disabled until Ollama finishes responding.

## Contributing

Contributions are welcome! If you find any bugs or have suggestions for improvement, please submit an issue or a pull request.
