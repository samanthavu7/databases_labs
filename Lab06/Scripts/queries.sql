/* SELECT S.sname, COUNT(*)
FROM Suppliers S, Parts P, Catalog C
WHERE S.sid=C.sid AND C.pid=P.pid
GROUP BY S.sid; 

SELECT S.sname, COUNT(*)
FROM Suppliers S, Parts P, Catalog C
WHERE S.sid=C.sid AND C.pid=P.pid
GROUP BY S.sid
HAVING COUNT(*)>=3;   

SELECT S.sname, COUNT(*)
FROM Suppliers S
WHERE S.sid IN (SELECT S.sid
	FROM Suppliers S, Parts P, Catalog C
	WHERE S.sid=C.sid AND C.pid=P.pid AND P.color='Green')
AND S.sid NOT IN (SELECT S.sid
	FROM Suppliers S, Parts P, Catalog C
	WHERE S.sid=C.sid AND C.pid=P.pid AND P.color!='Green') 
GROUP BY S.sid; */
 
SELECT S.sname, MAX(C.cost)
FROM Suppliers S, Catalog C
WHERE S.sid=C.sid AND S.sid IN (SELECT S.sid
	FROM Suppliers S, Catalog C
	WHERE S.sid IN (SELECT S.sid
		FROM Suppliers S, Parts P, Catalog C
		WHERE S.sid=C.sid AND C.pid=P.pid AND P.color='Green')
	AND S.sid IN (SELECT S.sid
		FROM Suppliers S, Parts P, Catalog C
		WHERE S.sid=C.sid AND C.pid=P.pid AND P.color='Red')
	GROUP BY S.sid)
GROUP BY S.sid;
