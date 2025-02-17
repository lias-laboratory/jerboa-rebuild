# Jerboa Rebuild

This plugin records the modelling operations with their parameters during the
modelling process. 
Users have the ability to edit a modelling process by adding
and deleting within the modelling process in order to regenerate a model.

## Project requirements

Although more recent JDKs may work, it is recommended to use the Java 11.0.11 oracle JDK for this project.

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

This project is the result of a collaboration between the
[LIAS](https://www.lias-lab.fr) and [XLIM](https://www.xlim.fr) laboratories.
It was funded by [ISAE-ENSMA](https://www.ensma.fr/) as part of a ministerial grant.

### Contributors

- [Maxime Gaide, LIAS](https://www.lias-lab.fr/members/maximegaide/)
- [Hakim Belhaouari, XLIM](https://xlim-sic.labo.univ-poitiers.fr/membres/hbelhaou/)
- Tom Boireau
- Victor Laurin

## References
- [1] [Automatic detection of topological changes in modeling operations](https://hal.science/hal-04228069)
- [2] [Reevaluation in Rule-Based Graph Transformation Modeling Systems](https://hal.science/hal-04607231)
- [3] [Modélisation et rejeu basés sur des règles](https://hal.science/tel-04886518v1)
