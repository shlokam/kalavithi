CREATE TABLE IF NOT EXISTS like_image (
	userid  INT NOT NULL,
	imageid  INT NOT NULL,
	status BOOLEAN NOT NULL,
    PRIMARY KEY (userid,imageid),

	FOREIGN KEY (userid) REFERENCES user_profile (id) ON DELETE CASCADE,
	FOREIGN KEY (imageid) REFERENCES image (id) ON DELETE CASCADE
);