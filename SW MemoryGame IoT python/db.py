import sqlite3

NSLOTS=10

def create_tables(dbname):
    db=sqlite3.connect(dbname)
    db.execute("CREATE TABLE highScore (data INTEGER)")
    db.execute("INSERT INTO highScore VALUES(0)")
    db.commit()
    db.close()

#create_tables('highScore.db')

db=sqlite3.connect('highScore.db')
cur = db.cursor()
cur.execute("SELECT data FROM highScore")

rows = cur.fetchall()

for row in rows:
    print(row[0])

db.close()

db = sqlite3.connect('highScore.db')
cur = db.cursor()
cur.execute("UPDATE highScore SET data =" + str(5))
db.commit()
db.close()