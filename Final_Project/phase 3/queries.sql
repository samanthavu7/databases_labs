SELECT COUNT(r.roomNo)
FROM Room r
WHERE r.hotelID = 381 AND r.roomNo IN (SELECT r.roomNo
	FROM Room r
	WHERE r.hotelID = 381 
	EXCEPT
	SELECT b.roomNo
	FROM Booking b
	WHERE b.hotelID = 381)
