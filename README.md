# Jerboa-Rebuilt

## Project overview

This project is a plugin to allow the automatic rebuild of a parametric
specification.

## Notice

In order to be able to run the project you will need to export the modeler
through the Jerboa Modeler Editor interface which is not found within this
project and update it using a script as follow:

From within `$PROJECT_ROOT/scripts/`
``` sh
$ ./update-rules.sh
```

This script updates the generated files (modeler and rules) in order to match
the project structure.


## Jerboa dependencies

Jar dependencies from the Jerboa project (no available from Maven Repositories)

* Jerboa
* JerboaModelerViewer
* JerboaModelerTransmitter

## Configure the Maven project

Since the Jerboa Jar dependencies are not available on any Maven Repositories,
you need to configure your Java development environment based on Maven build
tool.

Two approches to add Jerboa Jar dependencies:

* into your local repository in manual way
* from the LIAS repository manager

We suppose the files related to the Jerboa Jar dependencies are available on
your file system.

### Manually

* From command line, execute the following instructions.

``` sh
$ mvn install:install-file -Dfile=Jerboa_04032022.jar -DgroupId=fr.up.xlim.sic.ig -DartifactId=jerboa -Dversion=1.1 -Dpackaging=jar -DgeneratePom=true
$ mvn install:install-file -Dfile=JerboaModelerViewer_05102021.jar -DgroupId=fr.up.xlim.sic.ig -DartifactId=jerboamodelerviewer -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
$ mvn install:install-file -Dfile=JerboaModelerTransmitter_05102021.jar -DgroupId=fr.up.xlim.sic.ig -DartifactId=jerboamodelertransmitter -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
```

### LIAS repository manager

**Note: this approach requires the computer to be connected to the ISAE-ENSMA LAN network.**

* Edit the _settings.xml_ file (located to Maven installation directory) and
  replace by this content

``` xml
<?xml version="1.0" encoding="UTF-8"?>

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <localRepository>${user.home}/.m2/repository</localRepository>
    <proxies></proxies>
    <servers>
        <server>
            <id>Release</id>
            <username>deployment</username>
            <password>deployment123</password>
        </server>
        <server>
            <id>snapshots</id>
            <username>deployment</username>
            <password>deployment</password>
        </server>
    </servers>

    <mirrors>
        <mirror>
            <id>Nexus</id>
            <name >Nexus Public Mirror</name>
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
                    <url>http://central/</url>
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

## Directories

### Examples
This directory should contain examples of models to reevaluate.

### Exports
This directory should contain any file exported by the user such as:
- parametric specifications,
- history records, 
- matching trees (coming soon),
- graphviz dots and pdfs.

### Scripts
- update-rules.sh updates files generated with the Jerboa Modeler Editor.
- hr-graphic-export generates graphviz exports from history record json exports.
