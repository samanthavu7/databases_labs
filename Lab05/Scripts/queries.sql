SELECT P.pid
FROM Parts P, Catalog C
WHERE P.pid=C.pid AND C.cost<10

SELECT P.pname
FROM Parts P, Catalog C
WHERE P.pid=C.pid AND C.cost<10

SELECT S.address
FROM Suppliers S, Catalog C, Parts P
WHERE S.sid=C.sid AND C.pid=P.pid AND P.pname='Fire Hydrant Cap'

SELECT S.sname
FROM Suppliers S, Catalog C, Parts P
WHERE S.sid=C.sid AND C.pid=P.pid AND P.color='Green' 

SELECT S.sname, P.pname
FROM Suppliers S, Catalog C, Parts P
WHERE S.sid=C.sid AND C.pid=P.pid
