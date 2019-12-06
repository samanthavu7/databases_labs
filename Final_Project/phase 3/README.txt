--------------------------------------------------------------------------		
			CS 166 Databases Project phase 3
----------------------------------------------------------------------------

source ./startPostgreSQL.sh
source ./createPostgreDB.sh
cp *.csv /tmp/$USER/myDB/data/
psql -h localhost -p $PGPORT $USER"_DB" < create.sql
source ./compile.sh 
