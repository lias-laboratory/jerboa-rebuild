# Jerboa Rebuild

<!--toc:start-->
- [Jerboa Rebuild](#jerboa-rebuild)
  - [Pre-requisits](#pre-requisits)
  - [Installation](#installation)
  - [Usage](#usage)
  - [Documentation](#documentation)
  - [License](#license)
  - [Acknowledgements](#acknowledgements)
    - [Contributors](#contributors)
<!--toc:end-->

Jerboa Rebuild is a 3D modelling plugin allowing users to interactively rebuild
geometric 3D models.
This plugin records the modelling operation with their
parameters and offers users to add, delete and move those operations in order to
regenerate a model.

## Notice

Although more recent JDKs may work, it is recommended to use java 11.0.11 oracle JDK for this project.

## Installation

Within the projet's root directory execute :

```sh 
mvn install
```

then

``` sh
mvn package
```

## Usage

Within the projet's root directory execute :
``` sh
java -cp "target/dependency/*:target/jerboa-rebuilt-1.5-SNAPSHOT.jar" \
fr.ensma.lias.jerboa.SpecEditorLauncher
```
## Documentation

The `mvn package` command generates the project documentation under `$PROJECT_ROOT/target/apidocs`.

Open the `index.html` file to access the documentation.

## More information

For more information about this project's underlying processes:
- Events' detection [\[1\]](https://hal.science/hal-04228069)
- Reevaluation mechanisms [\[2\]](https://hal.science/hal-04607231)

For an even more complete insight (in french) [\[3\]](https://hal.science/tel-04886518v1)

## License

This project is licensed under the GNU Lesser General Public License v2.1 - see
the LICENSE file for details.

## Acknowledgements

This project results from the collaboration of the
[LIAS](https://www.lias-lab.fr) and [XLIM](https://www.xlim.fr) laboratories
and was funded by [ISAE-ENSMA](https://www.ensma.fr/)

### Contributors

- Maxime Gaide, LIAS
- Hakim Belhaouari, XLIM
- Tom Boireau, XLIM (intern)
- Victor Laurin, XLIM (intern)

## References
- [1] [Automatic detection of topological changes in modeling operations](https://hal.science/hal-04228069)
- [2] [Reevaluation in Rule-Based Graph Transformation Modeling Systems](https://hal.science/hal-04607231)
- [3] [Modélisation et rejeu basés sur des règles](https://hal.science/tel-04886518v1)

