
java -jar JerboaModelerRebuilt_perf_CREATE_SPLIT.jar dynamic SubdivQuad cube.jba > LOG_SPLIT_dynamic.log
bash convertPerf.sh LOG_SPLIT_dynamic.log
java -jar JerboaModelerRebuilt_perf_CREATE_SPLIT.jar static SubdivQuad cube.jba > LOG_SPLIT_static.log
bash convertPerf.sh LOG_SPLIT_static.log

java -jar JerboaModelerRebuilt_perf_CREATE_SPLIT.jar dynamic ExtrudeVolumeFaceYAxis cube.jba > LOG_CREATE_dynamic.log
bash convertPerf.sh LOG_CREATE_dynamic.log
java -jar JerboaModelerRebuilt_perf_CREATE_SPLIT.jar static ExtrudeVolumeFaceYAxis cube.jba > LOG_CREATE_static.log
bash convertPerf.sh LOG_CREATE_static.log

