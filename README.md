# repsy-package-app

repsy-package-app is a lightweight tool designed to help you manage and publish private Maven packages using [Repsy.io](https://repsy.io/). It simplifies repository setup and streamlines package deployment for personal or organizational use.

## Features
- Seamless publishing of Maven packages to Repsy.io.
- Simple and quick configuration.
- CI/CD pipeline integration ready.
- Minimal, clean, and developer-friendly design.

## Installation
Clone the repository:
```bash
git clone https://github.com/kaanraghos/repsy-package-app.git
cd repsy-package-app
Build the project:

bash
mvn clean install

## Usage
Run the application:
bash


java -jar target/repsy-package-app.jar
## Maven Configuration
Ensure your settings.xml includes your Repsy credentials, and your pom.xml is configured properly.

settings.xml
xml


<servers>
    <server>
        <id>repsy</id>
        <username>your-username</username>
        <password>your-api-key</password>
    </server>
</servers>
pom.xml
xml
<distributionManagement>
    <repository>
        <id>repsy</id>
        <url>https://repo.repsy.io/mvn/your-username/your-repo/</url>
    </repository>
</distributionManagement>

##Contributing
Fork this repository.

Create a new branch: git checkout -b feature/your-feature

Commit your changes: git commit -m 'Add some feature'

Push to your branch: git push origin feature/your-feature

Open a Pull Request.

##License
This project is licensed under the MIT License.
