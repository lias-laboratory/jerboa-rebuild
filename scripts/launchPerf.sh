JAVA="/mnt/c/Program\ Files/Java/jdk-11.0.13/bin/java.exe"

${JAVA} -jar JerboaModelerRebuilt_perf_CREATE_SPLIT.jar dynamic SubdivQuad cube.jba > LOG_SPLIT_dynamic.log
convertPerf.sh LOG_SPLIT_dynamic.log
${JAVA} -jar JerboaModelerRebuilt_perf_CREATE_SPLIT.jar static SubdivQuad cube.jba > LOG_SPLIT_static.log
convertPerf.sh LOG_SPLIT_static.log

${JAVA} -jar JerboaModelerRebuilt_perf_CREATE_SPLIT.jar dynamic ExtrudeVolumeFaceYAxis cube.jba > LOG_CREATE_dynamic.log
convertPerf.sh LOG_CREATE_dynamic.log
${JAVA} -jar JerboaModelerRebuilt_perf_CREATE_SPLIT.jar static ExtrudeVolumeFaceYAxis cube.jba > LOG_CREATE_static.log
convertPerf.sh LOG_CREATE_static.log

