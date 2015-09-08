# !/bin/sh

#kill tomcat thread and copy /root/ROOT.war to /tomcat and start it

#step 1 build

githome=/root/codes/git/rpd

cd $githome

echo checkout now

./checkout.sh

echo wait build

#cd trunk/

./build.sh

autoconfig rpd-manager/target/rpd-manager.war

#step 2 start server

echo find tomcat and kill

for pid in $(ps -ef | grep tomcat | grep -v grep | cut -c 9-15); do
    echo kill  $pid
    kill -9 $pid
done

home=/usr/local/apache-tomcat-6.0.43

rm -rf $home/webapps/rpd-manager*

echo wait for delete webapps/

sleep 5

cp $githome/rpd-manager/target/rpd-manager.war $home/webapps/

cd $home

$home/bin/startup.sh