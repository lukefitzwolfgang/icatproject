export user='username test'
export pass='password elettratest'
export authenticator=db
credentials="$user $pass"
export icat="https://icat-elettra.grid.elettra.trieste.it:8443"

export user='username alistair.mills@btinternet.com'
export pass='password 711978200'
export authenticator=db
credentials="$user $pass"
export icat="http://www.icatproject.org:5080"

export user='username alistair.mills@btinternet.com'
export pass='password 711978200'
export authenticator=db
credentials="$user $pass"
export icat="https://wwws.esrf.fr/icat"


./search.py $icat $authenticator $credentials Datafile
