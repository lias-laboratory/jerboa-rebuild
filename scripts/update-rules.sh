#!/bin/bash

WORKING_DIR="../src/main/java/fr/ensma/lias/jerboa/core/rule/rules"
MODELER="$WORKING_DIR/ModelerGenerated.java"

is_file_updated(){
	if [[ $(grep -F "$2" "$1") == *"$2"* ]]
	then
		return 0
	fi
	return 1
}

update_modeler() {
	is_file_updated "$1" "JerboaModelerGeneric"
	if [[ $? -eq 1 ]]
	then
		return
	else
		sed -i '0,/^package*/a import fr.ensma.lias.jerboa.core.JerboaRebuiltModeler;' "$1"
		sed -i 's/JerboaModelerGeneric/JerboaRebuiltModeler/g' "$1"
	fi
}

update_entry() {
	is_file_updated "$1" "JerboaRuleGenerated"
	if [[ $? -eq 1 ]]  
	then
		return
	else
		sed -i '0,/^package*/a import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;' "$1"
		sed -i 's/JerboaRuleGenerated/JerboaRebuiltRule/g' "$1"
		#sed -i '/SPECIFIED FEATURE/a enrichTrackingExpressions();' "$1"
	fi
}

search_dir=$(find "$WORKING_DIR/" -type f)
for entry in $search_dir
do
	if [ "$entry" = $MODELER ]
	then
		update_modeler "$entry"
	else
		update_entry "$entry"
	fi
done	
