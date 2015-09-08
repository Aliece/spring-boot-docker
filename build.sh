cd rpd-common-util
rm -rf target
mvn -o install -DskipTests=true
cd ..
cd rpd-dao
rm -rf target
mvn -o install -DskipTests=true
cd ..
cd rpd-service
rm -rf target
mvn -o install -DskipTests=true
cd ..
cd rpd-wb4j
rm -rf target
mvn -o install -DskipTests=true
cd ..
cd rpd-payeco
rm -rf target
mvn -o install -DskipTests=true
cd ..
cd rpd-paysina
rm -rf target
mvn -o install -DskipTests=true
cd ..
cd rpd-zmscore
rm -rf target
mvn -o install -DskipTests=true
cd ..
cd rpd-manager
rm -rf target
mvn -o install -DskipTests=true
cd ..
