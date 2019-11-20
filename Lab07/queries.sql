SELECT count(nyc.on_hand) 
FROM part_nyc nyc
WHERE nyc.on_hand > 70;

SELECT sum(Red.on_hand)
FROM (SELECT *
	FROM part_nyc nyc, color c 
	WHERE nyc.color = c.color_id AND c.color_name = 'Red'
	UNION ALL
	SELECT *
	FROM part_sfo sfo, color c
	WHERE sfo.color = c.color_id AND c.color_name = 'Red') AS Red;

SELECT s.supplier_name
FROM (SELECT nyc.supplier, sum(nyc.on_hand) AS nyc_onhand 
	FROM part_nyc nyc
	GROUP BY nyc.supplier) AS s_nyc, 
	(SELECT sfo.supplier, sum(sfo.on_hand) AS sfo_onhand
	FROM part_sfo sfo
	GROUP BY sfo.supplier) AS s_sfo,
	Supplier s
WHERE s_nyc.supplier = s_sfo.supplier AND s_nyc.nyc_onhand > s_sfo.sfo_onhand AND s_nyc.supplier = s.supplier_id;

SELECT DISTINCT s.supplier_name 
FROM part_nyc nyc, supplier s
WHERE nyc.supplier = s.supplier_id AND nyc.part_number NOT IN (SELECT sfo.part_number
	FROM part_sfo sfo);

UPDATE part_nyc
SET on_hand = on_hand - 10;

DELETE FROM part_nyc
WHERE on_hand < 30;
