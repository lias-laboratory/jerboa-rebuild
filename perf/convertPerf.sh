#!/bin/bash

INPUTFILE=$1
ITERATIONS=$(grep "= ITERATION:" ${INPUTFILE} | cut -d ' ' -f 3 | cut -d ':' -f 1 > ite.log)
APPLI=`grep "=== End delegate rule" ${INPUTFILE} | cut -d ' ' -f 9 > appli.log`
ISLET=`grep "= islet done" ${INPUTFILE} | cut -d ' ' -f 4 > islet.log`
STARTANA=`grep "= start" ${INPUTFILE} | grep "analysis evolution" | cut -d ' ' -f 8 > startana.log`
ENDANA=`grep "= end" ${INPUTFILE} | grep "analysis " | cut -d ' ' -f 7 > endana.log`
TOTAUX=`grep "^= end" ${INPUTFILE} | grep "tracking: " | cut -d ' ' -f 5 > totaux.log`
GMAPSIZE=`grep "===GMAP SIZE" ${INPUTFILE} | cut -d ' ' -f 3 | cut -d ':' -f 1 > gmapsize.log`
IDLINE=`grep "= start tracking:" ${INPUTFILE} | grep -v initial | cut -d ':' -f 2 | tr ',' ' ' > idline.log`

if [ -z "$1" ]
then
    echo "Trouble with input file"
    INPUTFILE=sample.log
fi


COUNTITE=$(cat ite.log| wc -l)
COUNTAPP=$(cat appli.log | wc -l)
COUNTISL=$(cat islet.log | wc -l)
COUNTSTA=$(cat startana.log | wc -l)
COUNTEND=$(cat endana.log | wc -l)
COUNTTOT=$(cat totaux.log | wc -l)
COUNTGMA=$(cat gmapsize.log | wc -l)
COUNTIDL=$(cat idline.log | wc -l)


echo count line of iterations: $COUNTITE
echo count line of APPLI: $COUNTAPP
echo count line of ISLET: $COUNTISL
echo count line of STARTANA: $COUNTSTA
echo count line of ENDANA: $COUNTEND
echo count line of TOTAUX: $COUNTTOT
echo count line of GMAPSIZE: $COUNTGMA
echo count line of IDLINE: $COUNTIDL

OUTPUTFILE=${INPUTFILE//.log/.csv}
HEADER="operation,iteration, application, islet, startAnalysis, endAnalysis,total, gmapsize"

echo $HEADER > ${OUTPUTFILE}
COUNTORB=$((${COUNTIDL} /  ${COUNTITE}))
echo count of detected orbit: $COUNTORB
for((orbit=0; orbit < ${COUNTORB}; orbit++))
do
    echo "=== $(($orbit+1)) / ${COUNTORB} ==="
    for((i=0; i < ${COUNTITE}; i++))
    do
        LINE=$(( (${i} * ${COUNTORB}) + ${orbit} + 1))
        DLINE=$(( $i + 1 ))
        #echo LINE: $LINE DLINE: $DLINE
        DATA=$(head -n ${LINE} idline.log | tail -n 1)
        DATA="${DATA}, $(head -n ${DLINE} ite.log | tail -n 1)"
        DATA="${DATA}, $(head -n ${DLINE} appli.log | tail -n 1)"
        DATA="${DATA}, $(head -n ${LINE} islet.log | tail -n 1)"
        DATA="${DATA}, $(head -n ${LINE} startana.log | tail -n 1)"
        DATA="${DATA}, $(head -n ${LINE} endana.log | tail -n 1)"
        DATA="${DATA}, $(head -n ${LINE} totaux.log | tail -n 1)"
        DATA="${DATA}, $(head -n ${DLINE} gmapsize.log | tail -n 1)"
        echo $DATA >> ${OUTPUTFILE}
    done
    echo "" >> ${OUTPUTFILE}
done

rm -f idline.log ite.log appli.log islet.log startana.log endana.log totaux.log gmapsize.log