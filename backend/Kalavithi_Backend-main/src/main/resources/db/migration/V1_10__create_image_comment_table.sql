CREATE TABLE IF NOT EXISTS image_comment(
    comment_id SERIAL NOT NULL PRIMARY KEY,
    user_id Int NOT NULL,
    image_id Int NOT NULL,
    comment varchar(50) NOT NULL,
    comment_time varchar(200) NOT NULL DEFAULT CURRENT_TIMESTAMP
);