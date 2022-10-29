LOAD DATA LOCAL INFILE './src/main/resources/data/academy_awards.csv'
INTO TABLE oscar_nomination
FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(@year,@category,@nominee,@additional,@won)
SET year=SUBSTRING_INDEX(@year, ' ', 1),
    category=@category,
    nominee=@nominee,
    won=CASE @won WHEN 'YES' THEN 1 WHEN 'NO' THEN 0 ELSE 0 END;