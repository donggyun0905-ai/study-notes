CREATE TABLE tblMessage (
    num INT AUTO_INCREMENT PRIMARY KEY,         
    sender_id VARCHAR(20) NOT NULL,             
    receiver_id VARCHAR(20) NOT NULL,           
    content TEXT NOT NULL,                     
    send_date DATETIME DEFAULT CURRENT_TIMESTAMP);