sqlplus icat42user/myicat42userpasswd@localhost <</eof
INSERT INTO PASSWD (USERNAME, ENCODEDPASSWORD)
               VALUES ('icat42', 'icat42passwd');
exit
/eof

