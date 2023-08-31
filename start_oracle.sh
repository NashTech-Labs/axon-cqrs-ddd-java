#For Mac M1 only
# colima delete
# colima start --memory 4 --arch x86_64
docker run -d -p 1521:1521 -e ORACLE_PASSWORD=password -v oracle-volume:/opt/oracle/oradata gvenzl/oracle-free