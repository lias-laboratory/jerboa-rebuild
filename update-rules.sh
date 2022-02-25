#!/bin/bash

MODELER="./gen/fr/ensma/lias/jerboa/trackingModeler/JerboaTrackingModelerGenerated.java"

is_file_updated(){
	if [[ $(grep -F "$2" "$1") == *"$2"* ]]
	then
		return 1
	fi
	return 0
}

update_modeler() {
	is_file_updated "$1" "JerboaRebuiltModeler"
	if [[ $? -eq 1 ]]
	then
		return
	else
		sed -i '0,/^package*/a import fr.ensma.lias.jerboa.JerboaRebuiltModeler;' "$1"
		sed -i 's/JerboaModelerGeneric/JerboaRebuiltModeler/g' "$1"
	fi
}

update_entry() {
	is_file_updated "$1" "JerboaRebuiltRule"
	if [[ $? -eq 1 ]]  
	then
		return
	else
		sed -i '0,/^package*/a import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;' "$1"
		sed -i 's/JerboaRuleGenerated/JerboaRebuiltRule/g' "$1"
		sed -i '/SPECIFIED FEATURE/a enrichTrackingExpressions();' "$1"
	fi
}

search_dir=$(find ./gen/fr/ensma/lias/jerboa/trackingModeler/ -type f)
for entry in $search_dir
do
	if [ "$entry" = $MODELER ]
	then
		update_modeler "$entry"
	else
		update_entry "$entry"
	fi
done	
