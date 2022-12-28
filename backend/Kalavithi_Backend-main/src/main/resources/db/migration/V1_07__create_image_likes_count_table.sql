CREATE TABLE IF NOT EXISTS image_likes_count (
	imageid INT NOT NULL,
	likescount  INT NOT NULL DEFAULT 0,
    PRIMARY KEY (imageid),

	FOREIGN KEY (imageid) REFERENCES image (id) ON DELETE CASCADE
);