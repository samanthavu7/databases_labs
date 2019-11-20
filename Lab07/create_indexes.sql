CREATE INDEX nyc_onhand
ON part_nyc
USING BTREE
(on_hand);

CREATE INDEX c_cid 
ON color
USING BTREE
(color_id);
