#!/bin/bash
export PERFECUT_ARTIFACT=ide

MVN_SETTINGS="--settings ${HOME}/.m2/m3-settings.xml"

. `dirname $0 2> /dev/null`/common.sh

ACTION=$1

cd "${basedir}"

git stash
git checkout master
git stash
git pull

BUILDNO=`cat ${PERFECUT_ARTIFACT}-buildno.txt`

BUILDNO=$((${BUILDNO}+1))
BUILD=`printf "%02d" "$((${BUILDNO}))"` 

echo ============================================================================
echo " Incrementing build train to: ${PERFECUT_ARTIFACT}-${BUILD}"
echo "Using MVN_SETTINGS: ${MVN_SETTINGS}"
echo ============================================================================

echo ${BUILDNO} > ${PERFECUT_ARTIFACT}-buildno.txt
VERSION=7.1.${BUILDNO}
git commit -a -m "getting ready for version ${VERSION}"
git push origin master:master
git stash clear

echo "Creating release branch for ${VERSION}"

git branch release-${VERSION}
git checkout release-${VERSION}

# root project uses regular maven versions
find * -name 'pom.xml' | xargs perl -pi -e "s/<version>7.1.0-SNAPSHOT<\/version>/<version>${VERSION}<\/version>/g"

# replace the OSGi version names in poms
find plugins -name 'pom.xml' | xargs perl -pi -e "s/<version>7.1.0.qualifier<\/version>/<version>${VERSION}<\/version>/g"

# replace manifest versions
find plugins -name '*.xml' | xargs perl -pi -e "s/7.1.0.qualifier/${VERSION}/g"
find plugins -name 'bundle.properties' | xargs perl -pi -e "s/7.1.0.qualifier/${VERSION}/g"
find plugins -name 'MANIFEST.MF' | xargs perl -pi -e "s/7.1.0.qualifier/${VERSION}/g"
find plugins -name 'fuse*.product' | xargs perl -pi -e "s/7.1.0.qualifier/${VERSION}/g"

# replace IDE version
perl -pi -e "s/<ide-version>.*<\/ide-version>/<ide-version>${VERSION}<\/ide-version>/g" plugins/pom.xml

#echo "Zapping P2 cache of fusesource and snapshot stuff"
#rm -rf /mnt/hudson/.m2/repository/p2/osgi/bundle/org.fusesource*
#rm -rf /mnt/hudson/.m2/repository/p2/osgi/bundle/com.fusesource*
rm -rf /mnt/hudson/.m2/repository/p2/osgi/bundle/*/*SNAPSHOT

#rm -rf /mnt/hudson/.m2/repository/org/fusesource/
#rm -rf /mnt/hudson/.m2/repository/com/fusesource/

echo "Building the update site..."
cd plugins
#mvn ${MVN_SETTINGS} -Psign -Dkeystore=$PWD/fusesource.ks -Dstorepass=sp33d0 -Dalias=fusesource.com clean install
#mvn ${MVN_SETTINGS} clean install

# lets not fail the build if tests fail
mvn ${MVN_SETTINGS} -Dtest=false -DfailIfNoTests=false -Dmaven.test.skip=true clean install

if [ $? -eq 0 ]         # Test exit status of "mvn" command.
then
  echo "Build succeeded. Progress to update site deployment..."
else  
  echo "Build failed. Exit now..."
#  exit $?
fi

# now copy the aggregated dependencies list to the rcp rootFiles folders 
echo "From: 'Eclipse Foundation' (http://www.eclipse.org/)" >> ./target/maven-shared-archive-resources/META-INF/DEPENDENCIES
echo "  - Eclipse Platform Bundles, 3.7 (Indigo) " >> ./target/maven-shared-archive-resources/META-INF/DEPENDENCIES
echo "    License: Eclipse Public License, Version 1.0  (http://www.eclipse.org/legal/epl-v10.html)" >> ./target/maven-shared-archive-resources/META-INF/DEPENDENCIES
cp ./target/maven-shared-archive-resources/META-INF/DEPENDENCIES ./rcp_build/org.fusesource.ide.rcp.feature/rootFiles/unix/notices.txt
cp ./target/maven-shared-archive-resources/META-INF/DEPENDENCIES ./rcp_build/org.fusesource.ide.rcp.feature/rootFiles/win/notices.txt

# now deploy the update site
cd org.fusesource.ide.updatesite
mvn ${MVN_SETTINGS} updatesite:deploy

if [ $? -eq 0 ]         # Test exit status of "mvn" command.
then
  echo "Deployment succeeded. Progress to RCP build..."
else  
  echo "Deployment failed. Exit now..."
  exit $?
fi

echo ============================================================================
echo "Update site complete now updating the update site versions"
echo ============================================================================

cd ../..

# echo "Build deployed - press OK to continue to update the site with the updated version"
# read dummy

# update the update site versions
./addUpdateSiteVersion ${VERSION} beta/3.0.x


echo ============================================================================
echo "Creating the RCP build"
echo ============================================================================

cd plugins/rcp_build
#mvn ${MVN_SETTINGS} -Psign -Dkeystore=$PWD/fusesource.ks -Dstorepass=sp33d0 -Dalias=fusesource.com clean install
mvn ${MVN_SETTINGS} clean install

if [ $? -eq 0 ]         # Test exit status of "mvn" command.
then
  echo "Build succeeded. Progress to integration testing..."
else  
  echo "Build failed. Exit now..."
  exit $?
fi

echo "Deploying the distribution archives"
echo ============================================================================

cd org.fusesource.ide.rcp.product
mvn ${MVN_SETTINGS} updatesite:deploy

if [ $? -eq 0 ]         # Test exit status of "mvn" command.
then
  echo "RCP deployment succeeded. Clean up and exit..."
else
  echo "RCP deployment failed. Exit now..."
  exit $?
fi



echo "Executing the integration tests"
echo ============================================================================
cd ../testing
mvn ${MVN_SETTINGS} clean install -Djubula.home=/opt/jubula_5.2.00266 -Djubula.plugin.archive=/opt/jubula_5.2.00266/rcp-support.zip

if [ $? -eq 0 ]         # Test exit status of "mvn" command.
then
  echo "Integration testing succeeded. Progress to RCP deployment..."
else  
  echo "Integration testing failed. Exit now..."
  exit $?
fi

cd ../../..

echo "Deployed update site for Release ${VERSION}"
echo "Now committing branch"

git commit -a -m "Release ${VERSION}"
git push origin release-${VERSION}

git checkout master

echo "One it has been tested, move it to the production repo area"


