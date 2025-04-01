#!/bin/bash

for dir in ./*; do
    if [[ -d $dir ]]; then
        echo $dir
        cd $dir
        for figure in *.svg; do
            echo $figure
            target=${figure//svg/pdf}
            inkscape ${echo file} -o ${echo target}
        done
        cd ..
    fi
done
