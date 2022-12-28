CREATE TABLE IF NOT EXISTS favourite_image (
	userid  INT NOT NULL,
	imageid  INT NOT NULL,
	favouritestatus BOOLEAN NOT NULL,
    PRIMARY KEY (userid,imageid),

	FOREIGN KEY (userid) REFERENCES user_profile (id) ON DELETE CASCADE,
	FOREIGN KEY (imageid) REFERENCES image (id) ON DELETE CASCADE
);
