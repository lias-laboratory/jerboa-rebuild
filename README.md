# Jerboa-Rebuilt

## Project overview

This project is a plugin to allow the automatic rebuild of a parametric specification.

## Notice

The gen folder must be set as source directory.
In Eclipse right-click on gen:
`"Build Path" -> "Use as Source Folder" 

## Jerboa dependencies

Jar dependencies from the Jerboa project (no available from Maven Repositories)

* Jerboa
* JerboaModelerViewer
* JerboaModelerTransmitter

## Configure the Maven project

Since the Jerboa Jar dependencies are not available on any Maven Repositories, you need to configure your Java development environnment based on Maven build tool.

Two approches to add Jerboa Jar dependencies:

* into your local repository in manual way
* from the LIAS repository manager

We suppose the files related to the Jarboa Jar dependencies are available on your file system.

### Manual way

* From command line, execute the following instructions.

```
$ mvn install:install-file -Dfile=Jerboa_05102021.jar -DgroupId=fr.up.xlim.sic.ig -DartifactId=jerboa -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
$ mvn install:install-file -Dfile=JerboaModelerViewer_05102021.jar -DgroupId=fr.up.xlim.sic.ig -DartifactId=jerboamodelerviewer -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
$ mvn install:install-file -Dfile=JerboaModelerTransmitter_05102021.jar -DgroupId=fr.up.xlim.sic.ig -DartifactId=jerboamodelertransmitter -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
```

### LIAS repository manager

**Note: this approach requires your computer is connected to the ISAE-ENSMA LAN network.**

* Edit the _settings.xml_ file (located to Maven installation directory) and replace by this content

```
<settings>
        <localRepository><HOME_USER>/.m2/repository</localRepository>
        <proxies></proxies>
        <servers>
                <server>
                        <id>releases</id>
                        <username>deployment</username>
                        <password>deployment123</password>
                </server>
                <server>
                        <id>snapshots</id>
                        <username>deployment</username>
                        <password>deployment123</password>
                </server>
                <server>
                        <id>thirdparty</id>
                        <username>deployment</username>
                        <password>deployment123</password>
                </server>
        </servers>

        <mirrors>
                <mirror>
                        <id>Nexus</id>
                        <name>Nexus Public Mirror</name>
                        <mirrorOf>central</mirrorOf>
                        <url>http://s-nexus-lias.ensma.fr/content/groups/public/</url>
                </mirror>
        </mirrors>

        <profiles>
                <profile>
                        <id>nexus</id>
                        <activation>
                                <activeByDefault>true</activeByDefault>
                        </activation>
                        <repositories>
                                <repository>
                                        <id>central</id>
                                        <url>http://central</url>
                                        <releases><enabled>true</enabled></releases>
                                        <snapshots><enabled>true</enabled></snapshots>
                                </repository>
                        </repositories>
                </profile>
        </profiles>

        <activeProfiles>
                <activeProfile>nexus</activeProfile>
        </activeProfiles>
</settings>
```
