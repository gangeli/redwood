# -- VARIABLES --
NAME=redwood
# (locations)
SRC=src
TEST_SRC=test/src
BUILD=bin
TEST_BUILD=test/bin
DIST=dist
# (compiler)
JAVAC=javac

default: ${DIST}/${NAME}.jar

# -- BUILD --
${DIST}/${NAME}.jar: $(wildcard ${SRC}/edu/stanford/nlp/util/logging/*.java)
	@echo "--------------------------------------------------------------------------------"
	@echo "                          BUILDING REDWOOD LOGGER"
	@echo "--------------------------------------------------------------------------------"
	mkdir -p ${BUILD}
	mkdir -p ${DIST}
	#(compile)
	${JAVAC} -Xlint:unchecked -Xlint:deprecation -d $(BUILD) `find ${SRC} -name "*.java"`
	#(copy)
	cp "COPYING.LESSER" ${BUILD}/
	#(jar)
	jar cf ${DIST}/${NAME}.jar -C $(BUILD) .
	jar uf ${DIST}/${NAME}.jar -C $(SRC) .

clean:
	rm -f ${DIST}/${NAME}.jar
	rmdir ${DIST} || [ ! -d ${DIST} ]
	rm -rf ${BUILD}/*
	rmdir ${BUILD} || [ ! -d ${BUILD} ]
